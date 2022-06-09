<template>
   
   <div>
   <table>    
    <thead class="angebotsinfo">
            <td class="beschreibung" >{{fake_angebot.beschreibung}}</td>
            <td class ="gebote">{{fake_angebot.gebote}}</td>
            <td class="topgebot">{{fake_angebot.topgebot}}</td>
            <td><button @click="showDetails()">DETAILS</button></td>
        </thead>

        <tbody v-if="showDet">
            <tr>{{fake_angebot.mindestpreis}}</tr>
            <tr>{{fake_angebot.anbietername}}</tr>
            <tr><GeoLink :lat="fake_angebot.lat" :lon="fake_angebot.lon" :zoom="18">Ort</GeoLink></tr>
        </tbody>
        
    </table>

    </div>
</template>

<!-- oder hier wie slot nutzen um Textfelder anzuzeigen???-->
<script  setup lang="ts">import { ref } from 'vue';
    import type { AngebotListeDing, IAngebotListeItem } from '@/services/IAngebotListeItem';
    import {useFakeAngebot} from '@/services/useFakeAngebot'
    import GeoLink from '@/components/GeoLink.vue'

    //const props = defineProps<{
    //angebot: AngebotListeDing
    //}>()
    
    let fake_angebot = useFakeAngebot().angebote.value[1]
    
    
    //let beschreibung = props.angebot.abholort
    //let gebote = props.angebot.gebote
    //let topgebot = props.angebot.topgebot
    
    
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