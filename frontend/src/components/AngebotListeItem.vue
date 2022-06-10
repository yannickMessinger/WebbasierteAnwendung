<template>
   
   <div>
   <table>    
    <thead class="angebotsinfo">
            <td class="beschreibung" >{{props.angebot.beschreibung}}</td>
            <td class ="gebote">{{props.angebot.gebote}}</td>
            <td class="topgebot">{{props.angebot.topgebot}}</td>
            <td><button @click="showDetails()">DETAILS</button></td>
        </thead>

        <tbody v-if="showDet">
            <tr>Mindestpreis: {{props.angebot.mindestpreis}}</tr>
            <tr>Von: {{props.angebot.anbietername}}</tr>
            <tr>Bis: {{props.angebot.ablaufzeitpunkt.toLocaleDateString()}} um {{props.angebot.ablaufzeitpunkt.toLocaleTimeString()}}</tr>
            <tr><GeoLink :lat="props.angebot.lat" :lon="props.angebot.lon" :zoom="18">Abholort</GeoLink></tr>
        </tbody>
        
    </table>

    
    </div>
</template>

<!-- oder hier wie slot nutzen um Textfelder anzuzeigen???-->
<script  setup lang="ts">import { ref } from 'vue';
    import type {IAngebotListeItem } from '@/services/IAngebotListeItem';
    import GeoLink from '@/components/GeoLink.vue'

    const props = defineProps<{
    angebot: IAngebotListeItem
    }>()
    
    
    
    
    
    
    
    let showDet = ref(false)

    function showDetails(): void{
        
        if (!showDet.value){
            showDet.value = true
        
        }else{
        showDet.value = false
        }
    }

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