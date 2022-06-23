package de.hsrm.mi.web.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.projektuser.ProjektUser;
import de.hsrm.mi.web.projekt.projektuser.ProjektUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private ProjektUserRepository projektUserRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
    ProjektUser user = projektUserRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    
    // Schritt 2: Spring 'User'-Objekt mit relevanten Daten für 'username' zurückgeben
    return org.springframework.security.core.userdetails.User
    .withUsername(username)
    .password(user.getPassword()) // falls in DB encoded gespeichert
    .roles("USER") // Rolle könnte auch aus DB kommen
    .build();
    }
    
}
