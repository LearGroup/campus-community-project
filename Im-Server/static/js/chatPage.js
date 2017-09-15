export default {
  getMessageData: getMessageData,
  sendMessage: sendMessage
}


function getMessageData(thi) {
  console.log('getMessageData');
  thi.$store.commit('updateCurrentMessageChatList',thi.$store.getters.getCurrentMessage)
  thi.items = thi.$store.getters.getCurrentMessage
  if(thi.items.message){
      thi.message=thi.items.message
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
    thi.message.push(data)
    thi.$store.commit('updateCurrentMessageChat',data)
    pushToCurrentMessageList(thi,data)
  }
}


function pushToCurrentMessageList(thi, data) {
  console.log('c1');
  console.log(data);
  let obj={}
  let message_list = thi.$store.getters.getCurrentMessageList;
  console.log('c2');
  let current_obj = thi.$store.getters.getCurrentMessage;
  obj.data=data
  obj.currentMessage=current_obj
  thi.$store.commit('updateCurrentMessageList',obj)
  console.log(message_list);
  console.log(data);
  console.log(current_obj);
  console.log('pushToCurrentMessageList end');
}
