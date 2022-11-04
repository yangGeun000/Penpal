window.onload = function() {
	getRoom();
}

function getRoom() {
	let msg = {
		memberId: member.id
	};
	console.log(msg);
	commonAjax('/getRoom', msg, 'post', function(result) {
		createRecentRoom(result);
		createRoomList(result);
	});
}

// by 구양근, 최근 대화방 5개만 출력
function createRecentRoom(result) {
	let index = 5;
	let tag = "";
	if (result != null) {
		result.forEach(function(room) {
			let your;
			if (member.id != room.maker.id) {
				your = room.maker;
			} else {
				your = room.guest;
			}
			tag += "<li class='menu_message' onclick = 'openRoom(\"" + room.id + "\")'>" +
				"<div class='menu_message_profile_img'>" +
				"</div>" +
				"<div class='menu_message_profile_name'>" +
				your.name +
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
	console.log(result);
	let tag = "";
	if (result != null) {
		result.forEach(function(room) {
			let your;
			if (member.id != room.maker.id) {
				your = room.maker;
			} else {
				your = room.guest;
			}
			tag +=
				"<a id='pop_btn' class='pop_message' href='javascript:openMessageRoom()' onclick='openRoom(\"" + room.id + "\")'>" +
				"<div class='message_list_view'>" +
				"<div class='message_profile_img'>" +
				"</div>" +
				"<div class='message_profile_name'>" +
				your.name +
				"</div>" +
				"<div class='message_content'>" +
				"<span>test</span>" +
				"</div>" +
				"<div class='message_new_count'>" +
				"<span>1</span>" +
				"</div>" +
				"</div>" +
				"</a>";
		});
		$(".message_list_section").empty().append(tag);
	}
}

// by 구양근, ajax 통신 함수
function commonAjax(url, parameter, type, calbak) {
	$.ajax({
		url: url,
		data: parameter,
		type: type,
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		success: function(result) {
			calbak(result);
		},
		error: function(err) {
			console.log('error');
			calbak(err);
		}
	});
}

let ws; // by 구양근, 웹 소켓 변수

// by 구양근, 클릭하면 소켓 열고 초기화
function openRoom(roomId) {
	$('#roomId').val(roomId);
	console.log($('#roomId').val());
	ws = new WebSocket("ws://" + location.host + "/chating/" + $("#roomId").val());
	wsEvt();
}

function wsEvt() {
	ws.onopen = function(data) {
		//소켓이 열리면 동작
		console.log($("#roomId").val() + "번방 연결");
		getMessage();
		document.addEventListener("keypress", Enter);
	}

	ws.onmessage = function(data) {
		//메시지를 받으면 동작
		let msg = data.data;
		if (msg != null && msg.trim() != '') {
			let d = JSON.parse(msg);
			if (d.type == "getId") {
				let si = d.sessionId != null ? d.sessionId : "";
				if (si != '') {
					$("#sessionId").val(si);
				}
			} else if (d.type == "message") {
				if (d.sessionId == $("#sessionId").val()) {
					$("#chating").append("<div class='my_message_log'>" +
						"<p class='my_message'>" + d.msg + "</p>" + "</div>");
				} else {
					$("#chating").append("<div class='friend_message_log'>" +
						"<p class='friend_message'>" + d.msg + "</p>" + "</div>");
				}
				scrollBottom();
			} else {
				console.warn("unknown type!")
			}
		}
	}
}

function send() {
	let option = {
		type: "message",
		roomId: $("#roomId").val(),
		sessionId: $("#sessionId").val(),
		memberId: member.id.toString(),
		msg: $("#chatting").val()
	}
	console.log(option);
	ws.send(JSON.stringify(option))
	$('#chatting').val("");
}

// by 구양근, 엔터치면 메세지 보내게 이벤트 추가
function Enter(e) {
	if (e.keyCode == 13) { //enter press
		send();
	}
}

// by 구양근, 메세지창 스크롤 맨 밑으로 이동
function scrollBottom(){
	let chatView = document.querySelector(".message_room_chat_view");
	chatView.scrollTop = chatView.scrollHeight;
}

// by 구양근, ajax로 대화방의 이전 메세지 받기
function getMessage() {
	let msg = {
		roomId: $("#roomId").val()
	};
	console.log(msg);
	commonAjax('/getMessage', msg, 'post', function(result) {
		createRoomMessage(result);
	});
}

// by 구양근, 예전 메세지 갱신
function createRoomMessage(result) {
	console.log(result);
	let tag = "";
	if (result != null) {
		result.forEach(function(message) {
			if (member.id != message.sender.id) {
				tag += "<div class='friend_message_log'>" +
					"<p class='friend_message'>" + message.content + "</p>" + "</div>";
			} else {
				tag += "<div class='my_message_log'>" +
					"<p class='my_message'>" + message.content + "</p>" + "</div>";
			}
		});
		$("#chating").empty().append(tag);
		scrollBottom();
		document.querySelector("#message_room").style.display = "block";
	}
}