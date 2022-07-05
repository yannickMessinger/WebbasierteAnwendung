import { reactive, readonly } from "vue";

import { Client } from '@stomp/stompjs';
const wsurl = `ws://${window.location.host}/stompbroker`;


import { useLogin } from '@/services/useLogin';
const { logindata } = useLogin();

export function useGebot(angebotid: number) {
    console.log("Zeige Gebotsliste für Angebot id: " + angebotid);
    /*
     * Mal ein Beispiel für CompositionFunction mit Closure/lokalem State,
     * um parallel mehrere *verschiedene* Versteigerungen managen zu können
     * (Gebot-State ist also *nicht* Frontend-Global wie Angebot(e)-State)
     */

    // STOMP-Destination
    const DEST = `/topic/gebot/${angebotid}`;


    ////////////////////////////////

    // entspricht GetGebotResponseDTO.java aus dem Spring-Backend
    interface IGetGebotResponseDTO {
        gebotid: number,
        gebieterid: number,
        gebietername: string,
        angebotid: number,
        angebotbeschreibung: string,
        betrag: number,
        gebotzeitpunkt: string // kommt als ISO-DateTime String-serialisiert an!
    }

    // Basistyp für gebotState
    interface IGebotState {
        angebotid: number,              // ID des zugehörigen Angebots
        topgebot: number,               // bisher höchster gebotener Betrag
        topbieter: string,              // Name des Bieters, der das aktuelle topgebot gemacht hat
        gebotliste: IGetGebotResponseDTO[], // Liste der Gebote, wie vom Backend geliefert
        receivingMessages: boolean,     // Status, ob STOMP-Messageempfang aktiv ist oder nicht
        errormessage: string            // (aktuelle) Fehlernachricht oder Leerstring
    }


    /* reaktives Objekt auf Basis des Interface <IGebotState> */
    const gebotState = reactive<IGebotState>({ angebotid: 0, topgebot: 0, topbieter: "", gebotliste: [], receivingMessages: false, errormessage: "" });




    function processGebotDTO(gebotDTO: IGetGebotResponseDTO) {

        const dtos = JSON.stringify(gebotDTO)
        console.log(`processGebot(${dtos})`)

       
        /*
         * suche Angebot für 'gebieter' des übergebenen Gebots aus der gebotliste (in gebotState)
         * falls vorhanden, hat der User hier schon geboten und das Gebot wird nur aktualisiert (Betrag/Gebot-Zeitpunkt)
         * falls nicht, ist es ein neuer Bieter für dieses Angebot und das DTO wird vorne in die gebotliste des State-Objekts aufgenommen
         */
      

        console.log("Liste vor Prozessierung");
        console.log(gebotState.gebotliste)
        
        console.log("PROZZESIERE GEBOT")
        const index = gebotState.gebotliste.findIndex((gebot) => gebot.gebieterid === gebotDTO.gebieterid);

        if(index === -1){
            console.log("erstes Gebot von " + gebotDTO.gebietername);
            gebotState.gebotliste.unshift(gebotDTO);
            //oder gebotstate.gebotliste.unshift(gebotDTO);?????
        }else{
            gebotState.gebotliste[index].betrag = gebotDTO.betrag
            gebotState.gebotliste[index].gebotzeitpunkt = gebotDTO.gebotzeitpunkt
        }
        
        //updateGebote();

        //gebotState.gebotliste.forEach(o => o.gebieterid === gebotDTO.gebieterid)
        
        

        




        
        
        /*

        for (let gebot of gebotState.gebotliste) {
            if (gebot.gebieterid === gebotDTO.gebieterid) {
                gebot.betrag = gebotDTO.betrag
                gebot.gebotzeitpunkt = gebotDTO.gebotzeitpunkt
            }
        }

        */

        //neuer Bieter da nicht gefunden in der Liste
        
        //gebotState.gebotliste.unshift(gebotDTO);

        console.log("Liste nach prozessierung");
        console.log(gebotState.gebotliste);


        /*
         * Falls gebotener Betrag im DTO größer als bisheriges topgebot im State,
         * werden topgebot und topbieter (der Name, also 'gebietername' aus dem DTO)
         * aus dem DTO aktualisiert
         */

        /*
        let max = 0;

        gebotState.gebotliste.forEach(gebot => {
            if (gebot.betrag > max) {
                max = gebot.betrag
            }
        })

        if (max > gebotState.topgebot) {
            gebotState.topgebot = max;
            gebotState.topbieter = gebotDTO.gebietername
        }
        */


    }


    function receiveGebotMessages() {
        /*
         * analog zu Message-Empfang bei Angeboten
         * wir verbinden uns zur brokerURL (s.o.),
         * bestellen Nachrichten von Topic DEST (s.o.)
         * und rufen die Funktion processGebotDTO() von oben
         * für jede neu eingehende Nachricht auf diesem Topic auf.
         * Eingehende Nachrichten haben das Format IGetGebotResponseDTO (s.o.)
         * Die Funktion aktiviert den Messaging-Client nach fertiger Einrichtung.
         * 
         * Bei erfolgreichem Verbindungsaubau soll im State 'receivingMessages' auf true gesetzt werden,
         * bei einem Kommunikationsfehler auf false 
         * und die zugehörige Fehlermeldung wird in 'errormessage' des Stateobjekts geschrieben
         */




        const stompclient = new Client({ brokerURL: wsurl })
        stompclient.onWebSocketError = (event) => {
            console.log("WebsocketError -> useGebot()")
        }

        stompclient.onStompError = (frame) => {
            console.log("STOMP Error in useGebot() ")
            gebotState.receivingMessages = false;
            gebotState.errormessage = " useGebot -> KOMMUNIKATIONSFEHLER!";
        }

        stompclient.onConnect = (frame) => {
            // Callback: erfolgreicher Verbindugsaufbau zu Broker
            console.log("erfolgreicher Verbindugsaufbau in useGebot() zu Broker");
            console.log(DEST);
            gebotState.receivingMessages = true;

            stompclient.subscribe(DEST, (message) => {
                // Callback: Nachricht auf DEST empfangen
                // empfangene Nutzdaten in message.body abrufbar,
                // ggf. mit JSON.parse(message.body) zu JS konvertieren
                
                
               
                const receivedMessage: IGetGebotResponseDTO = (JSON.parse(message.body));
                

                console.log("Neue STOMP Nachricht in useGebot() erhalten:");
                //console.log(receivedMessage)

                


                processGebotDTO(receivedMessage)


                



            });
        };

        stompclient.onDisconnect = () => {
            console.log("STOMP Verbindung in useGebot() abgebaut")
        }

        stompclient.activate();




    }


    async function updateGebote() {
        /*
         * holt per fetch() auf Endpunkt /api/gebot die Liste aller Gebote ab
         * (Array vom Interface-Typ IGetGebotResponseDTO, s.o.)
         * und filtert diejenigen für das Angebot mit Angebot-ID 'angebotid' 
         * (Parameter der useGebot()-Funktion, s.o.) heraus. 
         * Falls erfolgreich, wird 
         *   - das Messaging angestoßen (receiveGebotMessages(), s.o.), 
         *     sofern es noch nicht läuft
         *   - das bisherige maximale Gebot aus der empfangenen Liste gesucht, um
         *     die State-Properties 'topgebot' und 'topbieter' zu initialisieren
         *   - 'errormessage' auf den Leerstring gesetzt
         * Bei Fehler wird im State-Objekt die 'gebotliste' auf das leere Array 
         * und 'errormessage' auf die Fehlermeldung geschrieben.
         */

        //console.log("setze topgebot und topbieter auf 0");
        //gebotState.topgebot = 0
        //gebotState.topbieter=""

        const url = '/api/gebot';
        console.log('bin in updateGEbot()')

       await fetch(url, {
            method: 'GET',
            headers:{'Authorization' : `Bearer ${logindata.jwtToken}`}
        }).then(response => {

            if (!response.ok) {
                gebotState.errormessage = response.statusText;
                gebotState.gebotliste = []
                console.log('Fehler von Backend updateGEbot()')
            }

            console.log('GebotListe von Backend angefragt -> updateGEbot() Response aus Backend:')
            console.log(response)

            if (gebotState.receivingMessages === false) {
                receiveGebotMessages()

            }
            return response.json();

        })

            .then((jsondata: [IGetGebotResponseDTO]) => {
                console.log("topgebot: " + gebotState.topgebot)
                console.log("topbieter " + gebotState.topbieter)
               
                //let gebotListe:IGetGebotResponseDTO[] = jsondata;
                gebotState.gebotliste = jsondata;
                console.log("VOR FILTERN:")
                console.log(gebotState.gebotliste)
                

                
                //filtern amk
                //gebotState.gebotliste
                const filterList =   gebotState.gebotliste.filter((gebot) => {
                    return gebot.angebotid === angebotid;
                });
                console.log("NACH FILTERN");
                console.log(gebotState.gebotliste)
               
                let topbetrag = Math.max(... gebotState.gebotliste.map(gebot => gebot.betrag))
                //let topbieter = gebotState.gebotliste.find((o) => { return o.betrag === topbetrag})
                
                
                
                
                gebotState.gebotliste = filterList;
                gebotState.topgebot = topbetrag;
                
               
            
                
                gebotState.gebotliste.forEach((gebot) => {
                    if (gebot.betrag === topbetrag) {
                       
                        
                        gebotState.topbieter = gebot.gebietername;
                        
                    }
                })

                if(gebotState.topgebot < 0){
                    gebotState.topgebot = 0;
                }

                if(gebotState.topbieter === ""){
                    gebotState.topbieter = "noch niemand";
                }
                
                //console.log("topgebot: " + gebotState.topgebot)
                //console.log("topbieter " + gebotState.topbieter)

                
                //console.log("GebotListe nach Modifikation")
                //console.log(gebotState.gebotliste)

                //console.log("Höchstes Gebot:" + max1)
                //console.log("topbieter:" + gebotState.topbieter)
                //console.log("Anzahl Gebote " + gebotState.gebotliste.length)
                

                gebotState.errormessage = ''
            }
            ).catch((error) => {
                //is das hier dann ein json fehler ?
                gebotState.errormessage = error.statusText
            })

    }






    // Analog Java-DTO AddGebotRequestDTO.java
    interface IAddGebotRequestDTO {
        benutzerprofilid: number,
        angebotid: number,
        betrag: number
    }

    async function sendeGebot(betrag: number) {
        /*
         * sendet per fetch() POST auf Endpunkt /api/gebot ein eigenes Gebot,
         * schickt Body-Struktur gemäß Interface IAddGebotRequestDTO als JSON,
         * erwartet ID-Wert zurück (response.text()) und loggt diesen auf die Console
         * Falls ok, wird 'errormessage' im State auf leer gesetzt,
         * bei Fehler auf die Fehlermeldung
         */

        const url = '/api/gebot';
        //const hilo = <IAddGebotRequestDTO>({benutzerprofilid: gebotState.angebotid, angebotid: gebotState.angebotid, betrag:betrag});
        const sendGebot: IAddGebotRequestDTO = ({ benutzerprofilid: logindata.benutzerprofilid, angebotid: angebotid , betrag: betrag });
        //console.log(sendGebot)
        //console.log("sendGebot angebotid: " + sendGebot.angebotid)

        await fetch(url, {
            method: 'POST',
            headers: { "Content-Type": "application/json",'Authorization' : `Bearer ${logindata.jwtToken}`},
            body: JSON.stringify(sendGebot)

        }).then(response => {


            if (!response.ok) {
                console.log('Fehler von sendeGebot()')
                gebotState.errormessage = response.statusText;
               
            } else {

                console.log("Gebot erfolgreich abgegeben aus useGebot() -> sendeGebot()!");
                //console.log(response.text());

            }

        })





    }

    // Composition Function -> gibt nur die nach außen freigegebenen Features des Moduls raus
    return {
        gebote: readonly(gebotState),
        updateGebote,
        sendeGebot
    }
}

