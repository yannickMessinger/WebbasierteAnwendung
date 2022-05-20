package de.hsrm.mi.web.projekt.geo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//kp ob ich das so brauch...
@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")

public record AdressInfo(String display_name, String type,
double lat, double lon) {
    
}
