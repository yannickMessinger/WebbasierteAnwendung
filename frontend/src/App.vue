

<template>
<div class="header">
  <header>
    

    
      <div>
        Sie sind: {{logindata.name}} 
      </div>  
      <div style="padding-top: 1em;">
      <button @click="logout">Logout</button>
      </div>
      

     
    
  </header>

  </div>

  <RouterView />
</template>

<script setup lang="ts">
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useLogin } from '@/services/useLogin'
const { logindata } = useLogin()

const router = useRouter();

async function logout(){

  

   useLogin().logout();
    router.push('/login');
      
  

}








</script>

<style scoped>


.header{
    margin-left: auto;
    margin-right: auto;
    width: 8em;
    background-color: lightgrey;
    width: 100%;
    font-family:'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell;
    padding: 2em;
    
    
}



</style>
