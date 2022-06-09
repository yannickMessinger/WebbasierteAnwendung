<template>
   
   <div>
   <tr class="angebotsinfo">
        <td class="beschreibung" >{{ beschreibung }}</td>
        <td class ="gebote">{{ gebote}}</td>
        <td class="topgebot">{{ topgebot }}</td>
        <td><button>del</button></td>
    
    </tr>
    </div>
</template>

<!-- oder hier wie slot nutzen um Textfelder anzuzeigen???-->
<script  setup lang="ts">
    let beschreibung = "Beschreibung-Dummy"
    let gebote ="Gebote-Dummy"
    let topgebot = "Topgebot-Dummy"
</script>

<style>

.angebotsinfo{
    background-color:lightgrey;
}

.beschreibung{
    padding-left: 1vw;
    background-color:orange;
    width: 60%;
}

.gebote{
    padding-left: 1vw;
    background-color:blue;
    width: 20%;
}

.topgebot{
    padding-left: 1vw;
    background-color:purple;
    width: 20%;

}

</style>