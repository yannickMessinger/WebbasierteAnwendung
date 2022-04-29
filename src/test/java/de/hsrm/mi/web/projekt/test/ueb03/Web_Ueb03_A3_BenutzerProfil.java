package de.hsrm.mi.web.projekt.test.ueb03;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import de.hsrm.mi.web.projekt.benutzerprofil.BenutzerProfil;

@Testable
public class Web_Ueb03_A3_BenutzerProfil {
    private final String TESTNAME = "Jöndhard";
    private final String TESTORT = "Waldstraße 17, 99441 Vollradisroda";
    private final LocalDate TESTDATUM = LocalDate.now();
    private final String TESTEMAIL = "joendhard@mi.hs-rm.de";
    private final String TESTLIEBLINGSFARBE = "#171717";
    private final String TESTINTERESSEN1 = "hüpfen,gucken,Briefmarken sammeln";
    private final List<String> TESTINTERESSEN1_L = List.of("hüpfen", "gucken", "Briefmarken sammeln");
    private final String TESTINTERESSEN2 = "weit hüpfen, fern sehen  ,  Topflappen häkeln";
    private final List<String> TESTINTERESSEN2_L = List.of("weit hüpfen", "fern sehen", "Topflappen häkeln");
    
    BenutzerProfil benutzerprofil = null;

    @BeforeEach
    public void benutzerprofil_init() {
        benutzerprofil = new BenutzerProfil();
        benutzerprofil.setName(TESTNAME);
        benutzerprofil.setAdresse(TESTORT);
        benutzerprofil.setGeburtsdatum(TESTDATUM);
        benutzerprofil.setEmail(TESTEMAIL);
        benutzerprofil.setLieblingsfarbe(TESTLIEBLINGSFARBE);
        benutzerprofil.setInteressen(TESTINTERESSEN1);
    }

    @Test
    @DisplayName("BenutzerProfil-Instanz anlegen und setter/toString() testen")
    public void benutzerprofil_vorhanden() {
        String tostr = benutzerprofil.toString();
        assertThat(tostr).contains(TESTNAME);
        assertThat(tostr).contains(TESTORT);
        assertThat(tostr).contains(TESTEMAIL);
    }

    @Test
    @DisplayName("BenutzerProfil: getInteressenListe() checken")
    public void benutzerprofil_getinteressenliste() {
        assertThat(benutzerprofil.getInteressenListe()).containsExactlyElementsOf(TESTINTERESSEN1_L);
        benutzerprofil.setInteressen(TESTINTERESSEN2);
        assertThat(benutzerprofil.getInteressenListe()).containsExactlyElementsOf(TESTINTERESSEN2_L);
    }

    @Test
    @DisplayName("BenutzerProfil: equals()/hashCode()")
    public void benutzerprofil_equalshashcode() {
        var bp2 = new BenutzerProfil();
        bp2.setName(TESTNAME);
        bp2.setAdresse("Hauptstraße 17, 12345 Dahannesburg");
        bp2.setGeburtsdatum(TESTDATUM);
        bp2.setEmail("spam@mi.hs-rm.de");
        bp2.setLieblingsfarbe("#010203");
        bp2.setInteressen(TESTINTERESSEN2);
        assertThat(bp2).isEqualTo(benutzerprofil);
        assertThat(bp2.hashCode()).isEqualTo(benutzerprofil.hashCode());
    }
}