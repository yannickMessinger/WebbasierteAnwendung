package de.hsrm.mi.web.projekt.projektuser;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.TransientPropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerprofilServiceImpl;

@Service
public class ProjektUserServiceImpl implements ProjektUserService {


    public static final Logger logger = LoggerFactory.getLogger(ProjektUserServiceImpl.class);

    @Autowired
    private PasswordEncoder pw_encoder;

    @Autowired
    private ProjektUserRepository projektUserRepo;

    @Autowired
    private BenutzerprofilServiceImpl profil_service_Impl;


    
    public ProjektUserServiceImpl(ProjektUserRepository u,BenutzerprofilServiceImpl b){
        this.projektUserRepo = u;
        this.profil_service_Impl = b;
    }

    



    @Override
    @Transactional
    public ProjektUser neuenBenutzerAnlegen(String username, String klartextpasswort, String rolle) throws ProjektUserServiceException {
        
        logger.info("ProjektUser DB neuenBenutzerAnlegen()");
        ProjektUser addProjektUser = new ProjektUser();
        BenutzerProfil addProfil = new BenutzerProfil();

        if(projektUserRepo.existsById(username)){
            throw new ProjektUserServiceException("User " + username + " bereits in Datenbank vorhanden!!!");
        }

        if(rolle == null || rolle.equals("")){
            //rolle+="USER";
            addProjektUser.setRole("USER");
        }

        addProjektUser.setPassword(pw_encoder.encode(klartextpasswort));
        addProjektUser.setUsername(username);
        addProfil.setName(username);
        addProfil.setAdresse("Platzhalterstr.1");
        addProfil.setInteressen("Platzhalterinteressen");
    

   
        
        addProfil = profil_service_Impl.speichereBenutzerProfil(addProfil);
        addProjektUser = projektUserRepo.save(addProjektUser);
        
        

        logger.info("ProjektUser angelegt");

        addProfil.setProjektUser(addProjektUser);
        addProjektUser.setBenutzerprofil(addProfil);
        
        
        
        return projektUserRepo.save(addProjektUser);
       
    }



    @Override
    @Transactional
    public ProjektUser findeBenutzer(String username){
        
        logger.info("ProjektUser DB findeBenutzer()");
        
        Optional<ProjektUser> foundProjektUser = projektUserRepo.findById(username);

        if(foundProjektUser.isEmpty()){
            throw new ProjektUserServiceException();
            
        
        }else if(foundProjektUser.isPresent()){
            foundProjektUser = Optional.of(foundProjektUser.get());
            
        }

        return foundProjektUser.get();
    }

    
    
}
