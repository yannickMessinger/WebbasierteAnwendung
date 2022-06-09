<template>
  
  <div>
    <h1>This is an about page</h1>
    <br/>
    <GeoLink :lat="50.164802" :lon="8.624993" :zoom="18"></GeoLink>
    <br/>
    <AngebotListeItem></AngebotListeItem>
  </div>
 
</template>

<script  setup lang="ts">
import GeoLink from '@/components/GeoLink.vue'
import AngebotListeItem from '../components/AngebotListeItem.vue'
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
