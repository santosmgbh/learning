<template>
  <div>

    <table>
      <body>
        <tr>
          <label >Login</label>
          <input type="text" v-model="username"/>
        </tr>
        <tr>
          <label >Senha</label>
          <input type="password" v-model="password"/>
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
      username: '',
      password: ''
    };
  },
  methods: {

    login() {          
      
      var config = {headers: {'Content-Type': 'application/json'}};
      // config.body = {"name":"josé","username": "12345", "password":"12345", "active":"1"};
      var me = this;
      this.$http
        .post('/hmchat-api/login', {username: this.username, password:this.password})
        .then(
          response => {
            this.$router.push({name: "chat", params: {username:me.username}});
          },
          error => {
            console.log(error);
          }
        )
        .finally(function() {});
      // this.$router.push("chat");
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
