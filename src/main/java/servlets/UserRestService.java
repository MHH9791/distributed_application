package servlets;

import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.annotation.WebServlet;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;

import javax.xml.ws.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/user")
@ApplicationPath("/resources")
@Stateless
//@jakarta.ejb.Stateless(name = "UserRestService")
public class UserRestService extends Application {
    @PersistenceContext(unitName = "ClassReservationPU")
    EntityManager em;

    @GET
    public String notification() {
        String noti = "Enter the email address to find the user :)";
        return noti;
    }
    //http://localhost:8080/DAdemo/resources/user/

    @GET
    @Path("{email}")
    public String searchUser(@PathParam("email") String email){
        Query query = em.createQuery("SELECT p FROM UserEntity p Where p.email = :searchEmail", UserEntity.class);
        query.setParameter("searchEmail",email);
        List<UserEntity> users = query.getResultList();

        if(users.isEmpty())
            return "This user does not exist :(";
        else {
            UserEntity user = users.get(0);
            return "This user is " + user.getFirstname() + " " + user.getLastname();
        }
    }
}
