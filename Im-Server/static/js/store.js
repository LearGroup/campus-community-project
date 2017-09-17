import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userState: 0,
    userName: null,
    userId: null,
    frendList: null,
    headImageUrl: null,
    currentMessageList: [],
    currentMessage: null,
    socket: null,
    deviceHeight: null,

  },
  getters: {
    getSocket: state => {
      return state.socket
    },
    getState: state => {
      return state.userState
    },
    getFrendList: state => {
      if (!state.frendList && sessionStorage.frendList) {
        state.frendList = JSON.parse(sessionStorage.frendList)
      }
      return state.frendList
    },
    getUserId: state => {
      if (!state.userId && sessionStorage.userId) {
        state.userId = sessionStorage.userId
      }
      return state.userId
    },
    getHeadImageUrl: state => {
      if (!state.headImageUrl && sessionStorage.headImageUrl) {
        state.headImageUrl = sessionStorage.headImageUrl
      }
      return state.headImageUrl
    },
    getUserName: state => {
      if (!state.userName && sessionStorage.userName) {
        state.userName = sessionStorage.userName
      }
      return state.userName
    },
    getCurrentMessageList: state => {

      if (!state.currentMessageList) {
        state.currentMessageList = JSON.parse(sessionStorage.getItem('currentMessageList'))
      }
      return state.currentMessageList
    },
    getCurrentMessage: state => {
      if (!state.currentMessage && sessionStorage.currentMessage) {
        state.currentMessage = JSON.parse(sessionStorage.getItem('currentMessage'))
      }
      return state.currentMessage
    }
  },
  mutations: {
    updateSocket(state, socket) {
      state.socket = socket()
      if (!state.userName) {
        state.userName = sessionStorage.userName
        state.userId = sessionStorage.userId
      }
      state.socket.on('connect',function(){
        console.log('connect login');
        state.socket.emit('login', {
          userName: state.userName,
          userId: state.userId
        })
      })
      state.socket.on('reconnect',function(){
        console.log('reconnect login');
        state.socket.emit('login', {
          userName: state.userName,
          userId: state.userId
        })
      })

      state.socket.on("loginResponse", function(res) {
        console.log(res);
      })

    },
    updateUserState(state, State) {
      state.userState = State.userState
      state.userName = State.userName
      state.userId = State.userId
      state.headImageUrl = State.headImageUrl
      sessionStorage.userState = State.userState
      sessionStorage.userName = State.userName
      sessionStorage.userId = State.userId
      sessionStorage.headImageUrl = State.headImageUrl
    },
    updateUserFrendList(state, List) {
      console.log(List);
      state.frendList = List
      sessionStorage.setItem('userFrendList', JSON.stringify(state.frendList))
    },
    updateCurrentMessage(state, item) {
      item['activeTime'] = new Date()
      var str = JSON.stringify(item)
      sessionStorage.setItem('currentMessage', str)
      state.currentMessage = item
    },
    updateDeviceHeight(state, height) {
      state.deviceHeight = height
      sessionStorage.deviceHeight = height;
    },

    updateCurrentMessageList(state, obj) {
      console.log('updateCurrentMessageList');
      console.log(obj.data);
      console.log(state.currentMessageList);
      //如果currentMessageList不存在，创建一个
      if (!state.currentMessageList || state.currentMessageList.length == 0) {
        console.log('cs1');
        let temp = JSON.parse(sessionStorage.getItem('currentMessageList'))
        state.currentMessageList = temp
        console.log('cs2');
        console.log(state.currentMessageList);
        if (!state.currentMessageList) {
          console.log('cs3');
          state.currentMessageList = []
          state.currentMessageList.push(obj.currentMessage)
          state.currentMessageList[0].message.push(obj.data)
          let tem = JSON.stringify(state.currentMessageList)
          sessionStorage.setItem('currentMessageList', tem)
          return
        }
      }
      //若currentMessageList 并且对应的currentMessage存在，插入聊天数据
      for (var i = 0; i < state.currentMessageList.length; i++) {
        console.log('fors');
        console.log(state.currentMessageList);
        console.log(state.currentMessageList[i].minor_user);
        console.log(obj.currentMessage.minor_user);
        console.log(state.currentMessageList[i].minor_user == obj.currentMessage.minor_user);
        console.log(obj.data);
        console.log(obj.data != null);
        if (state.currentMessageList[i].minor_user == obj.currentMessage.minor_user && obj.data != null) {
          state.currentMessageList[i].message.push(obj.data)
          let temps = JSON.stringify(state.currentMessageList)
          sessionStorage.setItem('currentMessageList', temps)
          console.log('cs for');
          console.log(state.currentMessageList);
          return 0;
        }
      }
      //若currentMessageList 并且对应的currentMessage不存在，在currentMessageList中创建一个对应的currentMessage
      console.log('cs4');
      console.log(obj.currentMessage);
      obj.currentMessage.message.push(obj.data)
      state.currentMessageList.push(obj.currentMessage)
      sessionStorage.setItem('currentMessageList', JSON.stringify(state.currentMessageList))

    },
    updateCurrentMessageChat(state, data) {
      console.log('updateCurrentMessageChat');
      if (!state.currentMessage) {
        console.log('updateCurrentMessageChat 1');
        state.currentMessage = JSON.parse(sessionStorage.getItem('currentMessage'))
      }
      if (!state.currentMessage.message) {
        console.log('updateCurrentMessageChat 2');
        state.currentMessage.message = []
      }
      if (data != null) {
        console.log('updateCurrentMessageChat 3');
        state.currentMessage.message.push(data)
        sessionStorage.setItem('currentMessage', JSON.stringify(state.currentMessage))

      }
    },
    //获取当前currentMessage历史聊天记录
    updateCurrentMessageChatList(state, currentMessage) {
      console.log('updateCurrentMessageChatList');
      state.currentMessageList = JSON.parse(sessionStorage.getItem('currentMessageList'))
      console.log(state.currentMessageList);
      if (state.currentMessageList != null && state.currentMessageList.length != 0) {
        for (var i = 0; i < state.currentMessageList.length; i++) {
          if (state.currentMessageList[i].minor_user == currentMessage.minor_user) {
            state.currentMessage = state.currentMessageList[i]
            sessionStorage.setItem('currentMessage', JSON.stringify(state.currentMessage))
          }
        }
      }
    }

  },
  actions: {},
  modules: {}
})
