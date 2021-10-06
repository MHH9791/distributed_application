package ejb;


import entities.MessageEntity;
import entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@ManagedBean(name = "MyMessageManagedBean")
public class MyMessageManagedBean {

    private List<MessageEntity> messages = new ArrayList<>();
    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;
    public MyMessageManagedBean()
    {

    }

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        if(httpSession.getAttribute("id")!=null)
        {
            int userID = (Integer) httpSession.getAttribute("id");
            messages = em.find(UserEntity.class, userID).getMessages();
        }
        else
        {
            try {
                context.getExternalContext().redirect("./login.xhtml");
            }
            catch(IOException exception){
                System.out.println("Logout error");
            }
        }
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }
}
