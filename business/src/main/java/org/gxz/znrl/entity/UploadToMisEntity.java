package org.gxz.znrl.entity;

/**
 * Created by HXL on 2016/5/12.
 */
public class UploadToMisEntity {

    //以json字符串的方式传入存储过程
    public String jsonStr;
    public String opCode;

    //返回操作结果
    public String resCode;
    public String resMsg;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
