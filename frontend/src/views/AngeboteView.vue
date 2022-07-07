<template>
<h2>Wir haben aktuell {{ n }} Angebote fuer sie!!</h2>

    <div>
        <AngebotListe></AngebotListe>
        <button @click="updateAngebote()">RELOAD</button>
    </div>

    <div v-if = "angebote.errormessage" >{{angebote.errormessage}}</div>

</template>

<script setup lang="ts">
import { computed, ref, reactive, onMounted } from "vue";
import AngebotListe from '../components/AngebotListe.vue';
import {receiveAngebotMessages, useAngebot} from '@/services/useAngebot'
import {updateAngebote} from '@/services/useAngebot'

onMounted( async () => {
    updateAngebote()
    receiveAngebotMessages()
  //wie hier auf Fehlermeldung reagieren?
});








const {angebote} = useAngebot()
const n = computed(() => angebote.angebotliste.length)





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