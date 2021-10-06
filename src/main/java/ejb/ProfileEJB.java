package ejb;


import entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@jakarta.ejb.Stateful(name = "ProfileEJB")

public class ProfileEJB {
    private UserEntity user;
    private List<ClassroomEntity> classrooms = new ArrayList<>();
    private List<ReservationEntity> reservations = new ArrayList<>();

    @PersistenceContext(unitName = "ClassReservationPU")
    private EntityManager em;

    public ProfileEJB(){

    }

    public UserEntity getUser() {
        return user;
    }

    public void setClassrooms(List<ClassroomEntity> classrooms) {
        this.classrooms = classrooms;
    }

    public List<ClassroomEntity> getClassrooms() {
        return classrooms;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void showReservation(int searchId){
        this.user = em.find(UserEntity.class,searchId);
        setReservations(user.getReservations());
    }

    public void showClassroom(){
        if(!reservations.isEmpty()){
            for(ReservationEntity reservation : reservations){
                ClassroomEntity classroom = em.find(ClassroomEntity.class, reservation.getRoomId());
                this.classrooms.add(classroom);
            }
        }
    }

    public void deleteReservation(ReservationEntity r){
//        Query delete_query = em.createQuery("DELETE FROM ReservationEntity r where r.id=:deleteId", ReservationEntity.class);
//        delete_query.setParameter("deleteId", deleteId);
//        List<ReservationEntity> a = delete_query.getResultList();

        if(reservations.contains(r)) reservations.remove(r);
//        ReservationEntity d = em.find(ReservationEntity.class,deleteId);
//        this.user.getReservations().remove(d);
//        this.reservations.remove(d);
//        em.remove(d);
//        List<ReservationEntity> reservations = user.getReservations();
//        if(!reservations.isEmpty()){
//            for(ReservationEntity reservation : reservations){
//                if(deleteId == reservation.getId()){
//                    em.remove(reservation);
//                }
//            }
//        }
    }

}
