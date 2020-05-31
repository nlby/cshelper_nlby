var isPost = true;
var pid = -1;

$(function () {
    if(getRequest("id") !== null) pid = getRequest("id");
    if(pid !== -1) insertPost();  //如果已存在，则为更改

    addBlockInfo();
    catInfo();
    toggle();
    send();
});

function getRequest(name) {
    let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r!=null) return unescape(r[2]); return null; //返回参数值
}

//变化文章和问答两个样式
function toggle() {
    $(".info").height($(".main").height() - 51);

    $(".t").css({ display: "" });
    $(".q").css({ display: "none" });
    $(".main .title li").click(function () {

        //tab shift
        $(".main .title li").removeClass("select");
        $(this).addClass("select");

        if($(this).text() === "文章"){
            isPost = true;
            $(".t").css({ display: "" });
            $(".q").css({ display: "none" });
        } else {
            isPost = false;
            $(".q").css({ display: "" });
            $(".t").css({ display: "none" });
        }

        $(".info").height($(".main").height());
    })
}

function send() {
    $(".pub button").click(function () {

        if(checkContent() && checkTitle()){

            let title = $(".rtc input").val();
            let content = $(".post-body textarea").val();

            if(sessionStorage.user){//再次确认登陆信息

                var usr = JSON.parse(sessionStorage.user);
                var cid = "1";

                if(isPost)  //是帖子，就要获得发送的分类值
                    cid = getCatId;
                if(pid === -1){ //说明是新发帖子
                    $.ajax({
                        url: "http://123.56.15.54:8080/anbbs/post",
                        method: "post",
                        dataType: "json",
                        data: {
                            "userId": usr.id,
                            "categoryId": cid,
                            "title": title,
                            "content": content
                        },
                        success: function (res) {
                            if(res.status){
                                window.location.href = "post.html?id=" + res.data;
                            }
                        }
                    });
                } else {// 修改帖子
                    $.ajax({
                        url: "http://123.56.15.54:8080/anbbs/post",
                        method: "put",
                        data: {
                            "id": pid,
                            "userId": usr.id,
                            "categoryId": cid,
                            "title": title,
                            "content": content
                        },
                        success: function (res) {
                            if(res.status){
                                window.location.href = "post.html?id=" + pid;
                            }
                        },
                        error: function (x) {
                            console.log(x.status);
                        }
                    });
                }
            }
        }
    })
}

function checkTitle() {
    return $(".rtc input").val();
}

function checkContent() {
    return $(".post-body textarea").val();
}

function getCatId() {
    return $("#cat").val();
}


//在选项卡中增加版块信息
function addBlockInfo() {
    $.ajax({
        url: "http://123.56.15.54:8080/anbbs/block",
        method: "get",
        dataType: "json",
        success: function (res) {
            if (res.status) {

                $("#block").empty();

                $.each(res.data, function () {
                    if(this.id !== 1){  //排除问答版块
                        $("#block").append(genOption(this.id, this.name))
                    }
                });
            }
        }
    });
}

function genOption(id, name) {
    return "<option value=" + id + ">" + name + "</option>>"
}


//根据版块下拉菜单的信息变化，动态加载分类信息
function catInfo() {
    $("#block").change(function () {
        let catId = $(this).val();

        $.ajax({
            url: "http://123.56.15.54:8080/anbbs/block/id/" + catId,
            method: "get",
            dataType: "json",
            success: function (res) {
                if(res.status){
                    $("#cat").empty();
                    $.each(res.data.categories, function () {
                        $("#cat").append(genOption(this.id, this.name))
                    })
                }
            },
            error: function (x) {
                console.log(x.status);
            }
        });
    });
}

function insertPost() {
    $.ajax({
        url: "http://123.56.15.54:8080/anbbs/post/id/" + pid,
        method: "get",
        dataType: "json",
        success: function (res) {
            if(res.status){
                $(".rtc input").val(res.data.title);
                $(".post-body textarea").val(res.data.content);
            }
        },
        error: function (x) {
            console.log(x.status);
        }
    });
}


