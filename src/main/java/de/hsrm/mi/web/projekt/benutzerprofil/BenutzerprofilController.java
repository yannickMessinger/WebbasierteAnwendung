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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.geo.GeoService;

@Controller
@RequestMapping("/")
@SessionAttributes(names = {"profil"})

public class BenutzerprofilController {
    
    

    public static final Logger logger = LoggerFactory.getLogger(BenutzerprofilController.class);

    @Autowired
    private BenutzerprofilService b_profilService;

    @Autowired
    private GeoService geo_Service;

    
    
    @ModelAttribute("profil")
    public void initBenutzerProfil(Locale locale, Model m) {

        BenutzerProfil profil = new BenutzerProfil();
        m.addAttribute("profil", profil);
        m.addAttribute("sprache", locale.getDisplayLanguage());
        
    }

    //hier alle GET

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

    @GetMapping("benutzerprofil/angebot")
    public String getAngebotsFormular(Model m){

        Angebot a = new Angebot();

      

        m.addAttribute("angebot",a);



        return "benutzerprofil/angebotsformular";
    }


    @GetMapping("benutzerprofil/angebot/{id}/del")
    public String delAngebotfromList(@PathVariable("id") long id, @SessionAttribute("profil") BenutzerProfil profil, Model m){
        
        logger.info("Angebot mit ID: " + String.valueOf(id) + " wird entfernt!");
        b_profilService.loescheAngebot(id);

        m.addAttribute("profil", b_profilService.holeBenutzerProfilMitId(profil.getId()).get());
        
        return "redirect:/benutzerprofil";
    }
    
 

    //------------------------------------------------------------------------------------------------------------------
    //hier alles POST

    @PostMapping("/benutzerprofil/bearbeiten")
    public String postForm(@Valid @ModelAttribute("profil") BenutzerProfil profil, BindingResult result, Model m){
        
        

        if(result.hasErrors()){
            
            logger.error("fehlerhafte Profileingabe!",profil);
            return "benutzerprofil/profileditor";
        }


       

        m.addAttribute("profil", b_profilService.speichereBenutzerProfil(profil));
        
        
        



        return "redirect:/benutzerprofil";
    }


    @PostMapping("/benutzerprofil/angebot")
    public String postAngebot(@SessionAttribute("profil") BenutzerProfil profil,@ModelAttribute("angebot") Angebot a, Model m){
       
        logger.info("Akt Session Profil ID: " + String.valueOf(profil.getId()));
        logger.info("Angebotsinhalt:" + a.getBeschreibung());

        b_profilService.fuegeAngebotHinzu(profil.getId(), a);
        //so SessionAtt aktualisieren??
        m.addAttribute("profil", b_profilService.holeBenutzerProfilMitId(profil.getId()).get());

        return "redirect:/benutzerprofil";
    }





}
