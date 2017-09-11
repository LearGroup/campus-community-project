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
        <transition :name="transitionName">
          <router-view  class="main-view"></router-view>
        </transition>

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
      show: true,
      transitionName: 'slide-left'
    };
  },
  watch:{
    '$route'(to,from){
      let lis={Information:1,Contacts:2,My:3,Settings:4}
      const toDepth = lis[to.name]
      const fromDepth = lis[from.name]
      this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'
      console.log(toDepth,fromDepth);
    }
  },
  mounted: function() {
    let heigh
    if (document.body.clientWidth > 768) {
      heigh = (window.innerHeight - 150) + 'px'
    } else {
      heigh = window.innerHeight + 'px'
    }
    this.$parent.$data.item.height = heigh
    this.$router.push({
      path: '/Main/Contacts'
    })
    console.log('main mounted');
    console.log(this.$parent.item.height);
  },
  methods: {
    handleClick(tab, event) {

      if (event.target.innerText != "") {
        this.$router.push({
          path: '/Main/' + List[event.target.innerText]
        })
      }else{

        this.$router.push({
            path: '/Main/' + 'Settings'
        })

      }
    }
  }
}
</script>

<style lang="css">
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
