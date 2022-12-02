import java.util.*;
import java.util.stream.Stream;

public class Station {

    /**
     * The maximum baseline processing time for a PassengerEntity.
     */
    private static final double MAX_PROCESSING_TIME = 10.0;
    private static final int MIN_QUEUE_LENGTH = 10;
    private static final int MAX_QUEUE_LENGTH = 100;

    /**
     * Tracks a Supervisor overseeing this Station,
     * if present.
     */
    private Supervisor supervisor;

    /**
     * Tracks the agent in charge of this Station.
     */
    private Agent agent;

    /**
     * Tracks the queue of passengers that are waiting
     * in line to be assisted.
     */
    private PassengerSupplier queue;

    /**
     * A constructor that creates a Station based on the
     * given supervisor, agent, and queue arguments.
     * None of the arguments should be null; otherwise,
     * a NullPointerException is thrown. The agent must
     * be either an Agent or AutomatedAgent (ie. not a
     * Supervisor).
     * @param supervisor the Supervisor for this Station
     * @param agent the agent for this Station
     * @param queue the queue of Passengers for this Station
     */
    public Station(Supervisor supervisor,
                   Agent agent,
                   PassengerSupplier queue) {
        Objects.requireNonNull(agent);
        Objects.requireNonNull(queue);

        this.supervisor = supervisor;
        this.agent = agent;
        this.queue = queue;
    }

    /**
     * A method to get the supervisor that is overseeing this
     * Station. If the supervisor is not present, returns null.
     * @return the Supervisor overseeing this Station or null.
     */
    public Supervisor getSupervisor() {
        return supervisor;
    }

    /**
     * A method to clear the current Supervisor who is
     * overseeing this Station.
     * @return the Supervisor who was just cleared from
     * this Station or null if there was no previous Supervisor.
     */
    public Supervisor clearSupervisor() {
        Supervisor temp = this.getSupervisor();
        this.supervisor = null;
        return temp;
    }

    /**
     * A method to check whether this Station has a Supervisor.
     * @return true if this Station has a Supervisor.
     */
    public boolean hasSupervisor() {
        return this.getSupervisor() != null;
    }

    /**
     * A method to set the Supervisor overseeing this Station.
     * The previous Supervisor is cleared from the Station,
     * if present.
     * @param supervisor the Supervisor who was just cleared from
     * this Station or null if there was no previous Supervisor.
     */
    public Supervisor setSupervisor(Supervisor supervisor) {
        Supervisor temp = this.clearSupervisor();
        this.supervisor = supervisor;
        return temp;
    }

    /**
     * A method to check whether this Station is handled by
     * an AutomatedAgent.
     * @return true if this Station's agent is an AutomatedAgent.
     */
    public boolean isAutomated() {
        return this.agent.isAutomatedStation();
    }

    /**
     * A method to determine if this Station's queue is empty.
     * @return true if the queue is empty.
     */
    public boolean isQueueEmpty() {
        return this.queue.isEmpty();
    }

    /**
     * A method to get the next PassengerEntity in line if
     * they are irregular.
     * @return the next PassengerEntity in line if
     * they are irregular or null.
     */
    public PassengerEntity removeIrregularEntity() {
        if (this.queue.peekNextPassenger() != null
                && this.queue.peekNextPassenger().isIrregular()) {
            return this.queue.getNextPassenger();
        } else {
            return null;
        }
    }

    /**
     * A method to process a PassengerEntity.
     * @return the time taken to process the Entity or -1.0 if
     * a PassengerEntity cannot be processed at this time.
     */
    public double processPassengerEntity() {
        double totalProcessingTime = -1.0;
        PassengerEntity passengerEntity = this.queue.peekNextPassenger();

        /* Null check. */
        if (passengerEntity == null) {
            return -1.0;
        }

        /* Process this Passenger if possible. */
        if (this.canProcess(passengerEntity)) {
            totalProcessingTime = 0;
            totalProcessingTime += passengerEntity.getProcessTimeRequired();

            /* Reduce processing time in half if Supervisor present. */
            if (this.hasSupervisor()) {
                totalProcessingTime *= 0.5;
            }
        }

        return totalProcessingTime;
    }

    /**
     * A method to determine whether a specific PassengerEntity
     * can be currently processed.
     * @param passengerEntity the PassengerEntity to process.
     * @return true if the PassengerEntity can be processed.
     */
    private boolean canProcess(PassengerEntity passengerEntity) {
        /* Return false if this Passenger needs an In-Person Station. */
        if (passengerEntity.isIrregular() && this.isAutomated()) {
            return false;
        }

        /* Return false if this Passenger needs to see a Supervisor. */
        if (passengerEntity.needsSupervisor() && !this.hasSupervisor()) {
            return false;
        }

        return true;
    }

    /**
     * A method to create a new queue of PassengerEntities
     * if the current queue is empty. If the queue is not
     * empty, then no actions are taken.
     * @return true if the queue is refilled.
     */
    public boolean refillQueue() {
        if (this.isQueueEmpty()) {
            Random random = new Random();
            Stream<PassengerEntity> frequentFlyers = populateQueue(random.nextInt(MIN_QUEUE_LENGTH, MAX_QUEUE_LENGTH));
            Stream<PassengerEntity> regularFlyers = populateQueue(random.nextInt(MIN_QUEUE_LENGTH, MAX_QUEUE_LENGTH));
            this.queue = new PassengerSupplier(frequentFlyers, regularFlyers);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method to return a random Stream of PassengerEntities
     * of a specified length.
     * @return a random Stream of PassengerEntities.
     */
    private static Stream<PassengerEntity> populateQueue(int queueLength) {
        /* Initialize a Random and a List of the enum types in PassengerType. */
        Random random = new Random();
        List<PassengerType> types = Arrays.stream(PassengerType.values()).toList();

        /* Initializing the queue to return. */
        PassengerEntity[] passengerQueue = new PassengerEntity[queueLength];

        /* Populate queue array with randomly generated Employees. */
        for (int i = 0; i < passengerQueue.length; i++) {
            passengerQueue[i] = new Passenger(random.nextDouble(MAX_PROCESSING_TIME),
                                        types.get(random.nextInt(types.size())));
        }

        return Arrays.stream(passengerQueue);
    }
}
