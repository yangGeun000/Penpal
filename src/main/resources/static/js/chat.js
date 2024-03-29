let messageNotiFlag = false;
let ws; // by 구양근, 웹 소켓 변수

function getRoom() {
	let msg = {
		memberId: member.id,
	};
	commonAjax("/getRoom", msg, "post", function (result) {
		newMessage = 0;
		createRecentRoom(result);
		createRoomList(result);
		messageNotification();
	});
}

// by 구양근, 메뉴 사이드바 최근 대화방 5개만 출력
function createRecentRoom(result) {
	let index = 5;
	let tag = "";
	if (result != null) {
		result.forEach(function (room) {
			let your;
			if (member.id != room.maker.id) {
				your = room.maker;
			} else {
				your = room.guest;
			}
			tag +=
				"<li class='menu_message' onclick = 'openRoom(\"" +
				room.id +
				'", "' +
				your.profile.nickname +
				'", "' +
				your.profile.url +
				"\")'>" +
				"<div class='menu_message_profile_img'>";
			if (your.profile.url != null) {
				tag +=
					"<img src = '/users/image?url=" +
					your.profile.url +
					"' alt = '프로필 이미지'>";
			}
			tag +=
				"</div>" +
				"<div class='menu_message_profile_name'>" +
				your.profile.nickname +
				"</div>" +
				"<div class='menu_message_profile_on_icon'>" +
				"<i class='far fa-envelope'></i>" +
				"</div>" +
				"</li>";
			index--;
			if (index <= 0) {
				return false;
			}
		});
		$(".menu_message_list").empty().append(tag);
	}
}

// by 구양근, 대화방 리스트 출력
function createRoomList(result) {
	let tag = "";
	if (result != null) {
		result.forEach(function (room) {
			let length = room.messageList.length;
			let your;
			let messageCount;
			if (member.id != room.maker.id) {
				your = room.maker;
				messageCount = room.guestCount;
			} else {
				your = room.guest;
				messageCount = room.makerCount;
			}
			newMessage += messageCount;
			tag +=
				"<a id='pop_btn' class='pop_message' href='javascript:openMessageRoom()' onclick='openRoom(\"" +
				room.id +
				'", "' +
				your.profile.nickname +
				'", "' +
				your.profile.url +
				"\")'>" +
				"<div class='message_list_view'>" +
				"<div class='message_profile_img'>";
			if (your.profile.url != null) {
				tag +=
					"<img src = '/users/image?url=" +
					your.profile.url +
					"' alt = '프로필 이미지'>";
			}
			tag +=
				"</div>" +
				"<div class='message_profile_name'>" +
				your.profile.nickname +
				"</div>" +
				"<div class='message_content'>";
			if (length > 0) {
				tag +=
					"<span>" +
					room.messageList[length - 1].content +
					"</span>" +
					"<br>" +
					room.messageList[length - 1].sendDate;
			}
			tag +=
				"</div>" +
				"<div class='message_new_count'>" +
				"<span>" +
				messageCount +
				"</span>" +
				"</div>" +
				"<button type='button' id='message_room_delete_btn' value='DELETE' onclick = 'deleteRoom(\"" +
				room.id +
				"\")'>" +
				"Delete" +
				"</button>" +
				"</div>" +
				"</a>";
		});
		$(".message_list_section").empty().append(tag);
	}
}

// by 구양근, 클릭하면 소켓 열고 초기화
function openRoom(roomId, name, url) {
	$("#roomId").val(roomId);
	createRoomProfile(name, url);
	ws = new WebSocket(
		"ws://" + location.host + "/chating/" + $("#roomId").val()
	);
	wsEvt();
}

function closeMessageRoom() {
	$("#message_room").hide();
	$(".message_list_section").removeClass("active");

	document.getElementById("chatting").removeEventListener("keyup",Enter);
	if (ws != null) {
	    ws.onclose = null; // onclose에 할당된 이벤트를 없애서 채팅창을 닫았을때 재연결을 못하게 동작
		ws.close();
		ws = null;
		getRoom();
	}
} // by 조성빈, 채팅받 닫기 기능

function deleteRoom(roomId) {
	event.preventDefault(); // by 구양근, a 태그 기본 이벤트 중지
	event.stopPropagation(); // by 구양근, 상위로 이벤트 전파 중지
	let msg = {
		roomId: roomId,
	};
	commonAjax("/deleteRoom", msg, "post", function (result) {
	    messageNotiFlag = true;
		getRoom();
	});
}

