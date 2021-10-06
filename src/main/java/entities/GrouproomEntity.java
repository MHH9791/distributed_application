package entities;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("GroupRoom")
public class GrouproomEntity extends ClassroomEntity {

    private boolean withTV;

    @Basic
    @Column(name = "withTV", nullable = true)
    public boolean getWithTV() {
        return withTV;
    }
    public void setWithTV(boolean withTV) {
        this.withTV = withTV;
    }
}
