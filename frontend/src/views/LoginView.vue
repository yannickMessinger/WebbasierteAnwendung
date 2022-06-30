<template>
<h1>LOGIN VIEW</h1>

    <div>
        <input type="text" v-model="benutzername" placeholder="BENUTZERNAME"/>
    </div>

    <div>
        <input type="password" v-model="passwort" placeholder="PASSWORT"/>
    </div>

    <button @click="submitLoginData">LOGIN</button>




</template>




<script setup lang="ts">
import { onMounted, ref } from 'vue';
import {useLogin} from '@/services/useLogin'


onMounted( async () => {
   useLogin().logout()
});

const benutzername = ref("");
const passwort = ref("")


function submitLoginData(){
    console.log("Benutzername: " + benutzername.value + "PW: " + passwort.value  + " aus LoginView");
    useLogin().login(benutzername.value,passwort.value);
}

</script>