$('.header_navi').ready(function(){
    var url = window.location.pathname,
    urlRegExp = new RegExp(url.replace(/\/$/,) + "$");

    $('a').parent('li').removeClass('active');
    $('a').each(function(){
        if(urlRegExp.test(this.href.replace(/\/$/,))){
            $(this).parent('li').addClass('active');
        }
    })
}) // 메뉴에 active 클래스 추가하는 기능

$('.community_category').ready(function(){
    var category = 
})

function CheckLoginInput(){
    var loginForm = document.loginForm;
    var uId = loginForm.uid.value;
    var uPw = loginForm.upw.value;

    if(!uId || !uPw){
        alert("Please enter your ID & PASSWORD");
        return false;
    }
    

    
    loginForm.submit();
} // 로그인 처리 기능