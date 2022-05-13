package de.hsrm.mi.web.projekt.benutzerprofil;




import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {
    
    

    public static final Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);

    @Autowired
    private BenutzerprofilService b_profilService;


    
    @ModelAttribute("profil")
    public void initBenutzerProfil(Locale locale, Model m) {

        BenutzerProfil profil = new BenutzerProfil();
        m.addAttribute("profil", profil);
        m.addAttribute("sprache", locale.getDisplayLanguage());

    }


    @GetMapping("benutzerprofil")
    public String getProfilansicht(Model m){
        

        return "benutzerprofil/profilansicht";
    }

    @GetMapping("benutzerprofil/bearbeiten")
    public String showProfilEditor(Model m){
        
        
        
        return "benutzerprofil/profileditor";
    }

    @GetMapping("benutzerprofil/clearsession")
    public String clearUserSession(SessionStatus s){
        
        logger.info("Session Status komplettiert");
        s.setComplete();

        return "redirect:/benutzerprofil";
    }


    @PostMapping("/benutzerprofil/bearbeiten")
    public String postForm(@Valid @ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m){
        
        

        if(result.hasErrors()){
            
            logger.error("fehlerhafte Profileingabe!",profil);
            return "benutzerprofil/profileditor";
        }


        /*Also muss das in profil gehaltene BenutzerProfil auch hinsichtlich version stets mit
        dem Stand in der DB abgeglichen sein Muss man das noch iwo abfangen?!*/

        m.addAttribute("profil", b_profilService.speichereBenutzerProfil(profil));
        
        
        



        return "redirect:/benutzerprofil";
    }





}
