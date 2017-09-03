<template>
<div id="app" class="container-fluid root">
  <div class="col-xs-12 col-lg-3 col-lg-push-4 content">
    <router-view></router-view>
  </div>
</div>
</template>

<script>
export default {
  name: 'app',
  mounted: function() {
  if (document.body.clientWidth > 768){
      $('.content').height(0)
  }else{
    $('.content').height(window.innerHeight)
  }
  }
}
</script>

<style>
.root {
  margin: 0px;
}

.content {
  height: 0px;
  background: #eeeeee;
  padding: 0px;
  margin: 0px;
}

#app {
  margin: 0px;
  padding: 0px;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  text-align: center;
}




/* 需要在 bootstrap.css 之后调用 */


/* Fuck 15px for iPhone Start */

.row {
  margin: 0;
}

.col-lg-1,
.col-lg-10,
.col-lg-11,
.col-lg-12,
.col-lg-2,
.col-lg-3,
.col-lg-4,
.col-lg-5,
.col-lg-6,
.col-lg-7,
.col-lg-8,
.col-lg-9,
.col-md-1,
.col-md-10,
.col-md-11,
.col-md-12,
.col-md-2,
.col-md-3,
.col-md-4,
.col-md-5,
.col-md-6,
.col-md-7,
.col-md-8,
.col-md-9,
.col-sm-1,
.col-sm-10,
.col-sm-11,
.col-sm-12,
.col-sm-2,
.col-sm-3,
.col-sm-4,
.col-sm-5,
.col-sm-6,
.col-sm-7,
.col-sm-8,
.col-sm-9,
.col-xs-1,
.col-xs-10,
.col-xs-11,
.col-xs-12,
.col-xs-2,
.col-xs-3,
.col-xs-4,
.col-xs-5,
.col-xs-6,
.col-xs-7,
.col-xs-8,
.col-xs-9 {
  padding: 0;
}



/* Fuck 15px for iPhone Over */
</style>
