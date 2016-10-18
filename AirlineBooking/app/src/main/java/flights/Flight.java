package flights;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/** Flight class manages the information associated to a flight
 *  object, as well as initially creating flight objects */
public class Flight implements Serializable {

    private static final long serialVersionUID = 5901540873021570232L;

    /** the flight number associated to this Flight */
    private String flightNumber;

    /** the departure date and time of this Flight */
    private String departureDateTime;

    /** the arrival date and time of this Flight's destination */
    private String arrivalDateTime;

    /** the airline company name associated to this Flight */
    private String airline;

    /** the origin city from where departure will take place */
    private String origin;

    /** the destination city of this Flight route */
    private String destination;

    /** the cost per person to board this Flight */
    private double cost;

    /** the travel time of this Flight */
    private int travelTime;

    /** the number of seats in this Flight */
    private int numSeats;

    /** Constructor of the class that take and assigns the appropriate context
     *  to the information of the flight under this Flight object.
     *
     * @param number the flight number associated to this Flight
     * @param departure	the departure date and time of this Flight
     * @param arrival the arrival date and time of this Flight's destination
     * @param airline the airline company name associated to this Flight
     * @param origin the origin city from where departure of this Flight will
     *               take place
     * @param destination the destination city of this Flight
     * @param cost the cost per person to board this Flight
     */
    public Flight(String number, String departure, String arrival,
                  String airline, String origin, String destination, Double cost, int numSeats) {
        this.flightNumber = number;
        this.departureDateTime = departure;
        this.arrivalDateTime = arrival;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
        this.numSeats = numSeats;

        try {
            this.travelTime = this.calculateTravelTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /** Returns a string representation of the flight number for this
     * flight.
     *
     * @return the flight number of this flight
     */
    public String getFlightNumber() {
        return this.flightNumber;
    }

    /** Returns a string representation of the Date and Time as as taken
     *  from CSV files, as (YYYY-MM-DD HH-MM).
     *
     * @return the departure date and time as a String without
     *  	   any modification
     */
    public String getDepartureDateTime() {
        return this.departureDateTime;
    }

    /** Returns the arrival date and time of this Flight.
     *
     * @return the arrival date and time of this Flight.
     */
    public String getArrivalDateTime() {
        return this.arrivalDateTime;
    }

    /** Returns the airline of this Flight.
     *
     * @return the airline of this Flight.
     */
    public String getAirline() {
        return this.airline;
    }

    /** Returns a String representation of the origin from where
     *  this Flight is leaving, this will be a city name.
     *
     * @return the origin of this Flight's destination.
     */
    public String getOrigin() {
        return this.origin;
    }

    /** Returns a String representation of the destination from where
     *  this Flight is leaving, this will be a city name.
     *
     * @return the destination of where this Flight is going towards.
     */
    public String getDestination() {
        return this.destination;
    }

    /** Returns a string representation of how much this Flight
     *  costs given the specifications of this Flight.
     *
     * @return String representation of this Flight's cost in dollars,
     *  	   including cents
     */
    public double getCost() {
        return this.cost;
    }

    /** Returns the travelTime of this Flight.
     *
     * @return the travelTime of this Flight.
     */
    public int getTravelTime() {
        return this.travelTime;
    }

    /** Returns the number of seats in this Flight.
     *
     * @return the number of seat in this Flight.
     */
    public int getNumSeats() {
        return this.numSeats;
    }

    /** Sets the departure date and time of this Flight.
     *
     * @param departureDateTime the new departure date and time.
     */
    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /** Sets the arrival date and time of this Flight.
     *
     * @param arrivalDateTime the new arrival date and time.
     */
    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    /** Sets the airline of this Flight.
     *
     * @param airline the new airline of this Flight.
     */
    public void setAirline(String airline) {
        this.airline = airline;
    }

    /** Sets the origin of this Flight.
     *
     * @param origin the new origin of this Flight.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /** Sets the destination of this Flight.
     *
     * @param destination the new destination of this Flight.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /** Sets the cost of this Flight.
     *
     * @param cost the new cost of this Flight.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /** Sets the number of seats in this Flight.
     *
     * @param seats the new number of seats in this Flight.
     */
    public void setNumSeats(int seats) {
        this.numSeats = seats;
    }

    /** Returns the calculated difference using the departure and arrival time
     *  of this Flight, in minutes.
     *
     * @return the difference between two travel dates and their time
     * 		   in minutes.
     * @throws ParseException In-case that parsing the arrival/departure time
     * 						  would not work.
     */
    private int calculateTravelTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date departure = sdf.parse(this.departureDateTime);
        Date arrival = sdf.parse(this.arrivalDateTime);

        long difference = arrival.getTime() - departure.getTime();

        int minutes = (int) difference/60000;

        return minutes;
    }

    /** Returns all this Flight's information as a String, ready to be
     *  later used for output onto the console.
     *
     * @return the String representation of this Flight.
     */
    @Override
    public String toString() {
        DecimalFormat costDecimals = new DecimalFormat("0.00");
        String formattedCost = costDecimals.format(this.cost);
        return this.flightNumber + "," + this.departureDateTime + "," +
                this.arrivalDateTime + "," + this.airline + "," +
                this.origin + "," + this.destination + "," + formattedCost;
    }
}

