<template lang="html">
<div class="chatBox">
  <mt-header :title="items.username">
  <router-link to="/" slot="left">
    <mt-button icon="back">返回</mt-button>
  </router-link>
  <mt-button icon="more" slot="right"></mt-button>
</mt-header>
<div class="row">
  <div class="col-xs-12 message-box">
    <div id="convo"  v-for="item in message" class="row chat-thread chat-item" v-bind:class="item.objectType">
      <div class="avatar-chatePage">
         <img class="avatar" :src="item.header_pic" alt="">
      </div>
      <div class="shape-tag">

      </div>
      <div class="chat-content">
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
  <div class="col-xs-2">
     <i class="fa fa-smile-o express-icon"></i>
  </div>
  <div class="col-xs-2">
<el-button  type="primary">发送</el-button>
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
        chat: '<p>Hello World!</p>'
      }, {
        objectType: 'opposite',
        chat: '<p>Hello Friend!</p>'
      }]

    }
  },
  mounted: function() {
    if (document.body.clientWidth > 768) {
      $('.message-box').height(window.innerHeight - 240)
    } else {
      $('.message-box').height(window.innerHeight - 110)
    }
    pageJs.getMessageData(this)
    console.log('active Message');
    console.log(this.items);
  }
}
</script>

<style lang="css">
.avatar-chatePage{
  float: left;
  margin-left: 10px;
  margin-top: 4px;
  padding-top: 2px;
  height: 35px;
  width: 35px;
  border: solid .5px  #BBBBBB;
  border-radius: 50%;
}

.chat-thread  .chat-content p{
  margin-bottom: 0px;
}
.chat-thread  .chat-content{
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

.shape-tag{
  margin-left:0px;
  margin-top: 14px;
  float: left;
  border-left: 8px solid transparent;
  border-bottom: 8px solid transparent;
  border-right:8px solid #dddddd;
}


.opposite{

}
.chat-item{
  margin-top: 10px;
  font-size: 12.5px;
}
.express-icon{
  font-size: 30px;
  color:#666666;
}
.input-box{
  padding-right: 5px;
}
.footer-box{
  padding-top:6px;
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
  margin-top: 4px;
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
