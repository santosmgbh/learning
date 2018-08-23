<template>
  <div id="app">
    <div class="col-md-12">
      <Character v-for="(id, index) in initial_ids" :id="id" :key="index"/>
    </div>
    <Character :id="1"/>
  </div>
</template>

<script>
import Character from './components/Character.vue'

export default {
  name: 'app',
  data () {
    return {      
      msg: 'Welcome to Your Vue.js App',
      initial_ids: [1, 13, 14]
    }
  },
  components: {
    Character
  }
}
</script>

<style>


/* h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
} */
</style>
