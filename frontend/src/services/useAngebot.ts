import type { IAngebotListeItem } from '@/services/IAngebotListeItem';
import { readonly, ref,reactive } from "vue";

export interface IAngebotState {
    angebotliste: IAngebotListeItem[],
    errormessage: string
    }
    
    
//potentiell falsch
let angebotState: IAngebotState;
angebotState = reactive({angebotliste: [], errormessage:""})

const angebote = angebotState 


export function useAngebot() {

    return {
        angebote: readonly(angebote), // state vor unkontrollierten Änderungen von außen schützen
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
            console.log('Angebot von Backend angefragt')
            console.log(response)
            return response.json();
        
        })
        
        .then((jsondata: [IAngebotListeItem]) => {
            console.log('bin im then amina')
            angebotState.angebotliste = jsondata
            console.log(angebotState)
            angebotState.errormessage = ''
        }
        ).catch((error) =>{
            angebotState.errormessage = error.statusText
        })

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