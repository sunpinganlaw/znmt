/**
 * @author mrdoob / http://mrdoob.com/
 */

THREE.PointerLockControls = function (camera,domElement) {

	var scope = this;

	camera.rotation.set( 0, 0, 0 );
	camera.position.set(0,0,0);

	var pitchObject = new THREE.Object3D();
	pitchObject.add( camera );

	var yawObject = new THREE.Object3D();
	//yawObject.position.y = 0;
	yawObject.add( pitchObject );

	var PI_2 = Math.PI / 2;

	this.rotate_enable = false;

	var onMouseDown = function(event){
		if(event.button == 0){
			scope.rotate_enable = true;
		}
	}

	var onMouseUp = function(event){
		if(event.button == 0){
			scope.rotate_enable = false;
		}
	}

	var onMouseMove = function ( event ) {

		if ( scope.enabled === false ) return;

		if(scope.rotate_enable === false ) return;

		var movementX = event.movementX || event.mozMovementX || event.webkitMovementX || 0;
		var movementY = event.movementY || event.mozMovementY || event.webkitMovementY || 0;

		yawObject.rotation.y -= movementX * 0.005;
		pitchObject.rotation.x -= movementY * 0.005;

		pitchObject.rotation.x = Math.max( - PI_2, Math.min( PI_2, pitchObject.rotation.x ) );
	};

	this.dispose = function() {
		domElement.removeEventListener( 'mousemove', onMouseMove, false );
		domElement.removeEventListener( 'mousedown', onMouseDown, false );
		domElement.removeEventListener( 'mouseup', onMouseUp, false );
		pitchObject.rotation.set(0,0,0);
		pitchObject.remove(camera);
	};

	domElement.addEventListener( 'mousemove', onMouseMove, false );
	domElement.addEventListener( 'mousedown', onMouseDown, false );
	domElement.addEventListener( 'mouseup', onMouseUp, false );

	this.enabled = false;

	this.getObject = function () {
		return yawObject;
	};

	this.enablecontrol = function(station){
		scope.rotate_enable = false;
		scope.enabled = station;
	}

	this.getDirection = function() {
		// assumes the camera itself is not rotated
		var direction = new THREE.Vector3( 0, 0, - 1 );
		var rotation = new THREE.Euler( 0, 0, 0, "YXZ" );

		return function( v ) {
			rotation.set( pitchObject.rotation.x, yawObject.rotation.y, 0 );

			v.copy( direction ).applyEuler( rotation );

			return v;
		};
	}();

};
