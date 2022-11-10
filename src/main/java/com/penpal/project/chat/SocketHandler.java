package com.penpal.project.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.penpal.project.member.Member;
import com.penpal.project.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SocketHandler extends TextWebSocketHandler{
	//HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); //웹소켓 세션을 담아둘 맵
		List<HashMap<String, Object>> rls = new ArrayList<>(); //웹소켓 세션을 담아둘 리스트 ---roomListSessions
		
		private final RoomService roomService;
		private final MessageService messageService;
		private final MemberService memberService;
		
		// by 구양근, 메세지 받았을 때 
		@Override
		public void handleTextMessage(WebSocketSession session, TextMessage message) {
			String msg = message.getPayload();
			JSONObject obj = jsonToObjectParser(msg);
			
			String rN = (String) obj.get("roomId");
			
			HashMap<String, Object> temp = new HashMap<String, Object>();
			if(rls.size() > 0) {
				for(int i=0; i<rls.size(); i++) {
					String roomId = (String) rls.get(i).get("roomId"); //세션리스트의 저장된 방번호를 가져와서
					if(roomId.equals(rN)) { //같은값의 방이 존재한다면
						temp = rls.get(i); //해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
						break;
					}
					if(i == rls.size()) return; // by 구양근, 방이 없다면 그냥 종료
				}
				
				// by 구양근, 메세제 db에 저장
				Room room = this.roomService.getRoom(Integer.parseInt((String) obj.get("roomId")));
				Member sender = this.memberService.getMember(Integer.parseInt((String) obj.get("memberId")));
				String content = (String) obj.get("msg");
				// by 구양근, 보낸시간을 대화방의 마지막 시간으로 설정
				room.setLastDate(this.messageService.createMessage(sender, room, content).getSendDate());
				//this.messageService.createMessage(sender, room, content);
				obj.put("sendDate", room.getLastDate().toString());
				// by 구양근, 메세제 발송
				for(String k : temp.keySet()) { 
					if(k.equals("roomId")) { //다만 방번호일 경우에는 건너뛴다.
						continue;
					}
					
					WebSocketSession wss = (WebSocketSession) temp.get(k);
					if(wss != null) {
						try {
							wss.sendMessage(new TextMessage(obj.toJSONString()));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		// by 구양근, 소켓 연결될 때
		@Override
		public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			super.afterConnectionEstablished(session);
			boolean flag = false;
			String url = session.getUri().toString();
			System.out.println(url);
			String roomId = url.split("/chating/")[1];
			int idx = rls.size(); //방의 사이즈를 조사한다.
			if(rls.size() > 0) {
				for(int i=0; i<rls.size(); i++) {
					String rN = (String) rls.get(i).get("roomId");
					if(rN.equals(roomId)) {
						flag = true;
						idx = i;
						break;
					}
				}
			}
			
			if(flag) { //존재하는 방이라면 세션만 추가한다.
				HashMap<String, Object> map = rls.get(idx);
				map.put(session.getId(), session);
			}else { //최초 생성하는 방이라면 방번호와 세션을 추가한다.
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("roomId", roomId);
				map.put(session.getId(), session);
				rls.add(map);
			}
			
			//세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
			JSONObject obj = new JSONObject();
			obj.put("type", "getId");
			obj.put("sessionId", session.getId());
			session.sendMessage(new TextMessage(obj.toJSONString()));
		}
		
		// by 구양근, 소켓이 종료될 때
		@Override
		public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
			if(rls.size() > 0) { //소켓이 종료되면 해당 세션값들을 찾아서 지운다.
				for(int i=0; i<rls.size(); i++) {
					rls.get(i).remove(session.getId());
				}
			}
			super.afterConnectionClosed(session, status);
		}
		
		
		// by 구양근, json 파서 함수
		private static JSONObject jsonToObjectParser(String jsonStr) {
			JSONParser parser = new JSONParser();
			JSONObject obj = null;
			try {
				obj = (JSONObject) parser.parse(jsonStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return obj;
		}
}
