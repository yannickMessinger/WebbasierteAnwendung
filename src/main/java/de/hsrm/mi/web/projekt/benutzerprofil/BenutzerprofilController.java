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
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("benutzerprofil/liste")
    public String getProfilListe(Model m){

        logger.info("fordere Liste an || BenutzerprofilController getProfilListe()");
        
        
        
           
         m.addAttribute("profilliste", b_profilService.alleBenutzerProfile());


        return "benutzerprofil/profilliste";
    }

    @GetMapping(value = "benutzerprofil/liste", params ="op")
    public String getDelView(@RequestParam String op, @RequestParam("id") Long id, Model m){
       
        if(op.equals("loeschen")){
            logger.info("versuche User mit ID:"+ String.valueOf(id) +" zu loeschen!");
            b_profilService.loescheBenutzerProfilMitId(id);
            return "redirect:/benutzerprofil/liste";
        }
        
        if(op.equals("bearbeiten")){
            logger.info(op+ ":" + String.valueOf(id));
            m.addAttribute("profil",b_profilService.holeBenutzerProfilMitId(id).get());
            return "redirect:/benutzerprofil/bearbeiten";

        }
        
       return "redirect:/benutzerprofil/liste";
    }

 




    @PostMapping("/benutzerprofil/bearbeiten")
    public String postForm(@Valid @ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m){
        
        

        if(result.hasErrors()){
            
            logger.error("fehlerhafte Profileingabe!",profil);
            return "benutzerprofil/profileditor";
        }


       

        m.addAttribute("profil", b_profilService.speichereBenutzerProfil(profil));
        
        
        



        return "redirect:/benutzerprofil";
    }





}
