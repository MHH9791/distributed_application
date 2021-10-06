package entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="disc", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Classroom")
@Table(name = "Classroom", schema = "hellodemo", catalog = "")
public class ClassroomEntity {
    @Id
    @Column(name = "roomID", nullable = false)
    private int roomId;
    private String location;
    private int openStart;
    private int openEnd;
    private int seats;
    @Column(name = "disc", insertable = false, updatable = false)
    private String disc;

    @ManyToMany(mappedBy = "savedClassroom")
    private List<UserEntity> savedByUsers;

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public List<UserEntity> getSavedByUsers() {
        return savedByUsers;
    }

    public void setSavedByUsers(List<UserEntity> savedByUsers) {
        this.savedByUsers = savedByUsers;
    }

    public String getDisc() {
        return disc;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Basic
    @Column(name = "location", nullable = false, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "openStart", nullable = false)
    public int getOpenStart() {
        return openStart;
    }

    public void setOpenStart(int openStart) {
        this.openStart = openStart;
    }

    @Basic
    @Column(name = "openEnd", nullable = false)
    public int getOpenEnd() {
        return openEnd;
    }

    public void setOpenEnd(int openEnd) {
        this.openEnd = openEnd;
    }

    @Basic
    @Column(name = "seats", nullable = false)
    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassroomEntity that = (ClassroomEntity) o;
        return roomId == that.roomId && openStart == that.openStart && openEnd == that.openEnd && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, location, openStart, openEnd);
    }
}
