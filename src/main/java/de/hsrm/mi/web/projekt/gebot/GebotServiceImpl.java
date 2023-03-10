package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendInfoServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;

@Service
public class GebotServiceImpl implements GebotService{


    
    public static final Logger logger = LoggerFactory.getLogger(GebotServiceImpl.class);

    @Autowired
    private GebotRepository gebot_Repository;
    
    @Autowired
    private BenutzerprofilServiceImpl benutzerProfil_service;

    @Autowired
    private BackendInfoServiceImpl backEndInfo_Service;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private SimpMessagingTemplate messaging;



    
    


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

        //liefert (ebenso erwartbar) eine Liste der Gebote, die sich auf das Angebot mit der ├╝bergebenen ID beziehen.

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
            bietender.getGebote().add(gebot);
            aufWelchesAngebot.getGebote().add(gebot);
            logger.info("BACKEND INFO MESSAGE in bieteFuerAngebot() -> Create");
            
            //backEndInfo_Service.sendInfo("gebot/" + gebot.getAngebot().getId(), BackendOperation.CREATE, gebot.getId());
            GetGebotResponseDTO gebotresponseDTO = GetGebotResponseDTO.from(gebot);
            messaging.convertAndSend("/topic/gebot/" + gebot.getAngebot().getId() ,gebotresponseDTO);
            logger.info("convertAndSend -> /topic/gebot/" + gebot.getAngebot().getId());
        
            //applicationEventPublisher.publishEvent(gebotDTO);

        
        }else if(sucheGebot.isPresent()){
            
            //eigentlich m├╝sste hier ja schon der Bieter und welches Angebot es ist gesetzt sein....
            gebot = sucheGebot.get();
            gebot.setBetrag(betrag);
            gebot.setGebotzeitpunkt(LocalDateTime.now());
            logger.info("BACKEND INFO MESSAGE in bieteFuerAngebot() -> Update");
            
            //backEndInfo_Service.sendInfo("gebot/" + gebot.getAngebot().getId(), BackendOperation.UPDATE, gebot.getId());
            GetGebotResponseDTO gebotresponseDTO = GetGebotResponseDTO.from(gebot);
            messaging.convertAndSend("/topic/gebot/" + gebot.getAngebot().getId() ,gebotresponseDTO);
            logger.info("convertAndSend -> /topic/gebot/" + gebot.getAngebot().getId());
            //applicationEventPublisher.publishEvent(gebotDTO);
            


        }
        
        
        

        return  gebot_Repository.save(gebot);
    }

    @Override
    @Transactional
    public void loescheGebot(long gebotid) {

        //List<Gebot> gebotsList = gebot_Repository.findAll();
        
        Gebot delGebot = gebot_Repository.findById(gebotid).get();

        delGebot.getGebieter().getGebote().remove(delGebot);
        delGebot.getAngebot().getGebote().remove(delGebot);

        gebot_Repository.deleteById(gebotid);

        /*
        for(Gebot g : gebotsList){
            if(g.getId() == gebotid){
                
                delGebot = g;
                g.getGebieter().getGebote().remove(delGebot);
                g.getAngebot().getGebote().remove(delGebot);
            }
        }
        
        
        gebot_Repository.deleteById(gebotid);
        */
    }
    
}
