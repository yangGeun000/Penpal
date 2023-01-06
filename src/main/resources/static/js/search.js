//by 장유란, 페이징 js
const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function (element) {
	element.addEventListener('click', function () {
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


let category = '';
$(document).ready(function () {
	$(".click-category").on('click', function (e) {
		category = (e.target.id);
	});
});
const btn_list = document.getElementById("btn_list");
if (btn_list !== null) {
	btn_list.addEventListener('click', function () {
	document.getElementById('select_category').value = category;
	document.getElementById('select_location').value = "";
	document.getElementById('select_country').value = "";
	document.getElementById('page').value = 0; // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
	document.getElementById('searchForm').submit();

	});
}
//by 장유란, 검색 js
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function () {
	document.getElementById('kw').value = document.getElementById('search_kw').value;
	document.getElementById('select_location').value = document.getElementById('location').value;
	document.getElementById('select_country').value = document.getElementById('country').value;
	document.getElementById('page').value = 0; // 검색버튼을 클릭할 경우 0페이지부터 조회한다.
	console.log("searchBtnclick");
	document.getElementById('searchForm').submit();
});

// by 장유란, if문 추가
window.onload = function () {
	if (document.getElementById('select_category') !== null) {
		const selectCategory = document.getElementById('select_category').value;
		const clickCategory = document.getElementsByClassName(selectCategory);
		if (selectCategory !== "") {
			$("li").removeClass("active");
			$(clickCategory).addClass("active");
		}
	}
} // by 조성빈, 페이지 로드 후 선택된 category class에 active 넣는 기능
