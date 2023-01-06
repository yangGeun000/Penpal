$('.header_navi').ready(function () {

	var url = window.location.pathname,

		urlRegExp = new RegExp(url.replace(/\/$/, ) + "$");

	$('a').parent('li').removeClass('active');
	$('a').each(function () {
		if (urlRegExp.test(this.href.replace(/\/$/, ))) {
			$(this).parent('li').addClass('active');
		}
	})
}) // by 조성빈, 상단 NAVI에 active 클래스 추가하는 기능

// by 조성빈, 로그인 null 처리 기능 해당 템플릿으로 이동

$(".friend_list_function_btn").children().click(function () {
	if ($('button').hasClass("active")) {
		$('button').removeClass("active")
	}

	$(this).addClass(" active");
}) // by 조성빈, 목록 & 요청 버튼 active 클래스 추가하는 기능

$(".friend_list_section").ready(function () {
	$(".friend_list_function_btn").click(function () {
		if ($(".friend_list_btn").hasClass("active")) {
			$(".my_friends").addClass("active");
			$(".request_friend").removeClass("active");
		}

		if ($(".friend_request_btn").hasClass("active")) {
			$(".my_friends").removeClass("active");
			$(".request_friend").addClass("active");
		}
	})
}) //by 조성빈, 친구 목록 & 친구 요청 버튼 클릭 시 list 영역 전환하는 기능


$(document).mouseup(function (e) {
	if ($("#pop_friend").has(e.target).length === 0) {
		$("#pop_friend").hide();
	}

	if ($("#pop_message").has(e.target).length === 0) {
		if ($("#message_room").css("display") == "block") {
			return false;
		}

		if ($("#message_room").css("display") == "none") {
			$(".message_list_seciton").removeClass("open_list")
			$("#pop_message").hide();
		}
	}

}); //by 조성빈, 외부 클릭 시 레이어 팝업 숨기는 기능

// by 장유란, paging, search 관련 js => search.js로 이동 

function sideMenuFunc() {
	if ($("#menu_section").css("left") == '-320px') {

		$("#menu_section").animate({
			"left": "0px"
		}, "Fast");
	} else {
		$("#menu_section").animate({
			"left": "-320px"
		}, "Fast");
	}
	getRoom(); // 대화방 갱신
	getOnlineFriend(); // 접속중인 친구 리스트 갱신
	getFriend(); // 전체 친구 리스트 갱신
	getFriendRequest(); // 친구 요청 리스트 갱신
} // by 조성빈, 사이드 메뉴 열고 닫기 기능

function openPopFriend() {
	document.getElementById("pop_friend").style.display = "block";
}

function openMessageRoom() {
	document.querySelector("#message_room").style.display = "block";
	$(".message_list_section").addClass("active");
}

function closeMessageRoom() {
	$("#message_room").hide();
	$(".message_list_section").removeClass("active");

	document.getElementById("chatting").removeEventListener("keyup",Enter);
	if (ws != null) {
		ws.close();
		ws = null;
		console.log("소켓 종료");
		getRoom();
	}
} // by 조성빈, 채팅받 닫기 기능

//by 구양근, 확인한 개수보다 새 개수가 많으면 알림 아이콘 색 red
function notification(){
	if(checkMessage < newMessage || checkFriend < newFriend){
		document.getElementById("bell").style.color = "red";
	}
	else{
		document.getElementById("bell").style.color = "#384250";
	}
}

function openNoti() {
	if($(".new_message_btn").hasClass("active")){
		$(".new_message_btn").removeClass("active")
	}else{
		$(".new_message_btn").addClass("active")
	}// by 조성빈, 클릭시 알림 메시지 active 추가/제거 기능화

	// by 구양근, 알림 확인 기능
	checkMessage = newMessage;
	console.log("checkMessage : " + checkMessage);
	checkFriend = newFriend;
	console.log("checkFriend : " + checkFriend);
	let msg = {
		checkMessage : checkMessage,
		checkFriend : checkFriend
	};
	commonAjax('/member/setCount', msg, 'post', function(result) {
		notification()
	});
}

function openPopMessage() {
	document.getElementById("pop_message").style.display = "block";
	$(".message_list_section").addClass("open_list");
}

function closePop() {
	document.getElementById("pop_friend").style.display = "none";
	document.getElementById("pop_message").style.display = "none";
} // by 조성빈, 사이드 메뉴에서 친구 목록 & 메시지 목록 열고 닫기 기능