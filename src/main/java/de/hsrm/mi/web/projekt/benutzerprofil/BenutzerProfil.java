package de.hsrm.mi.web.projekt.benutzerprofil;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import de.hsrm.mi.web.projekt.angebot.Angebot;
import de.hsrm.mi.web.projekt.gebot.Gebot;
import de.hsrm.mi.web.projekt.validierung.Bunt;

@Entity
public class BenutzerProfil {
    
    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;


    private double lat;

    private double lon;



    @Size(min = 3, max = 60, message = "{name_fehler}")
    @NotNull
    private String name;

    @PastOrPresent(message ="{geburtstag_fehler}")
    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate geburtsdatum;
    
    @NotNull(message = "{adresse_fehler}")
    @NotBlank(message = "{adresse_fehler}")
    private String adresse;
    
    @Email(message = "{email_fehler}")
    private String email;
    
    @Bunt(message = "{farbe_fehler}")
    @NotNull(message = "{farbe_fehler}")
    private String lieblingsfarbe;
    
    @NotNull(message = "{interessen_fehler}")
    @NotBlank(message = "{interessen_fehler}")
    private String interessen;

    @OneToMany(mappedBy = "anbieter",cascade=CascadeType.ALL
    ,orphanRemoval=true, fetch = FetchType.EAGER)
    private List<Angebot> angebote;

   
    @OneToMany(mappedBy = "gebieter")
    private List<Gebot> gebote;

    public BenutzerProfil(){
        
        this.name = "";
        this.geburtsdatum = LocalDate.of(1,1,1);
        this.adresse = "";
        this.email = null;
        this.lieblingsfarbe = "";
        this.interessen = "";
        this.angebote = new ArrayList<>();
        this.gebote = new ArrayList<>();

    }


    public List<String> getInteressenListe(){

        List<String> interessenListe = new ArrayList<String>();
        List<String> clean_Strings = new ArrayList<String>();
        
        this.interessen = this.interessen.strip();
        

        if(this.interessen.equals("")){
            return interessenListe;
        }
        
        
        //Map trim() nomma auf alle Listenelemente
        interessenListe = Arrays.asList(this.interessen.split(","));
        
        for(String i : interessenListe) {
            clean_Strings.add(i.trim());
          }
          
          interessenListe = clean_Strings;

        

        return interessenListe;

    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }


    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }


    public String getAdresse() {
        return adresse;
    }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getLieblingsfarbe() {
        return lieblingsfarbe;
    }


    public void setLieblingsfarbe(String lieblingsfarbe) {
        this.lieblingsfarbe = lieblingsfarbe;
    }


    public String getInteressen() {
        return interessen;
    }


    public void setInteressen(String interessen) {
        this.interessen = interessen;
    }


    @Override
    public String toString() {
        return " Der Username ist:" + name + ", wohnhaft in: " + adresse +"lat("+ lat +")" + "lon("+ lon +")" + ", Geburtstag am: " + geburtsdatum + ", email: " + email + ", Lieblingsfarbe: " + lieblingsfarbe + " ,Interessen: " + interessen + "die H2 ID ist: " + String.valueOf(id) + " in Version: " + String.valueOf(version);
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BenutzerProfil other = (BenutzerProfil) obj;
        if (geburtsdatum == null) {
            if (other.geburtsdatum != null)
                return false;
        } else if (!geburtsdatum.equals(other.geburtsdatum))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public long getId(){
        return this.id;
    }


    public double getLat(){
        return this.lat;
    }

    public double getLon(){
        return this.lon;
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public void setLon(double lon){
        this.lon = lon;
    }


    public List<Angebot> getAngebote() {
        return angebote;
    }


    public void setAngebote(List<Angebot> angebote) {
        this.angebote = angebote;
    }
    
    
}
