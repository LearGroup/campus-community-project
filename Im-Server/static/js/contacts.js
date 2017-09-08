export default {
  Init: Init,
  getFrendList: getFrendList,
  pushToMessagePage: pushToMessagePage
}


function Init(thi) {
  console.log('init');

  console.log(thi.$parent.$parent.height);
  if (document.body.clientWidth > 768) {
      thi.$parent.$parent.height = window.innerHeight - 150+'px'
  } else {
  hi.$parent.$parent.height = window.innerHeight +'px'
  }
  $(".contact-item").on("click", function(even) {
    //  $(".contact-item").removeClass('actived')
    $(even).attr('class', 'contact-item actived')
    //.attr('class','contact-item actived')
  })
}

function pushToMessagePage(thi, item) {
  console.log('updateCureentMessage:' + item);
  thi.$store.commit('updateCureentMessage', item)
  thi.$router.push({
    path: '/ChatPage'
  })
}

function getFrendList(thi) {
  console.log('getFrendList');
  if (!thi.$store.getters.getFrendList || thi.$store.getters.getFrendList.length == 0) {

    thi.checkStatus('/Main/Contacts')

    $.ajax({
      type: 'post',
      dataType: 'json',
      async: true,
      url: "/getFrendList",
      xhrFields: {
        withCredentials: true
      },
      data: {
        id: thi.$store.getters.getUserId
      },
      crossDomain: true,
      success: function(data) {
        for (var i = 0; i < data.length; i++) {
          data[i]['class'] = 'row contact-item'
          data[i]['header_pic'] = data[i]['header_pic'] + '?x-oss-process=image/resize,m_lfit,h_35,w_35'
        }
        console.log('frendList');
        thi.$store.commit('updateUserFrendList', data)
        console.log(thi.$store.getters.getFrendList)
        thi.items = thi.$store.getters.getFrendList
        console.log(thi.items);
      }
    })

  } else {
    thi.items = thi.$store.getters.getFrendList
    console.log(thi.items);
  }
}
