package ejb;

import entities.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

import java.util.List;

@jakarta.ejb.Stateless(name = "RegisterBean")
public class RegisterBean {


    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;

    public RegisterBean() {

    }

    public boolean registerUser(UserEntity user){
        if(checkDuplicatedUser(user))
        {
            return false;
        }
        else {
            try {
                em.persist(user);
            } catch (PersistenceException e) {
                return false;
            }
            return true;
        }
    }

    public boolean checkDuplicatedUser(UserEntity user){
        Query query = em.createQuery("SELECT p FROM UserEntity p Where p.email = :searchEmail", UserEntity.class);
        query.setParameter("searchEmail",user.getEmail());
        List<UserEntity> l = query.getResultList();
        if(l.isEmpty())
            return false;
        else
            return true;
    }
}
