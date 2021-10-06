package ejb;

import entities.UserEntity;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@jakarta.ejb.Stateful(name = "LoginBean")
public class LoginBean {

    private int userID;
    private String email;
    private String firstName;
    private String lastName;
    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;
    public LoginBean() {
    }

    public boolean login(String email, String password) {
        Query query = em.createQuery("SELECT p FROM UserEntity p Where p.email = :searchEmail and p.password = :searchPassword", UserEntity.class);
        query.setParameter("searchEmail", email);
        query.setParameter("searchPassword", password);
        List<UserEntity> l = query.getResultList();
        if (l.isEmpty())
            return false;
        else {
            UserEntity user = l.get(0);
            userID = user.getId();
            firstName = user.getFirstname();
            lastName = user.getLastname();
            this.email = email;
            return true;
        }
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
