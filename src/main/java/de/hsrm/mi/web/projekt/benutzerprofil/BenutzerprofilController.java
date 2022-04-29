package de.hsrm.mi.web.projekt.benutzerprofil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")


public class BenutzerprofilController {
    
    @GetMapping("benutzerprofil")
    public String showProfilansicht(){

    
    
        return "benutzerprofil/profilansicht";
    }
}
