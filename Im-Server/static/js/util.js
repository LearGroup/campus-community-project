export default {
  install(Vue) {
    Vue.prototype.pushToCurrentMessageList = pushToCurrentMessageList
    Vue.prototype.checkStatus = checkStatus
    Vue.prototype.pullCurrentMessageList=pullCurrentMessageList
  }
}


function checkStatus(targetUrl, callback) {
  arguments[1] ? arguments[0] : null
  console.log('checkStatus');
  console.log(this.$store.state);
  console.log(this.$store.state.userState);
  if (this.$store.state.userState == 0) {
    console.log('checkStatus ajax');
    let thi = this

    $.ajax({
      type: 'post',
      async: true,
      data: {
        first:'1'
      },
      dataType: 'json',
      url: "/checkStatus",
      xhrFields: {
        withCredentials: true
      },
      crossDomain: true,
      success: function(data) {
        console.log('data' + data);
        if (data == null) {
          console.log('checkStatus push ');
          thi.$router.push({
            path: '/Login'
          })
        } else {
          console.log('store save' + targetUrl);
          thi.$store.commit('updateUserState', {
            userState: 1,
            userName: data.username,
            userId: data.id,
            headImageUrl: data.header_pic
          })
          console.log('userState:');
          console.log(thi.$store.getters.getState);
          if (callback) {
            callback()
          }
          thi.$router.push({
            path: targetUrl
          })
        }
      }
    })

  } else {
    this.$router.push({
      path: targetUrl
    })
  }
}

function pullCurrentMessageList(thi) {
  console.log('pullCurrentMessageList');
  $.ajax({
    type: 'post',
    async: true,
    url: "/pullCurrentMessageList",
    dataType: 'json',
    xhrFields: {
      withCredentials: true
    },
    crossDomain: true,
    success: function(data) {
      console.log('data' + data);
      if (data) {
        let list = thi.$store.getters.getFrendList
        for (var i = 0; i < data.length; i++) {
          data[i]=JSON.parse(data[i])
          for (var type = 0; type < list.length; type++) {
            console.log(list[type].minor_user);
            if (list[type].minor_user == data[i].ownId) {
              console.log('message variable');
              console.log(list[type]);
              thi.pushToCurrentMessageList(thi, {
                time: data[i].time,
                objectType: 'opposite',
                chat: data[i].content
              }, list[type])
            }
          }
        }
      }
    }
  })
}


function pushToCurrentMessageList(thi, data, message) {
  console.log('c1');
  let current_obj = arguments[2] ? arguments[2] : thi.$store.getters.getCurrentMessage;
  let myName = thi.$store.getters.getUserName;

  console.log(data);
  let obj = {}
  console.log('c2');
  obj.data = data
  obj.currentMessage = current_obj
  console.log(thi.$store.getters.getCurrentMessageList);
  thi.$store.commit('updateCurrentMessageList', obj)
  console.log(data);
  console.log(current_obj);
  console.log('pushToCurrentMessageList end');
}
