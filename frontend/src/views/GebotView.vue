<template>
    <div style="text-align: center;">
    <h2>GEBOT VIEW</h2>
    <img alt="Vue logo" class="logo" src="@/assets/hochschule-rheinmain-bildmarke.jpg" width="125"/>
     <nav>
        <RouterLink to="/">Gebotsübersicht</RouterLink>
        
      </nav>
    </div>
    <div v-if = "angebote.errormessage" >{{gebote.errormessage}}</div>

    

    

    <div class="gebotview">

        <div v-if = "countdown"><h1 style="color: red; text-align: center;">{{restzeit}}</h1></div>

         <h3>Topgebot:</h3>     
            Bisheriges Topgebot ist: {{gebote.topgebot}}€, geboten von: {{gebote.topbieter}}!!
        
        <div v-if ="restzeit == 0"><h2>Bieter {{gebote.topbieter}} hat den Zuschlag für {{gesuchtesAngebot.beschreibung}} erhalten!</h2></div>
        
        <br/>
        <br/>


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

    
    
    
   
        <h3>Gebotsliste:</h3>

    

    
         <input type="text" v-model="suchfeld" placeholder="Suchbegriff" style="width:90%;"/>

         <br/>
         <br/>

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
                  <tr v-else>Zeit abgelaufen!</tr>
            </tbody>
    
        </table>
    

    
 
 </div>
   
   
    

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

const countdown = ref(false);



const gebotslistefiltered = computed(() => {
    const n: number = suchfeld.value.length;
        
        
        //noch nach Höchstgebot sortieren! Höchsgebot ganz oben, dann zeitlich absteigend
        if (suchfeld.value.length < 3) {
           
            
            let orderedByTime = gebote.gebotliste.slice()
            orderedByTime = orderedByTime.sort((a,b) => (a.gebotzeitpunkt < b.gebotzeitpunkt) ? 1 : -1)
            let topgebot  = orderedByTime.find((o) =>  o.betrag === gebote.topgebot);
           
           
            
            if (topgebot !== undefined){
                 let index = orderedByTime.findIndex((i) => i.gebotid === topgebot?.gebotid);
                 if(index !== -1){
                     orderedByTime.splice(index, 1);
                     orderedByTime.unshift(topgebot);
                 }
                
            }
           
           
            
            return orderedByTime.slice(0,10);
        
        } else {
            
            return gebote.gebotliste.filter((e) =>
            e.gebietername.toLowerCase().includes(suchfeld.value.toLowerCase())).sort((a,b) => (a.gebotzeitpunkt < b.gebotzeitpunkt) ? 1 : -1).slice(0,10) 
        }
    });




function updateRestzeit() {
    if (gesuchtesAngebot != undefined) {
        restzeit.value = new Date(gesuchtesAngebot.ablaufzeitpunkt).getTime() - Date.now()
        restzeit.value = Math.ceil(restzeit.value/1000)

        if(restzeit.value <= 3){
            countdown.value = true;
        }


        if (restzeit.value <= 0) {
            clearInterval(timerid)
            showBietFeld.value = false;
            restzeit.value = 0;
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


<style scoped>


.gebotview{
    
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 50%;
    background-color: transparent;
    margin-top: 5%;
    text-align: center;
}


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