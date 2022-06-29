package de.hsrm.mi.web.projekt.api.gebot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.gebot.GebotService;
import de.hsrm.mi.web.projekt.messaging.BackendInfoServiceImpl;
import de.hsrm.mi.web.projekt.messaging.BackendOperation;


@RestController
@RequestMapping("/")
public class GebotRestController {

    public static final Logger logger = LoggerFactory.getLogger(GebotRestController.class);

    @Autowired
    private GebotService g_gebotService;

    
    
    @GetMapping("api/gebot")
    public List<GetGebotResponseDTO> getGebotResponseDTOList(){
        logger.info("Gebot REST aufgerufen!");
        List<Gebot> alleGebote =  g_gebotService.findeAlleGebote();
        
        List<GetGebotResponseDTO> responseDTOList = alleGebote.stream()
                .map(gebot -> GetGebotResponseDTO.from(gebot))
                .collect(Collectors.toList());
        
        

        return responseDTOList;
    }

    @PostMapping(value = "api/gebot",consumes = MediaType.APPLICATION_JSON_VALUE)
    public long addGebot(@RequestBody AddGebotRequestDTO addGebot){
        
    
    //alter...VL Folien 137/138
    
    return g_gebotService.bieteFuerAngebot(addGebot.benutzerprofilid(), addGebot.angebotid(), addGebot.betrag()).getId();
        
        
        
       
    }

    @DeleteMapping("api/gebot/{id}")
    public void delGebot(@PathVariable("id") long id){
        g_gebotService.loescheGebot(id);
    }
        

}    

