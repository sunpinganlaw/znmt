(function ($)
{
    //全局系统对象
    window['jqueryUtil'] = {};
    //修改ajax默认设置

    /**
     * @author 孙宇
     *
     * @requires jQuery,EasyUI
     *
     * 扩展treegrid，使其支持平滑数据格式
     */
    $.fn.treegrid.defaults.loadFilter = function(data, parentId) {
        var opt = $(this).data().treegrid.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };

    /**
     * @author 夏悸
     *
     * @requires jQuery,EasyUI
     *
     * 扩展tree，使其支持平滑数据格式
     */
    $.fn.tree.defaults.loadFilter = function(data, parent) {
        var opt = $(this).data().tree.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };
    /**
     * @author sy
     *
     * @requires jQuery,EasyUI
     *
     * 扩展combotree，使其支持平滑数据格式
     */
    $.fn.combotree.defaults.loadFilter = function(data, parent) {
        var opt = $(this).data().tree.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };


    //高级查询
    jqueryUtil.gradeSearch=function($dg,formId,url) {
        $("<div/>").dialog({
            href : url,
            modal : true,
            title : '高级查询',
            top : 120,
            width : 480,
            buttons : [ {
                text : '增加一行',
                iconCls : 'icon-add',
                handler : function() {
                    var currObj = $(this).closest('.panel').find('table');
                    currObj.find('tr:last').clone().appendTo(currObj);
                    currObj.find('tr:last a').show();
                }
            }, {
                text : '确定',
                iconCls : 'icon-ok',
                handler : function() {
                    $dg.datagrid('reload',jqueryUtil.serializeObject($(formId)));
                }
            }, {
                text : '取消',
                iconCls : 'icon-cancel',
                handler : function() {
                    $(this).closest('.window-body').dialog('destroy');
                }
            } ],
            onClose : function() {
                $(this).dialog('destroy');
            }
        });
    };
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };


    // 将param中值为空的节点剔除。暂不支持嵌套
    jqueryUtil.deleteEmptyNode = function (param) {
        var retObj = {};
        for (var key in param) {
            var val = param[key];
            if (undefined == val || null == val || "" == val || "''" == val) {
                null;
            } else {
                retObj[key] = val;
            }
        }
        return retObj;
    }

    // 取本月1号日期；YYYY-MM-DD
    jqueryUtil.getMonthFirstDay = function () {
        var date = new Date();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-01';
    }

    // 取本年度1号日期；YYYY-MM-DD
    jqueryUtil.getYearFirstDay = function () {
        var date = new Date();
        return date.getFullYear() + '-01-01';
    }

    // 取当前日期；YYYY-MM-DD
    jqueryUtil.getToday = function () {
        var date = new Date();
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1);
        return date.getFullYear() + '-' + month + '-' + day;
    }

    // 判断变量是否为空
    jqueryUtil.isEmpty = function (param) {
        if (undefined == param || null == param || "" == param) {
            return true;
        } else {
            return false;
        }
    }

    // 获取Url请求携带的参数
    jqueryUtil.getRequestParam = function () {
        var url = location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    }

    /**
     * 适应页面宽度
     */
    jqueryUtil.expand2PageWidth = function () {
        var iframe = $("iframe");
        if (!iframe) {
        }
        if (iframe.get(0).attachEvent) {
            iframe.get(0).attachEvent("onload", function () {
                var tabParent = iframe.contents().find(".jrPage").parent().parent();
                tabParent.remove("td:first");
                tabParent.remove("td:last");
                tabParent.find("td").find(".jrPage").attr("width", "100%");
            });
        } else {
            iframe.get(0).onload = function () {
                var tabParent = iframe.contents().find(".jrPage").parent().parent();
                tabParent.find("td:first").remove();
                tabParent.find("td:last").remove()
                tabParent.find("td").find(".jrPage").css("width", "100%");
            };
        }
    }

    /**
     * 锁定表头
     */
    jqueryUtil.lockHeader = function () {
        var iframe = $("iframe");
        if (!iframe) {
            console.log('锁定表头失败');
        }
        addLoadEvent(lockHeaderOp);
    }
    function addLoadEvent(func) {
        var iframe = $("iframe");
        var oldonload = iframe.get(0).onload;
        if (typeof iframe.get(0).onload != "function") {
            iframe.get(0).onload = func;
        }
        else {
            iframe.get(0).onload = function () {
                if (oldonload) {
                    oldonload();
                }
                func();
            }
        }
    }

    function lockHeaderOp(iframe) {
        var iframe = $("iframe");
        // 隐藏横向滚动条
        var tabHtml = iframe.contents().find("html");
        tabHtml.css("overflow-x", "hidden");
        // 获取表头和表体
        var jrTable = iframe.contents().find(".jrPage");
        var thead = iframe.contents().find(".jrPage thead:first-child");
        var tbody = iframe.contents().find(".jrPage tbody:last-child");
        if (thead.length > 0 && tbody.length > 0) {
            tbody.prepend(thead.find("tr:last"));
        }
        if (jrTable.length > 0 && thead.length > 0) {
            jrTable.css("margin-top", thead.height() - 8 - 1);
            thead.css("top", "0px");
            thead.css("z-index", "99");
            thead.css("position", "fixed");
            thead.css("width", tbody.width() + "px");
            // 如果iframe的宽度小于tbody；则设置iframe的宽度=tbody+20
            if (iframe.width() < tbody.width()) {
                iframe.css("width", tbody.width() + 20 + "px"); //设置iframe的宽度略大于表格内容
            }
            thead.css("background-color", "white");
        }
    }

    /**
     * 修改报表HTML。设置待填报的单元格。
     */
    jqueryUtil.modifyReport = function () {
        var iframe = $("iframe");
        if (!iframe) {
        }
        if (iframe.get(0).attachEvent) {
            iframe.get(0).attachEvent("onload", function () {
                var tabSpans = iframe.contents().find("span");
                if (tabSpans) {
                    $.each(tabSpans, function (index, item) {
                        var spanHtml = item.outerHTML;
                        var spanContent = item.innerHTML;
                        // 包含需要填报的单元格
                        if (spanContent && spanContent.indexOf('text.') > -1) {
                            var varName = spanContent.substring(spanContent.indexOf('text.') + 5, spanContent.indexOf('('));
                            var varVal = spanContent.substring(spanContent.indexOf('(') + 1, spanContent.indexOf(')'));
                            var newSpanHtml = spanHtml.replace('<span', '<input type="text" name="' + varName + '" value="' + varVal + '" ').replace('</span>', '</input>').replace(spanContent, '').replace("style=\"","style=\"width:95%;background-color:#fff3f3;text-align:center; ");
                            newSpanHtml = newSpanHtml.replace(/\"/g,"\'");
                            $(item).replaceWith(newSpanHtml);
                        }
                    });
                }
            });
        } else {
            iframe.get(0).onload = function () {
                var tabSpans = iframe.contents().find("span");
                if (tabSpans) {
                    $.each(tabSpans, function (index, item) {
                        var spanHtml = item.outerHTML;
                        var spanContent = item.innerHTML;
                        // 包含需要填报的单元格
                        if (spanContent && spanContent.indexOf('text.') > -1) {
                            var varName = spanContent.substring(spanContent.indexOf('text.') + 5, spanContent.indexOf('('));
                            var varVal = spanContent.substring(spanContent.indexOf('(') + 1, spanContent.indexOf(')'));
                            var newSpanHtml = spanHtml.replace('<span', '<input type="text" name="' + varName + '" value="' + varVal + '" ').replace('</span>', '</input>').replace(spanContent, '').replace("style=\"","style=\"width:95%;background-color:#fff3f3;text-align:center; ");
                            newSpanHtml = newSpanHtml.replace(/\"/g,"\'");
                            $(item).replaceWith(newSpanHtml);
                        }
                    });
                }
            };
        }
    }
})(jQuery);