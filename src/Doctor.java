public class Doctor extends Thread {

    private final int id;
    private final String name;

    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;

    private volatile boolean active = true;

    public Doctor(int id, String name, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        this.id = id;
        this.name = name;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    public String getDoctorInfo() {
        return "Doctor "+id;
    }

    public void stopWorking() {
        active = false;
        this.interrupt();
    }

    @Override
    public void run() {
        while (active) {

            boolean roomAcquired = false;
            try {

                Patient patient = waitingRoom.getPatient(this);
                Hospital.log(getDoctorInfo()+" is trying to acquire a treatment room");
                roomAcquired = treatmentRooms.P_with_timeout();

                if (!roomAcquired) {
                    Hospital.log(getDoctorInfo() + " could not acquire a treatment room in time. " +"\n"
                            +getDoctorInfo()+ " is sening back "+patient.getPatientInfo() +" to the waiting room.");
                    waitingRoom.enter(patient);
                }
                else {
                    Hospital.log(getDoctorInfo() + " acquired a treatment room." + "\n"
                            + getDoctorInfo() + " begins treating "+ patient.getPatientInfo()+".");

                    int treatmentTime = 1000 + (int) (Math.random() * 3000);
                    Thread.sleep(treatmentTime);


                    Hospital.log(getDoctorInfo() + " finished treating " + patient.getPatientInfo() + ".\n"
                            + getDoctorInfo() + " released the treatment room."+"\n"
                           + getDoctorInfo() + " is now available.\n"
                            + patient.getPatientInfo() + " left.");
                    treatmentRooms.V();

                    Hospital.patientTreated();

                }

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
