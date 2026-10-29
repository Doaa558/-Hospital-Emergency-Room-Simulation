# -Hospital-Emergency-Room-Simulation
# Assignment Description:
In this assignment, you are required to implement a classic Producer–Consumer Problem using the Bounded Buffer pattern to simulate a busy Hospital Emergency Room (ER). The simulation should demonstrate proper synchronization between multiple patient arrivals and available doctors while preventing race conditions and ensuring correct access to shared resources. The simulation models an emergency department with a limited waiting room capacity, a fixed number of treatment rooms, and a fixed number of doctors available to treat patients concurrently. 
## System Description

- Each patient arriving at the hospital attempts to enter the waiting room.
- The waiting room has a limited capacity specified by the user.
- If the waiting room is full, newly arriving patients must wait outside the room until a seat becomes available.
- Doctors treat patients concurrently. A doctor remains idle until a patient is available in the waiting room. When a patient is available, a doctor is assigned to this patient for treatment.
- Before beginning the treatment of a patient, a doctor must try to acquire an available treatment room (only two treatment rooms exist in this whole emergency department).
- If the doctor is unable to acquire a treatment room within a specific period of time, the doctor sends the patient back to the waiting room. Otherwise, the treatment of the patient starts and takes some time.
- Once the treatment is completed, the doctor releases the treatment room, potentially allowing another doctor to acquire it and a patient to be treated, and so on.

## Output Requirements

The output must clearly indicate all system activities whenever a patient:

- Arrives at the hospital
- Enters the waiting room
- Waits outside because the waiting room is full
- Is assigned to a doctor
- Begins treatment in a treatment room acquired by his doctor
- Goes back to the waiting room if his doctor could not acquire a treatment room in time
- Finishes treatment
- Leaves the hospital
