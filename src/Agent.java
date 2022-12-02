import java.util.List;

public class Agent extends EmployeeEntity {

    List<AutomatedAgent> machines;

    /**
     * A constructor.
     * @param machines
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
