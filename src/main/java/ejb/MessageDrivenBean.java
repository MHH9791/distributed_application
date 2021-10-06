package ejb;

import entities.MessageEntity;
import entities.UserEntity;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.MessageListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.jms.Message;
import jakarta.persistence.Query;

import java.util.Calendar;
import java.util.List;

@MessageDriven(mappedName = "jms/msgQueue")
public class MessageDrivenBean implements MessageListener {

    @PersistenceContext(name="ClassReservationPU")
    private EntityManager em;

    public MessageDrivenBean(){

    }

    @Override
    public void onMessage(Message message)
    {
        try
        {
            Query query = em.createQuery("SELECT p FROM UserEntity p", UserEntity.class);
            List<UserEntity> userEntityList = query.getResultList();
            if(!userEntityList.isEmpty())
            {
                for(UserEntity u: userEntityList)
                {
                    MessageEntity messageEntity = new MessageEntity();
                    messageEntity.setMessage(message.getBody(String.class));
                    Calendar today = Calendar.getInstance();
                    messageEntity.setDate(today);
                    em.persist(messageEntity);
                    u.getMessages().add(messageEntity);
                }
            }
        }catch (JMSException e)
        {
            System.out.println("MDB error");
        }
    }
}
