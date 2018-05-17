  import firebase from 'firebase'
  
  const config = {
    apiKey: "AIzaSyB4zjqYBx0S0e0dVBgc02Gj0ppeAYVKYOU",
    authDomain: "digi-test-3219b.firebaseapp.com",
    databaseURL: "https://digi-test-3219b.firebaseio.com",
    projectId: "digi-test-3219b",
    storageBucket: "digi-test-3219b.appspot.com",
    messagingSenderId: "290429991720"
  };

  export const firebaseApp = firebase.initializeApp(config)
  export const eventsRef = firebaseApp.database().ref().child('events')