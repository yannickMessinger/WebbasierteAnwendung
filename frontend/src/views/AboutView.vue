<template>
  
  <div>
    <h1>This is an about page</h1>
    <br/>
    
    
    
    <AngebotTabelle></AngebotTabelle>
    <button @click="useFakeAngebot().bietenSimulieren()">bieten simulieren</button>
  </div>
 

</template>

<script  setup lang="ts">

  import type {IAngebotListeItem } from '@/services/IAngebotListeItem';
  import AngebotTabelle from '../components/AngebotTabelle.vue';
  import {useFakeAngebot} from '@/services/useFakeAngebot'
  
  
  let a : IAngebotListeItem
  a = useFakeAngebot().angebote.value[0]

 
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
