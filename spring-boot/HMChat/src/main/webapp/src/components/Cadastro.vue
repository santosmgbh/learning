<template>
    <div>
          <table>
      <body>
        <tr>
          <label>Nome:</label>
          <input type="text" v-model="user.name"/>
        </tr>
        <tr>
          <label>Usuário:</label>
          <input type="text" v-model="user.username"/>
        </tr>
        <tr>
          <label>Senha:</label>
          <input type="password" v-model="user.password"/>
        </tr>
        <!-- <tr>
          <label >Repita a Senha:</label>
          <input type="password2" v-model="password2"/>
        </tr> -->
        <tr>
          <button @click="register()">Cadastrar</button>
        </tr>
      </body>
    </table>
    </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        name: "",
        username: "",
        password: ""                
      }
    };
  },
  methods: {
    register() {
      this.$http
        .post(process.env.API_URL+"users/", this.user)
        .then(
          response => {
            console.log("Usuário cadastrado")
            this.$router.push({name: "login"});
          },
          error => {
            console.log(error);
          }
        )
        .finally(function() {});
    }
  }
};
</script>
