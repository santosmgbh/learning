<template>
  <b-container fluid>  
    <a href="http://localhost:8090/hmchat-api/swagger-ui.html">Docs</a>

 <b-form class="mx-auto" style="width: 300px;" >
      <b-form-group label="Username:"
                    label-for="input1">
        <b-form-input id="input1"
                      type="text"
                      v-model="username"
                      required
                      placeholder="Email">
        </b-form-input>
      </b-form-group>
      <b-form-group label="Senha:"
                    label-for="input2">
        <b-form-input id="input2"
                      type="password"
                      v-model="password"
                      required
                      placeholder="Senha">
        </b-form-input>
      </b-form-group>      
      <b-button type="button" @click="login" variant="success">Acessar</b-button>
      <b-button type="button" @click="signup()" variant="primary">Cadastrar</b-button>
    </b-form>    
  <!-- Content here -->
</b-container>
</template>

<script>
import Notification from '../util/notification'
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
        .post(process.env.API_URL+'login', {username: this.username, password:this.password})
        .then(
          response => {            
            localStorage.setItem('Authorization', response.headers.map.authorization[0]);            
            localStorage.setItem('User', JSON.stringify({username: me.username, password:me.password}))
            this.$router.push({name: "chat"});
          },
          error => {
            Notification.showBasic("Acesso", "Usuário e/ou senha incorretos!", "error");
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
