// commonAjax
function commonAjax(url, parameter, type, calbak) {
    $.ajax({
        url: url,
        data: parameter,
        type: type,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        success: function (res) {
            calbak(res);
        },
        error: function (err) {
            console.log('error');
            calbak(err);
        }
    });
}

// by 안준언, 현재 접속중인 친구들만 출력
function getOnlineFriend() {
    let msg = {
        memberId: member.id
    };
    console.log(msg);
    commonAjax('/getOnlineFriend', msg, 'post', function (result) {
        createOnlineFriendList(result);
    });
}

function createOnlineFriendList(res) {
    console.log(res);
    let tag ="";
    if (res != null) {
        res.forEach(function (friend) {
            let your;
			your = friend.friend.name;
			tag += "<li class='menu_friend'>" + "<div class='menu_friend_profile_img'>" + "</div>" +
                		"<div class='menu_friend_profile_name'>" +
                    	your +
                		"</div>" +
                		"<div class='menu_friend_profile_on_icon'>" + "<i class='far fa-user'>" + "</i>" +
                		"</div>" + "</li>";
        });
        $("#friendList").empty().append(tag);
    }
}


// by 안준언, 전체 친구 목록 출력
function getFriend() {
	let msg = {
        memberId: member.id
    };
    console.log(msg);
    commonAjax('/getFriend', msg, 'post', function (result) {
        createFriendList(result);
    });
}

function createFriendList(res) {
    console.log(res);
    let tag ="";
    if (res != null) {
        res.forEach(function (friend) {
            let your;
			your = friend.friend.name;
			tag += "<div class='my_friend_section'>" +
						"<div class='my_friend_img'>" + "</div>" +
						"<div class='my_friend_info'>" +
                		"<div class='my_friend_name'>" +
                    		your +
                		"</div>" + "<div class='my_friend_nationality'>" + "United States" +
                		"</div>" + "</div>" + "<div class='my_friend_comment'>" + "최대 세 줄 까지 출력됨" +
                		"</div>" + "<div class='friend_btn_section'>" +
                		"<button type='button' class='friend_remove_btn' onclick='deleteFriend(\"" + friend.id + "\")'>" + "Delete" +
                		"</button>" + "</div>" +
            		"</div>";
        });
        $("#myFriendSection").empty().append(tag);
    }
}

// by 안준언, 친구 요청 목록 출력
function getFriendRequest() {
	let msg = {
        memberId: member.id
    };
    console.log(msg);
    commonAjax('/getFriendRequest', msg, 'post', function (result) {
        createFriendRequestList(result);
    });
}

function createFriendRequestList(res) {
    console.log(res);
    let tag ="";
    if (res != null) {
        res.forEach(function (friendRequest) {
            let your;
			your = friendRequest.send.name;
			tag += "<div class='request_friend_section'>" +
						"<div class='request_friend_img'>" + "</div>" +
						"<div class='request_friend_info'>" +
                		"<div class='request_friend_name'>" +
                    		your +
                		"</div>" + "<div class='request_friend_nationality'>" +
                		"</div>" + "</div>" + "<div class='request_friend_btn'>" +
                		"<ul>" + "<li>" + "<button class='accept_btn' type='button' onclick='agreeFriend(\"" + friendRequest.id + "\")'>" +
                		"ACCEPT" + "</button>" + "</li>" +
                		"<li>" + "<button class='refuse_btn' type='button' onclick='rejectFriend(\"" + friendRequest.id + "\")'>" +
                		"REFUSE" + "</button>" + "</li>" +
                		"</ul>" + "</div>" +
            		"</div>";
        });
        $("#requestFriendSection").empty().append(tag);
    }
}

// by 안준언, 친구 삭제 (내가 상대방 친구 삭제 시, 상대방의 친구 목록에서도 내 정보가 삭제됨.)
function deleteFriend(friendId) {

	let msg = {
        friendId: friendId
    };

    commonAjax('/deleteFriend', msg, 'post', function () {
        getFriend();
        getOnlineFriend();
    });
}

// by 안준언, 친구 요청 수락
function agreeFriend(friendRequestId) {
	let msg = {
		friendRequestId: friendRequestId
	};
	
	commonAjax('/agreeFriend', msg, 'post', function () {
        getFriend();
        getOnlineFriend();
        getFriendRequest();
    });
}

// by 안준언, 친구 요청 거절
function rejectFriend(friendRequestId) {
	let msg = {
		friendRequestId: friendRequestId
	};
	
	commonAjax('/rejectFriend', msg, 'post', function () {
        getFriendRequest();
    });
}
