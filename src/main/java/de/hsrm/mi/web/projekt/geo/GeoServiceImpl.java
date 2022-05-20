package de.hsrm.mi.web.projekt.geo;

import java.util.List;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;


@Service
public class  GeoServiceImpl implements GeoService {
    
    public static final Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);
    
   

    @Override
    public List<AdressInfo> findeAdressInfo(String adresse) {
    
   
    WebClient web_Client = WebClient.create("https://nominatim.openstreetmap.org");
        List<AdressInfo> antworten = web_Client.get()
                            .uri(UriBuilder -> UriBuilder
                            .path("/search")
                            .queryParam("q",adresse)
                            .queryParam("format","json")
                            .build())
                            .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToFlux(AdressInfo.class)
                        .collectList()
                        .block();                           
        
        
        return null;
    }

    @Override
    public double abstandInKm(AdressInfo adr1, AdressInfo adr2) {
        return 0;
    }

    @Override
    public double abstandKmNachGrad(double abstand) {
        return 0;
    }
    
}
