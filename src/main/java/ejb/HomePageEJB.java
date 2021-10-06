package ejb;


import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.TemporalType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@jakarta.ejb.Stateless(name = "HomePageEJB")
public class HomePageEJB {
    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;


    @PostConstruct
    public void initCount(){
        getAllRooms();

    }

    public List<ClassroomEntity> getAllRooms(){
        Query query = em.createQuery("SELECT p FROM ClassroomEntity p", ClassroomEntity.class);
        return query.getResultList();
    }

    public List<ClassroomEntity> getLabRooms(){
        Query query = em.createQuery("SELECT p FROM LabroomEntity p", LabroomEntity.class);
        return query.getResultList();
    }

    public List<ClassroomEntity> getLectureRooms(){
        Query query = em.createQuery("SELECT p FROM LectureroomEntity p", LectureroomEntity.class);
        return query.getResultList();
    }

    public List<ClassroomEntity> getGroupRooms(){
        Query query = em.createQuery("SELECT p FROM GrouproomEntity p", GrouproomEntity.class);
        return query.getResultList();
    }

    public List<ClassroomEntity> getAvailableRooms(List<ClassroomEntity> classroomEntities, Date searchDate, boolean isToday){
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY)+1;
        List<ClassroomEntity> resultList = new ArrayList<>(classroomEntities);
        for(ClassroomEntity c: classroomEntities) {
            Query temp_query = em.createQuery("SELECT p FROM ReservationEntity p where p.roomId=:roomID and p.reserveDate=:reserveDate and p.reserveHour>=:searchHour", ReservationEntity.class);
            temp_query.setParameter("roomID", c.getRoomId());
            int searchHour = c.getOpenStart();;
            if(isToday)
                searchHour = currentHour;
            temp_query.setParameter("reserveDate", searchDate, TemporalType.DATE );
            temp_query.setParameter("searchHour", searchHour);
            List<ReservationEntity> reservationEntities = temp_query.getResultList();
            if(reservationEntities.size()>=c.getOpenEnd()-searchHour)
                resultList.remove(c);
        }
        return resultList;
    }

}
