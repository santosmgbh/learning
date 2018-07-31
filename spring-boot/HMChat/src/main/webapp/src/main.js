// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Router from 'vue-router'
import BootstrapVue from 'bootstrap-vue'
import VueResource from 'vue-resource'
import Notifications from 'vue-notification'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faCoffee } from '@fortawesome/free-solid-svg-icons'
import { faCheck } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import Login from './components/Login'
import Cadastro from './components/Cadastro'
import Chat from './components/Chat'
import moment from 'moment'


Vue.use(Router)
Vue.use(BootstrapVue);
Vue.use(VueResource)
Vue.use(Notifications)

library.add(faCheck)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.filter('formatDate', function(value) {  
  if (value) {    
    return moment(new Date(value)).format('DD/MM/YYYY hh:mm')
  }
});

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
