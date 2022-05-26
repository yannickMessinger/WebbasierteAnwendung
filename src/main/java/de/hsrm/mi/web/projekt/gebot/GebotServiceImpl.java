package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;

public class GebotServiceImpl implements GebotService{


    /*
    !Bitte greifen Sie bei der Umsetzung von GebotServiceImpl auf kein anderes Repository als das
    !GebotRepository direkt zu und nutzen Sie ansonsten Methoden eines vorhandenen anderen Service, um
    !benötigte Objekte zu erhalten
    */
    public static final Logger logger = LoggerFactory.getLogger(GebotServiceImpl.class);

    private GebotRepository gebot_Repository;
    private BenutzerprofilServiceImpl benutzerProfil_service;


    @Autowired
    public GebotServiceImpl(GebotRepository g, BenutzerprofilServiceImpl b){

        this.gebot_Repository = g;
        this.benutzerProfil_service = b;
    }


    @Override
    @Transactional
    public List<Gebot> findeAlleGebote() {

        //liefert (erwartbar) eine Liste aller gespeicherten Gebote.

        List<Gebot> alleGebote = new ArrayList<>();

        alleGebote = gebot_Repository.findAll();
        
        return alleGebote;
    }

    @Override
    @Transactional
    public List<Gebot> findeAlleGeboteFuerAngebot(long angebotid) {

        //liefert (ebenso erwartbar) eine Liste der Gebote, die sich auf das Angebot mit der übergebenen ID beziehen.

        List<Gebot> alleGeboteFuerAngebotList = benutzerProfil_service.findeAngebotMitId(angebotid).get().getGebote();

        return alleGeboteFuerAngebotList;
    }

    @Override
    @Transactional
    public Gebot bieteFuerAngebot(long benutzerprofilid, long angebotid, long betrag) {
        
        Optional<Gebot> sucheGebot = gebot_Repository.findByAngebotIdAndBieterId(angebotid, benutzerprofilid);
        BenutzerProfil bietender = benutzerProfil_service.holeBenutzerProfilMitId(benutzerprofilid).get();
        Angebot aufWelchesAngebot = benutzerProfil_service.findeAngebotMitId(angebotid).get();
        Gebot gebot = null;

        if(sucheGebot.isEmpty()){
            
            gebot = new Gebot();
            gebot.setBetrag(betrag);
            gebot.setGebieter(bietender);
            gebot.setAngebot(aufWelchesAngebot);
        
        }else if(sucheGebot.isPresent()){
            
            //eigentlich müsste hier ja schon der Bieter und welches Angebot es ist gesetzt sein....
            gebot = sucheGebot.get();
            gebot.setBetrag(betrag);
            gebot.setGebotzeitpunkt(LocalDateTime.now());
            


        }
        
        

        return gebot;
    }

    @Override
    @Transactional
    public void loescheGebot(long gebotid) {

        List<Gebot> gebotsList = gebot_Repository.findAll();
        Gebot delGebot;

        for(Gebot g : gebotsList){
            if(g.getId() == gebotid){
                
                delGebot = g;
                g.getGebieter().getGebote().remove(delGebot);
            }
        }
        
        
        gebot_Repository.deleteById(gebotid);
        
    }
    
}
