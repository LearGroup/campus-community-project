export default {
  getMessageData:getMessageData,
  sendMessage:sendMessage
}


function getMessageData(thi){
   console.log('getMessageData');
   console.log(sessionStorage.currentMessage);

   if(sessionStorage.getCureentMessage){
     this.items=thi.$store.getters.getCureentMessage
   }else{
       let th=this
       let t=thi
       thi.checkStatus('ChatPage',function(){
          th.items=thi.$store.getters.getCureentMessage
          console.log('callback items');
          console.log(t.$store.getters.getCureentMessage);
       })

   }
}


function sendMessage(thi){
    console.log('sendMessage');
}
