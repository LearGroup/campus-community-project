export default {
  getMessageData: getMessageData,
  sendMessage: sendMessage
}


function getMessageData(thi) {

  console.log('getMessageData');
  thi.items = thi.$store.getters.getCurrentMessage
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
    thi.message.push(data)
    thi.$store.commit('updateCurrentMessageChat',data)
    pushToCurrentMessageList(thi, data)
  }
}


function pushToCurrentMessageList(thi, data) {
  console.log('c1');
  let message_list = thi.$store.getters.getCurrentMessageList;
  console.log('c2');
  let current_obj = thi.$store.getters.getCurrentMessage;
  thi.$store.commit('updateCurrentMessageList',current_obj)
  console.log(message_list);
  console.log(data);
  console.log(current_obj);
}
