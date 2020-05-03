/**
 * Created by admin on 2014/7/2.
 */
$.extend($.fn.validatebox.defaults.rules, {
    alphabet:{
        validator:function(value,param){
            if (value){
                return /^[a-zA-Z]*$/.test(value) && value.length >= param[0];
            } else {
                return true;
            }
        },
        message:'请输入至少{0}位字母.'
    },
    minNumeric:{
        validator:function(value,param){
            if (value){
                return /^[0-9]*(\.[0-9]+)?$/.test(value) && value.length >= param[0];
            } else {
                return true;
            }
        },
        message:'请输入至少{0}位数字.'
    },
    maxNumeric:{
        validator:function(value,param){
            if (value){
                return /^[0-9]*(\.[0-9]+)?$/.test(value) && value.length <= param[0];
            } else {
                return true;
            }
        },
        message:'请输入最多{0}位数字.'
    },
    alphabetNum:{
        validator:function(value,param){
            if (value){
                return /^([a-zA-Z0-9])*$/.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入字母和数字.'
    },
    alpha:{
        validator:function(value,param){
            if (value){
                return /^[a-zA-Z\u00A1-\uFFFF]*$/.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入字母.'
    },
    alphanum:{
        validator:function(value,param){
            if (value){
                return /^([a-zA-Z\u00A1-\uFFFF0-9])*$/.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入字母和数字.'
    },
    positive_int:{
        validator:function(value,param){
            if (value){
                return /^[0-9]*[1-9][0-9]*$/.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入正整数.'
    },
    between_int:{
        validator:function(value,param){
            if (value){
                return value >= param[0] && param[1] >= value && /^[0-9]*[1-9][0-9]*$/.test(value);;
            } else {
                return true;
            }
        },
        message:'只能输入区间{0}~{1}的正整数.'
    },
    numeric:{
        validator:function(value,param){
            if (value){
                return /^[0-9]*(\.[0-9]+)?$/.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入数字.'
    },
    chinese:{
        validator:function(value,param){
            if (value){
                return /[^\u4E00-\u9FA5]/g.test(value);
            } else {
                return true;
            }
        },
        message:'只能输入中文'
    },
    minLength: {
        validator: function(value, param){   //value 为需要校验的输入框的值 , param为使用此规则时存入的参数
            return value.length >= param[0];
        },
        message: '请输入最小{0}位字符.'
    },
    maxLength: {
        validator: function(value, param){
            return param[0] >= value.length;
        },
        message: '请输入最大{0}位字符.'
    },
    length: {
        validator: function(value, param){
            return value.length >= param[0] && param[1] >= value.length;
        },
        message: '请输入{0}-{1}位字符.'
    },
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '内容不相同.'
    },
    web : {
        validator: function(value){
            return /^(http[s]{0,1}|ftp):\/\//i.test($.trim(value));
        },
        message: '网址格式错误.例如http://www.163.com'
    },
    mobile : {
        validator: function(value){
            return /^1[0-9]{10}$/i.test($.trim(value));
        },
        message: '手机号码格式错误.'
    },
    date : {
        validator: function(value){
            return /^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$/i.test($.trim(value));
        },
        message: '曰期格式错误,如2014-09-11.'
    },
    email : {
        validator: function(value){
            return /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/i.test($.trim(value));
        },
        message: '电子邮箱格式错误.'
    },
    faxno : {// 验证传真
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '传真号码不正确'
    },
    zip : {// 验证邮政编码
        validator : function(value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message : '邮政编码格式不正确'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /^\d{1,3}\.{1}\d{1,3}\.{1}\d{1,3}\.{1}\d{1,3}$/i.test(value);
        },
        message : 'IP地址格式不正确'
    },
    //<input type="text" validtype="remote['checkname.aspx','name']" invalidMessage="用户名已存在"/>
    exist : {
        validator: function(value,param){
            //param 0 url
            //param 1 entity column name
             var d_name = param[1];
            var testJson = '{ "'+d_name+'": "'+value+'" }';
            var datas = JSON.parse(testJson);
            $.ajax({
                async: false,
                cache: false,
                type: 'POST',
                data:datas,
                url: WEB_GLOBAL_CTX+param[0],
                error: function () {// 请求失败处理函数
                    showMsg("提示","参数无效")
                },
                success: function (rsp) {
                    if(rsp.state!="0") {
                        return true;
                    }
                    else
                    return false;
                }
            });
        },
        message: '无效数据.'
    }

});

