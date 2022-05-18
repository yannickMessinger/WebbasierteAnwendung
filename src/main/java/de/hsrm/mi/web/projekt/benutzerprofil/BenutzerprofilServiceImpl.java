package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public  class BenutzerprofilServiceImpl implements BenutzerprofilService {

    

    //stimmt des so, ist das die Depenency Injection?
    public static final Logger logger = LoggerFactory.getLogger(BenutzerprofilServiceImpl.class);
    private BenutzerprofilRepository profil_repository;

    @Autowired
    public BenutzerprofilServiceImpl(BenutzerprofilRepository b){
        this.profil_repository = b;
    }

    @Override
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        /*speichert das übergebene bp im
        BenutzerprofilRepository ab. Bitte geben Sie das durch die Speicheraktion
        entstandene Entity zurück (und machen Sie sich klar, ob und worin sich dieses Rückgabeobjekt
        vom anfangs hereingereichten bp unterscheiden könnte).*/
        logger.info("speichere Benutzerprofil || BenutzerprofilServiceImpl speichereBenutzerProfil()");
        
        return profil_repository.save(bp);
    }

    @Override
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        /*liefert das BenutzerProfil mit der gewünschten id in einem
        Optional zurück, falls die id gefunden wurde, ansonsten ein leeres Optional.*/
        logger.info("hole Nutzerprofil || BenutzerprofilServiceImpl holeBenutzerProfilMitId()");

        Optional<BenutzerProfil> foundProfil = profil_repository.findById(id);

        if(foundProfil.isEmpty()){
            foundProfil = Optional.empty();
        
        }else if(foundProfil.isPresent()){
            foundProfil = Optional.of(foundProfil.get());
        }



        return foundProfil;
    }

    @Override
    public List<BenutzerProfil> alleBenutzerProfile() {
        /*gibt eine Liste aller BenutzerProfile, nach dem Inhalt von name aufsteigend
        sortiert, zurück.
        Hinweis 1: Verwenden Sie hierbei die Möglichkeit, der Repository-Standardmethode
        findAll() optional ein Sortierkriterium mitgeben zu können. Die Datenbank liefert Ihnen die
        BenutzerProfil-Objekte dann gleich in der gewünschten Reihenfolge. Sortieren Sie die
        Rückgabe-Liste der Repository-Abfrage nicht nachträglich “von Hand” in Java, eine Datenbank
        kann das gerade bei großen Datenmengen viel besser.*/
        logger.info("Nutzerliste angefordert|| BenutzerprofilServiceImpl alleBenutzerProfile()");


        List<BenutzerProfil> profilListOrderedByName = new ArrayList<BenutzerProfil>();
        
        profilListOrderedByName = profil_repository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        
        
        return profilListOrderedByName;
    }

    @Override
    public void loescheBenutzerProfilMitId(Long loesch) {
        /*löscht das Benutzerprofil mit der ID id aus dem Repository.
        Bitte vergessen Sie das Logging für die Service-Methoden nicht.*/

        //Logger noch rüber ziehen
        logger.info("Nutzerprofil gelöscht || BenutzerprofilServiceImpl loescheBenutzerProfilMitId()");
        profil_repository.deleteById(loesch);
    }
    
}
