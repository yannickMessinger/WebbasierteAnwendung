package de.hsrm.mi.web.projekt.gebot;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import de.hsrm.mi.web.projekt.api.gebot.GetGebotResponseDTO;

@Service
public class GebotMessageSender {

    public static final Logger logger = LoggerFactory.getLogger(GebotMessageSender.class);

    @Autowired
    private SimpMessagingTemplate messaging;

        @TransactionalEventListener
        @EventListener
        public void sendGebotResponseDTO(GetGebotResponseDTO gebotresponseDTO){
            logger.info("GebotMessageSender GebotresponseDTO gesendet");
            messaging.convertAndSend("/topic/gebot/" + gebotresponseDTO.gebotid() ,gebotresponseDTO);
        }


}
