<template>
    <b-container > 
        <b-row class="h-90">
            <b-col cols="9" class="border ">
                {{userSelected.name}}
              <ul>
                <li v-for="msg in userSelected.messages" :key="msg"><span v-if="msg.fromUsername == user.username">Me</span> <span>{{msg.fromUsername}}</span> : {{msg.text}}</li>                 
              </ul>

            </b-col>
            <b-col cols="3" class="border" >
              <ul>
                <li v-for="user in users" @click="selectUser(user)" :key="user">{{user.name}}</li>                 
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
                      no-resize="false">
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
      userSelected: {},
      user: {}
    };
  },
  mounted() {
    console.log("mounted");
    this.user = localStorage.getItem('User');
    this.connect();
    this.loadUsers();
  },
  methods: {
    connect() {
      var me = this;      
      me.socket = new SockJS("/hmchat-api/socket");
      me.stompClient = Stomp.over(me.socket);
      me.stompClient.connect({}, function(frame) {
        console.log("Connected: " + frame);
        me.stompClient.subscribe("/chat/messages/" + me.user.username, function(
          response
        ) {
          console.log("subscribe");
          console.log(response);
          let newMessage = JSON.parse(response.body);

          me.addMessage(newMessage, newMessage.fromUsername);          
        });
      });
    },
    loadUsers() {
      var me = this;

      this.$http
        .get("/hmchat-api/users/", {
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
    selectUser(user) {
      this.userSelected = user;
    },
    send() {
      var message = {
            text: this.text,
            fromUsername: this.user.username,
            toUsername: this.userSelected.username
          };
      let me = this;
      this.stompClient
        .send(
          "/app/sendMessage",
          JSON.stringify(message)
        );

        me.addMessage(message, this.userSelected.username);
    }
  }
};
</script>
