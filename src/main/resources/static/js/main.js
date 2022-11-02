$('.header_navi').ready(function() {

	var url = window.location.pathname,

		urlRegExp = new RegExp(url.replace(/\/$/,) + "$");

	$('a').parent('li').removeClass('active');
	$('a').each(function() {
		if (urlRegExp.test(this.href.replace(/\/$/,))) {
			$(this).parent('li').addClass('active');
		}
	})
}) // by 조성빈, 상단 NAVI에 active 클래스 추가하는 기능

function CheckLoginInput() {
	var loginForm = document.loginForm;
	var uId = loginForm.username.value;
	var uPw = loginForm.password.value;

	if (!uId || !uPw) {
		alert("Please enter your ID & PASSWORD");
		return false;
	}

	loginForm.submit();
} // by 조성빈, 로그인 처리 기능

$(".friend_list_function_btn").children().click(function () {
	if ($('button').hasClass("active")) {
		$('button').removeClass("active")
	}

	$(this).addClass(" active");
})// by 조성빈, 목록 & 요청 버튼 active 클래스 추가하는 기능

$(".friend_list_section").ready(function(){
	$(".friend_list_function_btn").click(function(){
		if($(".friend_list_btn").hasClass("active")){
			$(".my_friends").addClass("active");
			$(".request_friend").removeClass("active");
		}
		
		if($(".friend_request_btn").hasClass("active")){
			$(".my_friends").removeClass("active");
			$(".request_friend").addClass("active");
		}
	})
})//by 조성빈, 친구 목록 & 친구 요청 버튼 클릭 시 list 영역 전환하는 기능


$(document).mouseup(function (e){
	if($("#message_room").has(e.target).length === 0){
		$("#message_room").hide();
		$(".message_list_section").removeClass("active");
	}

	if($("#pop_friend").has(e.target).length === 0){
		$("#pop_friend").hide();
	}

	if($("#pop_message").has(e.target).length === 0){
		if($("#message_room").css("display") == "block"){
			return false;
		}
	
		if($("#message_room").css("display") == "none"){
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
} // by 조성빈, 사이드 메뉴 열고 닫기 기능

function openPopFriend() {
	document.getElementById("pop_friend").style.display = "block";
}

function openMessageRoom(){
	document.querySelector("#message_room").style.display = "block";
	$(".message_list_section").addClass("active");
}

function openPopMessage() {
	document.getElementById("pop_message").style.display = "block";
	$(".message_list_section").addClass("open_list");
}

function closePop() {
	document.getElementById("pop_friend").style.display = "none";
	document.getElementById("pop_message").style.display = "none";
} // by 조성빈, 사이드 메뉴에서 친구 목록 & 메시지 목록 열고 닫기 기능