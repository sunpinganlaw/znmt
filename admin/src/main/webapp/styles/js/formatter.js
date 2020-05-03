/**
 * Created by admin on 2014/8/12.
 */
function formatterLangString(value)
{
    if(value!=undefined&&value!=null&&value!="null") {
        if (value.length > 30) {
            return '<span title="' + titleBr(value) + '">' + value.substr(0, 30) + '</span>';
        }
        else
        {
            return value;
        }
    }
}
function titleBr(value)
{
    return value.replaceAll("<br>","\r\n");
}
String.prototype.replaceAll = function (oldStr, newStr) {
    return this.replace(new RegExp(oldStr, "gm"), newStr);
};