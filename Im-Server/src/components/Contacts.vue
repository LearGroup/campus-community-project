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
import contacts from '../../static/js/contacts'
export default {
  name: 'contacts',
  data() {
    return {
      items: [{}],
    }
  },
  beforeMount:function(){

  },
  mounted: function() {
    contacts.Init(this)
    contacts.getFrendList(this)
    console.log('contacts:'+this.$parent.$parent.$data.item.height);
  },
  methods: {
    selectStyle: function(items) {
      console.log('clicked');
    
      if (document.body.clientWidth > 768) {
        this.$nextTick(function() {
          this.items.forEach(function(item) {
            item.class = "row contact-item"
          })
          items.class = "row contact-item activate"
        })
      } else {
        contacts.pushToMessagePage(this, items)
      }
    },
    openChatPage: function(items) {
      contacts.pushToMessagePage(this, items)
    },

  },
  computed: {
    getData: function() {

    }
  }
}
</script>

<style lang="css">
.contact-item{
  border-radius: 4px 4px 4px 4px;
  height: 50px;
}
.contact-item:hover{
  background: #DDDDDD;
}
.activate{
  background:#CCCCCC;
}

.box-card{

}
.avatar{

}
.avatar-box{
  float: left;
  margin-left: 10px;
  margin-top: 4px;
  padding-top: 2px;
  height: 40px;
  width: 40px;
  border: solid .5px  #BBBBBB;
  border-radius: 50%;
}

.username{
  margin-left: 10px;
  margin-top: 5.5px;
  font-size: 12.5px;
  float: left;
}
</style>
