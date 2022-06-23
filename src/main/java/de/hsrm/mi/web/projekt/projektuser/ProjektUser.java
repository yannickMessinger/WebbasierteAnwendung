package de.hsrm.mi.web.projekt.projektuser;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Entity
public class ProjektUser {
    
    @Id
    @Size(min = 3,  message="username muss mind. 3 Zeichen haben")
    @NotBlank(message = "username darf nicht leer sein")
    private String username;

    @Size(min = 3,  message="password muss mind. 3 Zeichen haben")
    @NotBlank(message = "password darf nicht leer sein")
    private String password;
    
    private String role;

    @OneToOne
    private BenutzerProfil benutzerProfil;


    public ProjektUser(){
        this.role = "";
        
    }

    
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public BenutzerProfil getBenutzerprofil() {
        return benutzerProfil;
    }


    public void setBenutzerprofil(BenutzerProfil benutzerProfil) {
        this.benutzerProfil = benutzerProfil;
    }

    
    


    
}
