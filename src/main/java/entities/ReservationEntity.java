package entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "Reservation", schema = "hellodemo", catalog = "")
public class ReservationEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private int roomId;
    @Temporal(TemporalType.DATE)
    private Calendar reserveDate;
    private int reserveHour;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "roomId", nullable = false)
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "reserveDate", nullable = false)
    public Calendar getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Calendar reserveDate) {
        this.reserveDate = reserveDate;
    }

    @Basic
    @Column(name = "reserveHour", nullable = false)
    public int getReserveHour() {
        return reserveHour;
    }

    public void setReserveHour(int reserveHour) {
        this.reserveHour = reserveHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity that = (ReservationEntity) o;
        return id == that.id && roomId == that.roomId && reserveHour == that.reserveHour && Objects.equals(reserveDate, that.reserveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, reserveDate, reserveHour);
    }
}

