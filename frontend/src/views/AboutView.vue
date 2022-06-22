<template>
  
  <div>
    <h1>This is an about page</h1>
    <br/>
    
    
    
    
  </div>
 

</template>

<script  setup lang="ts">

  import type {IAngebotListeItem } from '@/services/IAngebotListeItem';
  import AngebotListe from '../components/AngebotListe.vue';
  import {useFakeAngebot} from '@/services/useFakeAngebot'
  
  
  

 
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
