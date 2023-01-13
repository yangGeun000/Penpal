package com.penpal.project.handler;

import com.penpal.project.domain.Member;
import com.penpal.project.domain.Message;
import com.penpal.project.domain.Room;
import com.penpal.project.service.MemberService;
import com.penpal.project.service.MessageService;
import com.penpal.project.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RequiredArgsConstructor
@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
    HashMap<String, HashMap<String, WebSocketSession>> roomMap = new HashMap<>(); // 세션들을 각각의 방으로 저장할 map
    private final RoomService roomService;
    private final MessageService messageService;
    private final MemberService memberService;

    // by 구양근, 메세지 받았을 때
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload(); // 보낸 메세지를 얻어서
        JSONObject json = jsonToObjectParser(msg); // JSON 오브젝트에 파서

        String roomId = (String) json.get("roomId");
        HashMap<String, WebSocketSession> socketRoom = roomMap.get(roomId);
        LocalDateTime sendDate = LocalDateTime.now(); // 메세지 보낸 시간
        json.put("sendDate",sendDate.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")));

        // by 구양근, 일단 메세지 먼저 보내고 DB에 저장
        if (socketRoom != null) {
            socketRoom.forEach((key, value) -> { // 방에 저장된 session에 모두 메세지를 보낸다
                try {
                    value.sendMessage(new TextMessage(json.toJSONString()));
                } catch (IOException e) {
                    log.error("send message error = {}", e.getMessage());
                    e.printStackTrace();
                }
            });
        }

        // by 구양근, 메세지 db에 저장
        Room room = this.roomService.getRoom(Integer.parseInt((String) json.get("roomId")));
        Member sender = this.memberService.getMember(Integer.parseInt((String) json.get("memberId")));
        String content = (String) json.get("msg");
        Message roomMessage = this.messageService.createMessage(sender, room, content, sendDate);
        // by 구양근, 보낸시간을 대화방의 마지막 시간으로 설정
        room.setLastDate(sendDate);

        // by 구양근, 상대가 접속중이지 않을때는 새 메세지 카운트 증가
        if (socketRoom.size() < 2) {
            if (sender.getId().equals(room.getMaker().getId())) {
                room.setGuestCount(room.getGuestCount() + 1);
                this.roomService.setRoom(room);
            } else {
                room.setMakerCount(room.getMakerCount() + 1);
                this.roomService.setRoom(room);
            }
        }
    }

    // by 구양근, 소켓 연결될 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String url = session.getUri().toString();
        String roomId = url.split("/chating/")[1];

        HashMap<String, WebSocketSession> socketRoom = roomMap.get(roomId);

        if (socketRoom != null) { // 사용중인 방이라면 session 추가
            socketRoom.put(session.getId(), session);
        } else { // 미사용 방이라면 roomList에 추가하고 session 추가
            socketRoom = new HashMap<>();
            socketRoom.put(session.getId(), session);
            roomMap.put(roomId, socketRoom);
        }

        //세션등록이 끝나면 발급받은 sessionId 발송
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());

        try {
            session.sendMessage(new TextMessage(obj.toJSONString()));
        } catch (IOException e) {
            log.error("send message error = {}", e.getMessage());
            e.printStackTrace();
        }

    }

    // by 구양근, 소켓이 종료될 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String url = session.getUri().toString();
        String roomId = url.split("/chating/")[1];

        HashMap<String, WebSocketSession> socketRoom = roomMap.get(roomId);

        socketRoom.remove(session.getId()); //소켓이 종료되면 해당 session 삭제
        if (socketRoom.isEmpty()) { // 방 안에 인원이 아무도 없으면 roomList에서 방 삭제
            roomMap.remove(roomId);
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
