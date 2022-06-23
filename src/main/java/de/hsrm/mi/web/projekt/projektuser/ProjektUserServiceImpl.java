package de.hsrm.mi.web.projekt.projektuser;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProjektUserServiceImpl implements ProjektUserService {


    public static final Logger logger = LoggerFactory.getLogger(ProjektUserServiceImpl.class);

    @Autowired
    private PasswordEncoder pw_encoder;

    @Autowired
    private ProjektUserRepository projektUserRepo;


    public ProjektUserServiceImpl(ProjektUserRepository u){
        this.projektUserRepo = u;
    }





    @Override
    @Transactional
    public ProjektUser neuenBenutzerAnlegen(String username, String klartextpasswort, String rolle) {
        
        logger.info("ProjektUser DB neuenBenutzerAnlegen()");
        ProjektUser addProjektUser = new ProjektUser();

        if(projektUserRepo.existsById(username)){
            throw new ProjektUserServiceException();
        }

        if(rolle == null || rolle.equals("")){
            //rolle+="USER";
            addProjektUser.setRole("USER");
        }

        addProjektUser.setPassword(pw_encoder.encode(klartextpasswort));
        addProjektUser.setUsername(username);
        logger.info("ProjektUser angelegt");
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

    //Syntax von Folie 250 klappt hier net
    
}
