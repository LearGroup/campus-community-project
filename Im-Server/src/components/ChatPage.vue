<template lang="html">
<div class="chatBox">

  <mt-header :title="items.username">
  <router-link to="/Main/Contacts" slot="left">
    <mt-button icon="back">返回</mt-button>
  </router-link>
  <mt-button icon="more" slot="right"></mt-button>
</mt-header>
<div class="row message-box">
  <div class="col-xs-12 ">
    <div id="convo"  v-for="item in message" class="row chat-thread chat-item" v-bind:class="item.objectType">
    <div class="message-time-box">
      <p class="message-time">昨天 下午5:31</p>
    </div>
     <div v-if="item.objectType==='opposite'" class="avatar-chatePage-left">
         <img class="avatar" :src="items.header_pic" alt="">
      </div>
      <div v-else class="avatar-chatePage-right">
          <img class="avatar" :src="own.header_pic" alt="">
      </div>
      <div  v-if="item.objectType==='opposite'"  class="shape-tag-left">

      </div>
      <div   v-else  class="shape-tag-right">

      </div>
      <div  v-if="item.objectType==='opposite'" class="chat-content-left">
            <p>awdawdawdawdaw</p>
        </div>
        <div   v-else   class="chat-content-right">
              <p>awdawdawdawdaw</p>
          </div>
    </div>
  </div>

</div>
<div class="row footer-box">
  <div class="col-xs-8 input-box">
    <el-input
    type="textarea" :autosize="{ minRows: 1, maxRows: 2}" placeholder="请输入内容"  v-model="textarea">
  </el-input>
  </div>
  <div class="col-xs-2 express">
     <i class=" fa fa-smile-o express-icon"></i>
  </div>
  <div class="col-xs-2 send">
<el-button  type="primary" v-on:click="sendMessage">发送</el-button>
  </div>

</div>
</div>

</template>

<script>
import pageJs from '../../static/js/chatPage'
import '../../static/css/chatUI.css'
export default {
  name: 'chatpage',
  data() {

    return {
      textarea: '',
      items: {},
      message: [{
        objectType: 'opposite',
        chat: '<p>Hello World!</p>',
      }, {
        objectType: 'own',
        chat: '<p>Hello Friend!</p>'
      }]  ,
      own:{username:'',id:'',header_pic:''}

    }
  },
  mounted: function() {
    if (document.body.clientWidth > 768) {
      this.$parent.height = window.innerHeight - 150+'px'
      $('.message-box').height(window.innerHeight - 240)
    } else {
      this.$parent.height = window.innerHeight+'px'
      $('.message-box').height(window.innerHeight- 100)

    }
    pageJs.getMessageData(this)
  },
  methods: {
    sendMessage: function(event) {
      pageJs.sendMessage(this,event)
    }
  }

}
</script>

<style lang="css">

.send{
  background-color: #eeeeee;
}
.express{
  background-color: #eeeeee;
}
.message-box .avatar{
  height: 100%;
  width: 100%;
  margin-top: -3px;
  border-radius: 50%;
}
.chatBox .el-button{
   line-height: 0.5;
   margin-left: -15px;
}
.chatBox .el-button .el-button--primary{
  line-height: .8;
}
.message-time-box .message-time{
  font-size: 10px;
}
.avatar-chatePage-left{
  float: left;
  margin-left: 10px;
  margin-top: 4px;
  padding-top: 2px;
  height: 35px;
  width: 35px;
  border: solid .5px  #BBBBBB;
  border-radius: 50%;
}
.avatar-chatePage-right{
  float: right;
  margin-right: 10px;
  margin-top: 4px;
  padding-top: 2px;
  height: 35px;
  width: 35px;
  border: solid .5px  #BBBBBB;
  border-radius: 50%;
}

.chat-thread  .chat-content-left p{
  margin-bottom: 0px;
}
.chat-thread  .chat-content-right p{
  margin-bottom: 0px;
}
.chat-thread  .chat-content-left{
  margin-top:  5px;
  padding-top: 6px;
  padding-bottom:6px;
  padding-left: 10px;
  padding-right: 10px;
  min-height: 35px;
  float: left;
  border-radius: 5px;
  background-color: #dddddd;
}
.chat-thread  .chat-content-right{
  margin-top:  5px;
  padding-top: 6px;
  padding-bottom:6px;
  padding-left: 10px;
  padding-right: 10px;
  min-height: 35px;
  float: right;
  border-radius: 5px;
  background-color: #42B983;
}

.shape-tag-left{
  margin-left:-4px;
  margin-top: 14px;
  float: left;
  border-left: 8px solid transparent;
  border-bottom: 8px solid transparent;
  border-right:8px solid #dddddd;
}

.shape-tag-right{
  margin-right:-4px;
  margin-top: 14px;
  float: right;
  border-right:8px solid transparent;
  border-bottom: 8px solid transparent;
  border-left: 8px solid #42B983 ;
}


.opposite{

}
.chat-item{
  margin-top: 10px;
  font-size: 12.5px;
}
.express-icon{
  margin-left: -15px;
  font-size: 30px;
  color:#666666;
}
.input-box{

  background-color:#eeeeee;
  padding-top: 4px;
  padding-right: 5px;
  padding-bottom: 4px;
}
.footer-box{
  height: 80px;
  background-color: #eeeeee;
  border-top: 1px solid #DDDDDD;
}

.mint-header{
  background-color:#eeeeee;
}
.mintui{
  color: #333333;
}
.mint-button-text{
  font-size: 14px;
  font-weight: 400;
  color: #333333;
}
.chatBox{
  margin-top: 12px;
}
.mintui-back{

  caret-color: #333333;
  color: #333333;
}
.el-button--succes{
  white-space: inherit;
}
.mint-header-title{
  color: #333333;
  margin-top: 3px;
}
.mint-header-button.is-right{
  margin-top: -4px;
}
.el-textarea__inner{
  outline:none;resize:none;
  border-bottom: 1px solid rgb(191, 217, 208) ;
  border-top:0px;
  border-left:0px;
  border-right :0px;
  border-top-color: #eeeeee;
  border-left-color: #eeeeee;
  border-right-color: #eeeeee;
}
.el-textarea__inner:hover{

  border-bottom-color: rgb(131, 165, 154);
  border-top-color: #eeeeee;
  border-left-color: #eeeeee;
  border-right-color: #eeeeee;
  border-left:0px;
  border-right :0px;
}
.message-box{
  border-top: solid 1px #DDDDDD;

}
</style>
