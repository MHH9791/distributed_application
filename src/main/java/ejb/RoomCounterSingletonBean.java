package ejb;

import entities.ClassroomEntity;
import entities.ReservationEntity;
import entities.UserEntity;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

import jakarta.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@jakarta.ejb.Singleton(name = "RoomCounterSingletonEJB")
public class RoomCounterSingletonBean {

    private int labRooms_today;
    private int groupRooms_today;
    private int lectureRooms_today;

    private int labRooms_tomorrow;
    private int groupRooms_tomorrow;
    private int lectureRooms_tomorrow;

    private int labRooms_day_after_tomorrow;
    private int groupRooms_day_after_tomorrow;
    private int lectureRooms_day_after_tomorrow;

    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;

    public RoomCounterSingletonBean() {
    }

    @PostConstruct
    public void init(){
        updateRoomCounters();

    }

    public int counter(List<ClassroomEntity> classroomEntities, Date searchDate, boolean isToday){
        int count = 0;
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY)+1;
        for(ClassroomEntity c: classroomEntities) {
            Query temp_query = em.createQuery("SELECT p FROM ReservationEntity p where p.roomId=:roomID and p.reserveDate=:reserveDate and p.reserveHour>=:searchHour", ReservationEntity.class);
            temp_query.setParameter("roomID", c.getRoomId());
            temp_query.setParameter("reserveDate", searchDate, TemporalType.DATE );
            int searchHour = searchHour = c.getOpenStart();
            if(isToday)
                searchHour = currentHour;

            temp_query.setParameter("searchHour", searchHour);
            List<ReservationEntity> reservationEntities = temp_query.getResultList();
            if(reservationEntities.size()<c.getOpenEnd()-searchHour)
                count++;
        }
        return count;
    }

    public void updateRoomCounters(){
        Query query = em.createQuery("SELECT p FROM ClassroomEntity p where p.disc=:roomType", ClassroomEntity.class);

        query.setParameter("roomType","LabRoom");
        List<ClassroomEntity> labs = query.getResultList();
        query.setParameter("roomType","LectureRoom");
        List<ClassroomEntity> lectures = query.getResultList();
        query.setParameter("roomType","GroupRoom");
        List<ClassroomEntity> groups = query.getResultList();

        Date searchDate_today = new Date();

        Date searchDate_tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        Date searchDate_day_after_tomorrow = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2));

        labRooms_today = counter(labs, searchDate_today, true);
        lectureRooms_today = counter(lectures, searchDate_today, true);
        groupRooms_today = counter(groups, searchDate_today, true);

        labRooms_tomorrow = counter(labs, searchDate_tomorrow,false);
        lectureRooms_tomorrow = counter(lectures, searchDate_tomorrow, false);
        groupRooms_tomorrow = counter(groups, searchDate_tomorrow, false);
//        labRooms_tomorrow = 10;

        labRooms_day_after_tomorrow = counter(labs, searchDate_day_after_tomorrow, false);
        lectureRooms_day_after_tomorrow = counter(lectures, searchDate_day_after_tomorrow,false);
        groupRooms_day_after_tomorrow = counter(groups, searchDate_day_after_tomorrow, false);
    }

    public int getLabRooms_today() {
        return labRooms_today;
    }

    public void setLabRooms_today(int labRooms_today) {
        this.labRooms_today = labRooms_today;
    }

    public int getGroupRooms_today() {
        return groupRooms_today;
    }

    public void setGroupRooms_today(int groupRooms_today) {
        this.groupRooms_today = groupRooms_today;
    }

    public int getLectureRooms_today() {
        return lectureRooms_today;
    }

    public void setLectureRooms_today(int lectureRooms_today) {
        this.lectureRooms_today = lectureRooms_today;
    }

    public int getLabRooms_tomorrow() {
        return labRooms_tomorrow;
    }

    public void setLabRooms_tomorrow(int labRooms_tomorrow) {
        this.labRooms_tomorrow = labRooms_tomorrow;
    }

    public int getGroupRooms_tomorrow() {
        return groupRooms_tomorrow;
    }

    public void setGroupRooms_tomorrow(int groupRooms_tomorrow) {
        this.groupRooms_tomorrow = groupRooms_tomorrow;
    }

    public int getLectureRooms_tomorrow() {
        return lectureRooms_tomorrow;
    }

    public void setLectureRooms_tomorrow(int lectureRooms_tomorrow) {
        this.lectureRooms_tomorrow = lectureRooms_tomorrow;
    }

    public int getLabRooms_day_after_tomorrow() {
        return labRooms_day_after_tomorrow;
    }

    public void setLabRooms_day_after_tomorrow(int labRooms_day_after_tomorrow) {
        this.labRooms_day_after_tomorrow = labRooms_day_after_tomorrow;
    }

    public int getGroupRooms_day_after_tomorrow() {
        return groupRooms_day_after_tomorrow;
    }

    public void setGroupRooms_day_after_tomorrow(int groupRooms_day_after_tomorrow) {
        this.groupRooms_day_after_tomorrow = groupRooms_day_after_tomorrow;
    }

    public int getLectureRooms_day_after_tomorrow() {
        return lectureRooms_day_after_tomorrow;
    }

    public void setLectureRooms_day_after_tomorrow(int lectureRooms_day_after_tomorrow) {
        this.lectureRooms_day_after_tomorrow = lectureRooms_day_after_tomorrow;
    }
}
