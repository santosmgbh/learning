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
          <button @click="signup()">Cadastre-se</button>
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
            localStorage.setItem('Authorization', response.headers.map.authorization[0]);            
            localStorage.setItem('User', {username: me.username, password:me.password})
            this.$router.push({name: "chat"});
          },
          error => {
            console.log(error);
          }
        )
        .finally(function() {});
      // this.$router.push("chat");
    },
    signup(){
      this.$router.push({name: "cadastro"});
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
