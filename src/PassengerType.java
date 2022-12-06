/**
 * This enum represents the different types of
 * Passengers who may be in line.
 *
 * @author alex wang axw582@case.edu
 * @version 0.0.1
 */
public enum PassengerType {
    /**
     * Regular Passengers have no special circumstances.
     */
    REGULAR,
    /**
     * Extra baggage Passengers require extra time to process
     * and weigh their check-in baggage.
     */
    EXTRA_BAGGAGE,
    /**
     * Rerouted Passengers require assistance from a Supervisor
     * and cannot be processed at a Station with only an Agent.
     */
    REROUTED,
    /**
     * Overbooked Passengers must be processed and given a
     * travel voucher.
     */
    OVERBOOKED
}
