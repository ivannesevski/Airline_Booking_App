package managers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import flights.Flight;
import users.User;
import users.UserInformation;

/** Manages the saving and loading of Flights. */
public class FlightManager implements Serializable {

    /** The serial version UID of this FlightManager */
    private static final long serialVersionUID = -1483481749760084784L;

    /** The logger of this FlightManager */
    private static final Logger logger =
            Logger.getLogger(ClientManager.class.getName());

    /** The console handler of this FlightManager */
    private static final Handler consoleHandler = new ConsoleHandler();

    /** The mapping of flight number to Flights */
    private Map<String, Flight> flights;

    /** The file path of this FlightManager */
    private String filePath;

    /** Creates a new empty FlightManager with the given file path filePath.
     *
     * @param filePath the file path of this FlightManager.
     * @throws ClassNotFoundException if the class is not found.
     * @throws IOException if there is an I/O error.
     */
    public FlightManager(String filePath) throws ClassNotFoundException, IOException {
        this.flights = new HashMap<String, Flight>();
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }

        this.filePath = file.getPath();
    }

    /** Creates a new empty Flight Manager */
    public FlightManager() {
        this.flights = new HashMap<>();
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        this.filePath = "";
    }

    /** Returns the map of flight number to Flights.
     *
     * @return the map of flight number to Flights.
     */
    public Map<String, Flight> getFlights() {
        return this.flights;
    }

    /** Uploads the Flight data in path filePath and adds all flights found in
     *  the file to flights. The flight number is the key mapped to each
     *  Flight object.
     *
     * @param filePath the path where the to be uploaded data is.
     * @throws FileNotFoundException in-case the path does not exist.
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        Flight flight;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            flight = new Flight(record[0], record[1], record[2], record[3],
                    record[4], record[5], Double.parseDouble(record[6]), Integer.parseInt(record[7]));
            this.flights.put(flight.getFlightNumber(), flight);
        }
        scanner.close();
    }

    /** Reads the a map of emails to Flights from path.
     *
     * @param path the file to be read from.
     * @throws ClassNotFoundException if the class is not found.
     */
    @SuppressWarnings("unchecked")
    private void readFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            this.flights = (Map<String, Flight>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Input cannot be read", ex);
        }
    }

    /** Adds a Flight to this FlightManager.
     *
     * @param flight the Flight to be added to this ClientManager.
     */
    public void add(Flight flight) {
        this.flights.put(flight.getFlightNumber(), flight);
        logger.log(Level.FINE, "Added a new flight: " + flight.toString());
    }

    /** Writes the Flight to file at filePath.
     *
     * @throws IOException if there is an I/O error.
     */
    public void saveToFile() throws IOException {
        if (!filePath.equals("")) {
            OutputStream file = new FileOutputStream(this.filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(this.flights);
            output.close();
        }
    }

    /** Returns the String representation of this FlightManager.
     *
     * @return the String representation of this FlightManager.
     */
    @Override
    public String toString() {
        String result = "";
        for (Flight flight : this.flights.values()) {
            result += flight.toString() + "\n";
        }
        return result;
    }
}

