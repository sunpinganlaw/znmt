package org.gxz.znrl.socket;


import com.mxgraph.io.graphml.mxGraphMlPort;
import org.gxz.znrl.util.Constant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class TcpClient {
    private  String IP="192.200.200.12213";
    private  int PORT=6000;
    //发送
    public String send(String reqMessage) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            if(Constant.getConstVal("FAKA_IP")!=""){
                IP = Constant.getConstVal("FAKA_IP");
            }
            if(Constant.getConstVal("FAKA_PORT")!=""){
                PORT = Integer.parseInt(Constant.getConstVal("FAKA_PORT"));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else{
                    return null;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }


    public String sendSampling(String reqMessage) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            if(Constant.getConstVal("GET_SAMPLING_CARD_IP")!=""){
                IP = Constant.getConstVal("GET_SAMPLING_CARD_IP");
            }
            if(Constant.getConstVal("GET_SAMPLING_CARD_PORT")!=""){
                PORT = Integer.parseInt(Constant.getConstVal("GET_SAMPLING_CARD_PORT"));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else{
                    return null;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }

    public String sendCarCardId(String reqMessage) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            if(Constant.getConstVal("CardID_IP")!=""){
                IP = Constant.getConstVal("CardID_IP");
            }
            if(Constant.getConstVal("CardID_PORT")!=""){
                PORT = Integer.parseInt(Constant.getConstVal("CardID_PORT"));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                System.out.println(result);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else{
                    return null;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }
    //yangfeifei-写码
    public String sendSamplingPackCode(String reqMessage) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            if(Constant.getConstVal("GET_SAMPLING_CARD_IP")!=""){
                IP = Constant.getConstVal("GET_SAMPLING_CARD_IP");
            }
            if(Constant.getConstVal("GET_SAMPLING_CARD_PORT")!=""){
                PORT = Integer.parseInt(Constant.getConstVal("GET_SAMPLING_CARD_PORT"));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                System.out.println(result);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else if(result.trim().endsWith(":EE")){
                    return result.trim();
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }

    public String getBottlePacketCode(String reqMessage,String reqName) throws Exception{
        Socket sock=null;
        BufferedOutputStream out=null;
        BufferedInputStream in=null;
        try {
            sock=new Socket();
            String desIP = reqName+"_IP";
            String desPORT = reqName+"_PORT";
            if(Constant.getConstVal(desIP)!=""){
                IP = Constant.getConstVal(desIP);
            }
            if(Constant.getConstVal(desPORT)!=""){
                PORT = Integer.parseInt(Constant.getConstVal(desPORT));
            }
            SocketAddress sockAdd=new InetSocketAddress(IP, PORT);
            sock.connect(sockAdd, 2000); //客户端设置连接建立超时时间

            out=new BufferedOutputStream(sock.getOutputStream());
            out.write(reqMessage.getBytes());
            out.flush();

            try {
                in = new BufferedInputStream(sock.getInputStream());
                if ((sock == null) || (in == null)) {
                    throw new Exception("套接口无效，无法读取数据");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte[] bts = new byte[10000];
            int totalLen = 0;
            while (true) {
                in.read(bts, totalLen, 8000);
                String result = new String(bts);
                if(result.trim().endsWith(":FF")){
                    return result.trim();
                }else{
                    return null;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                in.close();
            }
            if (out!=null){
                out.close();
            }
            if(sock!=null){
                sock.close();
            }
        }
    }

    public static void main(String[] args){
        //发送报文

        //发送
        String str="OA:FF";
        try {
            TcpClient a = new TcpClient();
            System.out.print(a.sendCarCardId(str));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}