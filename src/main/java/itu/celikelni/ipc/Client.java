package itu.celikelni.ipc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class Client {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${smoc.rabbitmq.exchange}")
    private String IPC_EXCHANGE ;


    @Value("${smoc.rabbitmq.routingkey}")
    private String IPC_ROUTING_KEY ;

    static final Logger logger = LoggerFactory.getLogger(Client.class);


    public void send() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddr = localhost.getHostAddress();
        String hostname = localhost.getHostName();
        logger.info("Client::send()");
        logger.info("Ip Addr of client = {}",ipAddr);
        logger.info("Hostname of client  = {}",hostname);
        Message msg = new Message();
        msg.setHostname(hostname);
        msg.setIpAddr(ipAddr);
        rabbitTemplate.convertAndSend(IPC_EXCHANGE,IPC_ROUTING_KEY, msg);
    }

}
