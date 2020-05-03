
THREE.CameraProjection = {PerspectiveCamera:0,OrthographicCamera:1}

THREE.Create = function(param){

    var origin = null;

    var scope = this;

    var config = $.extend({},param);

    this.domElement = (config.domElement !== undefined ) ? config.domElement : $(document);

    var cameraprojection = ( config.cameraprojection !== undefined ) ? config.cameraprojection :THREE.CameraProjection.PerspectiveCamera;

    var scene = null;
    var camera = null;
    var renderer = null;
    var control = null;
    var repository = null;

    var progress = 0;

    var material_map = null;

    var init = function(){
        THREE.Cache.enabled = true;

        scene = new THREE.Scene();
        if(cameraprojection == THREE.CameraProjection.PerspectiveCamera){
            var viewPort = getViewportSize();
            camera = new THREE.PerspectiveCamera(60,viewPort.width/viewPort.height, 0.1, 2000);
        }
        scene.add(camera);

        var ambient_light = new THREE.AmbientLight(0xCCCCCC,0.2);
        var direct_light_top = new THREE.DirectionalLight(0xCCCCCC,1);
        var direct_light_bottom = new THREE.DirectionalLight(0xCCCCCC,1);
        var direct_light_left = new THREE.DirectionalLight(0xCCCCCC,1);
        var direct_light_right = new THREE.DirectionalLight(0xCCCCCC,1);

        direct_light_top.position.set(0,1000,-1000)
        direct_light_bottom.position.set(0,1000,1000)
        direct_light_left.position.set(-1000,1000,0);
        direct_light_right.position.set(1000,1000,0);

        direct_light_top.lookAt(new THREE.Vector3(0,0,0));
        direct_light_bottom.lookAt(new THREE.Vector3(0,0,0));
        direct_light_left.lookAt(new THREE.Vector3(0,0,0));
        direct_light_right.lookAt(new THREE.Vector3(0,0,0));

        scene.add( ambient_light );
        scene.add(direct_light_top);
        scene.add(direct_light_bottom);
        scene.add(direct_light_left);
        scene.add(direct_light_right);

        /*
        scene.add(skybox( [
            'images/skybox/px.jpg', // right
            'images/skybox/nx.jpg', // left
            'images/skybox/py.jpg', // top
            'images/skybox/ny.jpg', // bottom
            'images/skybox/pz.jpg', // back
            'images/skybox/nz.jpg'  // front
        ], 2000));*/

        loader('models/coal.FBX');

        renderer = new THREE.WebGLRenderer({antialias:true, alpha: true});
        renderer.setClearAlpha(0.2);
        renderer.setPixelRatio(window.devicePixelRatio);
        scope.domElement.append(renderer.domElement);


        handOverOrbitControl();

        document.addEventListener( 'resize', resize, false );
        renderer.domElement.addEventListener("dblclick",onDoubleClick,false);
        resize();
        loop();
    }

    var handOverOrbitControl = function(){
        camera.position.set(94.02,148.62,54.15);
        camera.rotation.set(-1.11,0.49,0.75);
        orbitcontrol = new THREE.OrbitControls(camera,renderer.domElement );
        orbitcontrol.addEventListener('change',render ); // remove when using animation loop
        orbitcontrol.enableZoom = true;
        orbitcontrol.maxDistance = 600;
        orbitcontrol.minPolarAngle = 0;
        orbitcontrol.maxPolarAngle = Math.PI/180*70;
        orbitcontrol.target.set(-14.48,-32.16,-35.93);

        orbitcontrol.update();
    }

    var onDoubleClick = function(event){
        /*
        var  raycaster = new THREE.Raycaster();
        var  mouse = new THREE.Vector2();

        var x = event.clientX - scope.domElement.offset().left + $(document).scrollLeft();
        var y = event.clientY - scope.domElement.offset().top + $(document).scrollTop();
        mouse.x = (x/scope.domElement.width()) * 2 - 1;
        mouse.y = -(y/scope.domElement.height()) * 2 + 1;

        raycaster.setFromCamera(mouse, camera );
        var intersects = raycaster.intersectObjects(areaList.children,false);
        if(intersects.length!=0){
            if(config.mousedbclick){
                config.mousedbclick(intersects[0].object);
            }
        }*/
        console.log(orbitcontrol);
    }

    var skybox = function( urls, size ) {
        var skyboxCubemap = new THREE.CubeTextureLoader().load( urls );
        skyboxCubemap.format = THREE.RGBFormat;

        var skyboxShader = THREE.ShaderLib['cube'];
        skyboxShader.uniforms['tCube'].value = skyboxCubemap;

        return new THREE.Mesh(
            new THREE.BoxGeometry(size,size,size),
            new THREE.ShaderMaterial({
                fragmentShader : skyboxShader.fragmentShader, vertexShader : skyboxShader.vertexShader,
                uniforms : skyboxShader.uniforms, depthWrite : false, side : THREE.BackSide
            })
        );
    };

    var loader = function(url){
        var placeholder = new THREE.Object3D();

        var manager = new THREE.LoadingManager();
        manager.onProgress = function( item, loaded, total ) {
            console.log( item, loaded, total );
        };

        var onProgress = function ( xhr ) {
            if ( xhr.lengthComputable ) {
                var percentComplete = xhr.loaded / xhr.total * 100;
                progress = Math.round(percentComplete*0.5,2);
                $(".bar").css("width",progress+"%");
            }
        };
        var onError = function ( xhr ) { };

        var loader = new THREE.FBXLoader();
        loader.load(url, function(object){

            repository = object;

            scene.add(repository);

            if(config.loadercomplete){
                config.loadercomplete();
            }
        }, onProgress, onError);
        return placeholder;
    };

    var resize = function() {
        var viewport = getViewportSize();
        renderer.setSize(viewport.width,viewport.height);
        camera.aspect = viewport.width /viewport.height;
        camera.updateProjectionMatrix();
    };

    var render = function(){
        renderer.render(scene, camera );
    };

    var getViewportSize = function(){
        return{
            width: scope.domElement.width(), height: scope.domElement.height()
        };
    };

    var loop = function(){
        requestAnimationFrame(loop);
        if(orbitcontrol){
            orbitcontrol.update();
        }
        render();
    };

    var textureLoader = function () {
        var loader = new THREE.TextureLoader();

        loader.load('models/white.jpg',
            function ( texture ) {
                material_map = texture;
            },
            undefined,
            function () {
                console.error( 'An error happened.' );
            }
        );
    }

    init();
}

