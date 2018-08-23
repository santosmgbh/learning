<template>
    <div>
        <h3></h3>
        Dashboard Component
        <button class="btn btn-danger brn-sm singout-btn" @click="signOut">Sign Out</button>
        <hr>
        <AddEvent/>
        <hr>
        <div>
            <EventItem class="col-md-12"
            v-for="(event_item, index) in this.$store.state.events"
            :event="event_item"
            :key="index"
            />
        </div>
    </div>
</template>


<script>
    import {firebaseApp, eventsRef} from '../firebaseApp'
    import AddEvent from './addEvent.vue'
    import EventItem from './EventItem.vue'

    export default {
        methods: {
            signOut() {
                this.$store.dispatch('signOut')
                firebaseApp.auth().signOut()
            }
        }, 
        components: {
            AddEvent,
            EventItem
        },
        mounted() {
            eventsRef.on('value', snap => {
                let events = []
                snap.forEach(event => {
                    events.push(event.val())
                })
                this.$store.dispatch('setEvents', events)                
            })
        }
    }
</script>
