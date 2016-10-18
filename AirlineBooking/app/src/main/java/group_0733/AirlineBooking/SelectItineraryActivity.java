package group_0733.AirlineBooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import flights.Flight;
import flights.Itinerary;
import group_0733.csc207project.R;
import users.User;

/** The activity used to select an Itinerary */
public class SelectItineraryActivity extends AppCompatActivity {

    /** The list of searched Itineraries */
    public List<Itinerary> listItinerary = new ArrayList<>();

    /** The list of searched Itineraries sorted by cost */
    public List<Itinerary> listItineraryByCost = new ArrayList<>();

    /** The list of searched Itineraries sorted by time */
    public List<Itinerary> listItineraryByTime = new ArrayList<>();

    /** The email of the current user */
    private User current_user;

    /** The arrayAdaptor */
    private ArrayAdapter<String> arrayAdapter;

    /** The currentMode */
    private String currentMode;

    /** The currently selected Itinerary */
    private Itinerary currentlySelected;

    /** Sets up SelectItineraryActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_itinerary);

        Intent intent = getIntent();
        this.current_user = (User) intent.getSerializableExtra(MainActivity.USER_KEY);
        addItemsOnSpinner();
        launchSpinnerListener();
    }

    /** Fills the Spinner with Itineraries */
    public void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        this.listItinerary = MainActivity.application.getSearchedItineraries();

