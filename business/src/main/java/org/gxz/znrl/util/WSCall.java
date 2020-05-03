package org.gxz.znrl.util;


import org.apache.axiom.om.*;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.gxz.znrl.entity.BizLogEntity;
import org.gxz.znrl.service.IToolService;
import org.gxz.znrl.shiro.ShiroUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.xml.namespace.QName;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by xieyt on 15-8-24.
 * webservice客户端调用代码（RPC方式）
 */
public class WSCall {
    private static final Logger LOG = LoggerFactory.getLogger(WSCall.class);

    //soapAction，inputParm 保持和谢的风格统一，所以假如这两个个参数，从constant_data_config读取
    public static String invoke(String url, String method, String qName,String soapAction,String inputParm, String sendString) {
        String respXml = null;
        BizLogEntity logEntity = new BizLogEntity();
        logEntity.setInParam(sendString);
        long startTime = System.currentTimeMillis();
        logEntity.setBizId("0");
        logEntity.setServiceName("ygctCicsData");
        logEntity.setTransId("0");
        logEntity.setSysId("1");

        ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
        String opCode = "";
        if(shiroUser == null){
            opCode = Constant.getConstVal("YG02MM_AUTO_UPLOAD_STAFF");
        }else{
            opCode = shiroUser.getUser().getId().toString();
        }
        logEntity.setReqUserId(opCode);

        try {
            // 使用RPC方式调用WebService
            RPCServiceClient serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            // 指定调用WebService的URL
            options.setTo(new EndpointReference(url));

            //modify by zhangf 20150922 begin
            //确定调用方法
            options.setAction(soapAction);
            serviceClient.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            OMNamespace omNs = fac.createOMNamespace(qName, "");
            OMElement data = fac.createOMElement(method, omNs);
            // 对应参数的节点
            String[] strs = inputParm.split(",");
            // 参数值
            String[] val = new String[] { sendString };
            for (int i = 0; i < strs.length; i++) {
                OMElement inner = fac.createOMElement(strs[i], omNs);
                inner.setText(val[i]);
                data.addChild(inner);
            }
            OMElement respObj = serviceClient.sendReceive(data);
            //modify by zhangf 20150922 end

            LOG.debug("返回报文为：" + respObj);

            if (respObj == null) {
                logEntity.setResCode("1");
                logEntity.setResMsg("接口返回失败");
                throw new RuntimeException("调用远程ws接口未获取到响应结果信息");
            } else {
                Iterator iterator = respObj.getChildElements();
                while (iterator.hasNext()) {
                    OMNode omNode = (OMNode) iterator.next();
                    OMElement omElement = (OMElement) omNode;
                    respXml = omElement.getText();
                }
                logEntity.setResCode("0");
                logEntity.setResMsg("接口调用正常返回，详情请查看报文");
            }
        } catch (Exception e) {
            LOG.error("ws invoke err!"+e.getMessage());
            e.printStackTrace();
            logEntity.setResCode("1");
            logEntity.setResMsg("接口调用异常："+e.getMessage());
            throw new RuntimeException("调用远程ws接口发生异常(1)："+e.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();
            logEntity.setCostTime(new Long(endTime - startTime).toString());
            logEntity.setOutParam(respXml);
            LogUtil.recordLog(logEntity);
        }
        return respXml;
    }


    public static void main(String[] args){
        //RPC调用方法
//        String sendStr = "{\"base\":{\"sysId\" : \"10\",\"userId\" : \"100000013324\", \"service\" : \"qryReduceTonList\", \"signature\" : \"e93b86b37efb64d424a97ce5f1cbf11b\", \"randomNum\" : \"13579046\", \"timestamp\" : \"1431067080349\" ,\"bizId\" : \"\"}, \"param\":{\"beginRowIndex\":\"1\",\"endRowIndex\":\"10\" }}";
//        String newPointLocalAddr = "http://112.74.96.112:8080/znrlWS/services/znrlService";
//        String respStr = WSCall.invoke(newPointLocalAddr, "qryReduceTonList", "http://ws.intf.znrl.gxz.org", "","",sendStr);
//        System.out.println("callResp1:" + respStr);

        String zhuangtaiChaxun =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                        "<YGCT >" +
                        "  <HEAD>" +
                        "    <VERSION>1.0</VERSION>" +
                        "    <SRC>3</SRC>" +
                        "    <DES>4</DES>" +
                        "    <MsgNo>31198</MsgNo>" +
                        "    <MsgId>123</MsgId>" +
                        "    <MsgRef />" +
                        "    <TransDate>20150928121428</TransDate>" +
                        "    <Reserve />" +
                        "  </HEAD>" +
                        "  <MSG>" +
                        "    <YangPingChaXun31198>" +
                        "      <ID>123</ID>" +
                        "      <XUHAO>123</XUHAO>" +
                        "      <YPBM>2015037424</YPBM>" +
                        "    </YangPingChaXun31198>" +
                        "  </MSG>" +
                        "</YGCT>";


        String ygAddrHPN = "http://10.46.1.49:50000/MCService/Metadata";
        String qname = "http://tempuri.org/";
        String soapAction = "http://tempuri.org/IService1/ygctCicsData";
        String respStr = WSCall.invoke(ygAddrHPN, "ygctCicsData", qname, soapAction,"inXml", zhuangtaiChaxun);
        System.out.println("callResp1:" + respStr);
    }

}
