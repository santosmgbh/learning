<template>
    <b-container > 
        <b-row class="h-90">
            <b-col cols="9" class="border ">
              <ul>
                <li>User1: message 1</li>
                <li>User1: message 1</li>
                <li>User1: message 1</li>                
              </ul>

            </b-col>
            <b-col cols="3" class="border" >Contacts</b-col>          
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
              <b-button >
                  Enviar
              </b-button>
            </b-col>       
        </b-row>
    </b-container>
</template>

<script>
export default {};
</script>
