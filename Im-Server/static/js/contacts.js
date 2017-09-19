export default {
  Init: Init,
  getFrendList: getFrendList,
  pushToMessagePage: pushToMessagePage,
}


function Init(thi) {
  console.log('init');
  let heigh
  if (document.body.clientWidth > 768) {
    heigh = (window.innerHeight - 150) + 'px'
  } else {
    heigh = window.innerHeight + 'px'
  }
  thi.$parent.$parent.$data.item.height = heigh
  console.log(thi.$parent.$parent.$data.item.height);

}



function pushToMessagePage(thi, item) {
  console.log('updateCurrentMessage:' + item);
  thi.$store.commit('updateCurrentMessage', item)
  thi.$router.push({
    path: '/ChatPage'
  })
}

function getFrendList(thi) {
  console.log('getFrendList')
  if (!thi.$store.getters.getFrendList || thi.$store.getters.getFrendList.length == 0) {
    thi.checkStatus('/Main/Contacts')

    $.ajax({
      type: 'post',
      dataType: 'json',
      async: true,
      url: "/getFrendList",
      xhrFields: {
        withCredentials: true
      },
      data: {
        id: thi.$store.getters.getUserId
      },
      crossDomain: true,
      success: function(data) {
        for (var i = 0; i < data.length; i++) {
          data[i]['class'] = 'row contact-item'
          data[i]['message'] = []
          data[i]['header_pic'] = data[i]['header_pic'] + '?x-oss-process=image/resize,m_lfit,h_35,w_35'
        }
        console.log('frendList');
        thi.$store.commit('updateUserFrendList', data)
        console.log(thi.$store.getters.getFrendList)
        thi.items = thi.$store.getters.getFrendList
        let socket = thi.$store.getters.getSocket
        console.log('socekt');
        console.log(socket);
        if (!socket) {
          thi.$store.commit('updateSocket', thi.io)
          socket = thi.$store.getters.getSocket
        }
        thi.pullCurrentMessageList(thi)
        socket.removeAllListeners('message');
        socket.on('message', function(res) {
          console.log('message');
          console.log(res);

          if (res != undefined) {

            let list = thi.$store.getters.getFrendList
            console.log('list');
            console.log(list);
            console.log(res.ownId);
            for (var type = 0; type < list.length; type++) {
              console.log(list[type].minor_user);
              if (list[type].minor_user == res.ownId) {
                console.log('message variable');
                console.log(list[type]);
                thi.pushToCurrentMessageList(thi, {
                  time: res.time,
                  objectType: 'opposite',
                  chat: res.content
                }, list[type])
              }
            }
          }
        })
        console.log(thi.items);
      }
    })

  } else {
    thi.items = thi.$store.getters.getFrendList
    console.log(thi.items);
  }
}
