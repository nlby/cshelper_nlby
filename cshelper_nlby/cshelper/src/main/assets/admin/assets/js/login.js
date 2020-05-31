function login() {
  var username = $('#username').val();
  var password = $('#password').val();
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/login',
    dataType: 'json',
    // contentType: 'application/x-www-form-urlencoded',
    async: true,
    type: 'POST',
    data: {'username': username, 'password': password},
    success: function(result) {
      if (result.status) {
        sessionStorage.isLogin = true;
        sessionStorage.user = JSON.stringify(result.data);
        window.location.href = './index.html';
      }
    },
    error: function(xhr) {
      console.log(xhr);
    }
  })
}