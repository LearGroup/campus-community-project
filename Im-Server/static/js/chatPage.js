
export default {
  getMessageData: getMessageData,
  sendMessage: sendMessage,
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
    let ownId = thi.$store.getters.getUserId
    let header_pic = thi.$store.getters.getHeadImageUrl
    console.log('send ownId');
    console.log(ownId);
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
      ownId: ownId,
    })
    thi.pushToCurrentMessageList(thi, data)

  }
}
