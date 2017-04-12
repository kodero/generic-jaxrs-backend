package com.corvid.genericbackend.streams;

import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Named;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Named
@ServerEndpoint(value = "/streams/{userId}")
public class WSEndpoint {
	Logger log = Logger.getLogger(this.getClass());

	@Resource
	ManagedExecutorService mes;

	private static final ConcurrentHashMap<String, WsSession> activeSessions = new ConcurrentHashMap<>();

	@OnMessage
	public String receiveMessage(String message, Session session) {
		log.info("Received : "+ message + ", session:" + session.getId());
		return "Response from the server";
	}

	@OnOpen
	public void open(@PathParam("userId")String userId, @PathParam("retailStoreId")String retailStoreId, Session session) {
		log.info("Open session:" + session.getPathParameters());
		Map<String, String> params = session.getPathParameters();
		Map<String, Session> sessionMap = new HashMap<>();
		sessionMap.put(params.get("retailStoreId"), session);
		activeSessions.put(session.getId(), new WsSession(session, userId, retailStoreId));
		log.info("Active sessions" + activeSessions);
	}

	@OnClose
	public void close(Session session, CloseReason c) {
		log.info("Closing:" + session.getId() + ", reason : " + c.getReasonPhrase());
		activeSessions.remove(session.getId());
	}

	public class WsSession{
		Session session;
		String userId;
		String retailStoreId;

		public WsSession(Session session, String userId, String retailStoreId){
			this.session = session;
			this.userId = userId;
			this.retailStoreId = retailStoreId;
		}

		public Session getSession() {
			return session;
		}

		public String getUserId() {
			return userId;
		}

		public String getRetailStoreId() {
			return retailStoreId;
		}

		public String toString(){
			return "{session : " + session.getId() + ", userId : " + userId + ", retailStoreId : " + retailStoreId + "}";
		}
	}
}