package fx.github.greys.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/socket/{appId}")
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("appId") String appId) {
        log.info("有新连接加入：{},appId:{}", session.getId(), appId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有一连接关闭：{}", session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("appId") String appId) {
        log.info("服务端收到客户端[{}]的消息:{},appId:{}", session.getId(), message, appId);
        this.sendMessage("Hello, " + message, session, appId);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误.", error);
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession, String appId) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败.", e);
        }
    }
}
