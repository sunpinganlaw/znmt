package org.gxz.znrl.util;

import org.dom4j.*;

import java.util.Map;

/**
 * Created by xieyt on 15-8-20.
 */
public class XmlHelper {

    private static Element buildNode(String nodeName, Object value){
        return DocumentHelper.createElement(nodeName).addText(value == null ? "" :value.toString());
    }

    public String buildYGCabinetOpXml(Map<String, Object> map){
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement("root");
        document.add(root);
        Element pages = DocumentHelper.createElement("pages");
        root.add(pages);
        pages.add(buildNode("nodeName1", "node1val1"));
        pages.add(buildNode("nodeName2", "node2val2"));
        pages.add(buildNode("nodeName3", null));
        pages.add(buildNode("nodeName4", ""));

        return document.asXML();
    }


    //<?xml version="1.0" encoding="UTF-8"?><root><pages><nodeName1>node1val1</nodeName1><nodeName2>node2val2</nodeName2><nodeName3></nodeName3><nodeName4></nodeName4></pages></root>

    public Object parseYGCabinetOpXml(String xml){
        Document docIntf = null;
        try {
            docIntf = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element intfRoot = docIntf.getRootElement();
        String busCode = intfRoot.selectSingleNode("/ContractRoot/TcpCont/BusCode").getText();
        String transId = intfRoot.selectSingleNode("/ContractRoot/TcpCont/TransactionID").getText();
        String dstOrgID = intfRoot.selectSingleNode("/ContractRoot/TcpCont/DstOrgID").getText();
        Node node = intfRoot.selectSingleNode("/ContractRoot/TcpCont/DstOrgID");
        String s = node.getText();

        return null;
    }

    public static void main(String[] args) {
        XmlHelper xmlHelper = new XmlHelper();
        String s = xmlHelper.buildYGCabinetOpXml(null);
        System.out.println(s);
    }

}
