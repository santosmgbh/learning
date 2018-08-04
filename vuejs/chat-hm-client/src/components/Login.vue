<template>
  <div>

    <table>
      <body>
        <tr>
          <label >Login</label>
          <input type="text"/>
        </tr>
        <tr>
          <label >Senha</label>
          <input type="password"/>
        </tr>
        <tr>
          <button >Login</button>
          <button >Cadastre-se</button>
        </tr>
      </body>
    </table>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
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
}
</style>
