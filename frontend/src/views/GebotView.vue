<template>
<h2>GEBOT VIEW</h2>

    <div v-if = "angebote.errormessage" >{{gebote.errormessage}}</div>

    <div>
        <table>    
                <thead>
                    
                    <td>Artikel</td>
                    <td>Preis</td>
                    <td>Von</td>
                    <td>Endet am:</td>
                    <td>um:</td>
                    <td>Abholort:</td>
                    <td>Karte:</td>
                    <td>verbleibende Zeit:</td>
            
                </thead>

                <tbody>

                    <td>{{gesuchtesAngebot.beschreibung}}</td>
                    <td>{{gesuchtesAngebot.mindestpreis}} €</td>
                    <td>{{gesuchtesAngebot.anbietername}}</td>
                    <td>{{new Date(gesuchtesAngebot.ablaufzeitpunkt).toLocaleDateString()}}</td>
                    <td>{{new Date(gesuchtesAngebot.ablaufzeitpunkt).getHours()}}:{{new Date(gesuchtesAngebot.ablaufzeitpunkt).getMinutes()}} </td>
                    <td>{{gesuchtesAngebot.abholort}}</td>
                    <td><GeoLink :lat="gesuchtesAngebot.lat" :lon="gesuchtesAngebot.lon" :zoom="18">Abholort</GeoLink></td>
                    <td>{{restzeit}} Sekunden</td>




                </tbody>

        </table>

    </div>

    <br/>
    <br/>
    <br/>

     <div>
                
            Bisheriges Topgebot ist: {{gebote.topgebot}}€, geboten von: {{gebote.topbieter}}!!
    
    </div>

            

    <br/>

    <div>
        <input type="text" v-model="suchfeld" placeholder="Suchbegriff" />
    </div>

    <br/>
    <br/>

    <h3>Gebotsliste:</h3>

    <br/>

    <div>
        <table>
           
            <thead>
                
                <td>Gebotszeitpunkt</td>
                <td>Gebietername</td>
                <td>Betrag</td>
            </thead>
    

            <tbody>
              <tr v-for="gebot in gebote.gebotliste">
                 
                        <td>{{gebot.gebotzeitpunkt}}</td>
                        <td>{{gebot.gebietername}}</td>
                        <td>{{gebot.betrag}}</td>
                
            </tr>
                   
                
            </tbody>
    
        </table>
    </div>

<br/>
   <div>
                <input type ="number" v-model ="bietfeld" placeholder ="BIETEN SIE GEFÄLLIGST!"/>
                <button @click="gebotAbgeben()">BIETEN</button>
    </div>

   
   
    

</template>

<script setup lang="ts">
import { computed, ref, reactive, onMounted, toRef } from "vue";
import {useGebot} from '@/services/useGebot'
import {updateAngebote, useAngebot} from '@/services/useAngebot'
import GeoLink from '@/components/GeoLink.vue'


const props = defineProps<{
angebotidstr: string
}>()

onMounted( async () => {
   //useGebot(Number(props.angebotidstr));
   await updateGebote();

   //useGebot(Number(props.angebotidstr)).updateGebote()
   //console.log(Number(props.angebotidstr))
   //console.log("initiale Liste aus View aus onmounted")
    //console.log(gebote.gebotliste)
});


const suchfeld = ref("");
const bietfeld = ref(0);
const {gebote, updateGebote, sendeGebot} = useGebot(Number(props.angebotidstr));
const {angebote} = useAngebot();



//const gesuchtesAngebot : IAngebotListeItem = angebote.angebotliste.filter((a) => a.angebotid === Number(props.angebotidstr))

const index = angebote.angebotliste.findIndex((angebot) => angebot.angebotid === Number(props.angebotidstr));
const gesuchtesAngebot = angebote.angebotliste[index]
const restzeit = ref<number>();


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




function updateRestzeit() {
    if (gesuchtesAngebot != undefined) {
        restzeit.value = new Date(gesuchtesAngebot.ablaufzeitpunkt).getTime() - Date.now()
        restzeit.value = Math.ceil(restzeit.value/1000)
        if (restzeit.value <= 0) {
            clearInterval(timerid)
        }
    }
}

let timerid = setInterval(() => { updateRestzeit() }, restzeit.value)


async function gebotAbgeben():Promise<void>{
    console.log("angebotid aus GebotView: " + props.angebotidstr);
    await sendeGebot(bietfeld.value);
    await updateGebote();
    console.log("Gebotliste aus VIEW erstes Element! gebot abgeben")
    console.log(gebote.gebotliste)

}



</script >