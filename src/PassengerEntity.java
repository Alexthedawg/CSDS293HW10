public abstract class PassengerEntity extends Entity {

    /**
     * Tracks the time required to process this
     * PassengerEntity.
     */
    private double processTimeRequired;

    /**
     * A getter for getting the processTimeRequired.
     * @return the process time required.
     */
    public double getProcessTimeRequired() {
        return this.processTimeRequired;
    }

    /**
     * A setter for processTimeRequired value.
     * @param processTimeRequired the specified process time.
     */
    public void setProcessTimeRequired(double processTimeRequired) {
        this.processTimeRequired = processTimeRequired;
    }

    /**
     * A method to check if any Passengers in this group are
     * not REGULAR Passengers.
     * @return true if any Passengers in the group are not
     * REGULAR.
     */
    public abstract boolean isIrregular();

    /**
     * A method to check whether this Passenger or PassengerGroup
     * requires a Supervisor.
     * @return true if a Supervisor is needed to clear this
     * PassengerEntity.
     */
    public abstract boolean needsSupervisor();
}
