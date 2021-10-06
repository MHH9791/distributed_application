package ejb;

import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@ManagedBean(name = "registerManagedBean")
@RequestScoped
public class RegisterManagedBean{

    private String returnedText;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String correct = "Correct";
    private boolean success = true;
    @EJB
    private RegisterBean registerBean;

    public void register(){
        returnedText  = new registerValidatorSoap().validate(email, password, firstName, lastName);
        if(returnedText.equals(correct))
        {
            UserEntity user = new UserEntity();
            System.out.println(firstName);
            user.setFirstname(this.firstName);
            user.setLastname(this.lastName);
            user.setEmail(this.email);
            user.setPassword(this.password);
            FacesContext fc = FacesContext.getCurrentInstance();

            boolean result = registerBean.registerUser(user);
            success = result;
            returnedText = "This email was registered";
            if(result) {
                try{
                    fc.getExternalContext().redirect("login.xhtml");}
                catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }
        else
        {
            success = false;
        }
    }

    public String getReturnedText() {
        return returnedText;
    }

    public void setReturnedText(String returnedText) {
        this.returnedText = returnedText;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    // Encrypts the password along with salt
    private static Random random = new Random((new Date()).getTime());
    public static String encrypt(String passw){
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return encoder.encode(salt) + encoder.encode(passw.getBytes());
    }


}
