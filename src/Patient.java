
class Patient extends Thread {

    private int id;
    private String name;
    private WaitingRoom waitingRoom;

    public Patient(WaitingRoom waitingRoom, String name, int id) {
        this.waitingRoom = waitingRoom;
        this.id = id;
        this.name = name;
    }

    public String getPatientInfo() {
        return name  + id ;
    }

    public void display() {
        Hospital.log(getPatientInfo() +" arrived.");

    }

    public void run() {
        try {
            waitingRoom.enter(this);
        }catch(InterruptedException e){
        }
    }
}