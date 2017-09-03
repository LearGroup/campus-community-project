import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Main from '@/components/Main'
import Information from '@/components/Information'
import Contacts from '@/components/Contacts'
import My from '@/components/My'
import Settings from '@/components/Settings'
import ChatPage from '@/components/ChatPage'
Vue.use(Router)

const router =new Router({
  ode: 'history',
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello,
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login,
    },
    {
      path:'/Main',
      name:'Main',
      component:Main,
      meta:{requiresAuth:true},
      children:[
        {path:'/Main/Information',name:'Information',component:Information},
        {path:'/Main/Contacts',name:'Contacts',component:Contacts},
        {path:'/Main/My',name:'My',component:My},
        {path:'/Main/Settings',name:'Settings',component:Settings}
      ]
    },
    {
      path:'/ChatPage',
      name:'ChatPage',
      component:ChatPage
    }
  ]
})

export default router
