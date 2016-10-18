package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import flights.Itinerary;
import group_0733.csc207project.R;
import users.User;

/** The activity used to view a Clients booked Itineraries */
public class ViewBookedItinerariesActivity extends AppCompatActivity {

    /** The List of Itinerary */
    private List<Itinerary> listItinerary = new ArrayList<>();

    /** The currently selected Itinerary */
    private Itinerary currentlySelected;

    /** The array adaptor */
    private ArrayAdapter<String> arrayAdapter;

    /** The current user */
    private User current_user;

    /** Sets up ViewBookedItinerariesActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booked_itineraries);

        Intent intent = getIntent();
        current_user = (User) intent.getSerializableExtra(MainActivity.USER_KEY);
        listItinerary = current_user.getBookedItineraries();
        Log.d("booked", String.valueOf(listItinerary.size()));

        addItemsOnSpinner();
        launchSpinnerListener();
    }

    /** Adds the itineraries on to the Spinner */
    public void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);

        List<String> stringItinerary = new ArrayList<>();
        for (Itinerary current : this.listItinerary) {
            //Make a custom Header for display
            String currentHeader = "";
            currentHeader += "Total Cost: "
                    + String.valueOf(current.getTotalCost()) + ", ";
            currentHeader += "Travel Time: "
                    + String.valueOf(current.getTotalTravelTime()) + ", ";
            currentHeader += "Number of Flights: "
                    + String.valueOf(current.getFlights().size());
            stringItinerary.add(currentHeader);
        }

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stringItinerary);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    /** Populates the activity with the selected itineraries */
    public void populateActivityWithSelectedItinerary
    (Itinerary selectedItinerary) {
        currentlySelected = selectedItinerary;
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        //get values of selected
        String ItineraryDepartDate = selectedItinerary.getDepartDate();
        String ItineraryArrivalDate = selectedItinerary.getArrivalDate();
        String ItineraryOrigin = selectedItinerary.getOrigin();
        String ItineraryDestination = selectedItinerary.getDestination();
        double ItineraryTotalCost = selectedItinerary.getTotalCost();
        int ItineraryTotalTravelTime = selectedItinerary.getTotalTravelTime();
        int lengthOfFlights = selectedItinerary.getFlights().size();

        //Attach to the fields on the layout
        TextView destinationCity = (TextView)findViewById(R.id.destinationCity);
        TextView originCity = (TextView)findViewById(R.id.originCity);
        TextView totalCost = (TextView)findViewById(R.id.totalCost);
        TextView totalTravelTime = (TextView)findViewById(R.id.totalTravelTime);
        TextView numberOfFlightsWithin
                = (TextView)findViewById(R.id.numberOfFlightsWithin);
        TextView arrivalDate = (TextView)findViewById(R.id.arrivalDate);
        TextView departureDate = (TextView)findViewById(R.id.departureDate);


        destinationCity.setText(ItineraryDestination);
        originCity.setText(ItineraryOrigin);
        totalCost.setText(String.valueOf(ItineraryTotalCost));
        totalTravelTime.setText(String.valueOf(ItineraryTotalTravelTime));
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

    /** Listener for the Spinner */
    public void launchSpinnerListener() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner of Itineraries: position=" + position);
                        populateActivityWithSelectedItinerary(listItinerary.get(position));
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
    }

    /** Starts the ViewItineraryFlights activity with the selected Itinerary.
     *
     * @param view the View of the activity.
     */
    public void viewFlights(View view) {
        //pass in Itinerary Object
        Intent intent = new Intent(this, ViewItineraryFlightsActivity.class);
        intent.putExtra("flightsList", currentlySelected);
        startActivity(intent);
    }

}

