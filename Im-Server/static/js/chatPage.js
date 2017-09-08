export default {
  getMessageData:getMessageData,
  sendMessage:sendMessage
}


function getMessageData(thi){
     thi.items=thi.$store.getters.getCureentMessage   
}


function sendMessage(thi){
    console.log('sendMessage');
}
