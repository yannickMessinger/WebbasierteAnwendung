package de.hsrm.mi.web.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import de.hsrm.mi.web.projekt.projektuser.ProjektUserRepository;

public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private ProjektUserRepository projektUserRepo;


    @Override
    public UserDetails loadUserByUsername(String username) {
        
        return null;
    }
    
}
