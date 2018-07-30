<template>
    <b-container > 
      Logado como: {{loggedUser.username}} 
      <b-button @click="logout()">
                  Sair
              </b-button>      
        <b-row class="h-90">
            <b-col cols="9" class="border ">
                {{userSelected.name}}
              <ul>
                <li v-for="msg in userSelected.messages" :key="msg.id"><span v-if="msg.fromUsername == loggedUser.username">Me</span> <span>{{msg.fromUsername}}</span> : {{msg.text}} lida: {{msg.read}}</li>                 
              </ul>

            </b-col>
            <b-col cols="3" class="border" >
              <ul>
                <li v-for="user in users" @click="selectUser(user)" :key="user.id">nome: {{user.name}} <br> status: {{user.connected}} <br> não lidas: {{getDontReadMessages(user)}}</li>                 
              </ul>
            </b-col>          
        </b-row>
        <b-row align-v="center" class="h-10">
            <b-col cols="11"  >
              <b-form-textarea class="mt-1 mb-1" id="textarea1"
                      v-model="text"
                      placeholder="Enter something"
                      :rows="3"
                      :max-rows="6"
                      :no-resize="false">
              </b-form-textarea>  
            </b-col>            
            <b-col cols="1" >
              <b-button @click="send()">
                  Enviar
              </b-button>
            </b-col>       
        </b-row>
    </b-container>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

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
          }
        );

        me.stompClient.subscribe(
          "/chat/messagesRead/" + me.loggedUser.username,
          function(response) {
            let updatedMessagesInfo = JSON.parse(response.body);
            me.checkMessagesRead(updatedMessagesInfo.fromUsername);
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
          this.users[i].messages.push(message);
        }
      }
    },
    checkMessagesRead(fromUsername) {
      for (let i in this.users) {
        if (this.users[i].username == fromUsername) {
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
      this.checkReadMessages(user.username, this.loggedUser.username);
      for (let i in user.messages) {
        let message = user.messages[i];
        message.read = true;
      }
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
    },
    checkReadMessages(fromUsername, toUsername) {
      this.stompClient.send(
        "/app/checkReadMessages",
        JSON.stringify({ fromUsername: fromUsername, toUsername: toUsername })
      );
    },
    logout() {
      this.stompClient.send("/app/userDisconnected", JSON.stringify(me.user));
    }
  }
};
</script>
