<template>
    
    
    <div>
      <button><a :href="testLink" target="_blank"><slot>Dummy-Text</slot></a></button>
    </div>
    

</template>


<script setup lang="ts">
const props = withDefaults(
defineProps<{ lat: number, lon: number, zoom?: number}>(),
{ zoom: 17 }
)

let testLink = `https://www.openstreetmap.org/#map=${props.zoom}/${props.lat}/${props.lon}`
let url = "testAmina"


</script>