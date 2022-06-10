<template>



<div>
<table>
        <thead>
            <input type="text" v-model="suchfeld" placeholder="Suchbegriff oder Sterne" />
        </thead>
    
        <tbody>
            <AngebotListeItem :angebot="ele" v-for="ele in angebotsliste" :key="ele.anbieterid"/>
        </tbody>
    
    </table>
</div>

</template>




<script setup lang="ts">

    import { ref, computed} from 'vue';
    import type { AngebotListeDing, IAngebotListeItem } from '@/services/IAngebotListeItem';
    import AngebotListeItem from '../components/AngebotListeItem.vue'
    import {useFakeAngebot} from '@/services/useFakeAngebot'

    

    

    
    const suchfeld = ref("");
    const angebotsliste = useFakeAngebot().angebote
    // ... zu ergaenzen: "items.value" mit Daten fuellen
    
    //const listitems = computed(() => {
    //const n: number = suchfeld.value.length;
        //if (suchfeld.value.length < 3) {
            //return items.value;
        //} else {
        
            //return items.value.filter(e =>
            //e.text.toLowerCase()
            //.includes(suchfeld.value.toLowerCase())
            //);
        //}
    //});
    
    //function delZeile(id: number): void {
    //neuigkeiten.value = "Löschwunsch für " + id;
    //items.value = items.value.filter(ele => ele.id!==id);
    //}









</script>