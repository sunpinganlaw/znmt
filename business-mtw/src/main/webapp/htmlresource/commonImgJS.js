var wsUri = 'ws://192.200.200.2:8080/intf/ws';
//var wsUri = 'ws://localhost:8081/intf/ws';

function listen(webSocket, pageFlag) {
    webSocket.onmessage = function(evt) {
        businessProcess(evt);
    }

    webSocket.onopen = function(evt) {
        onOpen(evt)
    }

    webSocket.onclose = function(evt) {
        onClose(evt)
    }

    webSocket.onerror = function(evt) {
        onError(evt)
    }
}


function onOpen(evt) {

}

function onClose(evt) {
    // alert("与接收设备数据服务器连接断开，请尝试重新打开页面进行连接或联系管理员。");
    return false;
}

function onError(evt) {
    // alert("与接收设备数据服务器连接出现异常，请尝试重新打开页面进行连接或联系管理员。");
    return false;
}

function doSend(message) {
    // writeToScreen("SENT: " + message);  websocket.send(message);
}

//修改图片
function changeImg(htmlObj, clazzName){
    document.getElementById(htmlObj).className = clazzName;
}

//写文字内容，如车牌，毛重净重
function writeText(htmlObj, text){
    document.getElementById(htmlObj).innerHTML = text;
}

function pageX (elem){
    return elem.offsetLeft + (elem.offsetParent ? arguments.callee(elem.offsetParent) : 0)
}
function pageY (elem){
    return elem.offsetTop + (elem.offsetParent ? arguments.callee(elem.offsetParent) : 0)
}


function changeBColor(htmlObj, colr){
    var cl;
    if (colr == "white") {
        cl = "#FFF";
    } else if (colr == "red"){
        cl = "#FF4500";
    } else if (colr == "yellow"){
        cl = "#FFFF00";
    } else {
        cl = "#FFF";//默认白色
    }

    document.getElementById(htmlObj).style.background = colr;
}
