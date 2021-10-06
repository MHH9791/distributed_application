package ejb;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.xml.ws.Endpoint;

@WebService
public class registerValidatorSoap {

    @WebMethod
    public String validate(String email, String password, String firstName, String lastName)
    {
        String returnText = "Correct";
        boolean result = true;
        try{
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        }catch (AddressException e){
            returnText = "The input does not conform to the email format";
            result = false;
            return returnText;
        }
        if(result)
        {
            if(password.length()>20)
            {
                returnText = "The maximum length of password is 20";
                return returnText;
            }
            else if(firstName.length()>20)
            {
                returnText = "The maximum length of firstname is 20";
                return returnText;
            }
            else if(lastName.length()>20)
            {
                returnText = "The maximum length of lastname is 20";
                return returnText;
            }
        }
        return returnText;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/registerValidatorSoap", new registerValidatorSoap()); }
}
