<template>
<h1>LOGIN VIEW</h1>

    <div>
        <input type="text" v-model="benutzername" placeholder="BENUTZERNAME"/>
    </div>

    <div>
        <input type="password" v-model="passwort" placeholder="PASSWORT"/>
    </div>

    <button @click="submitLoginData">LOGIN</button>

    <div>
        {{test}}
    </div>


</template>




<script setup lang="ts">
import { computed, onMounted, ref, toRef } from 'vue';
import {useLogin} from '@/services/useLogin'
import { useRouter } from 'vue-router'

const router = useRouter()


onMounted( async () => {
   useLogin().logout()
});

const benutzername = ref("");
const passwort = ref("")
const {logindata} = useLogin(); 

//const timer_ID = setInterval(checkLogin,100)

//geht so au netttt aller
//const checkLogin = toRef(logindata, 'loggedin');

const check = computed(() => {
    let checkLogin = computed(() => logindata.loggedin);
    //const checkLogin = toRef(logindata, 'loggedin');

    if (checkLogin){
        //router.push('/');
        console.log("Login erfolgreich, Weiterleitung auf Übersichtsseite");
    }else{
        console.log("leider LOGIN FAIL");
    }

    
})

let test = computed(() => logindata.loggedin);
if (test){
        //router.push('/');
        console.log("Login erfolgreich, Weiterleitung auf Übersichtsseite");
    }else{
        console.log("leider LOGIN FAIL");
    }


function submitLoginData():void{
    console.log("Benutzername: " + benutzername.value + "PW: " + passwort.value  + " aus LoginView");
    useLogin().login(benutzername.value,passwort.value);
    
    
}

</script>