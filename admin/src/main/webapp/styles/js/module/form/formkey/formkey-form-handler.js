/**
 * 动态Form办理功能
 */
function claim() {
    var selectedRow = $('#tt').datagrid('getSelected');
    if (selectedRow) {
        var task_id = selectedRow.id;
        $.post(WEB_GLOBAL_CTX + '/leave/form/formkey/task/claim/' + task_id, function (rsp) {
            if (rsp.successful) {
                $.messager.alert("提示", "签收成功!");
                $('#tt').datagrid("reload");
            } else {
                $.messager.alert("提示", "签收失败!");
            }
        });
    }    else
    {
        $.messager.alert("提示", "请选择一行");
        return;
    }
    selectedRow = null;
}
/**
 * 打开办理对话框
 */
function handle() {
    var selectedRow = $('#tt').datagrid('getSelected');
    if (selectedRow) {
        var taskId = selectedRow.id;
        var tkey = selectedRow.taskDefinitionKey;
        var tname = selectedRow.name;

        $('#w').window({
            title: '启动流程[' + tname + ']'+tkey,
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
        $.get(WEB_GLOBAL_CTX + '/leave/form/formkey/get-form/task/' + taskId, function (form) {
            // 获取的form是字符行，html格式直接显示在对话框内就可以了，然后用form包裹起来
            $("#formContent").html(form).wrap("<form class='formkey-form' method='post' />");
            var $form = $('.formkey-form');
            // 设置表单action
            $form.attr('action', WEB_GLOBAL_CTX + '/leave/form/formkey/task/complete/' + taskId);
            $.parser.parse('#formContent');//重新渲染
        });

    }    else
    {
        $.messager.alert("提示", "请选择一行");
        return;
    }

    selectedRow = null;
}
function sendStartupRequest() {
    var selectedRow = $('#tt').datagrid('getSelected');
    if (selectedRow) {
        var params = $(".formkey-form").serialize();

        if (! $(".formkey-form").form('validate'))  return false;
        var taskId = selectedRow.id;
        $.post(WEB_GLOBAL_CTX + '/leave/form/formkey/task/complete/' + taskId,params, function (rsp) {
            if (rsp.successful) {
                $.messager.alert("提示", "办理成功!");
                $('#tt').datagrid("reload");
                $('#w').window('close');
            } else {
                $.messager.alert("提示", "办理失败!");
            }
        });
    }
}

