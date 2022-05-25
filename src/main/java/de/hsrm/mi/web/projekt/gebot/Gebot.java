package de.hsrm.mi.web.projekt.gebot;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Entity
public class Gebot {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    
    @ManyToOne
    private BenutzerProfil gebieter;

    
    @ManyToOne
    private Angebot angebot;

    private long betrag;

    @DateTimeFormat(iso=ISO.DATE_TIME)
    private LocalDateTime gebotzeitpunkt; 


    public Gebot(){

        this.betrag = 0;
        this.gebotzeitpunkt = LocalDateTime.now();
        
    }
    
}
