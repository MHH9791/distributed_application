package ejb;

import entities.ClassroomEntity;
import entities.LabroomEntity;
import entities.MessageEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

@jakarta.ejb.Stateless(name = "AddClassroomEJB")
public class AddClassroomBean {

    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;

    public AddClassroomBean() {
    }

    public boolean addRoom(ClassroomEntity room){
        try{
            em.persist(room);
        } catch(PersistenceException e){
            return false;
        }
        return true;
    }

    public void test(MessageEntity messageEntity)
    {
        try
        {
            em.persist(messageEntity);
        }catch (PersistenceException e)
        {
            System.out.println("error");
        }

    }

}
