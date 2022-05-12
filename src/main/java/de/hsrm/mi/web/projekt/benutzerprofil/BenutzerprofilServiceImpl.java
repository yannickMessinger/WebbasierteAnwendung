package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public  class BenutzerprofilServiceImpl implements BenutzerprofilService {

    //Logger net vergesse!

    //stimmt des so?
    @Autowired
    BenutzerprofilRepository benutzerprofilrepository;

    @Override
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        /*speichert das übergebene bp im
        BenutzerprofilRepository ab. Bitte geben Sie das durch die Speicheraktion
        entstandene Entity zurück (und machen Sie sich klar, ob und worin sich dieses Rückgabeobjekt
        vom anfangs hereingereichten bp unterscheiden könnte).*/
        
        
        return null;
    }

    @Override
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        /*liefert das BenutzerProfil mit der gewünschten id in einem
        Optional zurück, falls die id gefunden wurde, ansonsten ein leeres Optional.*/
        
        return null;
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
        
        
        return null;
    }

    @Override
    public void loescheBenutzerProfilMitId(Long loesch) {
        /*löscht das Benutzerprofil mit der ID id aus dem Repository.
        Bitte vergessen Sie das Logging für die Service-Methoden nicht.*/
    }
    
}
