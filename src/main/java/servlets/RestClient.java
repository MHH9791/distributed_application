package servlets;

import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

@ApplicationScoped
@ManagedBean(name = "RestClient")
public class RestClient {
    private String email;
    private String searchedUserInfo;
    private String classroomAvailability;

    private String location;
    private String day;
    private int start;
    private int end;

    public void searchClient(){
        Client client = ClientBuilder.newClient();

        String output = client.target("http://localhost:8080/DAdemo/resources/user/"+email).request().get(String.class);

        searchedUserInfo = output;
    }

    public void searchClassroomAvailability(){
        Client client = ClientBuilder.newClient();
        String output = client.target("http://localhost:8080/DAdemo/resources/classroom/"+location+"/"+day+"/"+start+"/"+end).request().get(String.class);
        classroomAvailability = output;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearchedUserInfo() {
        return searchedUserInfo;
    }

    public void setSearchedUserInfo(String searchedUserInfo) {
        this.searchedUserInfo = searchedUserInfo;
    }

    public String getClassroomAvailability() {
        return classroomAvailability;
    }

    public void setClassroomAvailability(String classroomAvailability) {
        this.classroomAvailability = classroomAvailability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
