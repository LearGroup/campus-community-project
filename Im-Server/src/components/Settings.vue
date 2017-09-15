<template lang="html">
  <div class="col-xs-12 my-box">
    <div class="row header-box">
       <div class="col-xs-12 head-back-img"></div>
       <div class="col-xs-12 header-content-box">
         <img src="../../static/img/back-img.png" alt="" class="user-img">
         <p class="header-username">{{header_item.username}}</p>
         <p class="custom-signature">{{header_item.custom_signature}}</p>
       </div>
    </div>
    <div class="row body-content">
      <div v-for="item in header_item.details" class="col-xs-12 person-details">
        <i :class="item.class"></i>
        <p class="content" :style="item.style">{{item.content}}</p>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: 'settings',
  data() {
    return {
      header_item: {
        header_pic: '',
        username: '劳谦',
        custom_signature: '其生若浮，其死若休。',
        details: [{
          'class': 'fa fa-line-chart',
          'content': '12级',
          'style':''
        }, {
          'class': 'fa fa-user-o',
          'content': '男 21岁 双鱼座',
          'style':'margin-left:9px'
        },
      {
        'class':'fa fa-graduation-cap',
        'content':'内蒙古工业大学'
      }]
      }
    }
  },
  mounted: function() {
    let heigh
    if (document.body.clientWidth > 768) {
      heigh = (window.innerHeight - 150) + 'px'
    } else {
      heigh = window.innerHeight + 'px'
    }
    this.$parent.$parent.$data.item.height = heigh
  }
}
</script>

<style lang="css">
.body-content .content{
  float: left;
  margin-top: 13px;
  font-size: 16px;
}
.person-details i{
  float:left;
  color: #666666;
  margin-left: 15px;
  margin-right: 15px;
  margin-top: 16px;
  font-size: 18px;
}
.header-username{
  font-size: 20px;
  color: #ffffff;
  margin-top: 5px;
}
.custom-signature{
  margin-top: -10px;
  color: #ffffff;
  font-size: 14px;
}

.header-content-box{
z-index: 15;
}
.user-img{

  height: 75px;
  width: 75px;
  opacity: 1;
  border-radius: 50%;
  margin-top: 15px;
  border: 1.5px solid #dddddd;
}
.header-box{
  position: relative;
  height:160px;
  margin-top: -3px;

}
.head-back-img{
  z-index: 10;
  position:absolute;
  height:160px;
  margin-top: -3px;
  opacity: 0.8;
  background-repeat:no-repeat;
  background-size: cover;
  background-image: url('../../static/img/back-img.png');
}

</style>
