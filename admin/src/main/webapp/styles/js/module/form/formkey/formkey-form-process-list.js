/**
 * 打开启动流程
 */

function showStartupProcessDialog() {
    var selectedRow = $('#tt').datagrid('getSelected');
    if (selectedRow) {
        var process_name = selectedRow.name;
        var process_id = selectedRow.id;

        $('#w').window({
            title: '启动流程[' + process_name + ']',
            modal: true,
            closed: true,
            height: $(this).height() - 300,
            width: $(this).width() / 2 - 100,
            iconCls: 'icon-save'
        });
        $('#w').window('open');
        $('#w').window('move',{ left:document.body.clientWidth/2-150,top:document.body.scrollTop+150});

        /**
         * 读取流程启动表单
         */
        $.get(WEB_GLOBAL_CTX + '/leave/form/formkey/get-form/start/' + process_id, function (form) {
            // 获取的form是字符行，html格式直接显示在对话框内就可以了，然后用form包裹起来
            $("#formContent").html(form).wrap("<form class='formkey-form' method='post' />");
            var $form = $('.formkey-form');
            // 设置表单action
            $form.attr('action', WEB_GLOBAL_CTX + '/leave/form/formkey/start-process/' + process_id);
            $.parser.parse('#formContent');//重新渲染
        });
    }
    else
    {
        $.messager.alert("提示", "请选择一行");
    }
}
/**
 * 提交表单
 * @return {[type]} [description]
 */
function sendStartupRequest() {
		$('.formkey-form').submit();
}