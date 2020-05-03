
function changeThemeFun(themeName) {/* 更换主题 */
    var theme_in = "";
    if(themeName.length==0) {
        if (parent.WEB_GLOBAL_THEME.length == 0) {
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                url: WEB_GLOBAL_CTX+"/management/extmsg/getThemeValue",
                error: function () {// 请求失败处理函数
                    alert("error");
                },
                success: function (rsp) {
                    if (rsp.successful) {
                        theme_in = rsp.msg;
                        parent.WEB_GLOBAL_THEME = theme_in;
                    } else {
                        alert("error");
                    }
                }
            });
        }
    }
    else
    {
        theme_in = themeName;
        parent.WEB_GLOBAL_THEME = themeName;
    }
    if(parent.WEB_GLOBAL_THEME.length==0)//依旧无值，使用默认
    return;
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + parent.WEB_GLOBAL_THEME + '/easyui.css';
    $easyuiTheme.attr('href', href);

    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for ( var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
};
changeThemeFun(parent.WEB_GLOBAL_THEME);
