<template>
    <div>

      <b-form class="mx-auto" style="width: 300px;" >
      <b-form-group label="Nome:"
                    label-for="input1">
        <b-form-input id="input1"
                      type="text"
                      v-model="user.name"
                      required
                      placeholder="Nome">
        </b-form-input>
      </b-form-group>
      <b-form-group label="Usuário:"
                    label-for="input2">
        <b-form-input id="input2"
                      type="text"
                      v-model="user.username"
                      required
                      placeholder="Usuário">
        </b-form-input>
      </b-form-group>
      <b-form-group label="Senha:"
                    label-for="input3">
        <b-form-input id="input3"
                      type="password"
                      v-model="user.password"
                      required
                      placeholder="Senha">
        </b-form-input>
      </b-form-group>            
      <b-button type="button" @click="register" variant="success">Cadastrar</b-button>
    </b-form>              
    </div>
</template>

<script>
import Notification from '../util/notification'

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
        .post(process.env.API_URL + "users/", this.user)
        .then(
          response => {
            Notification.showBasic("Cadastro", "Usuário cadastrado com sucesso!", "success");
            this.$router.push({ name: "login" });            
          },
          error => {
            if(error.status == 409){
              Notification.showBasic("Cadastro", "O nome de usuário já existe!", "error");
            }else{
              Notification.showBasic("Cadastro", "Verifique os campos digitados", "error");
            }
            
            console.log(error);
          }
        )
        .finally(function() {});
    }
  }
};
</script>
