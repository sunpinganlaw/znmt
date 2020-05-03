/**
 * Created by admin on 2014/7/3.
 */
$.extend($.fn.datagrid.defaults.editors, {   datetimebox: {//datetimebox就是你要自定义editor的名称
    init: function (container, options) {
        var input = $('<input class="easyui-datetimebox"  data-options="required:true">').appendTo(container);
        return input.datetimebox({
            formatter: function (date) {
                return new Date(date).format("yyyy-MM-dd hh:mm:ss");
            }
        });
    },
    getValue: function (target) {
        return $(target).parent().find('input.combo-value').val();

    },
    setValue: function (target, value) {
        $(target).datetimebox("setValue", value);
    },
    resize: function (target, width) {
        var input = $(target);
        if ($.boxModel == true) {
            input.width(width - (input.outerWidth() - input.width()));
        } else {
            input.width(width);
        }
    }
}
});

/*console.log($("#print_form").serializeArray());
console.log($("#print_form").serializeJson());*/
(function($){
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    };
})(jQuery);

var win_print;
var WEB_PRINT_HEAD;
var WEB_PRINT_COLUMNS;
var WEB_PRINT_URL;
var WEB_PRINT_PARAM;

$.fn.pagination.defaults = $.extend({}, $.fn.pagination.defaults, {
    loading: true,
    buttons:[{
        iconCls:'icon-print',
        text: '',
        handler:function(){
            $('.datagrid-view').printThis();
        }
    },'-',{
        iconCls:'icon-sys',
        text: '',
        handler:function(){


            var head_obj = $(".print th");
            var head_array = new Array();
            head_obj.each(//使用数组的循环函数 循环这个数组
                function () {
                    var obj = $(this);//循环中的每一个元素
                    head_array.push(obj.text())
                }
            )
            if(head_array.length==0)
            {
                showMsg('消息',"无class为print的表格中文列名");
                return;
            }
            WEB_PRINT_HEAD = head_array.join("|");

            var colunms_array = new Array();
            var colunms_ojb = $(".datagrid-header-row td");

            colunms_ojb.each(//使用数组的循环函数 循环这个数组
                function () {
                    var obj = $(this);//循环中的每一个元素
                    if(obj.attr("field")!=undefined)
                    colunms_array.push(obj.attr("field"));
                }
            )
            if(colunms_array.length==0)
            {
                showMsg('消息',"无效列值");
                return;
            }
            //alert(head_array.join("|"));
            //alert(colunms_array.join("|"));
            WEB_PRINT_COLUMNS = colunms_array.join("|");

            if($("#print_url")==undefined||$("#print_url").val()=="")
            {
                showMsg('消息',"无效数据请求链接");
                return;
            }
            WEB_PRINT_URL = $("#print_url").val();

            win_print = $('#win').window({
                modal: true,
                title: '导出及打印',
                height: 400,
                width: 600,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                href: WEB_GLOBAL_CTX+'/core/tool/print',
                onClose: function () {
                    // win_print.window('destroy',true);
                }
            });

/*
            $.ajax({
                async: false,
                cache: false,
                data: {'fp_head': head,'fp_columns':columns,'fp_url':url},
                type: 'POST',
                target:"_blank",
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                url: WEB_GLOBAL_CTX+"/core/tool/printX",
                error: function () {// 请求失败处理函数
                    showMsg('消息',"提交失败");
                },
                success: function (rsp) {
                    alert(rsp);
             }
            });*/
        }
    }]
});

