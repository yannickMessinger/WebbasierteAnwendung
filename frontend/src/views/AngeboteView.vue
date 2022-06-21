<template>
<h2>Wir haben aktuell Angebote {{ n }} fuer sie!!</h2>
    <div>
        <AngebotListe></AngebotListe>
        <button @click="updateAngebote()">RELOAD</button>
    </div>

    <div v-if =errorM> ANGEBOTE LEER</div>

</template>

<script setup lang="ts">
import { computed, ref, reactive } from "vue";
import AngebotListe from '../components/AngebotListe.vue';
import {useAngebot} from '@/services/useAngebot'
import {updateAngebote} from '@/services/useAngebot'


//let angebotState: IAngebotState;
//angebotState = reactive({angebotliste: [], errormessage:""})

//const IAngebotState = ({angebotsListe : [], errormessage : ""})
//const angebotState = reactive(IAngebotState)




const a = useAngebot().angebote.angebotliste
const n = ref(a.length)

let errorM = ref(a.length == 0)



</script >