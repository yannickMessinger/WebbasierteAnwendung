import type { IAngebotListeItem } from '@/services/IAngebotListeItem';
import { readonly, ref,reactive } from "vue";
import { Client, type Message } from '@stomp/stompjs';
import type { IBackendInfoMessage } from './IBackendInfoMessage';

export interface IAngebotState {
    angebotliste: IAngebotListeItem[],
    errormessage: string
    }
    
    

const angebotState = reactive<IAngebotState>({angebotliste: [], errormessage:""})



export function useAngebot() {

    return {
        angebote: readonly(angebotState), // state vor unkontrollierten Änderungen von außen schützen
        updateAngebote
    }


}

export function updateAngebote(): void {
    const url = '/api/angebot';
        console.log('bin in updateAngebot()')
        
        fetch(url,{
            method:'GET'
        }).then(response => {
            
            if(!response.ok){
                angebotState.errormessage = response.statusText;
                console.log('Fehler von Backend Angebot')
            }
            
            console.log('Angebot von Backend angefragt -> Response aus Backend:')
            console.log(response)
            return response.json();
        
        })
        
        .then((jsondata: [IAngebotListeItem]) => {
            
            angebotState.angebotliste = jsondata
            console.log('Parse AngebotListe')
            angebotState.errormessage = ''
        }
        ).catch((error) =>{
            angebotState.errormessage = error.statusText
        })

    }

    export function receiveAngebotMessages(){
        console.log("in receiveAngebotMessages()")
        const wsurl = `ws://${window.location.host}/stompbroker`;
        const DEST = "/topic/angebot";

        const stompclient = new Client({ brokerURL: wsurl })
        stompclient.onWebSocketError = (event) => { console.log("WebsocketError in  receiveAngebotMessages() ") }
        stompclient.onStompError = (frame) => { console.log("STOMP Error in  receiveAngebotMessages() ") }

        stompclient.onConnect = (frame) => {
            console.log("erfolgreicher Verbindugsaufbau zu Broker")
            // Callback: erfolgreicher Verbindugsaufbau zu Broker
            stompclient.subscribe(DEST, (message) => {
            // Callback: Nachricht auf DEST empfangen
            // empfangene Nutzdaten in message.body abrufbar,
            // ggf. mit JSON.parse(message.body) zu JS konvertieren
            const receivedMessage : IBackendInfoMessage = (JSON.parse(message.body))
            console.log("Neue STOMP Nachricht erhalten:");
            console.log(receivedMessage)
           
            updateAngebote()

            });
            };
            stompclient.onDisconnect = () => { console.log("STOMP Verbindung abgebaut") }

            stompclient.activate();

            // Nachrichtenversand vom Client zum Server
            //try {
            //stompclient.publish({ destination: DEST, headers: {},
            //body: JSON.stringify(datenobjekt)
            // ... oder body: "irgendein String"
            //});
            //} catch (fehler) {
            // Problem beim Senden
        //}

    }

    
    



//async function updateAngebote(): Promise <void>{
    //try{
        //const url = 'api/gebot'

        //const response = await fetch(url,{
            //method:'GET'
        //})

        //if(!response.ok){
            
            //angebotState.errormessage = response.statusText
            //throw new Error(response.statusText)
        
        //}else{
            //angebotState.angebotliste = await response.json()
            //angebotState.errormessage = ""
        //}




    //}catch{
        //angebotState.errormessage = response.statusText

    //}
//}