import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import Data from './components/Data.vue'

Vue.use(VueRouter) // Utilizando a biblioteca VueRouter

const routes = [ // Criando as rotas.
  {path: '/data/:type', component: Data}
]

const router = new VueRouter({ //Objeto VueRouter que será passado para a aplicação
  routes
})

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
