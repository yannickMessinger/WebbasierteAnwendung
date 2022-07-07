<template>
  
   <div>
   <table>    
    <thead>
            <td >{{props.angebot.beschreibung}}</td>
            <td >{{props.angebot.gebote}}</td>
            <td >{{props.angebot.mindestpreis}}</td>
            <td><button @click=" navToGebotView()">zur Versteigerung</button></td>
            <td v-if="!showDet"><button @click="showDetails()">Details</button></td>
            <td v-else><button @click="showDetails()">x</button></td>
        </thead>

        <tbody v-if="showDet">
            <tr>Mindestpreis: {{props.angebot.mindestpreis}}</tr>
            <tr>Von: {{props.angebot.anbietername}}</tr>
            <tr>Bis: {{props.angebot.ablaufzeitpunkt}} um {{props.angebot.ablaufzeitpunkt}}</tr>
            <tr><GeoLink :lat="props.angebot.lat" :lon="props.angebot.lon" :zoom="18">Abholort</GeoLink></tr>
        </tbody>
        
    </table>

    

    
    </div>
</template>


<script  setup lang="ts">import { ref } from 'vue';
    import type {IAngebotListeItem } from '@/services/IAngebotListeItem';
    import GeoLink from '@/components/GeoLink.vue'
    import { useRouter } from 'vue-router'

    const router = useRouter()

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

    function navToGebotView(): void {
        router.push(`/gebot/${props.angebot.angebotid}`);
}


</script>

<style scoped>



table{
    border-collapse: collapse;
    text-align: center;
    border:transparent;
    border-color: black;
    border-radius: 0.5px;
    width:100%;
    
    
}

tr{
    border:transparent;
    border-color: black;
    border-radius: 0.5px;
    width:20%;
    
}

td{
   
    
    border-radius: 0.5px; 
    width: 20%;
    padding: 0.5vw;
    
}

thead{
   
    background-color: darkgray;
     
}

</style>