<template>

<div>
    <table>
        <thead>
            <td>Beschreibung</td>
            <td>Anzahl Gebote</td>
            <td>Mindestpreis</td>
            <td>Versteigerung</td>
            <td>Gebotsdetails</td>
        </thead>
    </table>
    
</div>

<br/>

<div>
     <input type="text" style="width: 100%;" v-model="suchfeld" placeholder="Suchbegriff" />
<table>
       
    
        <tbody>
            
           <td><AngebotListeItem :angebot="ele" v-for="ele in angebotslistefiltered" :key="ele.anbieterid"/></td>
            
        </tbody>
    
    </table>
</div>

</template>




<script setup lang="ts">

    import { ref, computed} from 'vue';
    import AngebotListeItem from '../components/AngebotListeItem.vue'
    import {useAngebot} from '@/services/useAngebot'
    

    

    
    const suchfeld = ref("");
    const {angebote} = useAngebot()
    
    
    const angebotslistefiltered = computed(() => {
    const n: number = suchfeld.value.length;

        if (suchfeld.value.length < 3) {
            return angebote.angebotliste;
        } else {
        
            return angebote.angebotliste.filter(e =>
            e.abholort.toLowerCase().includes(suchfeld.value.toLowerCase()) || e.beschreibung.toLocaleLowerCase().includes(suchfeld.value.toLowerCase()) || e.anbietername.toLocaleLowerCase().includes(suchfeld.value.toLowerCase()) 
            );
        }
    });
    
    
    








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