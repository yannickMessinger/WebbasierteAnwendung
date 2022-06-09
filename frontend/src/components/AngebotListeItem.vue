<template>
   
   <tr>
        <td >{{ beschreibung }}</td>
        <td>{{ gebote}}</td>
        <td>{{ topgebot }}</td>
    
    </tr>

</template>

<!-- oder hier wie slot nutzen um Textfelder anzuzeigen???-->
<script  setup lang="ts">
    let beschreibung = "Beschreibung-Dummy"
    let gebote ="Gebote-Dummy"
    let topgebot = "Topgebot-Dummy"
</script>