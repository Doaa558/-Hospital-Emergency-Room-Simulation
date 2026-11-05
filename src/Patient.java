class Patient extends Thread {

    private int id;
    private String name;
    private WaitingRoom waitingRoom;
    private boolean flag = true;
    private boolean active = false;

    public Patient(WaitingRoom waitingRoom, String name, int id) {
        this.waitingRoom = waitingRoom;
        this.id = id;
        this.name = name;
    }

    public String getPatientInfo() {
        return name  + id ;
    }

    public void display() {
        if (flag) {
            Hospital.log(getPatientInfo() + " arrived.");
            flag = false;
        }
    }

    public void exit() {
        active = false;
        this.interrupt();
    }

    public void run() {
        try {
            waitingRoom.enter(this);
        }
        catch(InterruptedException e){

        }
    }
}