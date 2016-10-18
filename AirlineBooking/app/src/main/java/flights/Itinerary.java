package flights;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import flights.Flight;

/** A representation of an Itinerary. */
public class Itinerary implements Serializable {

    private static final long serialVersionUID = 6380207083706695601L;

    /** The list of Flights. */
    List<Flight> flights;

    /** The departure date of this Itinerary. */
    String departDate;

    /** The arrival date of this Itinerary. */
    String arrivalDate;

    /** The origin of this Itinerary. */
    String origin;

    /** The destination of this Itinerary. */
    String destination;

    /** The total cost of all the Flights in this Itinerary. */
    double totalCost;

    /** The total travel time of all the Flights. */
    int totalTravelTime;

    /** Initializes an Itinerary with flights flights, totalCost as the total
     *  cost of all the Flights, and totalTravelTime as the total travel time
     *  of all the Flights.
     *
     * @param flights the list of all the Flights
     */
    public Itinerary(List<Flight> flights) {
        this.flights = flights;
        this.departDate = this.flights.get(0).getDepartureDateTime()
                .substring(0, 10);
        this.arrivalDate = this.flights.get(this.flights.size() - 1)
                .getArrivalDateTime().substring(0, 10);
        this.origin = this.flights.get(0).getOrigin();
        this.destination = this.flights.get(this.flights.size() - 1)
                .getDestination();
        this.totalCost = this.calculateTotalCost();

        try {
            this.totalTravelTime = this.calculateTotalTravelTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /** Returns the flights of this Itinerary.
     *
     * @return the flights of this Itinerary
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * Returns the departDate of this Itinerary.
     *
     * @return the departDate of this Itinerary
     */
    public String getDepartDate() {
        return this.departDate;
    }

    /** Returns the arrivalDate of this Itinerary.
     *
     * @return the arrivalDate of this Itinerary
     */
    public String getArrivalDate() {
        return this.arrivalDate;
    }

    /** Returns the origin of this Itinerary.
     *
     * @return the origin of this Itinerary
     */
    public String getOrigin() {
        return this.origin;
    }

    /** Returns the destination of this Itinerary.
     *
     * @return the destination of this Itinerary
     */
    public String getDestination() {
        return this.destination;
    }

    /** Returns the totalCost of this Itinerary.
     *
     * @return the totalCost of this Itinerary
     */
    public double getTotalCost() {
        return totalCost;
    }

    /** Returns the totalTravelTime of this Itinerary.
     *
     * @return the totalTravelTime of this Itinerary
     */
    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    /** Returns a String representation of this Itinerary.
     *
     * @return a String representation of this Itinerary.
     */
    @Override
    public String toString() {
        String result = "";
        DecimalFormat costDecimals = new DecimalFormat("0.00");

        for (Flight flight : this.flights) {
            String cost = costDecimals.format(flight.getCost());
            result += flight.toString().substring(0, flight.toString()
                    .length() - (cost.length() + 1)) + "\n";
        }

        String totalCost = costDecimals.format(this.totalCost);
        result += totalCost + "\n";

        String hours = String.format("%02d", (this.totalTravelTime / 60));
        String minutes = String.format("%02d", (this.totalTravelTime % 60));

        result += hours + ":" + minutes;

        return result;
    }

    /** Returns the calculated combined cost of all the flights
     *  in this Itinerary.
     *
     * @return the total cost of all the flights that a
     * 		   person would have to take
     */
    private double calculateTotalCost() {
        double currentCost = 0.0;
        for (int i = 0; i < this.flights.size(); i++) {
            currentCost = currentCost + this.flights.get(i).getCost();
        }

        return currentCost;
    }

    /** Returns the combined travel time using two helper methods:
     *  "calculateStopoverTime" and "calculateTotalCost"
     *
     * @return the total travel time of the itinerary
     *
     * @throws ParseException In-case that parsing the arrival/departure Time
     * 	                      would not work
     */
    private int calculateTotalTravelTime() throws ParseException {
        int totalFlyingTime = 0;
        for (Flight flight : this.flights) {
            totalFlyingTime += flight.getTravelTime();
        }

        int totalStopoverTime = 0;
        for (int i = 0; i < (this.flights.size() - 1); i++) {
            totalStopoverTime += calculateStopoverTime(this.flights.get(i),
                    this.flights.get(i + 1));
        }

        return totalFlyingTime + totalStopoverTime;
    }

    /** Returns in minutes the amount of waiting time a person would have to
     *  wait between two flights.
     *
     * @param flight1 The first flight object to be compared with flight2
     * @param flight2 The second flight object to be compared with flight1
     * @return the minutes between one flight ending and another beginning
     * @throws ParseException In-case that parsing the arrival/departure Time
     * 					      would not work
     */
    private int calculateStopoverTime(Flight flight1, Flight flight2)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date arrival = sdf.parse(flight1.getArrivalDateTime());
        Date departure = sdf.parse(flight2.getDepartureDateTime());

        long difference = departure.getTime() - arrival.getTime();

        int minutes = (int) difference/60000;

        return minutes;
    }
}
