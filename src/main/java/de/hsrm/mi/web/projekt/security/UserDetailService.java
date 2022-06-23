package de.hsrm.mi.web.projekt.security;

import org.springframework.security.core.userdetails.UserDetails;

// Verbindung zwischen Spring Security und unserer selbstgebastelten ProjektUser-Tabelle

public interface UserDetailService {
    UserDetails loadUserByUsername(String username);
    
}
