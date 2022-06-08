<template>
    <h1>!!GEOLINK!!</h1>
    <RouterLink to="/ort/ulm">Ulm schnell</RouterLink>

</template>


<script setup lang="ts">
const props = withDefaults(
defineProps<{ lat: number, lon: number, zoom?: number}>(),
{ zoom: 17 }
)

let testLink = "https://www.openstreetmap.org/#map=zoom/lat/lon"


</script>