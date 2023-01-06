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
            let nickname = friend.friend.profile.nickname;
			tag += 		"<a href = '/users/profile/" + friend.friend.profile.id + "'>" + "<li class='menu_friend'>" +
						"<div class='menu_friend_profile_img'>";
						if( friend.friend.profile.url != null){ 
						tag+="<img src = '/users/image?url="+  friend.friend.profile.url +"' alt = '프로필 이미지'>";
						} 
						tag+="</div>" +
                		"<div class='menu_friend_profile_name'>" +
                    	nickname +
                		"</div>" +
                		"<div class='menu_friend_profile_on_icon'>" + "<i class='far fa-user'>" + "</i>" +
                		"</div>" +"</li>" + "</a>";
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
            let nickname  = friend.friend.profile.nickname;
            let country   = friend.friend.profile.country.name;
            let comment   = friend.friend.profile.comment;
			tag += 		"<a href = '/users/profile/" + friend.friend.profile.id + "'" + "<div class='my_friend_section'>" +
						"<div class='my_friend_img'>";
						if( friend.friend.profile.url != null){ 
						tag+="<img src = '/users/image?url="+  friend.friend.profile.url +"' alt = '프로필 이미지'>";
						} 
						tag+="</div>" +
						"<div class='my_friend_info'>" +
                		"<div class='my_friend_name'>" +
                    		nickname +
                		"</div>" + "<div class='my_friend_nationality'>" + country +
                		"</div>" + "</div>" + "<div class='my_friend_comment'>" + comment +
                		"</div>" + "</a>" +
                        "<div class='friend_btn_section'>" +
                		"<button type='button' class='friend_remove_btn' onclick='deleteFriend(\"" + friend.id + "\")'>" + "Delete" +
                		"</button>" + "</div>" + "</div>";
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
		newFriend=0;
        createFriendRequestList(result);
        friendNotification();
    });
}

function createFriendRequestList(res) {
    console.log(res);
    let tag ="";
    if (res != null) {
		newFriend = res.length;
        res.forEach(function (friendRequest) {
            let nickname = friendRequest.send.profile.nickname;
            let country  = friendRequest.send.profile.country.name;
			tag += 		"<a href = '/users/profile/" + friendRequest.send.profile.id + "'>" + "<div class='request_friend_section'>" +
						"<div class='request_friend_img'>";
						if(friendRequest.send.profile.url != null){ 
						tag+="<img src = '/users/image?url="+ friendRequest.send.profile.url +"' alt = '프로필 이미지'>";
						} 
						tag+="</div>" +
						"<div class='request_friend_info'>" +
                		"<div class='request_friend_name'>" +
                    		nickname +
                		"</div>" + "<div class='request_friend_nationality'>" + country +
                		"</div>" + "</div>" + "</a>" + 
                        "<div class='request_friend_btn'>" +
                		"<ul>" + "<li>" + "<button class='accept_btn' type='button' onclick='agreeFriend(\"" + friendRequest.id + "\")'>" +
                		"ACCEPT" + "</button>" + "</li>" +
                		"<li>" + "<button class='refuse_btn' type='button' onclick='rejectFriend(\"" + friendRequest.id + "\")'>" +
                		"REFUSE" + "</button>" + "</li>" +
                		"</ul>" + "</div>" + "</div>";
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
