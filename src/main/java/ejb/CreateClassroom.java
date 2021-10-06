package ejb;

import entities.GrouproomEntity;
import entities.LabroomEntity;
import entities.LectureroomEntity;
import entities.MessageEntity;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.jms.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

@ManagedBean(name = "CreateClassroomBean")
@RequestScoped
public class CreateClassroom {

    public String roomType;
    private String lab = "Lab Room";
    private String group = "Group Room";
    private String lecture = "Lecture Room";
    private String location;
    private int seats;
    private int openBegin;
    private int openEnd;

    private boolean withPC_withTV_withProjector=false;

    @EJB
    private AddClassroomBean addClassroomBean;

    public CreateClassroom(){

    }

    public void addRoom(){
        if(roomType.equals(lab))
        {
            LabroomEntity labroom = new LabroomEntity();
            labroom.setLocation(location);
            labroom.setSeats(seats);
            labroom.setOpenStart(openBegin);
            labroom.setOpenEnd(openEnd);
            labroom.setIsPCroom(withPC_withTV_withProjector);
            addClassroomBean.addRoom(labroom);

        }
        else if(roomType.equals(group)){
            GrouproomEntity grouproom = new GrouproomEntity();
            grouproom.setLocation(location);
            grouproom.setSeats(seats);
            grouproom.setOpenStart(openBegin);
            grouproom.setOpenEnd(openEnd);
            grouproom.setWithTV(withPC_withTV_withProjector);
            addClassroomBean.addRoom(grouproom);
        }
        else if(roomType.equals(lecture))
        {
            LectureroomEntity lectureRoom = new LectureroomEntity();
            lectureRoom.setLocation(location);
            lectureRoom.setSeats(seats);
            lectureRoom.setOpenStart(openBegin);
            lectureRoom.setOpenEnd(openEnd);
            lectureRoom.setWithProjector(withPC_withTV_withProjector);
            addClassroomBean.addRoom(lectureRoom);
        }
    }

    public void test()
    {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage("eeee");
        addClassroomBean.test(messageEntity);
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getOpenBegin() {
        return openBegin;
    }

    public void setOpenBegin(int openBegin) {
        this.openBegin = openBegin;
    }

    public int getOpenEnd() {
        return openEnd;
    }

    public void setOpenEnd(int openEnd) {
        this.openEnd = openEnd;
    }

    public boolean isWithPC_withTV_withProjector() {
        return withPC_withTV_withProjector;
    }

    public void setWithPC_withTV_withProjector(boolean withPC_withTV_withProjector) {
        this.withPC_withTV_withProjector = withPC_withTV_withProjector;
    }
}
