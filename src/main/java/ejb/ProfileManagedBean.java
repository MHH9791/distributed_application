package ejb;

import entities.ClassroomEntity;
import entities.ReservationEntity;
import entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@ManagedBean(name = "ProfileManagedBean")
@SessionScoped
public class ProfileManagedBean {
    private UserEntity user;
    private List<ClassroomEntity> classroomList;
    private List<ReservationEntity> reservationList;

    @EJB
    private ProfileEJB profileEJB;

    public ProfileManagedBean(){

    }

    @PostConstruct
    public void initReservedList(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = req.getSession(false);
        if(httpSession.getAttribute("id")!=null)
        {
            int uid = (int) httpSession.getAttribute("id");
            profileEJB.showReservation(uid);
            setReservationList(profileEJB.getReservations());
            profileEJB.showClassroom();
            setClassroomList(profileEJB.getClassrooms());
            this.user = profileEJB.getUser();
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

    public ClassroomEntity searchClassroom(int roomId){
        for(ClassroomEntity classroom : this.classroomList){
            if(classroom.getRoomId() == roomId){
                return classroom;
            }
        }
        return null;
    }

    public void deleteReservation(ReservationEntity r){
        profileEJB.deleteReservation(r);
        setReservationList(profileEJB.getReservations());
        profileEJB.showClassroom();
        setClassroomList(profileEJB.getClassrooms());
    }

    public UserEntity getUser() {
        return user;
    }

    public List<ClassroomEntity> getClassroomList() {
        return classroomList;
    }

    public void setClassroomList(List<ClassroomEntity> classroomList) {
        this.classroomList = classroomList;
    }

    public List<ReservationEntity> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<ReservationEntity> reservationList) {
        this.reservationList = reservationList;
    }
}
