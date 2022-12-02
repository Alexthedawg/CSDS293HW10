import java.util.ArrayList;
import java.util.List;

public abstract class EmployeeEntity extends Entity {
    /**
     * Tracks whether this EmployeeEntity is busy or not.
     */
    private boolean isBusy;

    /**
     * Tracks how long until this Employee is no longer busy.
     */
    private double busyTime;

    /**
     * A method to get the time needed to process a Passenger.
     * @param passenger the Passenger of interest.
     * @return the processing time required for this Passenger.
     */
    public static double getPassengerProcessTime(Passenger passenger) {
        /* The initial total time is retrieved from passenger. */
        double totalTime = passenger.getProcessTimeRequired();

        /* If the passenger is of a particular type, increase the processing time. */
        if (passenger.getPassengerType() != PassengerType.REGULAR) {
            totalTime *= 1.5;
        }

        return totalTime;
    }

    /**
     * A method to get the time needed to process a PassengerGroup.
     * @param group the PassengerGroup of interest.
     * @return the processing time required for this group.
     */
    public static double getGroupProcessTime(PassengerGroup group) {
        List<Double> processTimes = new ArrayList<>();

        /* Add the process times for each individual Passenger in the group. */
        group.stream().forEach(passenger -> processTimes.add(getPassengerProcessTime(passenger)));

        /* Return a discounted total processing time for the group. */
        return 0.8 * processTimes.stream().mapToDouble(time -> time.doubleValue()).sum();
    }

    /**
     * A method to make this Employee busy for a set amount
     * of time. If the Employee is already busy, then nothing
     * happens.
     * @param time the amount of time this Employee will be
     *             busy.
     * @return true if this Employee is not already busy.
     */
    public boolean setBusy(double time) {
        if (this.isBusy) {
            return false;
        } else {
            this.isBusy = true;
            this.busyTime = time;
            return true;
        }
    }

    /**
     * A method to get the time this Employee will be busy for.
     * @return the time this Employee will be busy.
     */
    public double getBusyTime() {
        return this.busyTime;
    }

    /**
     * A method to finish the Employee's task and set their
     * busy status to false.
     * @return the time that this Employee has worked for.
     */
    public double finishTask() {
        double busyTime = this.busyTime;
        this.busyTime = 0;
        this.isBusy = false;
        return busyTime;
    }
}
