<template>
<h2>GEBOT TEST VIEW</h2>

    <div>
   <table>    
    <thead>

            <td>{{gesuchtesAngebot.beschreibung}}</td>
            <td>{{gesuchtesAngebot.mindestpreis}}</td>
            <td>{{gesuchtesAngebot.anbietername}}</td>
            <td>{{gesuchtesAngebot.ablaufzeitpunkt}}</td>
            <td>{{gesuchtesAngebot.abholort}}</td>
            
    </thead>

   </table>

   <table>

       <thead>
           <td></td>
           <td></td>
            <td></td>

       </thead>

        
        <tbody>
            <tr>Gebieter: {{gebote.gebotliste[0].gebietername}}</tr>
            <tr>gebotener Betrag: {{gebote.gebotliste[0].betrag}}</tr>
            <tr>abgegeben am: {{gebote.gebotliste[0].gebotzeitpunkt}}</tr>
        </tbody>
        
        
    </table>

    

    
    </div>
    

</template>

<script setup lang="ts">
import { computed, ref, reactive, onMounted } from "vue";
import {useGebot} from '@/services/useGebot'
import {useAngebot} from '@/services/useAngebot'
import type { IAngebotListeItem } from "@/services/IAngebotListeItem";

const props = defineProps<{
angebotidstr: string
}>()


const {gebote} = useGebot(Number(props.angebotidstr))
const {angebote} = useAngebot()
//const gesuchtesAngebot : IAngebotListeItem = angebote.angebotliste.filter((a) => a.angebotid === Number(props.angebotidstr))

const index = angebote.angebotliste.findIndex((angebot) => angebot.angebotid === Number(props.angebotidstr));
const gesuchtesAngebot = angebote.angebotliste[index]




</script >