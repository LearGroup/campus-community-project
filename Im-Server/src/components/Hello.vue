<template>
<div class="hello col-xs-push-1 col-xs-12 panel panel-heading ">

  <div class="col-xs-12 col-lg-12">
    <h1 class="col-xs-12 col-lg-12">{{ msg }}</h1>
    <h2 class="col-xs-12 col-lg-12">及时通讯系统</h2>
    <ul>
      <li<router-link :to="{ name: 'Login',path: '/Login', params: {} }">去登陆</router-link>
        </li>
    </ul>
  </div>
</div>
</template>

<script>
export default {
  name: 'hello',
  data() {
    return {
      msg: 'Im-Server'
    }
  },
  created: function() {
    this.checkStatus("/Main/Contacts")
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.hello {
  margin-top: 100px;
}

h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
