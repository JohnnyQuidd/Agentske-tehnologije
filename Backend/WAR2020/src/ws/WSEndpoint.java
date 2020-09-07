package ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Singleton
@ServerEndpoint("/ws")
@LocalBean
public class WSEndpoint {
	static List<Session> sessions = new ArrayList<>();
	
	@OnOpen
	public void onOpen(Session session) {
		if(!sessions.contains(session))
			sessions.add(session);
	}
	
	@OnMessage
	public void echoTextMessage(String message) {
		try {
			for(Session session : sessions) {
				session.getBasicRemote().sendText(message);
			}
		} catch(Exception e) {
			System.out.println("Coulndn't send message: " + message);
		}
	}
	
	@OnClose
	public void OnClose(Session session) {
		sessions.remove(session);
	}
	
	@OnError
	public void error(Session session, Throwable t) {
		sessions.remove(session);
		t.printStackTrace();
	}
}
