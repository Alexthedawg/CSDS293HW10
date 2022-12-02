import java.util.Objects;

public class Passenger extends PassengerEntity {

    /**
     * Tracks the PassengerType of this Passenger.
     */
    private PassengerType passengerType;

    /**
     * A constructor that creates a Passenger based on
     * the given parameters.
     * @param processTime the process time for this Passenger.
     * @param passengerType the PassengerType of this Passenger.
     */
    public Passenger(double processTime,
                     PassengerType passengerType) {
        Objects.requireNonNull(processTime);
        Objects.requireNonNull(passengerType);

        this.setProcessTimeRequired(processTime);
        this.passengerType = passengerType;
    }

    /**
     * A getter method for PassengerType.
     * @return this Passenger's PassengerType.
     */
    public PassengerType getPassengerType() {
        return this.passengerType;
    }

    /**
     * A method to check if this Passenger is not REGULAR.
     * @return true if this Passenger is not REGULAR.
     */
    @Override
    public boolean isIrregular() {
        return this.getPassengerType() != PassengerType.REGULAR;
    }

    /**
     * A method to check whether this Passenger or PassengerGroup
     * requires a Supervisor.
     * @return true if a Supervisor is needed to clear this
     * PassengerEntity.
     */
    @Override
    public boolean needsSupervisor() {
        return this.getPassengerType() == PassengerType.REROUTED;
    }
}
