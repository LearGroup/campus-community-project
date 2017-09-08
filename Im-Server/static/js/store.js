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
    cureentMessageList: null,
    cureentMessage: null,
    socket: null,
    deviceHeight: null

  },
  getters: {
    getSocket: state => state.socket,
    getState: state => {

      if (!state.userState && sessionStorage.headImageUrl) {
        state.userState = sessionStorage.headImageUrl
      }
      return state.userState
    },
    getFrendList: state => {
      if (!state.frendList && sessionStorage.frendList) {
        state.frendList = JSON.parse(sessionStorage.frendList)
      }
      return state.frendList
    },
    getUserId: state => {
      if (!state.userId) {
        state.userId = sessionStorage.userId
      }
      return state.userId
    },
    getHeadImageUrl: state => {
      if (!state.headImageUrl) {
        state.headImageUrl = sessionStorage.headImageUrl
      }
      return state.headImageUrl
    },
    getUserName: state => {
      if (!state.userName) {
        state.userName = sessionStorage.userName
      }
      return state.userName
    },
    getCurrentMessageList: state => {

      if (!state.cureentMessageList) {
        state.cureentMessageList = JSON.parse(sessionStorage.cureentMessageList)
      }
      return state.cureentMessageList
    },
    getCureentMessage: state => {
      if (!state.cureentMessage) {
        state.cureentMessage = JSON.parse(sessionStorage.cureentMessage)
      }
      return state.cureentMessage
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
      sessionStorage.frendList == JSON.stringify(state.frendList);
    },
    updateCureentMessage(state, item) {
      state.cureentMessage = item
      state.cureentMessage['activeTime'] = new Date()
      sessionStorage.currentMessage = JSON.stringify(state.cureentMessage);
    },
    updateDeviceHeight(state, height) {
      state.deviceHeight = height
      sessionStorage.deviceHeight = height;
    }

  },
  actions: {},
  modules: {}
})
