<template lang="html">
<div class="chatBox">

  <mt-header :title="items.username">
  <router-link to="/Main/Contacts" slot="left">
    <mt-button icon="back">返回</mt-button>
  </router-link>
  <mt-button icon="more" slot="right"></mt-button>
</mt-header>
<div class="row message-box" v-bind:style="viewconfig.messageBoxStyle">
  <div class="col-xs-12 real-height-box">
    <div id="convo"  v-for="item in message" class="row chat-thread chat-item" v-bind:class="item.objectType">
    <div class="message-time-box">
      <p class="message-time">{{item.time}}</p>
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
      <div v-html="item.chat"  v-if="item.objectType==='opposite'" class="chat-content-left">

        </div>
          <div v-html="item.chat"  style="white-space: pre-line"  v-else   class="chat-content-right">

          </div>
    </div>
  </div>

</div>
<div class="row footer-box">
  <div class="col-xs-8 input-box">
    <el-input
        type="textarea"   :autofocus="autofocus"   :autosize="{ minRows: 1, maxRows: 2}"   v-model="sendMessages">
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
let example = {
  time: '昨天 下午4:40',
  objectType: 'opposite',
  chat: '<p>Hello World!</p>',
}
export default {
  name: 'chatpage',
  data() {

    return {
      autofocus: true,
      placeholder: '请输入内容',
      viewconfig: {
        messageBoxStyle: {
          'max-height': '0px',
          'min-height': '0px',
          overflow: 'auto',
          'overflow-x': 'hidden'

        }
      },
      sendMessages: '',
      items: {},
      message: [],
      own: {
        username: '',
        id: '',
        header_pic: ''
      }

    }
  },
  beforeMount: function() {
    let heigh, higt
    if (document.body.clientWidth > 768) {
      heigh = window.innerHeight - 150 + 'px'
      higt = window.innerHeight - 240 + 'px'
    } else {
      heigh = window.innerHeight + 'px'
      higt = window.innerHeight - 100 + 'px'
    }
     console.log("ChatPagebeForeMount")
    this.$parent.$data.item.height = heigh
    this.viewconfig.messageBoxStyle['max-height'] = higt
    this.viewconfig.messageBoxStyle['min-height'] = higt
    pageJs.getMessageData(this)
  },
  methods: {
    sendMessage: function(event) {
       console.log("ChatPageSendMessage")
      pageJs.sendMessage(this, event)
    }
  },
  watch: {
    message: function() {
      console.log('watch start');
      $(".message-box").animate({
        'scrollTop':$('.real-height-box').height()+'px'
    }, 500)
  //    $('.message-box').scrollTop = $('.message-box').scrollHeight;
    }
  }


}
</script>

<style lang="css">
.mint-header{
  background-color:#eeeeee;
}
</style>
