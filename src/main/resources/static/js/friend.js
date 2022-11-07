function getOnlineFriend() {
    let msg = {
        memberId: member.id
    };
    console.log(msg);
    commonAjax('/getOnlineFriend', msg, 'post', function (result) {
        createOnlineFriendList(result);
    });
}

// by 안준언, 현재 접속중인 친구들만 출력
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
                		"<button type='button' class='friend_remove_btn'>" + "Delete" +
                		"</button>" + "</div>" +
            		"</div>";
        });
        $("#myFriendSection").empty().append(tag);
    }
}

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
                		"<ul>" + "<li>" + "<button class='accept_btn' type='button'>" +
                		"ACCEPT" + "</button>" + "</li>" +
                		"<li>" + "<button class='refuse_btn' type='button'>" +
                		"REFUSE" + "</button>" + "</li>" +
                		"</ul>" + "</div>" +
            		"</div>";
        });
        $("#requestFriendSection").empty().append(tag);
    }
}

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