import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final int capacity;
    private final Queue<Patient> queue;
    private final Semaphore emptySeats;
    private final Semaphore fullSeats;
    private final Semaphore lock;

    public WaitingRoom(int capacity)
    {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.emptySeats = new Semaphore(capacity);
        this.fullSeats = new Semaphore(0);
        this.lock = new Semaphore(1);
    }

    public void enter(Patient patient)  throws InterruptedException
    {

        lock.P();
        patient.display();
        boolean gotSeat = emptySeats.tryP();
        if (gotSeat)
        {
            queue.add(patient);
            Hospital.log(patient.getPatientInfo() + " entered the waiting room.");
        }
        else
        {
            Hospital.log("The waiting room is full." + "\n" + patient.getPatientInfo() + " is waiting outside for an available seat.");
        }
        lock.V();

        if (!gotSeat)
        {
            emptySeats.P();

            lock.P();
            queue.add(patient);
            Hospital.log(patient.getPatientInfo() + " entered the waiting room.");
            lock.V();
        }

        fullSeats.V();
    }

    public Patient getPatient(Doctor doctor)  throws InterruptedException
    {
        fullSeats.P();

        lock.P();
        Patient patient = queue.poll();
        Hospital.log(doctor.getDoctorInfo() + " is assigned to " + patient.getPatientInfo() + ".");
        lock.V();
        emptySeats.V();

        return patient;
    }

}