var curPage = 1;
window.onload =
  function () {
    refreshPage(curPage);
    userTemplate();
  }

function
userTemplate() {
  var login = sessionStorage.isLogin;
  if (!login) {
    window.location.href = './login.html';
  }
  var user = JSON.parse(sessionStorage.getItem('user'));
  document.getElementById('nickname').textContent = user.nickname;
  document.getElementById('nickname2').textContent = user.nickname;
  $('#avatar').attr('src', user.avatar)
}

function refreshPage(page) {
  ajaxPosts(page);
  ajaxPage(page);
}

function ajaxPosts(page) {
  console.log('查询用户表page: ' + page);
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/post/page/' + page,
    type: 'GET',
    async: true,
    data: 'json',
    success: function (result) {
      console.log(result.data);
      postTemplate(result.data);
    },
    error: function (xhr) {
      alert(xhr);
    }
  })
}

function ajaxPage(page) {
  console.log('查询总页数');
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/page/post/',
    type: 'GET',
    async: true,
    data: 'json',
    success: function (result) {
      console.log(result.data);
      pageTemplate(result.data, page);
    },
    error: function (xhr) {
      alert(xhr);
    }
  })
}
/**
 * 使用数据显示user
 * @param  data
 */
function postTemplate(data) {
  var userlist = data;
  $('#users').html('')
  var s0, s1, s2, s3, s4;
  $.each(userlist, function (index, item) {
    if (item.status == 0) {
      s0 = true;
      s1 = false;
      s2 = false;
      s3 = false;
      s4 = false;
    } else if (item.status == 1) {
      s0 = false;
      s1 = true;
      s2 = false;
      s3 = false;
      s4 = false;
    } else if (item.status == 2) {
      s0 = false;
      s1 = false;
      s2 = true;
      s3 = false;
      s4 = false;
    } else if (item.status == 3) {
      s0 = false;
      s1 = false;
      s2 = false;
      s3 = true;
      s4 = false;
    } else if (item.status == 4) {
      s0 = false;
      s1 = false;
      s2 = false;
      s3 = false;
      s4 = true;
    }

    $('#users').append($('<tr>').append(
      $('<td>').append(item.id), $('<td>').append(item.title),
      $('<td>').attr('style', 'max-width:450px').append(item.content), $('<td>').append(item.user_id),
      $('<td>').append(item.create_time), $('<td>').append(item.update_time),
      $('<td>').append($('<select>')
        .attr('id', item.id)
        .attr('class', 'status')
        .attr('data-am-selected', '{searchBox: 1}')
        .attr('value', item.status)
        // .attr('style', 'display: none')
        .append(
          $('<option>')
          .attr('value', '0')
          .attr('selected', s0)
          .append('隐藏'),
          $('<option>')
          .attr('value', '1')
          .attr('selected', s1)
          .append('普通'),
          $('<option>')
          .attr('value', '2')
          .attr('selected', s2)
          .append('加精'),
          $('<option>')
          .attr('value', '3')
          .attr('selected', s3)
          .append('置顶'),
          $('<option>')
          .attr('value', '4')
          .attr('selected', s4)
          .append('置顶并加精')))))
  })
  $.each($('.status'), function (index, item) {
    var id = '#' + item.id;

    $(id).change(function () {
      console.log(item.id);
      console.log($(id).val());
      changeStatus(item.id, $(id).val());
    })
  })
  // onchange函数
}

/**
 * 刷新页面页数部分
 * @param data 页数
 * @param index 当前页
 */
function pageTemplate(data, index) {
  curPage = index;
  var pages = data;
  var active = '';
  $('#page').html('')
  for (var i = 1; i <= pages; i++) {
    if (index == i) {
      $('#page').append($('<li>')
        .attr('class', 'am-active')
        .append($('<a>').attr('href', '#').append(i)))
    } else {
      $('#page').append(
        $('<li>')
        .attr('onclick', 'refreshPage(' + i + ')')
        .attr('class', 'am-disabled')
        .append($('<a>').attr('id', i).attr('href', '#').append(i)))
    }
  }
}

function changeStatus(id, status) {
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/post/id/' + id + '/status/' + status,
    type: 'PUT',
    async: true,
    data: 'json',
    success: function (result) {
      // refreshPage(curPage);
      // alert('修改状态成功');
    },
    error: function (xhr) {
      alert(xhr);
    }
  })
}

function
logout() {
  sessionStorage.isLogin = false;
  window.location.href = './login.html';
}