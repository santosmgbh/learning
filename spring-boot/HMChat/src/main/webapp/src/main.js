// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Router from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import VueResource from 'vue-resource'
import Notifications from 'vue-notification'
import Login from './components/Login'
import Cadastro from './components/Cadastro'
import Chat from './components/Chat'

Vue.use(Router)
Vue.use(BootstrapVue);
Vue.use(VueResource)
Vue.use(Notifications)

Vue.config.productionTip = false

function requireAuth (to, from, next) {
  if (!localStorage.getItem('Authorization')) {
    next({
      path: '/login'
    })
  } else {
    next()
  }
}

const router = new Router({
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
      component: Chat,
      beforeEnter: requireAuth
    }
    
  ]
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
