package ejb;

import entities.UserEntity;
import jakarta.faces.bean.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@ManagedBean(name = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {


    private String email;
    private String password;
    private boolean success = true;

    @EJB
    private LoginBean loginBean;

    public void loginValidation(){
        FacesContext fc = FacesContext.getCurrentInstance();

        boolean result = loginBean.login(email,password);
        success = result;
        if(result)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
            httpSession.setAttribute("id", loginBean.getUserID());
            httpSession.setAttribute("email", loginBean.getEmail());
            httpSession.setAttribute("firstName", loginBean.getFirstName());
            httpSession.setAttribute("lastName", loginBean.getLastName());
            try{
                context.getExternalContext().redirect("./homePage.xhtml");
            }
            catch(IOException exception){
                System.out.println("Login error");
            }

        }
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
