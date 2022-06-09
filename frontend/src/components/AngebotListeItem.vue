<template>
   
   <div>
   <table>    
    <thead class="angebotsinfo">
            <td class="beschreibung" >TEST</td>
            <td class ="gebote">TEST</td>
            <td class="topgebot">TEST</td>
            <td><button @click="showDetails()">DETAILS</button></td>
        </thead>

        <tbody v-if="showDet">
            <tr>TEST</tr>
            <tr>TEST</tr>
            <tr>TEST</tr>
        </tbody>
        
    </table>

    </div>
</template>

<!-- oder hier wie slot nutzen um Textfelder anzuzeigen???-->
<script  setup lang="ts">import { ref } from 'vue';
    import type { AngebotListeDing, IAngebotListeItem } from '@/services/IAngebotListeItem';

    //const props = defineProps<{
    //angebot: AngebotListeDing
    //}>()
    
    
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