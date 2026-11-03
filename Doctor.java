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
        return name + " (Doctor #" + id + ")";
    }

    public void stopWorking() {
        active = false;
        this.interrupt();
    }

    @Override
    public void run() {
        while (active) {
            try {
                Patient patient = waitingRoom.getPatient();

                System.out.println(getDoctorInfo() + " is assigned to " + patient.getPatientInfo() + ".");

                boolean roomAcquired = treatmentRooms.P_with_timeout();

                if (!roomAcquired) {
                    System.out.println(getDoctorInfo() + " could not acquire a treatment room in time. "
                            + patient.getPatientInfo() + " is sent back to the waiting room.");
                    waitingRoom.enter(patient);

                } else {

                    System.out.println(patient.getPatientInfo() + " begins treatment in a treatment room assigned by "
                            + getDoctorInfo() + ".");

                    int treatmentTime = 1000 + (int) (Math.random() * 3000);
                    Thread.sleep(treatmentTime);

                    System.out.println(patient.getPatientInfo() + " has finished treatment.");
                    System.out.println(patient.getPatientInfo() + " is leaving the hospital.");

                    treatmentRooms.V();
                    System.out.println(getDoctorInfo() + " released the treatment room.");
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(getDoctorInfo() + " has finished their shift.");
    }
}

