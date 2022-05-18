package de.hsrm.mi.web.projekt.geo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class  GeoServiceImpl implements GeoService {

    @Override
    public List<AdressInfo> findeAdressInfo(String adresse) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double abstandInKm(AdressInfo adr1, AdressInfo adr2) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double abstandKmNachGrad(double abstand) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
