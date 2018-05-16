<template>
    <div>
        <h3></h3>
        Dashboard Component
        <button class="btn btn-danger brn-sm singout-btn" @click="signOut">Sign Out</button>
        {{$store.state}}    
    </div>
</template>


<script>
    import {firebaseApp} from '../firebaseApp'

    export default {
        methods: {
            signOut() {
                this.$store.dispatch('signOut')
                firebaseApp.auth().signOut()
            }
        }
    }
</script>
