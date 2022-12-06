import java.util.List;

/**
 * This class represents an Agent Entity in Airville.
 * An Agent helps process Passengers and PassengerGroups
 * at a Station.
 *
 * @author alex wang axw582@case.edu
 * @version 0.0.1
 */
public class Agent extends EmployeeEntity {

    /**
     * Tracks the machines that this Agent oversees.
     */
    List<AutomatedAgent> machines;

    /**
     * A constructor for an Agent.
     * @param machines the machines that this Agent oversees.
     */
    public Agent(List<AutomatedAgent> machines) {
        this.machines = machines;
    }

    /**
     * A method to check whether this Agent is overseeing an
     * Automated Station.
     * @return true if the machines List is not null.
     */
    public boolean isAutomatedStation() {
        return this.machines != null;
    }
}
