<template lang="html">
  <div class="row box">
    <el-tabs v-model="activeName" @tab-click="handleClick">

        <el-tab-pane label="消息"  name="first"></el-tab-pane>
        <el-tab-pane label="联系人" name="second"></el-tab-pane>
        <el-tab-pane label="我的" name="第三个"></el-tab-pane>
        <el-tab-pane label="设置" name="第四个">
           <span slot="label"><i class="el-icon-more"></i></span>
        </el-tab-pane>
      </el-tabs>
      <div class="col-xs-12">
             <router-view></router-view>
      </div>

  </div>

</template>

<script>
import '../../static/css/main.css'


let List = {
  '消息': 'Information',
  '联系人': 'Contacts',
  '我的': 'My'
}

export default {
  name: 'main',
  data() {
    return {
      activeName: 'second',
      show: true
    };
  },
  mounted: function() {
    this.$router.push({
      path: '/Main/Contacts'
    })
    console.log('mounted');
  },
  methods: {
    handleClick(tab, event) {
      console.log(event.target.innerText);
      if (event.target.innerText != "") {
        this.$router.push({
          path: '/Main/' + List[event.target.innerText]
        })
      }
    }
  }
}
</script>

<style lang="css">
body{
  font-family: "Helvetica Neue",Helvetica,"PingFang SC",
  "Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
.el-tabs__item{

  padding-left: 43px;
}
.el-tabs__active-bar{
  margin-left: 15px;

}
.box{
  margin: 0px;
}

</style>
