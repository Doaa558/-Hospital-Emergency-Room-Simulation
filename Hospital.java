import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Hospital {
    private int DOC_NUM;
    private static int PAT_NUM;
    private static int treated = 0;

    long minSleep = 10;
    long maxSleep = 1000;

    private TreatmentRooms treatmentRooms = new TreatmentRooms();
    private WaitingRoom waitingRoom ;

    private static List<Doctor> doctors = new  ArrayList<>();
    private static List<Thread> threads = new ArrayList<>();

    private static final Object PRINT_LOCK = new Object();
    private static final Object TREAT_LOCK = new Object();

    public static void log(String msg) {
        synchronized (PRINT_LOCK) {
            System.out.println(msg);
            System.out.println("___________________________");
        }
    }

    public static void patientTreated(Patient patient) {
        synchronized (TREAT_LOCK) {
            treated++;
            patient.exit();
            if (treated >= PAT_NUM) {
                for (Doctor doctor : doctors) {
                    doctor.stopWorking();
                }
            }
        }
    }

    public Hospital(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Waiting room capacity: ");
        int waitingRoom_size = sc.nextInt();
        System.out.print("Number of doctors: ");
        int doctor_num = sc.nextInt();
        System.out.print("Number of patients: ");
        int patient_num = sc.nextInt();

        System.out.println("\n");

        this.DOC_NUM = doctor_num;
        this.PAT_NUM = patient_num;

        waitingRoom = new WaitingRoom(waitingRoom_size);

        System.out.println("Patients arriving: ");
        for  (int i = 1; i <= PAT_NUM; i++) {
            System.out.print("P" + i + " ");
        }
        System.out.println("\n");
    }

    public void start() throws InterruptedException {

        for  (int i = 1; i <= PAT_NUM; i++) {

            Patient patient = new Patient(waitingRoom,"P",i);
            threads.add(patient);
        }

        for (int i = 1; i <= DOC_NUM; i++) {
            Doctor doctor = new Doctor(i,"D",waitingRoom,treatmentRooms);
            doctors.add(doctor);
            threads.add(doctor);
        }

        for (Thread thread : threads) {
            if (!(thread instanceof Patient)) {
                thread.start();
            }
        }

        for (Thread thread : threads) {
            if (thread instanceof Patient) {

                long rand = ThreadLocalRandom.current()
                        .nextLong(minSleep, maxSleep);

                thread.start();

                Thread.sleep(rand);
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }

        log("All patients have been treated.");
        log("Simulation completed.");
    }
}

