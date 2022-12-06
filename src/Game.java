import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

/**
 * This class represents the stage upon which
 * the Game Airville takes place. This is the
 * Main class that tracks all Entities and
 * Stations that exist in the Game. The class also
 * tracks the currency (diamonds) in this Game.
 *
 * @author alex wang axw582@case.edu
 * @version 0.0.1
 */
public class Game {

    /**
     * Tracks the supervisor of the entire game, if present.
     */
    private Supervisor supervisor;

    /**
     * Tracks the total number of diamonds accumulated.
     */
    private int diamonds;

    /**
     * Tracks all the entities present in the Game.
     */
    private List<Entity> entityList;

    /**
     * Tracks the Stations that are present in the Game.
     */
    private List<Station> stationList;

    /**
     * A constructor to create a new Game initialized
     * with the given parameters.
     * @param supervisor the supervisor of this Game.
     * @param diamonds the initial amount of diamonds.
     * @param entityList the initial Entities in the Game.
     * @param stationList the initial Stations in the Game.
     */
    public Game(Supervisor supervisor,
                int diamonds,
                List<Entity> entityList,
                List<Station> stationList) {
        Objects.requireNonNull(supervisor);
        Objects.requireNonNull(diamonds);
        Objects.requireNonNull(entityList);
        Objects.requireNonNull(stationList);

        this.supervisor = supervisor;
        this.diamonds = diamonds;
        this.entityList = entityList;
        this.stationList = stationList;
    }

    /**
     * A method to buy an "in-person" Station. An in-person
     * Station is bought with a non-automated Agent.
     *
     * @param cost the cost to buy an in-person Station.
     * @return true if the in-person Station was bought.
     */
    public boolean buyInPersonStation(int cost) {
        if (this.diamonds >= cost) {
            this.diamonds -= cost;
            Agent agent = new Agent(null);
            PassengerSupplier supplier = new PassengerSupplier();
            Station station = new Station(null, agent, supplier);
            this.stationList.add(station);
            this.entityList.add(agent);
            return true;
        } // if -- not enough diamonds
        return false;
    }

    /**
     * A method to buy an "automated" Station. An in-person
     * Station is bought alongside an automated Agent.
     *
     * @param cost the cost to buy an automated Station.
     * @return true if the automated Station was bought.
     */
    public boolean buyAutomatedStation(int cost) {
        if (this.diamonds >= cost) {
            this.diamonds -= cost;
            Agent agent = new Agent(List.of(new AutomatedAgent()));
            PassengerSupplier supplier = new PassengerSupplier();
            Station station = new Station(null, agent, supplier);
            this.stationList.add(station);
            this.entityList.add(agent);
            return true;
        } // if -- not enough diamonds
        return false;
    }

    /**
     * A method to buy a Supervisor at a
     * specified cost with diamonds.
     *
     * @param cost the cost to buy a Supervisor.
     * @return true if the Supervisor was bought.
     */
    public boolean buySupervisor(int cost) {
        if (this.diamonds >= cost) {
            this.diamonds -= cost;
            Supervisor supervisor = new Supervisor();
            this.entityList.add(supervisor);
            return true;
        } // if -- not enough diamonds
        return false;
    }

    /**
     * A method to set the supervisor overseeing the Game.
     * If one is already present, then this method replaces
     * that one.
     * @param supervisor the new Supervisor.
     * @return the previous Supervisor if present or null.
     */
    public Supervisor setSupervisor(Supervisor supervisor) {
        Supervisor temp = this.supervisor;
        this.supervisor = supervisor;
        return temp;
    }

    /**
     * A method to get the total number of Stations in the Game.
     * @return the number of Stations present in the Game.
     */
    public int getNumberOfStations() {
        return stationList.size();
    }

    /**
     * A method to get the total number of Automated Stations in the Game.
     * @return the number of Automatic Stations present in the Game.
     */
    public int getNumberOfAutomaticStations() {
        return (int) this.stationList.stream().filter(station -> station.isAutomated()).count();
    }

    /**
     * A method to get the total number of In Person Stations in the Game.
     * @return the number of In Person Stations present in the Game.
     */
    public int getNumberOfInPersonStations() {
        return (int) this.stationList.stream().filter(station -> !station.isAutomated()).count();
    }

    /**
     * A method to see whether any entities are busy.
     * @return true if any entities are busy.
     */
    public boolean isAnyEntitiesBusy() {
        return this.entityList.stream().filter(entity -> entity instanceof EmployeeEntity)
                .anyMatch(employee -> ((EmployeeEntity) employee).getBusyTime() > 0);
    }

    /**
     * A method to get the time left until no entities are busy.
     * @return the maximum time left until no more entities are busy
     * or 0.0 if no entities are currently busy.
     */
    public double maxTimeUntilNotBusy() {
        return this.entityList.stream().filter(entity -> entity instanceof EmployeeEntity)
                .flatMapToDouble(entity -> DoubleStream.of(((EmployeeEntity) entity).getBusyTime()))
                .max().orElseGet(() -> 0.0);
    }
}
