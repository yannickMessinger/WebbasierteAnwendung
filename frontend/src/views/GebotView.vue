<template>
<h2>GEBOT VIEW</h2>

    <div v-if = "angebote.errormessage" >{{gebote.errormessage}}</div>

    <div>
        <table>    
                <thead>

                    <td>{{gesuchtesAngebot.beschreibung}}</td>
                    <td>{{gesuchtesAngebot.mindestpreis}}</td>
                    <td>{{gesuchtesAngebot.anbietername}}</td>
                    <td>{{gesuchtesAngebot.ablaufzeitpunkt}}</td>
                    <td><GeoLink :lat="gesuchtesAngebot.lat" :lon="gesuchtesAngebot.lon" :zoom="18">Abholort</GeoLink></td>
                    <td>{{gesuchtesAngebot.abholort}}</td>
            
                </thead>

        </table>

    </div>

    <br/>
    

    <div>
        <table>
            <div>
                Bisheriges Topgebot ist: {{gebote.topgebot}} geboten von: {{gebote.topbieter}}, verbleibende Zeit: {{restzeit}}
            </div>

            <div>
                <input type ="number" v-model ="bietfeld" placeholder ="BIETEN SIE GEFÄLLIGST!"/>
                <button @click="gebotAbgeben()">BIETEN</button>
            </div>
            
           

            <thead>
                <input type="text" v-model="suchfeld" placeholder="Suchbegriff" />
            </thead>
    
            <tbody>
              <li v-for="gebot in gebotslistefiltered" :key="gebot.gebieterid">
                 <tr>
                        <td>{{gebot.gebotzeitpunkt}}</td>
                        <td>{{gebot.gebietername}}</td>
                        <td>{{gebot.betrag}}</td>
                </tr>
            </li>
                   
                
            </tbody>
    
        </table>
    </div>

   <div>
    <button @click="reloadGeboteList">reload gebote</button>
   </div>

    
   
    

</template>

<script setup lang="ts">
import { computed, ref, reactive, onMounted, toRef } from "vue";
import {useGebot} from '@/services/useGebot'
import {useAngebot} from '@/services/useAngebot'
import GeoLink from '@/components/GeoLink.vue'
import LoginView from './LoginView.vue'

const props = defineProps<{
angebotidstr: string
}>()

onMounted( async () => {
   useGebot(Number(props.angebotidstr))
   useGebot(Number(props.angebotidstr)).updateGebote()
});


const suchfeld = ref("");
const bietfeld = ref(0);
const {gebote} = useGebot(Number(props.angebotidstr))
//const geboteList = computed(() => {gebote.gebotliste})
const {angebote} = useAngebot()

//const gesuchtesAngebot : IAngebotListeItem = angebote.angebotliste.filter((a) => a.angebotid === Number(props.angebotidstr))

const index = angebote.angebotliste.findIndex((angebot) => angebot.angebotid === Number(props.angebotidstr));
const gesuchtesAngebot = angebote.angebotliste[index]
let restzeit = ref(0)


//noch Eingabefeld ausblenden wenn Zeit vorbei!
//let timer_ID = setInterval(updateRestzeit,1000)

const gebotslistefiltered = computed(() => {
    const n: number = suchfeld.value.length;
        console.log("Eigentliche Gebotsliste die angezeigt werden soll " +  gebote.gebotliste)
        //kp ob hier vllt reaktivität verloren geht....
        //noch nach Höchstgebot sortieren! Höchsgebot ganz oben, dann zeitlich absteigend
        if (suchfeld.value.length < 3) {
            
            return gebote.gebotliste.slice(0,10).sort((a,b) => (a.gebotzeitpunkt > b.gebotzeitpunkt) ? 1 : -1)
        
        } else {
            
            return gebote.gebotliste.filter((e) =>
            e.gebietername.toLowerCase().includes(suchfeld.value.toLowerCase())).sort((a,b) => (a.gebotzeitpunkt > b.gebotzeitpunkt) ? 1 : -1).slice(0,10) 
        }
    });



/*
function updateRestzeit(){
  
    restzeit.value = gesuchtesAngebot.ablaufzeitpunkt.getMilliseconds() - Date.now()
}
*/

function gebotAbgeben():void{
    console.log("angebotid aus GebotView: " + props.angebotidstr);
    useGebot(Number(props.angebotidstr)).sendeGebot(bietfeld.value);
}

function reloadGeboteList(){
    console.log("refreshe Gebotsliste")
     useGebot(Number(props.angebotidstr)).updateGebote()
      console.log(gebote.gebotliste)
      
}
 

</script >