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
import {useAngebot} from '@/services/useAngebot'
import {updateAngebote} from '@/services/useAngebot'

onMounted( async () => {
    updateAngebote()
  
});








const {angebote} = useAngebot()
const n = computed(() => angebote.angebotliste.length)





</script >