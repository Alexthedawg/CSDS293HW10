import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PassengerGroup extends PassengerEntity {

    /**
     * Tracks the list of passengers in this group.
     */
    private List<Passenger> passengerList;

    /**
     * A constructor that creates a PassengerGroup based
     * on the Stream of Passengers given to this constructor.
     * @param passengers the passengers in this group.
     */
    public PassengerGroup(Stream<Passenger> passengers) {
        Objects.requireNonNull(passengers);

        this.passengerList = new ArrayList<>();
        passengers.forEach(passenger -> passengerList.add(passenger));
        this.setProcessTimeRequired(this.getProcessTimeRequired());
    }

    /**
     * A method to stream this PassengerGroup.
     * @return a stream of Passengers from this PassengerGroup.
     */
    public Stream<Passenger> stream() {
        return this.passengerList.stream();
    }

    /**
     * A method to check if any Passengers in this group are
     * not REGULAR Passengers.
     * @return true if any Passengers in the group are not
     * REGULAR.
     */
    @Override
    public boolean isIrregular() {
        return this.stream().anyMatch(passenger -> passenger.getPassengerType() != PassengerType.REGULAR);
    }

    /**
     * A method to check whether any of the Passengers need to be
     * REROUTED.
     * @return true if any Passengers in the group must be REROUTED
     * and need a Supervisor.
     */
    @Override
    public boolean needsSupervisor() {
        return this.stream().anyMatch(passenger -> passenger.getPassengerType() == PassengerType.REROUTED);
    }
}
