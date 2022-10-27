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
		document.getElementById('page').value = this.dataset.page;
		document.getElementById('searchForm').submit();
	});
});

//by 장유란, 검색 js
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function() {
    document.getElementById('kw').value = document.getElementById('search_kw').value;
    document.getElementById('page').value = 0;  // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
    document.getElementById('searchForm').submit();
});

