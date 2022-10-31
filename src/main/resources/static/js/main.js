$('.header_navi').ready(function() {

	var url = window.location.pathname,

		urlRegExp = new RegExp(url.replace(/\/$/,) + "$");

	$('a').parent('li').removeClass('active');
	$('a').each(function() {
		if (urlRegExp.test(this.href.replace(/\/$/,))) {
			$(this).parent('li').addClass('active');
		}
	})
}) // 메뉴에 active 클래스 추가하는 기능

function CheckLoginInput() {
	var loginForm = document.loginForm;
	var uId = loginForm.uid.value;
	var uPw = loginForm.upw.value;

	if (!uId || !uPw) {
		alert("Please enter your ID & PASSWORD");
		return false;
	}



	loginForm.submit();
} // 로그인 처리 기능
//by 장유란, 페이징 js
const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		// by 장유란, 페이지 범위 밖으로 갈 수 없게 수정
		if (this.dataset.page < 0) {
			this.dataset.page = 0
		} else if (this.dataset.page >= page_elements.length - 3) {
			this.dataset.page = page_elements.length - 3;
		}
		document.getElementById('page').value = this.dataset.page;
		document.getElementById('searchForm').submit();
	});
});

var category = '';
$(document).ready(function() {
	$(".click-category").on('click', function(e) {
		category = (e.target.id);
	});
});

const btn_list = document.getElementById("btn_list");
btn_list.addEventListener('click', function() {
	document.getElementById('select_category').value = category;
	document.getElementById('select_location').value = "";
	document.getElementById('select_country').value = "";
	document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
	document.getElementById('searchForm').submit();

});

//by 장유란, 검색 js
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function() {
	document.getElementById('kw').value = document.getElementById('search_kw').value;
	document.getElementById('select_location').value = document.getElementById('location').value;
	document.getElementById('select_country').value = document.getElementById('country').value;
	document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
	document.getElementById('searchForm').submit();
});


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
function openPopMessage() {
	document.getElementById("pop_message").style.display = "block";
}
function closePop() {
	document.getElementById("pop_friend").style.display = "none";
	document.getElementById("pop_message").style.display = "none";
} // by 조성빈, 사이드 메뉴에서 친구 목록 & 메시지 목록 열고 닫기 기능