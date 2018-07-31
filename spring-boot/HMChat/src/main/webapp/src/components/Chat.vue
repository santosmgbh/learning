<template>
    <b-container style="height:100%" class="border">    
        <b-row style="height:10%" >
          <b-col cols="9" >
              Logado como: <strong>{{loggedUser.username}}</strong>
          </b-col>
          <b-col cols="3" >
              <b-link href="#" @click="logout">Sair</b-link>        
          </b-col>           
        </b-row>
        <b-row style="height:70%">
            <b-col cols="9" class="border" id="scrollMessages" style="overflow-y: auto;">                
                <b-list-group>
                  <b-list-group-item  v-for="msg in userSelected.messages" :key="msg.id">                    
                                                            

                    <div class="d-flex w-100 justify-content-between">
                      <h5 class="mb-1"><span v-if="msg.fromUsername == loggedUser.username">Eu</span> <span v-if="msg.fromUsername != loggedUser.username">{{msg.fromUsername}}</span> disse: </h5>
                      <small>{{msg.sended | formatDate}}</small>
                    </div>
                    <p class="mb-1">
                      {{msg.text}}
                    </p>
                    <small>                      
                      <font-awesome-icon icon="check" title="Mensagem lida!" v-show="msg.read"/>                      
                    </small>

                  </b-list-group-item>
                </b-list-group>
              <!-- <ul>
                <li v-for="msg in userSelected.messages" :key="msg.id"><span v-if="msg.fromUsername == loggedUser.username">Me</span> <span>{{msg.fromUsername}}</span> : {{msg.text}} lida: {{msg.read}}</li>                 
              </ul> -->

            </b-col>
            <b-col cols="3" class="border" style="overflow-y: auto;"  >
              <strong>Usuários</strong>             
              <b-list-group>
                <b-list-group-item class="d-flex justify-content-between align-items-center" v-bind:class="{ active: user.username == userSelected.username }" v-for="user in users" 
                v-show="user.username !== loggedUser.username"
                @click="selectUser(user)" :key="user.id">
                  {{user.name}}
                  <b-badge variant="primary" pill v-show="getDontReadMessages(user) > 0">{{getDontReadMessages(user)}}</b-badge>
                </b-list-group-item>
              </b-list-group>
            </b-col>          
        </b-row>
        <b-row align-v="center" style="height:20%">
            <b-col cols="9" >
              <b-form-textarea class="mt-1 mb-1" id="textarea1"
                      v-model="text"
                      placeholder="Digite uma mensagem"
                      :rows="3"
                      :max-rows="6"
                      :no-resize="false">
              </b-form-textarea>  
            </b-col>            
            <b-col cols="3" >
              <b-button @click="send()" style="width:100%;height: 90px;">
                  Enviar
              </b-button>
            </b-col>       
        </b-row>
    </b-container>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import Notification from "../util/notification";

export default {
  data() {
    return {
      users: [],
      messages: [],
      text: "",
      toUser: "",
      userSelected: {}, //Usuário selecionado na lista de contatos
      loggedUser: {} // Usuário logado
    };
  },
  mounted() {    
    this.loggedUser = JSON.parse(localStorage.getItem("User"));
    this.connect();
    this.loadUsers();
  },
  methods: {
    connect() {
      var me = this;
      me.socket = new SockJS(process.env.API_URL + "socket");
      me.stompClient = Stomp.over(me.socket);
      me.stompClient.connect({}, function(frame) {
        me.stompClient.subscribe(
          "/chat/messages/" + me.loggedUser.username,
          function(response) {
            console.log("subscribe");
            console.log(response);
            let newMessage = JSON.parse(response.body);

            me.addMessage(newMessage, newMessage.fromUsername);

            Notification.show(
              "Novas mensagens",
              "Message from " + newMessage.fromUsername
            );
          }
        );

        me.stompClient.subscribe(
          "/chat/messagesRead/" + me.loggedUser.username,
          function(response) {
            let updatedMessagesInfo = JSON.parse(response.body);
            me.checkMessagesRead(updatedMessagesInfo.toUsername);
          }
        );

        me.stompClient.subscribe("/chat/userConnected", function(response) {
          let connectedUser = JSON.parse(response.body);
          for (let i in me.users) {
            if (me.users[i].id == connectedUser.id) {
              me.users[i] = connectedUser;
              return;
            }
          }
          me.users.push(connectedUser);
        });

        me.stompClient.subscribe("/chat/userDisconnected", function(response) {
          let disconnectedUser = JSON.parse(response.body);
          for (let i in me.users) {
            if (me.users[i].id == disconnectedUser.id) {
              me.users[i] = disconnectedUser;
              return;
            }
          }
          me.users.push(disconnectedUser);
        });

        me.stompClient.send(
          "/app/userConnected",
          JSON.stringify(me.loggedUser)
        );
      });
    },
    loadUsers() {
      var me = this;

      this.$http
        .get(process.env.API_URL + "users/", {
          headers: {
            Authorization: localStorage.getItem("Authorization")
          }
        })
        .then(
          response => {
            me.users = response.body;
          },
          error => {
            console.log(error);
          }
        )
        .finally(function() {});
    },
    addMessage(message, username) {
      for (let i in this.users) {
        if (this.users[i].username == username) {
          if (!this.users[i].messages) this.users[i].messages = [];
          this.userSelected = this.users[i];
          this.users[i].messages.push(message);
          break;
        }
      }
    },
    checkMessagesRead(toUsername) {
      for (let i in this.users) {
        if (this.users[i].username == toUsername) {
          for (let m in this.users[i].messages) {
            this.users[i].messages[m].read = true;
          }
        }
      }
    },
    getDontReadMessages(user) {
      let dontReadMessages = 0;
      for (let i in user.messages) {
        if (
          !user.messages[i].read &&
          user.messages[i].fromUsername != this.loggedUser.username
        )
          dontReadMessages++;
      }
      return dontReadMessages;
    },
    selectUser(user) {      

      this.userSelected = user;

      let containsNotReadMessages = false;
      for (let i in user.messages) {
        let message = user.messages[i];
        if (!message.read) {
          containsNotReadMessages = true;
          message.read = true;
        }
      }
      if (containsNotReadMessages)
        this.checkReadMessages(user.username, this.loggedUser.username);

      setTimeout(function(){
        var element = document.getElementById("scrollMessages");
        element.scrollTop = element.scrollHeight - element.clientHeight;
      }, 500)
      
    },
    send() {
      var message = {
        text: this.text,
        fromUsername: this.loggedUser.username,
        toUsername: this.userSelected.username
      };

      let me = this;
      this.stompClient.send("/app/sendMessage", JSON.stringify(message));

      me.addMessage(message, this.userSelected.username);

      this.text = "";
    },
    checkReadMessages(fromUsername, toUsername) {
      this.stompClient.send(
        "/app/checkReadMessages",
        JSON.stringify({ fromUsername: fromUsername, toUsername: toUsername })
      );
    },
    logout() {
      this.stompClient.send("/app/userDisconnected", JSON.stringify(this.user));
      this.$router.push({ name: "login" });            
    }
  }
};
</script>
