<template  lang="html">
  <div class="row box"  v-finger:swipe="swipe">
    <el-tabs v-model="activeName" @tab-click="handleClick"   >

        <el-tab-pane label="消息"  name="Information"></el-tab-pane>
        <el-tab-pane label="联系人" name="Contacts"></el-tab-pane>
        <el-tab-pane label="我的" name="My"></el-tab-pane>
        <el-tab-pane label="设置" name="Settings">
           <span slot="label"><i class="el-icon-more"></i></span>
        </el-tab-pane>
      </el-tabs>

        <transition :name="transitionName">
          <router-view   class="main-view"  v-bind:style="{height:item.height}"></router-view>
        </transition>

  </div>

</template>

<script>
import '../../static/css/main.css'


let List = {
  '消息': 'Information',
  '联系人': 'Contacts',
  '我的': 'My',
  '设置': 'Settings'
}
let reVecto = [
  'Information',
  'Contacts',
  'My',
  'Settings'
]

let vecto = {
  'Information': 1,
  'Contacts': 2,
  'My': 3,
  'Settings': 4
}

export default {
  name: 'main',
  data() {
    return {
      item: {
        'height': '0px'
      },
      activeName: 'Contacts',
      activeView: 'Contacts',
      show: true,
      transitionName: 'slide-left'
    };
  },
  watch: {
    '$route' (to, from) {
      let lis = {
        Information: 1,
        Contacts: 2,
        My: 3,
        Settings: 4
      }
      const toDepth = lis[to.name]
      const fromDepth = lis[from.name]
      this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'

    }
  },
  mounted: function() {
    let heigh
    if (document.body.clientWidth > 768) {
      heigh = window.innerHeight - 150
    } else {
      heigh = window.innerHeight
    }
    this.$parent.$data.item.height = heigh + 'px'
    this.item.height = heigh - 56 + 'px'
    this.$router.push({
      path: '/Main/Contacts'
    })

  },
  methods: {
    handleClick(tab, event) {
      if (event.target.innerText != "") {
        this.activeView = List[event.target.innerText]
        this.$router.push({
          path: '/Main/' + List[event.target.innerText]
        })
      } else {
        this.activeView = 'Settings'
        this.$router.push({
          path: '/Main/' + 'Settings'
        })

      }
    },
    swipe: function(evt) {

      if (evt.direction == 'Left' && vecto[this.activeView] <= 3) {
        this.activeView = reVecto[vecto[this.activeView]]
        this.activeName = reVecto[vecto[this.activeView] - 1]
        this.$router.push({
          path: '/Main/' + reVecto[vecto[this.activeView] - 1]
        })
      } else if (evt.direction == 'Right' && vecto[this.activeView] >= 2) {
        this.activeView = reVecto[vecto[this.activeView] - 2]
        this.activeName = reVecto[vecto[this.activeView] - 1]
        this.$router.push({
          path: '/Main/' + reVecto[vecto[this.activeView] - 1]
        })
      }

    },
    pressMove: function(num, evt) {
      console.log(evt.deltaX);
      console.log(evt.deltaY);
      console.log('onPressMove with params:' + num);
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
.main-view{
  width: 100%;
}
.box{

  height: auto;
  margin: 0px;
}

</style>
