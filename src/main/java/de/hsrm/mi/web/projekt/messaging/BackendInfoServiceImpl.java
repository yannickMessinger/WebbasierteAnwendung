package de.hsrm.mi.web.projekt.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BackendInfoServiceImpl implements BackendInfoService {

    public static final Logger logger = LoggerFactory.getLogger(BackendInfoServiceImpl.class);

    @Autowired
    private SimpMessagingTemplate messaging;

    @Override
    public void sendInfo(String topicname, BackendOperation operation, long id) {
        final String TOPIC = "/topic/";
        final String dest = TOPIC + topicname;
        logger.info("BackendInfoService zu Destination:" + dest + " ,Operation:" + String.valueOf(operation));
        BackendInfoMessage message = new BackendInfoMessage(topicname, operation, id);
        messaging.convertAndSend(dest,message);
    }
    
}
