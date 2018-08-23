import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Cadastro from '@/components/Cadastro'
import Chat from '@/components/Chat'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/cadastro',
      name: 'Cadastro',
      component: Cadastro
    },
    {
      path: '/chat',
      name: 'Chat',
      component: Chat
    }
    
  ]
})
