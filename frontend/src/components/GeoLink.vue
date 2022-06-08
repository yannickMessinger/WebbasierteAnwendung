<template>
    <h1>!!GEOLINK!!</h1>
</template>


<script setup lang="ts">
const props = withDefaults(
defineProps<{ lat: number, lon: number, zoom?: number}>(),
{ zoom: 17 }
)



</script>