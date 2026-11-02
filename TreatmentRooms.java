
class TreatmentRooms{
 private Semaphore semaphore= new Semaphore(2);

 public boolean P_with_timeout(){
    return semaphore.P_with_timeout(3000);
}

 public void V(){
    semaphore.V();
}
}