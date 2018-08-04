import Vue from 'vue';
import App from './App.vue';

/**
 * Criando uma instância global de Vue,
 * associando ao id #app no HTML e renderizando
 * a classe App (principal)
 */
new Vue({
  el: '#app',
  render: h => h(App),
});
