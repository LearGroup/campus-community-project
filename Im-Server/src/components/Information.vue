<template lang="html">
  <div class="col-xs-12 box-card">
    <div v-for="item in items" @dblclick.self="openChatPage(item)"
    @click.self="selectStyle(item)" @keyup.enter="openChatPage(item)"
    v-bind:data-id="item.minor_user" :class="item.class">
         <div class="avatar-box">
            <img class="avatar" :src="item.header_pic" alt="">
         </div>
         <p class="username">{{item.username}}</p>
    </div>
  </div>
</template>

<script>
export default {
  name:'information',
  data(){
    return {

    }
  }
}
</script>

<style lang="css">
</style>