function wsEvt() {
	ws.onopen = function () {
		//소켓이 열리면 동작
		getMessage();
		messageNotiFlag = true;
		document.getElementById("chatting").addEventListener("keyup", Enter);
	};

	ws.onmessage = function (data) {
		//메시지를 받으면 동작
		let msg = data.data;
		if (msg != null && msg.trim() != "") {
			let d = JSON.parse(msg);
			if (d.type == "getId") {
				let si = d.sessionId != null ? d.sessionId : "";
				if (si != "") {
					$("#sessionId").val(si);
				}
			} else if (d.type == "message") {
				d.msg = d.msg.replace(/\n/g, "<br>"); // by 구양근, 개행 문자 치환
				if (d.sessionId == $("#sessionId").val()) {
					$("#chating").append(
						"<div class='my_message_log'>" +
						"<p class='my_message'>" +
						d.msg +
						"</p>" +
						d.sendDate +
						"</div>"
					);
				} else {
					$("#chating").append(
						"<div class='friend_message_log'>" +
						"<p class='friend_message'>" +
						d.msg +
						"</p>" +
						d.sendDate +
						"</div>"
					);
				}
				scrollBottom();
			} else {
				console.warn("unknown type!");
			}
		}
	};

	ws.onclose = function (){
	    // 소켓연결이 끊겼을 때 동작
	    // 재연결로 사용
	    setTimeout(function(){
	    console.log("소켓 재연결");
	    ws = new WebSocket(
        		"ws://" + location.host + "/chating/" + $("#roomId").val()
        	);
        wsEvt();
	    },1000);
	};
}

function send() {
	if ($("#chatting").val().trim() != "") {
		let option = {
			type: "message",
			roomId: $("#roomId").val(),
			sessionId: $("#sessionId").val(),
			memberId: member.id.toString(),
			msg: $("#chatting").val(),
		};
		ws.send(JSON.stringify(option));
		$("#chatting").val("");
	}
}

// by 구양근, ajax로 대화방의 이전 메세지 받기
function getMessage() {
	let msg = {
		roomId: $("#roomId").val(),
	};
	commonAjax("/getMessage", msg, "post", function (result) {
		createRoomMessage(result);
	});
}

// by 구양근, 대화방 프로필 갱신
function createRoomProfile(name, url) {
	let tag = "";
	tag += "<div class='message_room_friend_img'>";
	if (url != null && url != "null") {
		tag += "<img src = '/users/image?url=" + url + "' alt = '프로필 이미지'>";
	}
	tag +=
		"</div>" +
		"<div class='message_room_friend_name'>" +
		name +
		"</div>" +
		"<button id='message_room_exit_btn' type='button' value='EXIT' onclick='closeMessageRoom()'>" +
		"EXIT" +
		"</button>";
	$(".message_room_friend_profile").empty().append(tag);
}

// by 구양근, 예전 메세지 갱신
function createRoomMessage(result) {
	let tag = "";
	if (result != null) {
		result.forEach(function (message) {
			message.content = message.content.replace(/\n/g, "<br>"); // by 구양근, 개행문자 치환
			if (member.id != message.sender.id) {
				tag +=
					"<div class='friend_message_log'>" +
					"<p class='friend_message'>" +
					message.content +
					"</p>" +
					message.sendDate +
					"</div>";
			} else {
				tag +=
					"<div class='my_message_log'>" +
					"<p class='my_message'>" +
					message.content +
					"</p>" +
					message.sendDate +
					"</div>";
			}
		});
		$("#chating").empty().append(tag);
		document.querySelector("#message_room").style.display = "block";
		scrollBottom();
	}
}

// by 구양근, 엔터치면 메세지 보내게 이벤트 추가
function Enter(e) {
	// by 구양근, ctrl + enter 치면 그냥 줄바꿈
	if ((e.keyCode == 10 || e.keyCode == 13) && (e.ctrlKey || e.metaKey)) {
		this.value += "\n";
		return true;
	}
	// by 구양근, enter 치면 메세지 전송
	if (e.keyCode == 13 && !e.ctrlKey) {
		send();
	}
}

// by 구양근, 메세지창 스크롤 맨 밑으로 이동
function scrollBottom() {
	let chatView = document.getElementById("chating");
	chatView.scrollTop = chatView.scrollHeight;
}
