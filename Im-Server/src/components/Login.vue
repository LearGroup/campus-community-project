<template>
<div class="login col-xs-12">

  <div class="panel panel-heading   col-xs-12   col-lg-12 col-lg-push-1 login-box text-center">
    <h1 class="col-xs-12">{{ msg }}</h1>
    <div class="input-group login_input_username_div " id="login_input_username_div">
      <span class="input-group-addon"><i class="fa fa-user-o"></i></span>
      <input type="text" name="login_username" class="form-control " id="login_input_username" placeholder="手机号/邮箱">
    </div>
    <br>
    <div class="input-group login_input_password_div" id="login_input_password_div">
      <span class="input-group-addon"><i class="fa fa-key"></i></span>
      <input type="text" name="login_password" id="login_input_password" class="form-control" placeholder="密码">
    </div>

    <button v-on:click="login(0)" type="button" class="col-xs-12 btn btn-default btn-group-vertical login-btn" name="button" style="color:#42b983;">登陆</button>
    <div class="col-xs-12 text-center">
      <a href="#">忘记密码</a>
      <a href="#">|</a>
      <a href="#">注册</a>
    </div>



  </div>
</div>
</template>

<script>
import '../../static/css/login.css'
export default {
  name: 'login',
  data() {
    return {
      msg: 'Im-Servers',
    }
  },
  created: function() {
    this.checkStatus('Main/Contacts')
  },
  methods: {
    login: function(tag) {
      this.Login(this, tag)
    }
  }
}
</script>
<style media="screen">
.login{}
.login-box {
  background: #eeeeee;
  padding-left: 15px;
  padding-right: 15px;
  padding-bottom: 10px;
  margin-top: 80px;
}
</style>

<!-- Add "scoped" attribute to limit CSS to this component only -->