        List<String> stringItinerary = cycleThroughForHeader(listItinerary);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringItinerary);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        currentMode = "StartUp";
    }

    /** Populates the activity with the selected Itinerary.
     *
     * @param selectedItinerary the selected Itinerary
     */
    public void populateActivityWithSelectedItinerary(Itinerary selectedItinerary) {
        currentlySelected = selectedItinerary;
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //get values of selected
        List<Flight> flights = selectedItinerary.getFlights();

        int travelTime = selectedItinerary.getTotalTravelTime();
        String hours = String.format("%02d", (travelTime / 60));
        String minutes = String.format("%02d", (travelTime % 60));

        String ItineraryDepartDate = flights.get(0).getDepartureDateTime();
        String ItineraryArrivalDate = flights.get(flights.size() - 1).getArrivalDateTime();
        String ItineraryOrigin = selectedItinerary.getOrigin();
        String ItineraryDestination = selectedItinerary.getDestination();
        String ItineraryTotalCost = String.format("%.2f", selectedItinerary.getTotalCost());
        String ItineraryTotalTravelTime = hours + ":" + minutes;
        String lengthOfFlights = String.valueOf(flights.size());

        //Attach to the fields on the layout
        TextView destinationCity = (TextView)findViewById(R.id.destinationCity);
        TextView originCity = (TextView)findViewById(R.id.originCity);
        TextView totalCost = (TextView)findViewById(R.id.totalCost);
        TextView totalTravelTime = (TextView)findViewById(R.id.totalTravelTime);
        TextView numberOfFlightsWithin = (TextView)findViewById(R.id.numberOfFlightsWithin);
        TextView arrivalDate = (TextView)findViewById(R.id.arrivalDate);
        TextView departureDate = (TextView)findViewById(R.id.departureDate);


        destinationCity.setText(ItineraryDestination);
        originCity.setText(ItineraryOrigin);
        totalCost.setText(ItineraryTotalCost);
        totalTravelTime.setText(ItineraryTotalTravelTime);
        numberOfFlightsWithin.setText(String.valueOf(lengthOfFlights));
        arrivalDate.setText(ItineraryArrivalDate);
        departureDate.setText(ItineraryDepartDate);

    }

    /** Shows a message on screen with msg.
     *
     * @param msg the message to be shown on screen.
     */
    public void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /** Sorts the Itineraries on the spinner based on the mode selected.
     *  Either by time or by cost.
     */
    public void launchSpinnerListener() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        if (currentMode.equals("StartUp")) {
                            populateActivityWithSelectedItinerary
                                    (listItinerary.get(position));
                        } else if (currentMode.equals("ByCost")) {
                            populateActivityWithSelectedItinerary
                                    (listItineraryByCost.get(position));
                        } else if (currentMode.equals("ByTime")) {
                            populateActivityWithSelectedItinerary
                                    (listItineraryByTime.get(position));
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
    }

    /** Books the selected Itinerary for the current user and displays an
     *  on screen message of the Itinerary.
     *
     * @param view the View of activity.
     */
    public void bookItinerary(View view) {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        this.current_user.bookItinerary(currentlySelected);

        MainActivity.application.getClientManager().add(this.current_user);

        try {
            MainActivity.application.getClientManager().saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.putExtra(MainActivity.USER_KEY, current_user);
        setResult(RESULT_OK, intent);

        Toast.makeText(SelectItineraryActivity.this,
                "You have booked: " +
                        "\n" + String.valueOf(spinner.getSelectedItem()),
                Toast.LENGTH_SHORT).show();
    }

    /** Starts the ViewItinerary activity with the selected Itinerary on the
     *  Spinner.
     *
     * @param view the View of the activity.
     */
    public void viewFlights(View view) {
        //pass in Itinerary Object
        Intent intent = new Intent(this, ViewItineraryFlightsActivity.class);
        intent.putExtra("flightsList", currentlySelected);
        startActivity(intent);
    }

    /** Sorts the Itineraries on the Spinner by cost.
     *
     * @param view the View of the activity.
     */
    public void toggleCost(View view) {
        //Re-arrange Itineraries by Cost
        ToggleButton toggleCost = (ToggleButton)
                findViewById(R.id.toggleButtonCost);
        ToggleButton toggleTime = (ToggleButton)
                findViewById(R.id.toggleButtonTime);

        //Toggle buttons
        toggleCost.setChecked(true);
        toggleTime.setChecked(false);

        //Sort Itineraries By Cost Here
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        MainActivity.application.sortSearchedItinerariesByCost();
        listItineraryByCost = MainActivity.application.getSearchedItineraries();

        List<String> stringItinerary = cycleThroughForHeader(listItineraryByCost);

        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, stringItinerary);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        currentMode = "ByCost";
    }

    /** Sorts the Itineraries on the Spinner by time.
     *
     * @param view the View of the activity.
     */
    public void toggleTime(View view) {
        //Re-arrange Itineraries by Time
        ToggleButton toggleTime = (ToggleButton)
                findViewById(R.id.toggleButtonTime);
        ToggleButton toggleCost = (ToggleButton)
                findViewById(R.id.toggleButtonCost);

        //Toggle buttons
        toggleCost.setChecked(false);
        toggleTime.setChecked(true);

        //Sort Itineraries By Time Here
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        MainActivity.application.sortSearchedItinerariesByTime();
        listItineraryByTime = MainActivity.application
                .getSearchedItineraries();

        List<String> stringItinerary
                = cycleThroughForHeader(listItineraryByTime);

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stringItinerary);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        currentMode = "ByTime";
    }

    /** Returns a list of a String representation for the list if Itinerary.
     *
     * @param ItineraryList the list of Itinerary.
     * @return a list of a String representation for the list if Itinerary.
     */
    private List<String> cycleThroughForHeader(List<Itinerary> ItineraryList) {
        List<String> stringItinerary = new ArrayList<String>();
        for (Itinerary current : ItineraryList) {
            //Make a custom Header for display
            String currentHeader = "";
            currentHeader += "Cost: "
                    + String.valueOf(current.getTotalCost()) + ", ";
            currentHeader += "Time: "
                    + String.valueOf(current.getTotalTravelTime()) + ", ";
            currentHeader += "Flights #: "
                    + String.valueOf(current.getFlights().size());
            stringItinerary.add(currentHeader);
        }
        return stringItinerary;
    }
}