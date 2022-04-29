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
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {
    

    Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);
    
    @ModelAttribute("profil")
    public void initBenutzerProfil(Model m) {

        BenutzerProfil profil = new BenutzerProfil();
        profil.setName("Yannick");
        profil.setGeburtsdatum(LocalDate.of(1993,7,13));
        profil.setAdresse("Geheime Straße 101");
        profil.setEmail("vauzwomachtfroh@web.de");
        profil.setLieblingsfarbe("orange");
        profil.setInteressen("weit hüpfen, fern sehen  ,  Topflappen häkeln");
        
        m.addAttribute("profil", profil);
        

    }


    @GetMapping("benutzerprofil")
    public String getProfilansicht(@ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m){
        
        
        m.addAttribute("name", profil.getName());
        m.addAttribute("geburtstag", profil.getGeburtsdatum());
        m.addAttribute("adresse", profil.getAdresse());
        m.addAttribute("email", profil.getEmail());
        m.addAttribute("lieblingsfarbe", profil.getLieblingsfarbe());
        m.addAttribute("interessen", profil.getInteressenListe());

        
        
        
        
        
        if(result.hasErrors()) {
            logger.error("Problem beim binden der werte", profil);
        }
        
        return "benutzerprofil/profilansicht";
    }


}
