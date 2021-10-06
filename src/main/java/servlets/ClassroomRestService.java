package servlets;


import entities.ClassroomEntity;
import entities.LabroomEntity;
import entities.ReservationEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;

import java.util.Date;
import java.util.List;

@Path("/classroom")
@ApplicationPath("/resources")
@Stateless
public class ClassroomRestService extends Application {
    @PersistenceContext(unitName = "ClassReservationPU")
    EntityManager em;

    //http://localhost:8080/DAdemo/resources/classroom/
    @GET
    @Path("/{location}/{day}/{beginHour}/{endHour}")
    public String checkRoomAvailable(@PathParam("location") String location,
                                     @PathParam("day") String day,
                                     @PathParam("beginHour") int beginHour,
                                     @PathParam("endHour") int endHour)
    {
        if(endHour>24 || beginHour<0 || beginHour>24 || endHour<0 || endHour<=beginHour)
        {
            return "Please enter correct beginHour and endHour.Both beginHour and endHour are between 0 and 24. BeginHour should be smaller than endHour ";
        }
        Date date;
        if(day.equals("Today"))
        {
            date = new Date();
        }
        else if(day.equals("Tomorrow"))
        {
            date = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
        }
        else if(day.equals("The day after tomorrow"))
        {
            date = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2));
        }
        else
        {
            return "Please enter 'Today', 'Tomorrow', or 'The day after tomorrow' in the box of day";
        }

        Query query = em.createQuery("SELECT p FROM ClassroomEntity p Where p.location = :searchLocation", ClassroomEntity.class);
        query.setParameter("searchLocation", location);
        List<ClassroomEntity> classroomEntities = query.getResultList();
        if(!classroomEntities.isEmpty())
        {
            ClassroomEntity classroomEntity = classroomEntities.get(0);
            Query query_1 = em.createQuery("SELECT p FROM ReservationEntity p Where p.roomId = :searchRoom and p.reserveDate = :searchDate and p.reserveHour>= :searchStart and p.reserveHour<:searchEnd", ReservationEntity.class);
            query_1.setParameter("searchRoom", classroomEntity.getRoomId());
            query_1.setParameter("searchDate", date);
            query_1.setParameter("searchStart",beginHour);
            query_1.setParameter("searchEnd", endHour);
            List<ReservationEntity> reservationEntities = query_1.getResultList();
            if(reservationEntities.isEmpty())
            {
                return "This room is available during this time period";
            }
            else
            {
                return "This room is unavailable during this time period";
            }
        }
        else
        {
            return "This classroom does not exist. Please correct room location";
        }

    }


//    @POST
//    @Path("/addLabroom/{location}/{beginHour}/{endHour}/{seats}/{withPC}")
//    public String addLabroom(@PathParam("location") String location,
//                             @PathParam("beginHour") int beginHour,
//                             @PathParam("endHour") int endHour,
//                             @PathParam("seats") int seats,
//                             @PathParam("withPC") boolean withPC)
//    {
//        if(checkDuplicateClassroom(location)) {
//            return "This room is existing...";
//        }
//        else
//        {
//            LabroomEntity labroom = new LabroomEntity();
//            labroom.setLocation(location);
//            labroom.setOpenStart(beginHour);
//            labroom.setOpenEnd(endHour);
//            labroom.setSeats(seats);
//            labroom.setIsPCroom(withPC);
//            try{
//                em.persist(labroom);
//            }
//            catch (PersistenceException e){
//                return "error occurs";
//            }
//            String result = "Labroom " + location + " is added successfully! :)";
//            return result;
//        }
//    }
//
//    public boolean checkDuplicateClassroom(String location)
//    {
//        Query query = em.createQuery("SELECT p FROM LabroomEntity p Where p.location = :searchLocation", LabroomEntity.class);
//        query.setParameter("searchLocation",location);
//        List<UserEntity> l = query.getResultList();
//        if(l.isEmpty())
//            return false;
//        else
//            return true;
//    }


}
