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
  console.log(thi.$store.getters.getHeadImageUrl);
  thi.own.header_pic = thi.own.header_pic + '?x-oss-process=image/resize,m_lfit,h_35,w_35'
}


function sendMessage(thi,event) {
  console.log('sendMessage');
  let data= {
    objectType: 'own',
    chat: '<p>Vuex!</p>'
  }
  thi.message.push(data)
  console.log(thi.message);
}
