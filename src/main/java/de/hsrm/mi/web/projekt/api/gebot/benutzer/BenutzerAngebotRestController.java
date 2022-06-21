package de.hsrm.mi.web.projekt.api.gebot.benutzer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilService;
import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.gebot.GebotService;

@RestController
@RequestMapping("/")
public class BenutzerAngebotRestController {

    public static final Logger logger = LoggerFactory.getLogger(BenutzerAngebotRestController.class);

    @Autowired
    private BenutzerprofilService b_profilService;

    @Autowired
    private GebotService g_gGebotService;

    
    
    
    
    @GetMapping("api/angebot")
    public List<GetAngebotResponseDTO> getAngebotResponseDTOList(){
        logger.info("angebote REST aufgerufen!");
        List<Angebot> alleAngebote =  b_profilService.alleAngebote();
        
        List<GetAngebotResponseDTO> responseDTOList = alleAngebote.stream()
                .map(gebot -> GetAngebotResponseDTO.from(gebot))
                .collect(Collectors.toList());
        
        logger.info("Anz Angebote List: " + String.valueOf(responseDTOList.size()));
        return responseDTOList;
    }






    @GetMapping("api/angebot/{id}")
    public GetAngebotResponseDTO getAngebotAs_DTO(@PathVariable("id") long id){

        return GetAngebotResponseDTO.from(b_profilService.findeAngebotMitId(id).get());
    }






    @GetMapping("api/angebot/{id}/gebot")
    public List<GetGebotResponseDTO> getGebotResponseDTOList_perID(@PathVariable("id") long id){
        
        List<Gebot> gebotePerID = g_gGebotService.findeAlleGeboteFuerAngebot(id);

        List<GetGebotResponseDTO> responseDTOList = gebotePerID.stream()
                .map(gebot -> GetGebotResponseDTO.from(gebot))
                .collect(Collectors.toList());
        
        return responseDTOList;
    }


    @GetMapping("api/benutzer/{id}/angebot")
    public List<GetAngebotResponseDTO> getGetAngebotResponseDTOList_byNutzerProfilID(@PathVariable("id") long id){

        List<Angebot> AngebotResponseDTOList_byNutzerProfilID  = b_profilService.holeBenutzerProfilMitId(id).get().getAngebote();


        List<GetAngebotResponseDTO> responseDTOList = AngebotResponseDTOList_byNutzerProfilID.stream()
        .map(gebot -> GetAngebotResponseDTO.from(gebot))
        .collect(Collectors.toList());

        return responseDTOList;



    }




    
}
