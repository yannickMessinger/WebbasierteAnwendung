<template>



<div>
<table>
        <thead>
            <input type="text" v-model="suchfeld" placeholder="Suchbegriff" />
        </thead>
    
        <tbody>
            <AngebotListeItem :angebot="ele" v-for="ele in angebotslistefiltered" :key="ele.anbieterid"/>
        </tbody>
    
    </table>
</div>

</template>




<script setup lang="ts">

    import { ref, computed} from 'vue';
    import AngebotListeItem from '../components/AngebotListeItem.vue'
    import {useFakeAngebot} from '@/services/useFakeAngebot'

    

    

    
    const suchfeld = ref("");
    const angebotsliste = useFakeAngebot().angebote
    
    
    const angebotslistefiltered = computed(() => {
    const n: number = suchfeld.value.length;

        if (suchfeld.value.length < 3) {
            return angebotsliste.value;
        } else {
        
            return angebotsliste.value.filter(e =>
            e.abholort.toLowerCase().includes(suchfeld.value.toLowerCase()) || e.beschreibung.toLocaleLowerCase().includes(suchfeld.value.toLowerCase()) || e.anbietername.toLocaleLowerCase().includes(suchfeld.value.toLowerCase()) 
            );
        }
    });
    
    
    








</script>