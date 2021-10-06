package ejb;


import entities.*;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "HomePageManagedBean")
@RequestScoped
public class HomePageManagedBean {

    public List<ClassroomEntity> roomList;
    public String selected = "All";
    public String selectedDate = "Today";
    private int lab_count=0;
    private int lecture_count;
    private int group_count;
    private int total;
    private String All = "All";
    private String Lab = "Lab Room";
    private String Lecture = "Lecture Room";
    private String Group = "Group Room";
    private String Today = "Today";
    private String Tomorrow = "Tomorrow";
    private String DayAfterTomorrow = "The day after Tomorrow";


    @EJB
    private HomePageEJB homePageEJB;

    @EJB
    private RoomCounterSingletonBean roomCounterSingletonBean;

    public HomePageManagedBean(){

    }

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        if(httpSession.getAttribute("id")!=null)
        {
            updateCount_today();
            Date searchDate_today = new Date();
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getAllRooms(),searchDate_today,true);
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

    public void updateCount_today(){
        lab_count = roomCounterSingletonBean.getLabRooms_today();
        lecture_count = roomCounterSingletonBean.getLectureRooms_today();
        group_count = roomCounterSingletonBean.getGroupRooms_today();
        total = lab_count + lecture_count + group_count;
    }

    public void updateCount_tomorrow(){
        lab_count = roomCounterSingletonBean.getLabRooms_tomorrow();
        lecture_count = roomCounterSingletonBean.getLectureRooms_tomorrow();
        group_count = roomCounterSingletonBean.getGroupRooms_tomorrow();
        total = lab_count + lecture_count + group_count;
    }

    public void updateCount_day_after_tomorrow(){
        lab_count = roomCounterSingletonBean.getLabRooms_day_after_tomorrow();
        lecture_count = roomCounterSingletonBean.getLectureRooms_day_after_tomorrow();
        group_count = roomCounterSingletonBean.getGroupRooms_day_after_tomorrow();
        total = lab_count + lecture_count + group_count;
    }

    public void logout(){
        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("./login.xhtml");
        }
        catch(IOException exception){
            System.out.println("Logout error");
        }
    }

    public void filterClassrooms(){
        Date searchDate_today = new Date();
        Date searchDate_tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
        Date searchDate_day_after_tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2));

        if(selected.equals(All) && selectedDate.equals(Today))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getAllRooms(),searchDate_today,true);
            updateCount_today();
        }
        else if(selected.equals(All) && selectedDate.equals(Tomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getAllRooms(),searchDate_tomorrow,false);
            updateCount_tomorrow();
        }
        else if(selected.equals(All) && selectedDate.equals(DayAfterTomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getAllRooms(),searchDate_day_after_tomorrow,false);
            updateCount_day_after_tomorrow();
        }
        else if(selected.equals(Lab) && selectedDate.equals(Today))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLabRooms(),searchDate_today,true);
            updateCount_today();
        }
        else if(selected.equals(Lab) && selectedDate.equals(Tomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLabRooms(),searchDate_tomorrow,false);
            updateCount_tomorrow();
        }
        else if(selected.equals(Lab) && selectedDate.equals(DayAfterTomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLabRooms(),searchDate_day_after_tomorrow,false);
            updateCount_day_after_tomorrow();
        }
        else if(selected.equals(Lecture) && selectedDate.equals(Today))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLectureRooms(),searchDate_today,true);
            updateCount_today();
        }
        else if(selected.equals(Lecture) && selectedDate.equals(Tomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLectureRooms(),searchDate_tomorrow,false);
            updateCount_tomorrow();
        }
        else if(selected.equals(Lecture) && selectedDate.equals(DayAfterTomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getLectureRooms(),searchDate_day_after_tomorrow,false);
            updateCount_day_after_tomorrow();
        }
        else if(selected.equals(Group) && selectedDate.equals(Today))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getGroupRooms(),searchDate_today,true);
            updateCount_today();
        }
        else if(selected.equals(Group) && selectedDate.equals(Tomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getGroupRooms(),searchDate_tomorrow,false);
            updateCount_tomorrow();
        }
        else if(selected.equals(Group) && selectedDate.equals(DayAfterTomorrow))
        {
            roomList=homePageEJB.getAvailableRooms(homePageEJB.getGroupRooms(),searchDate_day_after_tomorrow,false);
            updateCount_day_after_tomorrow();
        }
    }

    public List<ClassroomEntity> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<ClassroomEntity> roomList) {
        this.roomList = roomList;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLab_count() {
        return lab_count;
    }

    public void setLab_count(int lab_count) {
        this.lab_count = lab_count;
    }

    public int getLecture_count() {
        return lecture_count;
    }

    public void setLecture_count(int lecture_count) {
        this.lecture_count = lecture_count;
    }

    public int getGroup_count() {
        return group_count;
    }

    public void setGroup_count(int group_count) {
        this.group_count = group_count;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}
