<template>
    <b-container > 
        <b-row class="h-90">
            <b-col cols="9" class="border ">
                {{userSelected.name}}
              <ul>
                <li v-for="msg in messages" :key="msg">User1: {{msg.text}}</li>                 
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
      username: "",
      userSelected: {}
    };
  },
  mounted() {
    console.log("mounted");
    this.connect();
    this.loadUsers();
  },
  methods: {
    connect() {
      var me = this;
      me.username = this.$route.params.username;
      me.socket = new SockJS("/hmchat-api/socket");
      me.stompClient = Stomp.over(me.socket);
      me.stompClient.connect({}, function(frame) {
        console.log("Connected: " + frame);
        me.stompClient.subscribe("/chat/messages/" + me.username, function(
          response
        ) {
          console.log("subscribe");
          console.log(response);
          let payload = response.body;
          me.messages.push(JSON.parse(payload));
        });
      });
    },
    loadUsers() {
      var me = this;
      this.$http
        .get("/hmchat-api/users/")
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
    selectUser(user){
        this.userSelected = user;
    },
    send() {
      console.log("send");
      console.log(JSON.stringify({ text: this.text }));
      this.stompClient.send(
        "/app/sendMessage",
        JSON.stringify({ text: this.text, toUser: this.userSelected.username })
      );
    }
  }
};
</script>
