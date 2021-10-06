package entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LectureRoom")
public class LectureroomEntity extends ClassroomEntity {
    private boolean withProjector;

    @Basic
    @Column(name = "withProjector", nullable = true)
    public boolean getWithProjector() {
        return withProjector;
    }

    public void setWithProjector(boolean withProjector) {
        this.withProjector = withProjector;
    }
}
