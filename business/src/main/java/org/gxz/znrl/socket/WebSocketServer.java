package org.gxz.znrl.socket;

/**
 * websocket服务端程序，处理和浏览器之间通讯
 * Created by xieyt on 14-12-09.
 */
import org.gxz.znrl.util.Constant;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.server.ServerEndpoint;

//@ServerEndpoint("/websocket")
public class WebSocketServer {
    private static final Set<Session> sessionsContainer =  new CopyOnWriteArraySet<>();

    //向所有客户端广播发送消息
    public static void broadcast(String message){
        for (Session session : sessionsContainer) {
            if (session != null && session.isOpen()) {
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (Exception e) {
                        closeSession(session);
                    }
                }
            } else {
                closeSession(session);
            }
        }
    }

    @OnOpen
    public void connectionOpen(Session session) {
        //设置空闲会话最多保持1小时
        session.setMaxIdleTimeout(Constant.KEEP_IDLE_WS_SESSION_TIMEOUT);
        //当前ws连接的会话存放进容器
        sessionsContainer.add(session);
        System.out.println("Client connected");
        System.out.println("session.getId():"+session.getId());
        System.out.println("session.isOpen():"+session.isOpen());

    }

    @OnClose
    public void connectionClose(Session session) {
        System.out.println("Connection closed");
        closeSession(session);
    }

    @OnError
    public void connectionError(Session session, Throwable thr) {
        System.out.println("Connection exception");
        closeSession(session);
    }

    @OnMessage
    public void handleReceivedMessage(String message, Session session)
            throws IOException, InterruptedException {
        //打印接收到的消息
        System.out.println("Received: " + message);
    }

    private static void closeSession(Session session){
        sessionsContainer.remove(session);
        if (session != null) {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
