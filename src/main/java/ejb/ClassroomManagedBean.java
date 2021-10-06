package ejb;

import entities.ClassroomEntity;
import entities.GrouproomEntity;
import entities.LabroomEntity;
import entities.LectureroomEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean(name="ClassroomManagedBean")
@ViewScoped
public class ClassroomManagedBean {

    private int classroomID;
    private int userID;
    private String location;
    private int openHour;
    private int closeHour;
    private String roomType;
    private boolean withPC;
    private boolean withTV;
    private boolean withProjector;
    private boolean notSaved;
    private List<Integer> today_available_hours;
    private List<Integer> tomorrow_available_hours;
    private List<Integer> the_day_after_tomorrow_available_hours;
    private String[] selected_today;
    private String[] selected_tomorrow;
    private String[] selected_day_after_tomorrow;

    @EJB
    private ClassroomEJB classroomEJB;

    @EJB
    private RoomCounterSingletonBean roomCounterSingletonBean;

    @PostConstruct
    public void init()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        if(httpSession.getAttribute("id")!=null)
        {
            Map<String,String> paramMap = context.getExternalContext().getRequestParameterMap();
            classroomID = Integer.parseInt(paramMap.get("classroomID").trim());
            userID=(int)httpSession.getAttribute("id");
            ClassroomEntity classroomEntity = classroomEJB.findRoom(classroomID);

            openHour=classroomEntity.getOpenStart();
            closeHour=classroomEntity.getOpenEnd();
            roomType=classroomEntity.getDisc();
            location = classroomEntity.getLocation();

            if(roomType.equals("LabRoom")) {
                LabroomEntity labroomEntity = (LabroomEntity) classroomEntity;
                withPC = labroomEntity.getIsPCroom();
            }
            else if(roomType.equals("LectureRoom")){
                LectureroomEntity lectureroomEntity = (LectureroomEntity) classroomEntity;
                withProjector = lectureroomEntity.getWithProjector();
            }
            else{
                GrouproomEntity grouproomEntity = (GrouproomEntity) classroomEntity;
                withTV = grouproomEntity.getWithTV();
            }

            today_available_hours = classroomEJB.findAvailableHours(classroomID,"Today", true);
            tomorrow_available_hours = classroomEJB.findAvailableHours(classroomID,"Tomorrow", false);
            the_day_after_tomorrow_available_hours = classroomEJB.findAvailableHours(classroomID,"Day after tomorrow", false);
            checkSaved();
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

    public void checkSaved()
    {
        boolean result = classroomEJB.checkNotSaved(userID, classroomID);
        notSaved=result;
    }

    public void reserve()
    {
        boolean result = classroomEJB.makeReservation(userID, classroomID, selected_today, selected_tomorrow, selected_day_after_tomorrow);
        if (result)
        {
            roomCounterSingletonBean.updateRoomCounters();
            returnToHome();
        }
    }

    public void returnToHome()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().redirect("./homePage.xhtml");
        }
        catch(IOException exception){
            System.out.println("error");
        }
    }

    public void saveClassroom()
    {
        classroomEJB.saveClassroom(userID,classroomID);
        notSaved = false;
    }

    public boolean noAvailableHours(){
        if(today_available_hours.isEmpty())
            return true;
        else
            return false;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isWithPC() {
        return withPC;
    }

    public void setWithPC(boolean withPC) {
        this.withPC = withPC;
    }

    public boolean isWithTV() {
        return withTV;
    }

    public void setWithTV(boolean withTV) {
        this.withTV = withTV;
    }

    public boolean isWithProjector() {
        return withProjector;
    }

    public void setWithProjector(boolean withProjector) {
        this.withProjector = withProjector;
    }

    public List<Integer> getToday_available_hours() {
        return today_available_hours;
    }

    public void setToday_available_hours(List<Integer> today_available_hours) {
        this.today_available_hours = today_available_hours;
    }

    public List<Integer> getTomorrow_available_hours() {
        return tomorrow_available_hours;
    }

    public void setTomorrow_available_hours(List<Integer> tomorrow_available_hours) {
        this.tomorrow_available_hours = tomorrow_available_hours;
    }

    public List<Integer> getThe_day_after_tomorrow_available_hours() {
        return the_day_after_tomorrow_available_hours;
    }

    public void setThe_day_after_tomorrow_available_hours(List<Integer> the_day_after_tomorrow_available_hours) {
        this.the_day_after_tomorrow_available_hours = the_day_after_tomorrow_available_hours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getSelected_tomorrow() {
        return selected_tomorrow;
    }

    public void setSelected_tomorrow(String[] selected_tomorrow) {
        this.selected_tomorrow = selected_tomorrow;
    }

    public String[] getSelected_day_after_tomorrow() {
        return selected_day_after_tomorrow;
    }

    public void setSelected_day_after_tomorrow(String[] selected_day_after_tomorrow) {
        this.selected_day_after_tomorrow = selected_day_after_tomorrow;
    }

    public boolean getNotSaved() {
        return notSaved;
    }

    public void setNotSaved(boolean notSaved) {
        this.notSaved = notSaved;
    }

    public String[] getSelected_today() {
        return selected_today;
    }

    public void setSelected_today(String[] selected_today) {
        this.selected_today = selected_today;
    }
}