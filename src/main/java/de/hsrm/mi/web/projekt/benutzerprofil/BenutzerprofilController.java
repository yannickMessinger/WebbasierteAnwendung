package de.hsrm.mi.web.projekt.benutzerprofil;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        m.addAttribute("profil", profil);
        

    }


    @GetMapping("benutzerprofil")
    public String getProfilansicht(){
        
        return "benutzerprofil/profilansicht";
    }

    @GetMapping("benutzerprofil/bearbeiten")
    public String showProfilEditor(Model m){
        
        return "benutzerprofil/profileditor";
    }


    @PostMapping("/benutzerprofil/bearbeiten")
    public String postForm(@ModelAttribute("profil") BenutzerProfil profil, BindingResult result){
        
        if(result.hasErrors()){
            return "benutzerprofil/profileditor";
        }

        
        



        return "redirect:/benutzerprofil";
    }


}
