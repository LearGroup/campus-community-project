export default {
  getMessageData: getMessageData,
  sendMessage: sendMessage
}


function getMessageData(thi) {
  console.log('getMessageData');
  thi.$store.commit('updateCurrentMessageChatList', thi.$store.getters.getCurrentMessage)
  thi.items = thi.$store.getters.getCurrentMessage
  if (thi.items.message) {
    thi.message = thi.items.message
    console.log(thi.$store.getters.getCurrentMessage);
  }
  thi.own.username = thi.$store.getters.getUserName
  thi.own.id = thi.$store.getters.getUserId
  thi.own.header_pic = thi.$store.getters.getHeadImageUrl
  thi.own.header_pic = thi.own.header_pic + '?x-oss-process=image/resize,m_lfit,h_35,w_35'
  let socket = thi.$store.getters.getSocket
  console.log('socekt');
  console.log(socket);
  if (!socket) {
    thi.$store.commit('updateSocket', thi.io)
    socket = thi.$store.getters.getSocket
  }
  socket.removeAllListeners('message');
  socket.on('message', function(res) {
    console.log('message');
    console.log(res);
    if (res != undefined) {
      pushToCurrentMessageList(thi, {
        time: res.time,
        objectType: 'opposite',
        chat: res.content
      })
    }
  })
}


function sendMessage(thi, event) {
  let message, data
  console.log('sendMessage');
  message = thi.sendMessages
  console.log(message)
  if (thi.sendMessages) {
    data = {
      time: new Date(),
      objectType: 'own',
      chat: message
    }
    let current_obj = thi.$store.getters.getCurrentMessage;
    let myName = thi.$store.getters.getUserName;
    let socket = thi.$store.getters.getSocket
    console.log('socekt');
    console.log(socket);
    if (!socket) {
      thi.$store.commit('updateSocket', thi.io)
      socket = thi.$store.getters.getSocket
    }
    console.log(current_obj);
    console.log(myName);
    socket.emit('message', {
      time: data.time,
      targetId: current_obj.minor_user,
      content: data.chat,
      myName: myName
    })
    pushToCurrentMessageList(thi, data)

  }
}


function pushToCurrentMessageList(thi, data) {
  console.log('c1');
  let current_obj = thi.$store.getters.getCurrentMessage;
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
