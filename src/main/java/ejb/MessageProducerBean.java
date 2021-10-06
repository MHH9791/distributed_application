package ejb;

import entities.MessageEntity;
import entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jms.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

import jakarta.annotation.Resource;
import javax.naming.Context;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.time.Month;
import java.util.List;

@jakarta.ejb.Stateless(name = "MessageProducerBean")
public class MessageProducerBean {

    @Resource(lookup = "jms/msgQueueFactory")
    private QueueConnectionFactory connectionFactory;
    @Resource(lookup = "jms/msgQueue")
    private Queue queue;
    
    public MessageProducerBean() {

    }

    @PostConstruct
    public void init(){
        try {
            Context context = new InitialContext();
            connectionFactory = (QueueConnectionFactory) context.lookup("jms/msgQueueFactory");
            queue = (Queue) context.lookup("jms/msgQueue");
        }catch (NamingException e)
        {
            System.out.println("Producer error");
        }
    }

    public void sendMessageToAll(String text) {
        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(queue, text);
        }
    }

    @Schedule(month = "1",dayOfMonth = "1",hour = "6")
    public void sendNewYearMsg()
    {
        String text = "Happy New Year!";
        sendMessageToAll(text);
    }

    @Schedule(month = "7",dayOfMonth = "1",hour = "6")
    public void sendHolidayMsg()
    {
        String text = "Have a nice holiday! The campus is closed.";
        sendMessageToAll(text);
    }

}
