package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "hellodemo", catalog = "")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    @OneToMany
    @JoinTable(
            name="User_Reservation",
            joinColumns = @JoinColumn(name = "user_ID"),
            inverseJoinColumns = @JoinColumn(name="reservation_ID")
    )
    private List<ReservationEntity> reservations;

    @OneToMany
    @JoinTable(
            name="User_Message",
            joinColumns = @JoinColumn(name = "user_ID"),
            inverseJoinColumns = @JoinColumn(name="message_ID")
    )
    private List<MessageEntity> messages;

    @ManyToMany
    @JoinTable(
            name = "user_save_classroom",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "classroom_fk")
    )
    private List<ClassroomEntity> savedClassroom;

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public List<ClassroomEntity> getSavedClassroom() {
        return savedClassroom;
    }

    public void setSavedClassroom(List<ClassroomEntity> savedClassroom) {
        this.savedClassroom = savedClassroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 20)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 20)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstname, lastname, password);
    }
}
