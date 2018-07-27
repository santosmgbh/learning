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
          <button @click="login()">Login</button>
          <button >Cadastre-se</button>
        </tr>
      </body>
    </table>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  },
  methods: {
    login() {      
      this.$http
        .get(process.env.API_URL)
        .then(
          response => {
            console.log(response);
          },
          error => {
            console.log(error);
          }
        )
        .finally(function() {});
      this.$router.push("chat");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
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
