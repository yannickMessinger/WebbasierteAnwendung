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
                    <td>{{parseDate(new Date(gesuchtesAngebot.ablaufzeitpunkt))}} </td>
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
           <h3>Topgebot:</h3>     
            Bisheriges Topgebot ist: {{gebote.topgebot}}€, geboten von: {{gebote.topbieter}}!!
    
    </div>

            

   <br/>
   <br/>
   <br/>
   
    

    <h3>Gebotsliste:</h3>

    <br/>
    

    <div>
         <input type="text" v-model="suchfeld" placeholder="Suchbegriff" />
        <table>
           
            <thead>
                
                <td>Gebotszeitpunkt</td>
                <td>Gebietername</td>
                <td>Betrag</td>
            </thead>
    

            <tbody>
              <tr v-for="gebot in gebotslistefiltered">
                 
                        <td>{{parseDate(new Date(gebot.gebotzeitpunkt))}}</td>
                        <td>{{gebot.gebietername}}</td>
                        <td>{{gebot.betrag}}</td>
                
            </tr>
                   <tr v-if="showBietFeld"> <input type ="number" v-model ="bietfeld" placeholder ="BIETEN SIE GEFÄLLIGST!"/> <button @click="gebotAbgeben()">BIETEN</button></tr>
                  <tr v-else>DI DÖM VORBEI!</tr>
            </tbody>
    
        </table>
    </div>

<br/>
 

   
   
    

</template>

<script setup lang="ts">
import { computed, ref, reactive, onMounted, toRef } from "vue";
import {useGebot} from '@/services/useGebot'
import {updateAngebote, useAngebot} from '@/services/useAngebot'
import GeoLink from '@/components/GeoLink.vue'

let showBietFeld = ref(true)

const props = defineProps<{
angebotidstr: string
}>()

onMounted( async () => {
   useGebot(Number(props.angebotidstr));
   await updateGebote();

   
});


const suchfeld = ref("");
const bietfeld = ref(0);
const {gebote, updateGebote, sendeGebot} = useGebot(Number(props.angebotidstr));
const {angebote} = useAngebot();





const index = angebote.angebotliste.findIndex((angebot) => angebot.angebotid === Number(props.angebotidstr));
const gesuchtesAngebot = angebote.angebotliste[index]
const restzeit = ref<number>();





const gebotslistefiltered = computed(() => {
    const n: number = suchfeld.value.length;
        
        
        //noch nach Höchstgebot sortieren! Höchsgebot ganz oben, dann zeitlich absteigend
        if (suchfeld.value.length < 3) {
           
            
            let orderedByTime = gebote.gebotliste.slice(0,10).sort((a,b) => (a.gebotzeitpunkt < b.gebotzeitpunkt) ? 1 : -1)
            let topgebot  = orderedByTime.find((o) => { return o.betrag === gebote.topgebot})
            //orderedByTime.unshift(topgebot)
           
            
            return orderedByTime;
        
        } else {
            
            return gebote.gebotliste.filter((e) =>
            e.gebietername.toLowerCase().includes(suchfeld.value.toLowerCase())).sort((a,b) => (a.gebotzeitpunkt < b.gebotzeitpunkt) ? 1 : -1).slice(0,10) 
        }
    });

console.log("gefilterte Liste aus GebotView");
console.log(gebotslistefiltered);


function updateRestzeit() {
    if (gesuchtesAngebot != undefined) {
        restzeit.value = new Date(gesuchtesAngebot.ablaufzeitpunkt).getTime() - Date.now()
        restzeit.value = Math.ceil(restzeit.value/1000)
        if (restzeit.value <= 0) {
            clearInterval(timerid)
            showBietFeld.value = false;
        }
    }
}

let timerid = setInterval(() => { updateRestzeit() }, restzeit.value)


async function gebotAbgeben():Promise<void>{
    console.log("angebotid aus GebotView: " + props.angebotidstr);
    await sendeGebot(bietfeld.value);
    await updateGebote();
    

}



function parseDate(datum: Date){

   
    let b = datum.getHours(); 
    let c = datum.getMinutes(); 
    let hours;
    let minutes;
    
    
    if(b < 10){
      hours = '0'+ b;
    }else{
        hours = b;
    } 

    if(c < 10){
      minutes = '0'+ c;
    }else{
        minutes = c;
    } 
   
    
    const zeit = hours +':'+ minutes;

    return zeit
    
}



</script >