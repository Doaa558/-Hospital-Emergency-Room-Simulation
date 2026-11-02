import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final int capacity;
    private final Queue<Patient> queue;
    private final Semaphore emptySeats;
    private final Semaphore fullSeats;
    private final Semaphore lock;
    private int patientCount = 0;

    public WaitingRoom(int capacity) 
    {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.emptySeats = new Semaphore(capacity);
        this.fullSeats = new Semaphore(0);
        this.lock = new Semaphore(1);
    }

    public void enter(Patient patient) 
    {
        lock.P();
        if (patientCount >= capacity) 
        {
            System.out.println("The waiting room is full.");
            System.out.println(patient.getName() + " is waiting outside for an available seat.");
        }
        patientCount++;
        lock.V();

        emptySeats.P();

        lock.P();
        queue.add(patient);
        System.out.println(patient.getName() + " entered the waiting room.");
        lock.V();

        fullSeats.V();
    }

    public Patient getPatient() 
    {
        fullSeats.P();

        lock.P();
        Patient patient = queue.poll();
        patientCount--;
        lock.V();

        emptySeats.V();

        return patient;
    }

    public void addPatient(Patient patient) 
    {
        enter(patient);
    }

    public Patient takePatient() 
    {
        return getPatient();
    }

    public Patient removePatient() 
    {
        return getPatient();
    }

    public int getCapacity() 
    {
        return capacity;
    }
}
