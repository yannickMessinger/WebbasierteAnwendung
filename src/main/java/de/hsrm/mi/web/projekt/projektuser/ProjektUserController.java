package de.hsrm.mi.web.projekt.projektuser;

import javax.validation.Valid;

import org.hibernate.TransientPropertyValueException;
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




@Controller
@RequestMapping("/")
@SessionAttributes(names = {"projektUser"})
public class ProjektUserController {
    
    @Autowired
    private ProjektUserServiceImpl projektUser_Service;

    public static final Logger logger = LoggerFactory.getLogger(ProjektUserController.class);

    
    //keeeeiine Ahnung
    @ModelAttribute("projektUser")
    public void initProjektUser(Model m) {

        ProjektUser user = new ProjektUser();
        m.addAttribute("projektUser", user);
        
        
    }

    //kann man das irgendwie vermeiden, das man hier passwort im klartext übermittelt???

    @PostMapping("/registrieren")
    public String postSignUpForm(@Valid @ModelAttribute("projektUser") ProjektUser user, BindingResult result, Model m){
        logger.info("ProjektUserController -> registrieren");

        if(result.hasErrors()){
            
            logger.error("fehlerhafte Profileingabe!",user);
            return "projektuser/registrieren";
        }
        
        projektUser_Service.neuenBenutzerAnlegen(user.getUsername(), user.getPassword(), user.getRole());
       
        return "redirect:/benutzerprofil";
    }

    

    @GetMapping("registrieren")
    public String getSignUpForm(Model m){
        return "projektuser/registrieren";
    }

}
