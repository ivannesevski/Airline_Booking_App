package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import flights.Flight;
import flights.Itinerary;
import managers.ClientManager;
import managers.FlightManager;
import users.User;
import users.UserInformation;

public class Application {

    private ClientManager clientManager;

    private FlightManager flightManager;

    private Collection<Flight> allFlights;

    private List<Itinerary> searchedItineraries;

    /** Initializes an Application with managers for clients and flights
     */
    public Application(ClientManager clientManager, FlightManager flightManager) {
        this.clientManager = clientManager;
        this.flightManager = flightManager;
        this.allFlights = flightManager.getFlights().values();
        this.searchedItineraries = new ArrayList<>();
    }

    public Application() {
        this.clientManager = new ClientManager();
        this.flightManager = new FlightManager();
        this.allFlights = flightManager.getFlights().values();
        this.searchedItineraries = new ArrayList<>();
    }

    public List<Itinerary> getSearchedItineraries() {
        return this.searchedItineraries;
    }

    public void uploadFlights(String filePath) {
        try {
            this.flightManager.readFromCSVFile(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.allFlights = this.flightManager.getFlights().values();
    }

    public ClientManager getClientManager() {
        return this.clientManager;
    }

    public FlightManager getFlightManager() {
        return this.flightManager;
    }

    public List<Flight> searchFlightsList(String departure, String origin,
                                          String destination) {
        List<Flight> searchedFlights = new ArrayList<>();

        for (Flight flight : this.allFlights) {
            if (flight.getDepartureDateTime().substring(0, 10)
                    .equals(departure) && flight.getOrigin().equals(origin)
                    && flight.getDestination().equals(destination)) {
                searchedFlights.add(flight);
            }
        }

        List<Flight> searchedFlightsListReturn = new ArrayList<>();

        for (Flight flight : searchedFlights) {
            searchedFlightsListReturn.add(flight);
        }

        return searchedFlightsListReturn;
    }
    /** Returns all flights that depart from origin and arrive at destination
     *  on the given date.
     *
     * @param departure a departure date (in the format YYYY-MM-DD)
     * @param origin a flight origin
     * @param destination a flight destination
     * @return            the flights that depart from origin and arrive at
     *                    destination on the given date formatted with one
     *                    flight per line in exactly this format: Number,
     *                    DepartureDateTime, ArrivalDateTime, Airline, Origin,
     *                    Destination, Price (the dates are in the format
     *                    YYYY-MM-DD; the price has exactly two decimal
     *                    places)
     */
    public String searchFlights(String departure, String origin,
                                String destination) {
        List<Flight> searchedFlights = new ArrayList<>();

        for (Flight flight : this.allFlights) {
            if (flight.getDepartureDateTime().substring(0, 10)
                    .equals(departure) && flight.getOrigin().equals(origin)
                    && flight.getDestination().equals(destination)) {
                searchedFlights.add(flight);
            }
        }

        String results = "";

        for (Flight flight : searchedFlights) {
            results += flight.toString() + "\n";
        }

        return results.substring(0, results.length() - 1);
    }

    /** Sorts the same Itineraries as getSearchedItineraries produces,
     *  but sorted according to total Itinerary cost, in non-decreasing
     *  order.
     */
    public void sortSearchedItinerariesByCost() {
        SortedMap<Double, Itinerary> costToItinerary = new TreeMap<>();
        List<Itinerary> searchedItinerariesByCost = new ArrayList<>();

        for (Itinerary itinerary : this.searchedItineraries) {
            costToItinerary.put(itinerary.getTotalCost(), itinerary);
        }

        for (Itinerary itinerary : costToItinerary.values()) {
            searchedItinerariesByCost.add(itinerary);
        }

        this.searchedItineraries = searchedItinerariesByCost;
    }

    /** Sorts the same Itineraries as getSearchedItineraries produces,
     * but sorted according to total Itinerary travel time, in non-decreasing
     * order.
     */
    public void sortSearchedItinerariesByTime() {
        SortedMap<Integer, Itinerary> timeToItinerary = new TreeMap<>();
        List<Itinerary> searchedItinerariesByTime = new ArrayList<>();

        for (Itinerary itinerary : this.searchedItineraries) {
            timeToItinerary.put(itinerary.getTotalTravelTime(), itinerary);
        }

        for (Itinerary itinerary : timeToItinerary.values()) {
            searchedItinerariesByTime.add(itinerary);
        }

        this.searchedItineraries = searchedItinerariesByTime;
    }

    public String searchedItinerariesToString() {
        String result = "";
        for (Itinerary itinerary : this.searchedItineraries) {
            result += itinerary.toString() + "\n";
        }

        return result.substring(0, result.length() - 1);
    }

    public void searchItineraries(String departure, String origin,
                                              String destination) throws ParseException {
        List<Itinerary> searchedItineraries = new ArrayList<>();

        // Adds itineraries that are just direct flights
        for (Flight flight : this.allFlights) {
            if (flight.getDepartureDateTime().substring(0, 10)
                    .equals(departure) && flight.getOrigin().equals(origin)
                    && flight.getDestination().equals(destination)
                    && flight.getNumSeats() != 0) {
                List<Flight> itineraryFlights = new ArrayList<>
                        (Arrays.asList(flight));
                searchedItineraries.add(new Itinerary(itineraryFlights));
            }
        }

        // Adds itineraries that are a sequence of 2 more flights
        for (Flight startFlight : this.allFlights) {
            if (startFlight.getDepartureDateTime().substring(0, 10)
                    .equals(departure)
                    && startFlight.getOrigin().equals(origin)
                    && startFlight.getNumSeats() != 0) {

                for (Flight endFlight : this.allFlights) {
                    if (endFlight.getDestination().equals(destination)
                            && endFlight != startFlight
                            && endFlight.getNumSeats() != 0) {

                        int connections = 1;
                        while (connections <= (this.allFlights.size() - 1)) {
                            List<String> visitedCities = new ArrayList<>();
                            List<Flight> itineraryFlights
                                    = generateItinerary(startFlight,
                                    endFlight, connections, visitedCities);

                            if (!itineraryFlights.isEmpty()) {
                                itineraryFlights.add(endFlight);
                                searchedItineraries
                                        .add(new Itinerary(itineraryFlights));
                            }
                            connections += 1;
                        }
                    }
                }
            }
        }
        this.searchedItineraries = searchedItineraries;
    }

    private List<Flight> generateItinerary(Flight startFlight,
                                           Flight endFlight, int connections,
                                           List<String> visitedCities)
            throws ParseException {
        List<Flight> itineraryFlights = new ArrayList<>
                (Arrays.asList(startFlight));
        visitedCities.add(startFlight.getOrigin());

        if (connections == 1) {

            // checks if startFlight connects with endFlight
            if (endFlight.getOrigin().equals(startFlight.getDestination())
                    && calculateStopoverTime(startFlight, endFlight) <= 360
                    && calculateStopoverTime(startFlight, endFlight) >= 0
                    && !(visitedCities.contains(endFlight
                    .getDestination()))) {
                return itineraryFlights;
            }
            return new ArrayList<Flight>();
        }
        else {

            // finds flights that connect to startFlight
            for (Flight nextFlight: this.allFlights) {
                if (nextFlight.getOrigin()
                        .equals(startFlight.getDestination())
                        && calculateStopoverTime(startFlight,
                        nextFlight) <= 360
                        && calculateStopoverTime(startFlight,
                        nextFlight) >= 0
                        && !(visitedCities
                        .contains(nextFlight.getOrigin()))
                        && nextFlight.getNumSeats() != 0) {
                    List <Flight> nextFlights = generateItinerary(nextFlight,
                            endFlight, (connections - 1), visitedCities);

                    if (!(nextFlights.isEmpty())) {
                        itineraryFlights.addAll(nextFlights);
                        return itineraryFlights;
                    }
                    else {
                        return new ArrayList<Flight>();
                    }
                }
            }
            return new ArrayList<Flight>();
        }
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

