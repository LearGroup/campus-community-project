// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import $ from 'jquery'
import 'jquery'
import './assets/jquery/jquery-3.1.1.min'
import './assets/bootstrap/css/bootstrap.min.css'
import './assets/bootstrap/js/bootstrap.min'
import './assets/font-awesome/css/font-awesome.min.css'
import Vue from 'vue'
import App from './App'
import router from './router'
import login from '../static/js/login'
import store from '../static/js/store'
import ElementUI  from 'element-ui'
import io from '../static/js/chatClient'
import 'element-ui/lib/theme-default/index.css'
import '../static/css/index.css'
import MintUI from 'mint-ui'
import '../static/js/customOrder'
import 'mint-ui/lib/style.css'
Vue.use(MintUI)
Vue.use(login)
Vue.use(ElementUI)
Vue.use(io)
Vue.config.productionTip = false


/* eslint-disable no-new */
$('#app')
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
