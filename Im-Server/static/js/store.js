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
    deviceHeight: null

  },
  getters: {
    getSocket: state => state.socket,
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
      state.socket = socket
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
    updateCurrentMessageList(state, item) {
      console.log('updateCurrentMessageList');

      if (state.currentMessageList.length == 0) {
        console.log('c1');
        state.currentMessageList = JSON.parse(sessionStorage.getItem('currentMessageList'))
        console.log('c2');
        console.log(state.currentMessageList);
        if (!state.currentMessageList) {
          console.log('c3');
          state.cureentMessageList=[]
          state.cureentMessageList.push(item)
          sessionStorage.setItem('cureentMessageList', JSON.stringify(state.cureentMessageList))
          return
        }
      }
      for (var i = 0; i < state.currentMessageList.length; i++) {
        if (state.currentMessageList[i].minor_user == item.minor_user) {
          state.currentMessageList[i] = item
          sessionStorage.setItem('cureentMessageList', JSON.stringify(state.cureentMessageList))
        }
      }
    },
    updateCurrentMessageChat(state,data){
      console.log('updateCurrentMessageChat');
      if(!state.currentMessage){
        state.currentMessage = JSON.parse(sessionStorage.getItem('currentMessage'))
      }
      if(!state.currentMessage.message){
        state.currentMessage.message=[]
      }
      state.currentMessage.message.push(data)
      sessionStorage.setItem('currentMessage', JSON.stringify(state.currentMessage))
    }

  },
  actions: {},
  modules: {}
})
