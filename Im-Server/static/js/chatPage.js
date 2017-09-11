export default {
  getMessageData: getMessageData,
  sendMessage: sendMessage
}


function getMessageData(thi) {

  console.log('getMessageData');
  thi.items = thi.$store.getters.getCureentMessage
  thi.own.username = thi.$store.getters.getUserName
  thi.own.id = thi.$store.getters.getUserId
  thi.own.header_pic = thi.$store.getters.getHeadImageUrl
  thi.own.header_pic = thi.own.header_pic + '?x-oss-process=image/resize,m_lfit,h_35,w_35'
}


function sendMessage(thi, event) {
  let message, data
  console.log('sendMessage');
  message = thi.sendMessages
  if (thi.sendMessages) {
    data = {
      objectType: 'own',
      chat: message
    }
    thi.message.push(data)

  }

}
