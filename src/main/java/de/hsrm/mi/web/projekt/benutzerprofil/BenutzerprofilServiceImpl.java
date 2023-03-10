package de.hsrm.mi.web.projekt.benutzerprofil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.angebot.AngebotRepository;
import de.hsrm.mi.web.projekt.geo.AdressInfo;
import de.hsrm.mi.web.projekt.geo.GeoService;
import de.hsrm.mi.web.projekt.geo.GeoServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public  class BenutzerprofilServiceImpl implements BenutzerprofilService {

    

    
    public static final Logger logger = LoggerFactory.getLogger(BenutzerprofilServiceImpl.class);
    
    @Autowired
    private BenutzerprofilRepository profil_repository;
    @Autowired
    private GeoServiceImpl geoService;
    @Autowired
    private AngebotRepository angebot_repository;

        
    public BenutzerprofilServiceImpl(BenutzerprofilRepository b,  GeoServiceImpl g, AngebotRepository a){
        this.profil_repository = b;
        this.geoService = g;
        this.angebot_repository = a;
    }
    

    @Override
    @Transactional
    public BenutzerProfil speichereBenutzerProfil(BenutzerProfil bp) {
        
        List<AdressInfo> AdressInfoList = geoService.findeAdressInfo(bp.getAdresse());

        logger.info("setze Adressdaten || BenutzerprofilServiceImpl -> GeoService -> speichereBenutzerProfil()");
        

        if(AdressInfoList.isEmpty()){
                bp.setLat(0.0);
                bp.setLon(0.0);
        
        }else{
                bp.setLat(AdressInfoList.get(0).lat());
                bp.setLon(AdressInfoList.get(0).lon());
        }

        logger.info("speichere Benutzerprofil || BenutzerprofilServiceImpl speichereBenutzerProfil()");
        
        return profil_repository.save(bp);
    }

    @Override
    @Transactional
    public Optional<BenutzerProfil> holeBenutzerProfilMitId(Long id) {
        
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
    @Transactional
    public List<BenutzerProfil> alleBenutzerProfile() {
        
        logger.info("Nutzerliste angefordert|| BenutzerprofilServiceImpl alleBenutzerProfile()");


        List<BenutzerProfil> profilListOrderedByName = new ArrayList<BenutzerProfil>();
        
        profilListOrderedByName = profil_repository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        
        
        return profilListOrderedByName;
    }

    @Override
    @Transactional
    public void loescheBenutzerProfilMitId(Long loesch) {
        
        logger.info("Nutzerprofil gel??scht || BenutzerprofilServiceImpl loescheBenutzerProfilMitId()");
        profil_repository.deleteById(loesch);
    }


    @Override
    @Transactional
    public void fuegeAngebotHinzu(long id, Angebot angebot) {
        
        BenutzerProfil foundProfil = profil_repository.findById(id).orElseThrow();
        
        
        List<AdressInfo> AngebotAdressInfoList = geoService.findeAdressInfo(angebot.getAbholort());

        logger.info("setze Adressdaten || BenutzerprofilServiceImpl -> GeoService -> speichereBenutzerProfil()");
        if(AngebotAdressInfoList.isEmpty()){
                angebot.setLat(0.0);
                angebot.setLon(0.0);
        
        }else{
                
                angebot.setLat(AngebotAdressInfoList.get(0).lat());
                angebot.setLon(AngebotAdressInfoList.get(0).lon());
        }

        
        
        foundProfil.getAngebote().add(angebot);
        angebot.setAnbieter(foundProfil);

        profil_repository.save(foundProfil);
        

        
        
    }

    @Override
    @Transactional
    public void loescheAngebot(long id) {
        
        
        Angebot angebot = angebot_repository.findById(id).orElseThrow();
        // Angebot angebot = angebot_repository.findById(id).get();

        BenutzerProfil profil = angebot.getAnbieter();
        
        profil.getAngebote().remove(angebot);

        angebot_repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Angebot> alleAngebote() {
       
        List<Angebot> alleAngeboteList  = angebot_repository.findAll();

        

        return alleAngeboteList;
    }

    @Override
    @Transactional
    public Optional<Angebot> findeAngebotMitId(long angebotid) {
        
        Optional<Angebot> gefundenes_angebot = angebot_repository.findById(angebotid);

        if(gefundenes_angebot.isEmpty()){
            gefundenes_angebot = Optional.empty();
        
        }else if(gefundenes_angebot.isPresent()){
            gefundenes_angebot = Optional.of(gefundenes_angebot.get());
        }



        return gefundenes_angebot;

        
    }



    
}
