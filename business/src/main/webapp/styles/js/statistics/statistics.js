var initTrNums = 5;
var onmouseoverColorset = '#BBFFEE';
var onmouseoutColorset = '#d4e3e5';

//初始化查询日期输入等按钮，
function initHeaderTool(){
    $('#statLatType').combobox({
        panelHeight:'auto',
        valueField:'id',
        textField:'name',
        data: [
            {id: '2', name: '月报表',selected: true},
            {id: '3', name: '日报表'}
        ],
        onChange: function (n, o) {
            if (n != undefined) {
                if('2' == n){
                    $("#qryDate" ).css("display", "none");
                    $("#qryDateOnlyMonth" ).css("display", "block");
                }else if('3' == n){
                    $("#qryDate" ).css("display", "block");
                    $("#qryDateOnlyMonth" ).css("display", "none");
                }
            }
        }
    });
}

//点击查询时，从控件中获取请求参数返回
function getParam(reportTypeCd) {
    var param = new Object();
    var beginDate = null;
    var endDate = null;
    var statLatType = $("#statLatType").textbox("getValue");
    if('2' == statLatType){//月报表
        beginDate = $("#beginDateOnlyMonth").datebox("getValue")+"-01";
        endDate = $("#endDateOnlyMonth").datebox("getValue")+"-01";
    }else if('3' == statLatType){//日报表
        beginDate = $("#beginDate").datebox("getValue");
        endDate = $("#endDate").datebox("getValue");
    }

    param.search_reportTypeCd = reportTypeCd;
    if (statLatType != null && statLatType != "" && statLatType.length > 0) {
        param.search_statLatType = statLatType;
    }
    if (beginDate != null && beginDate != "" && beginDate.length > 0) {
        param.search_beginDate = beginDate;
    }
    if (endDate != null && endDate != "" && endDate.length > 0) {
        param.search_endDate = endDate;
    }
    return param;
}

//导出excel
function exportExcel(){
    var table = document.getElementById("dataTb");
    var uri = 'data:application/vnd.ms-excel;base64,';
    var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><?xml version="1.0" encoding="UTF-8" standalone="yes"?><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table border="1" style="vnd.ms-excel.numberformat:@">{table}</table></body></html>';
    var base64 = function (s) {
        return window.btoa(unescape(encodeURIComponent(s)));
    };
    var format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p){return c[p];});
    };
    var ctx = {worksheet: '', table: table.innerHTML};
    window.location.href = uri + base64(format(template, ctx));
}

//输入控件修改成只能选择月份的
function changeDBOX(componentId) {
    $('#'+componentId).datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#'+componentId).datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) { return d.getFullYear() + '-' + (d.getMonth() + 1); }
    });

    var p = $('#'+componentId).datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        span = p.find('span.calendar-text'); //显示月份层的触发控件
}

//渲染表格
function renderTable(tableId){
    if(document.getElementsByTagName){
        var table = document.getElementById(tableId);
        var rows = table.getElementsByTagName("tr");
        for(i = 0; i < rows.length; i++){
            rows[i].onmouseover = function () {
                this.style.backgroundColor=onmouseoverColorset;
            };
            rows[i].onmouseout = function () {
                this.style.backgroundColor=onmouseoutColorset;
            };
        }
    }
}