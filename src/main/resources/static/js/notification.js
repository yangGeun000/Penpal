//by 구양근, 확인한 개수보다 새 개수가 많으면 알림 아이콘 색 red
function notification(){
	if(checkMessage < newMessage || checkFriend < newFriend){
		document.getElementById("bell").style.color = "red";
	}
	else{
		document.getElementById("bell").style.color = "#384250";
	}
}

// 알림 열기
function openNoti() {
	if($(".new_message_btn").hasClass("active")){
		$(".new_message_btn").removeClass("active")
	}else{
		$(".new_message_btn").addClass("active")
	}// by 조성빈, 클릭시 알림 메시지 active 추가/제거 기능화
	checkNoti();
}

// by 구양근, 확인한 알림 개수 저장
function checkNoti(){
	let msg = {
		checkMessage : newMessage,
		checkFriend : newFriend
	};
	commonAjax('/member/setCount', msg, 'post', function(result) {
		checkMessage = newMessage;
		checkFriend = newFriend;
		console.log("set message : " + checkMessage);
		console.log("set friend : " + checkFriend);
		notification();
	});
}

// 확인한 메세지
function messageNotification(){
	let mCount = document.getElementById("new_message");
	mCount.innerHTML="새 메세지 개수 : " + newMessage;
	if(messageNotiFlag) {
	    checkNoti();
	    messageNotiFlag = false;
	}
	notification();
}

// 확인한 친구요청
function friendNotification(){
	let fCount = document.getElementById("new_frined");
	fCount.innerHTML="새 친구요청 개수 : " + newFriend;
	if(friendNotiFlag) {
	    checkNoti();
	    friendNotiFlag = false;
	}
	notification();
}