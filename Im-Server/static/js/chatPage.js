export default {
  getMessageData:getMessageData
}


function getMessageData(thi){
    thi.items=thi.$store.getters.getCureentMessage
}
