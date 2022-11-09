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

// by 구양근, 메뉴 사이드바 최근 대화방 5개만 출력
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
			tag += "<li class='menu_message' onclick = 'openRoom(\"" + room.id + "\", \"" + your.profile.nickname + "\")'>" +
				"<div class='menu_message_profile_img'>" +
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
	console.log(result);
	let tag = "";
	if (result != null) {
		result.forEach(function(room) {
			let length = room.messageList.length;
			let your;
			if (member.id != room.maker.id) {
				your = room.maker;
			} else {
				your = room.guest;
			}
			tag +=
				"<a id='pop_btn' class='pop_message' href='javascript:openMessageRoom()' onclick='openRoom(\"" + room.id + "\", \"" + your.profile.nickname + "\")'>" +
				"<div class='message_list_view'>" +
				"<div class='message_profile_img'>" +
				"</div>" +
				"<div class='message_profile_name'>" +
				your.profile.nickname +
				"</div>" +
				"<div class='message_content'>" +
				"<span>";
			if (length > 0) {
				tag += room.messageList[length - 1].content;
			}
			tag += "</span>" +
				"</div>" +
				//"<div class='message_new_count'>" +
				//"<span></span>" +
				//"</div>" +
				"<button type='button' id='message_room_delete_btn' value='DELETE' onclick = 'deleteRoom(\"" + room.id + "\")'>" +
				"Delete" +
				"</button>" +
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
function openRoom(roomId, name) {
	$('#roomId').val(roomId);
	createRoomProfile(name);
	ws = new WebSocket("ws://" + location.host + "/chating/" + $("#roomId").val());
	wsEvt();
}

function deleteRoom(roomId) {
	event.preventDefault(); // by 구양근, a 태그 기본 이벤트 중지
	event.stopPropagation(); // by 구양근, 상위로 이벤트 전파 중지
	let msg = {
		roomId: roomId
	};
	console.log("delete : " + msg);
	commonAjax('/deleteRoom', msg, 'post', function(result) {
		getRoom();
	});
}

function wsEvt() {
	ws.onopen = function(data) {
		//소켓이 열리면 동작
		console.log($("#roomId").val() + "번방 연결");
		console.log($("#roomId").val());
		getMessage();
		//document.addEventListener("keyup", Enter);
		document.getElementById("chatting").addEventListener("keyup", Enter);
	}

	ws.onmessage = function(data) {
		//메시지를 받으면 동작
		let msg = data.data;
		if (msg != null && msg.trim() != '') {
			let d = JSON.parse(msg);
			console.log(d);
			if (d.type == "getId") {
				let si = d.sessionId != null ? d.sessionId : "";
				if (si != '') {
					$("#sessionId").val(si);
				}
			} else if (d.type == "message") {
				d.msg = d.msg.replace(/\n/g, "<br>") // by 구양근, 개행 문자 치환
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
	if ($("#chatting").val().trim() != "") {
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
	console.log("scroll");
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
// by 구양근, 대화방 프로필 갱신
function createRoomProfile(name) {
	let tag = "";
	tag += "<div class='message_room_friend_img'>" +
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
	console.log(result);
	let tag = "";
	if (result != null) {
		result.forEach(function(message) {
			message.content = message.content.replace(/\n/g, "<br>"); // by 구양근, 개행문자 치환
			if (member.id != message.sender.id) {
				tag += "<div class='friend_message_log'>" +
					"<p class='friend_message'>" + message.content + "</p>" + "</div>";
			} else {
				tag += "<div class='my_message_log'>" +
					"<p class='my_message'>" + message.content + "</p>" + "</div>";
			}
		});
		$("#chating").empty().append(tag);
		document.querySelector("#message_room").style.display = "block";
		scrollBottom();
	}
}