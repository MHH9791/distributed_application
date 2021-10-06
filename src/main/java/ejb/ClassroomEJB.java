package ejb;

import entities.ClassroomEntity;
import entities.MessageEntity;
import entities.ReservationEntity;
import entities.UserEntity;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@jakarta.ejb.Stateless(name = "ClassroomBean")
public class ClassroomEJB {

    @PersistenceContext(name="ClassReservationPU")
    EntityManager em;

    public ClassroomEJB() {

    }

    public ClassroomEntity findRoom(int roomID)
    {
        ClassroomEntity classroom = em.find(ClassroomEntity.class, roomID);
        return classroom;
    }

    public List<Integer> getNumbersUsingIntStreamRange(int start, int end) {
        return IntStream.range(start, end)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Integer> findAvailableHours(int roomID, String day, boolean isToday)
    {
        ClassroomEntity classroom = em.find(ClassroomEntity.class, roomID);
        List<Integer> availableHoursToday;
        Calendar now = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY)+1;
        int searchHour = classroom.getOpenStart();
        if(isToday)
            searchHour=currentHour;

        if(searchHour<classroom.getOpenStart())
        {
            searchHour = classroom.getOpenStart();
        }
        availableHoursToday = getNumbersUsingIntStreamRange(searchHour, classroom.getOpenEnd());
        Query query = em.createQuery("SELECT p FROM ReservationEntity p Where p.reserveDate = :searchDate and p.roomId = :searchRoom " +
                "and p.reserveHour>=:searchHour", ReservationEntity.class);
        if(day.equals("Today")) {
            query.setParameter("searchDate", new Date(), TemporalType.DATE);
        }
        else if(day.equals("Tomorrow"))
        {
            query.setParameter("searchDate", new Date(new Date().getTime() + (1000 * 60 * 60 * 24)), TemporalType.DATE);
        }
        else if(day.equals("Day after tomorrow"))
        {
            query.setParameter("searchDate", new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2)), TemporalType.DATE);
        }
        query.setParameter("searchRoom", roomID);
        query.setParameter("searchHour", searchHour);
        List<ReservationEntity> reservationsToday = query.getResultList();
        if(!reservationsToday.isEmpty()) {
            for (ReservationEntity r : reservationsToday) {
                int reservedHour = r.getReserveHour();
                if (reservedHour >= searchHour) {
                    availableHoursToday.remove(new Integer(reservedHour));
                }
            }
        }
        return availableHoursToday;
    }

    public boolean makeReservation(int userID,int roomID, String[] selectedToday, String[] selectedTomorrow, String[] selectedDayAfterTomorrow)
    {
        UserEntity user = em.find(UserEntity.class, userID);
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));

        Calendar dayAfterTomorrow = Calendar.getInstance();
        dayAfterTomorrow.setTime(new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2)));

        if(selectedToday!=null)
        for (String s: selectedToday
             ) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setRoomId(roomID);
            reservationEntity.setReserveDate(today);
            reservationEntity.setReserveHour(Integer.parseInt(s));
            em.persist(reservationEntity);
            user.getReservations().add(reservationEntity);
        }

        for (String s: selectedTomorrow
        ) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setRoomId(roomID);
            reservationEntity.setReserveDate(tomorrow);
            reservationEntity.setReserveHour(Integer.parseInt(s));
            em.persist(reservationEntity);
            user.getReservations().add(reservationEntity);
        }

        for (String s: selectedDayAfterTomorrow
        ) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setRoomId(roomID);
            reservationEntity.setReserveDate(dayAfterTomorrow);
            reservationEntity.setReserveHour(Integer.parseInt(s));
            em.persist(reservationEntity);
            user.getReservations().add(reservationEntity);
        }

        return true;

    }

    public void saveClassroom(int userID, int roomID){
        UserEntity user = em.find(UserEntity.class, userID);
        ClassroomEntity classroom = em.find(ClassroomEntity.class, roomID);
        user.getSavedClassroom().add(classroom);
    }



    public boolean checkNotSaved(int userID, int roomID){
        UserEntity user = em.find(UserEntity.class, userID);
        List<ClassroomEntity> classroomEntities = user.getSavedClassroom();
        boolean result = true;
        if(!classroomEntities.isEmpty())
        {
            for (ClassroomEntity c : classroomEntities)
            {
                if(c.getRoomId()==roomID)
                {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
}
