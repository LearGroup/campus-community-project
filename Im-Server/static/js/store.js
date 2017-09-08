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
    deviceHeight:null

  },
  getters: {
    getSocket: state => state.socket,
    getState: state => state.userState,
    getFrendList: state => state.frendList,
    getUserId: state => state.userId,
    getHeadImageUrl: state => state.headImageUrl,
    getUserName: state => state.userName,
    getCurrentMessageList: state => state.cureentMessageList,
    getCureentMessage: state => state.cureentMessage
  },
  mutations: {
    updateSocket(state,socket){
      state.socket=socket
    },
    updateUserState(state, State) {
      state.userState = State.userState
      state.userName = State.userName
      state.userId = State.userId
      state.headImageUrl = State.headImageUrl
    },
    updateUserFrendList(state, List) {
      state.frendList = List
    },
    updateCureentMessage(state, item) {
      state.cureentMessage = item
      state.cureentMessage['activeTime'] = new Date()
      sessionStorage.currentMessage =JSON.stringify(state.cureentMessage);
    },
    updateDeviceHeight(state,height){
      state.deviceHeight = item
      sessionStorage.deviceHeight =height;
    }

  },
  actions: {},
  modules: {}
})
