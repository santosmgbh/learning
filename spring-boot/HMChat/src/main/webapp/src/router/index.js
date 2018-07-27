import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Cadastro from '@/components/Cadastro'
import Chat from '@/components/Chat'
import BootstrapVue from 'bootstrap-vue'
import VueResource from 'vue-resource'


Vue.use(Router)
Vue.use(BootstrapVue);
Vue.use(VueResource)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/cadastro',
      name: 'cadastro',
      component: Cadastro
    },
    {
      path: '/chat',
      name: 'chat',
      component: Chat
    }
    
  ]
})
