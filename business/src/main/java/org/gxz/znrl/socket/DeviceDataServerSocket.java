package org.gxz.znrl.socket;

import org.gxz.znrl.util.Constant;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收设备发送过来的测点数据的ServerSocket
 * Created by xieyt on 14-12-11.
 */
public class DeviceDataServerSocket extends Thread{

    /**
     * 启动socket服务端监听线程
     */
    public void run() {
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            //创建socket服务器,并开放端口
            ServerSocket server = new ServerSocket(Constant.SOCKET_LISTEN_PORT);
            String receivedString = null;
            while(true){
                System.out.println("i am waiting...");
                //监听服务器端口，一旦有数据发送过来，那么就将数据封装成socket对象,如果没有数据发送过来，线程阻塞在此
                socket = server.accept();
                //从socket中得到读取流，该流中有客户端发送过来的数据
                InputStream in = socket.getInputStream();
                //InputStreamReader将字节流转化为字符流
                //br = new BufferedReader(new InputStreamReader(in, "GBK"));//
                br = new BufferedReader(new InputStreamReader(in, "UTF-8"));//

                receivedString = br.readLine();
                System.out.println(receivedString);

                //接收到的测点数据广播所有客户端浏览器
                WebSocketServer.broadcast(receivedString);

                //响应返回客户端
                OutputStream out = socket.getOutputStream();
                pw = new PrintWriter(out);
                pw.println("服务器响应：接收成功！");
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (pw != null){
                    pw.close();
                }
                if (br != null){
                    br.close();
                }
                if (socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
