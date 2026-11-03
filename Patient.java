 
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
    System.out.println(getPatientInfo() +" arrived.");
    
}

public void run() {
    display();
    waitingRoom.enter(this);

 }
 }