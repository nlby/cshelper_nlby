window.onload =
    function() {
  userTemplate();
  updateChart();
  ajaxStatus();
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
ajaxStatus() {
  console.log('查询主页信息');
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/status',
    type: 'GET',
    async: true,
    data: 'json',
    success: function(result) {
      console.log(result.data);
      document.getElementById('weekly_user').textContent =
          result.data.weeklyUser;
      document.getElementById('today_user').textContent = result.data.todayUser;
      document.getElementById('user_sum').textContent = result.data.userSum;
      document.getElementById('user_inc').textContent = result.data.userInc;
      document.getElementById('post_sum').textContent = result.data.postSum;
      document.getElementById('post_inc').textContent = result.data.postInc;
    },
    error: function(xhr) {
      alert(xhr);
    }
  })
}
// 更新首页表单信息
function
updateChart() {
  ;
  $.ajax({
    url: 'http://123.56.15.54:8080/anbbs/admin/status/week',
    type: 'GET',
    async: true,
    data: 'json',
    success: function(result) {
      console.log('week');
      var data = result.data;
      var echartsA = echarts.init(document.getElementById('tpl-echarts'));
      option = {
        tooltip: {trigger: 'axis'},
        grid: {
          top: '3%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          boundaryGap: false,
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }],
        yAxis: [{type: 'value'}],
        textStyle: {color: '#838FA1'},
        series: [{
          name: '帖子',
          type: 'line',
          stack: '总量',
          areaStyle: {normal: {}},
          data: [
            data.Sun, data.Sat, data.Fri, data.Thur, data.Wen, data.Tues,
            data.Mon
          ],
          itemStyle: {
            normal: {
              color: '#1cabdb',
              borderColor: '#1cabdb',
              borderWidth: '2',
              borderType: 'solid',
              opacity: '1'
            },
            emphasis: {

            }
          }
        }]
      };

      echartsA.setOption(option);
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