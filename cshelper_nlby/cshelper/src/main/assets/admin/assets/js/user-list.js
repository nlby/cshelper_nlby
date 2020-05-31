var curPage = 1;
window.onload =
    function() {
  refreshPage(curPage);
  userTemplate();
}


function refreshPage(page) {
  ajaxUsers(page);
  ajaxPage(page);
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

function
logout() {
  sessionStorage.setItem('isLogin', false);
  window.location.href = './login.html';
}

function ajaxUsers(page) {
  console.log('查询用户表page: ' + page);
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/user/page/' + page,
    type: 'GET',
    async: true,
    data: 'json',
    success: function(result) {
      console.log(result.data);
      usersTemplate(result.data);
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}

function ajaxPage(page) {
  console.log('查询总页数');
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/page/user/',
    type: 'GET',
    async: true,
    data: 'json',
    success: function(result) {
      console.log(result.data);
      pageTemplate(result.data, page);
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}
/**
 * 使用数据显示user
 * @param  data
 */
function usersTemplate(data) {
  var userlist = data;
  $('#users').html('')
  var c = '';
  var f = '';
  var t = '恢复';
  $.each(userlist, function(index, item) {
    if (item.status == 0) {
      c = '';
      f = 'rec(' + item.id + ')';
      t = '恢复';
    } else {
      c = 'tpl-table-black-operation-del';
      f = 'del(' + item.id + ')';
      t = '拉黑';
    }
    $('#users').append($('<tr>').append(
        $('<td>').append(item.id), $('<td>').append(item.username),
        $('<td>').append(item.nickname), $('<td>').append(item.gender),
        $('<td>').append(item.workplace),
        $('<td>').append(item.last_login_time),
        $('<td>').append($('<div>')
                             .attr('class', 'tpl-table-black-operation')
                             .append($('<a>')
                                         .attr('class', c)
                                         .attr('onclick', f)
                                         .append($('<i>').append(t))

                                         ))))
  });
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

function del(id) {
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/user/id/' + id,
    type: 'DELETE',
    async: true,
    data: 'json',
    success: function(result) {
      refreshPage(curPage);
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}

function rec(id) {
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/user/id/' + id,
    type: 'PUT',
    async: true,
    data: 'json',
    success: function(result) {
      refreshPage(curPage);
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}

function
search() {
  var nickname = $('#input_nickname').val();
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/user/nickname?nickname=' + nickname,
    type: 'GET',
    async: true,
    data: 'json',
    success: function(result) {
      usersTemplate(result.data);
      pageTemplate(0, 0);
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}

function
logout() {
  sessionStorage.isLogin = false;
  window.location.href = './login.html';
}