<template>




<div>
<h1>LOGIN</h1>
</div>


    

    <div>
        <input type="text" v-model="benutzername" placeholder="BENUTZERNAME"/>
    </div>

    <br/>
    
    <div>
        <input type="password" v-model="passwort" placeholder="PASSWORT"/>

    </div>

    <br/>

    <div>
        <button @click="check">LOGIN</button>
    </div>

    

  

</template>




<script setup lang="ts">
import { computed, onMounted, ref, toRef } from 'vue';
import {useLogin} from '@/services/useLogin'
import { useRouter } from 'vue-router'

const router = useRouter();


onMounted( async () => {
   useLogin().logout()
});

const benutzername = ref("");
const passwort = ref("")
const {logindata} = useLogin(); 




async function check():Promise<void>{
    
    await useLogin().login(benutzername.value,passwort.value);

    let checkLogin = logindata.loggedin
    
   

    if (checkLogin){
        router.push('/');
        console.log("Login erfolgreich, Weiterleitung auf Übersichtsseite");
    }else{
        console.log("leider LOGIN FAIL");
         router.push('/login');

    }

    
}


</script>


<style scoped>



</style>