/**
 * Created by Rubbish on 2015/9/15.
 */

var loadJsonConfigTag;
var flash = $("#index");
//备注，下面这个变量的值，会在具体的flash的html页面中,被 wsUri = "${wsUri}"代码进行了重新赋值，取的是数据库中的值 ;
var wsUri = 'ws://192.200.200.1:8080/intf/ws';
//var wsUri = 'ws://10.46.3.26:8080/intf/ws';
var fileNo = "1";
var ip = window.location.hostname;
var port = window.location.port;
var fileName = "json";


//alert("href:"+window.location.href);
//alert("protocol:"+window.location.protocol);
//alert("host:"+window.location.host);
//alert("port:"+window.location.port);
//alert("pathname:"+window.location.pathname);
//alert("hostname:"+window.location.hostname);


function listen(webSocket, pageFlag) {
    webSocket.onmessage = function (evt) {
        businessProcess(evt);
    }

    webSocket.onopen = function (evt) {
        onOpen(evt)
    }

    webSocket.onclose = function (evt) {
        onClose(evt)
    }

    webSocket.onerror = function (evt) {
        onError(evt)
    }
}

function onOpen(evt) {
    socketOnOpen();
}

function onClose(evt) {
    socketOnClose();
    return false;
}

function onError(evt) {
    socketOnError();
    return false;
}

function doSend(message) {
    // writeToScreen("SENT: " + message);  websocket.send(message);
}

function checkLoaded(flash) {
    try {
        return Math.floor(thisMovie("index").PercentLoaded()) == 100
    } catch (e) {
        //alert(e);
        return false;
    }
}

function init() {
    listen(new WebSocket(wsUri));
}
function businessProcess(event) {
     thisMovie("index").setFlashBySoketJson(JSON.parse(event.data));
}
function loadJsonConfig(jsonPath) {
    var jsonFile =  Number(fileNo) > 1 ? (fileName+ (Number(fileNo) - 1) + ".txt"):fileName+".txt";
    var flashTile =  "";
    if (Number(fileNo) > 0) {
        switch (Number(fileNo))
        {
        case 1:
            flashTile = "一";
            break;
        case 2:
            flashTile = "二";
            break;
        case 3:
            flashTile = "三";
            break;
        case 4:
            flashTile = "四";
            break;
        default:
            flashTile = "";
        }
    }
    loadJsonConfigTag = thisMovie("index").loadJsonConfig(WEB_GLOBAL_CTX, jsonFile, jsonPath, ip, port);
    thisMovie("index").setFlashTitle(flashTile);
}
function loadJsonConfigFinish(v) {
    if (loadJsonConfigTag == "SUCCESS" && v == "SUCCESS") {
        successLoadFlashJSON();
        init();
    } else {
        failedLoadFlashJSON(v);
    }
    finishLoadFlashJSON();
}
//thisMoivie 参数：是swf文件 的id
function thisMovie(movieName) {
    if (navigator.appName.indexOf("Microsoft") != -1) {
        return window[movieName]
    }
    else {
        return document[movieName];
    }
}
//提供flash子页面调用 start
function startLoadFlash(jsonPath){
    startLoadFlashJSON();
    var intervalID = setInterval(function () {
        if (checkLoaded(flash)) {
            clearInterval(intervalID);
            intervalID = null;
            loadJsonConfig(jsonPath);
        }
    }, 600);
}

function startLoadFlashJSON() {
    $.messager.progress()
}

function finishLoadFlashJSON() {
    $.messager.progress('close');
}

function successLoadFlashJSON() {
    $.messager.show({
        title: '系统消息',
        msg: '加载FLASH文件成功！！！',
        timeout: 5000,
        showType: 'slide'
    });
}

function failedLoadFlashJSON(e) {
    $.messager.alert('系统配置错误', '加载FLASH文件失败！！！'+e, 'error');
}

function socketOnClose() {
    $.messager.show({
        title: '系统配置错误',
        msg: '与接收设备数据服务器('+wsUri+')连接断开，请尝试重新打开页面进行连接或联系管理员。',
        timeout: 5000,
        showType: 'slide'
    });
}

function socketOnError() {
    $.messager.show({
        title: '系统配置错误',
        msg: '与接收设备数据服务器('+wsUri+')连接出现异常，请尝试重新打开页面进行连接或联系管理员。',
        timeout: 5000,
        showType: 'slide'
    });
}

function socketOnOpen() {
    $.messager.show({
        title: '系统配置提示',
        msg: '与接收设备数据服务器连接完成。',
        timeout: 5000,
        showType: 'slide'
    });
}
//提供flash子页面调用 end


