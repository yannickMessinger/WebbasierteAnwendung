<template>
<div>
    <div>
        <input type="text" v-model="suchfeld" placeholder="Suchbegriff oder Sterne" />
    </div>
<div>
<table>
        <thead>
            <th style="width:75%">Erkenntnis</th>
            <th style="width:25%">Rating</th>
        </thead>
    
        <tbody>
        <AngebotListItem :item="ele" v-for="ele in angebotsliste" :key="ele.angebotid"/>
        </tbody>
    </table>
</div>


</div>






</template>




<script setup lang="ts">

    import { ref, computed} from 'vue';
    import type { AngebotListeDing, IAngebotListeItem } from '@/services/IAngebotListeItem';
    import {useFakeAngebot} from '@/services/useFakeAngebot'

    

    const neuigkeiten = ref("");
    const suchfeld = ref("");
    const items = ref<IAngebotListeItem[]>([]);
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