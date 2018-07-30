import Vue from 'vue'

export default {
    show(title, message) {

        if (!window.Notification || window.Notification.permission == 'denied') {
            Vue.notify({
                group: 'foo',
                title: title,
                text: message
            })
            return "unsupported";
        }        

        if(window.Notification.permission == 'default'){        
            Notification.requestPermission().then(status => {
                if (status == 'granted') {
                    console.log('permissão concedida');
                    var n = new Notification(title, {
                        // use \n para quebrar linhas
                        body: message,
                         // opcional
                        icon: '../assets/logo.png'
                    });
                }else{
                    // pode ser default, ou denied
                    console.log(status);
                }
            });
        }

        if(window.Notification.permission == 'granted'){
            var n = new Notification(title, {
                // use \n para quebrar linhas
                body: message,
                 // opcional
                icon: 'far fa-comment-alt'
            });
        }
        

        


    }
}
