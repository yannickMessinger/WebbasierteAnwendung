package de.hsrm.mi.web.projekt.benutzerprofil;

import java.time.LocalDate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")


public class BenutzerprofilController {
    

    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);
    
    


    @GetMapping("benutzerprofil")
    public String getProfilansicht(@ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m){
        
        
        profil.setName("Yannick");
        profil.setGeburtsdatum(LocalDate.of(1991,9,12));
        profil.setAdresse("Geheime Straße 101");
        profil.setEmail("vauzwomachtfroh@web.de");
        profil.setLieblingsfarbe("orange");
        profil.setInteressen("racen, rasten, tanken");
        
        
        
        
        
        if(result.hasErrors()) {
            logger.error("Problem beim binden der werte", profil);
        }
        
        return "benutzerprofil/profilansicht";
    }


}
