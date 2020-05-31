$(function () {
    checkLogin();
    logOut();
    getBlockInfo();
    home();
    search();

});

function checkLogin() {
    let $login = $('.header-login');
    let $unLogin = $('.header-unlogin');

    //存在用户信息，说明已经登陆
    if (sessionStorage.user) {
        $login.css({
            display: ''
        });
        $unLogin.css({
            display: 'none'
        });
        addTou();     //填充头像
        checkAdmin();
    } else {
        $login.css({
            display: 'none'
        });
        $unLogin.css({
            display: ''
        });
    }
}

//得到每个版块的信息用于显示
function getBlockInfo() {
    $.ajax({
        url: 'http://123.56.15.54:8080/anbbs/block',
        method: 'get',
        dataType: 'json',
        success: function (res) {
            if (res.status) {
                $.each(res.data, function () {
                    let $item = genBlock(
                        this.id, this.name, this.icon, this.categorySum, this.postSum,
                        this.updateTime, this.adminName, this.adminId);
                    $('.module').append($item);
                });
            }
        }
    });
}

//信息显示
function genBlock(id, name, imgLoc, catN, postN, time, admin, uid) {
    return $(
        '<div class=item>' +
        '<a href=html/block.html?id=' + id + '>' +
        '<img src=' + imgLoc + '>' +
        '</a>' +
        '<span><a href=html/block.html?id=' + id + '>' + name + '</a></span>' +
        '<p>共有<span>' + catN + '</span>个分类，<span>' + postN +
        '</span>个帖子 &nbsp;版主：<a class=block-admin href=html/home.html?id=' +
        uid + '>' + admin + '</a></p>' +
        '<p>上次更新 <span>' + time + '</span></p>\n' +
        '</div>');
}

//退出
function logOut() {
    $('#logout').click(function () {
        sessionStorage.clear();
    })
}

function addTou() {
    let user = JSON.parse(sessionStorage.user);
    $('#usr-avt').attr('src', user.avatar);
}

//点主页回到个人主页
function home() {
    $("#user ul li:first a").on("click", function () {
        let uid = JSON.parse(sessionStorage.user).id;
        window.location.href = "html/home.html?id=" + uid;
        return false;
    });
}

function checkAdmin() {
    let status = JSON.parse(sessionStorage.user).status;
    console.log(sessionStorage.user);

    if(status !== 2){
        $(".admin").css({display: "none"});
    } else {
        $(".admin").css({display: ""});
    }
}

//点击图标搜索
function search() {
    $(".am-icon-search").on("click", function () {
        let keyword = $(this).parent(".search").children("input").val();
        if(keyword.trim()) window.location.href = "html/search.html?key=" + keyword;
    })
}