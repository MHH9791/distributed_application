package entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("LabRoom")
public class LabroomEntity extends ClassroomEntity {
    private boolean isPCroom;

    @Basic
    @Column(name = "isPCroom", nullable = true)
    public boolean getIsPCroom() {
        return isPCroom;
    }

    public void setIsPCroom(boolean isPCroom) {
        this.isPCroom = isPCroom;
    }
}

