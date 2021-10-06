package entities;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "Message", schema = "hellodemo", catalog = "")
public class MessageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Temporal(TemporalType.DATE)
    private Calendar date;

    @Basic
    @Column(name = "message", nullable = false, length = 100)
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }
}
