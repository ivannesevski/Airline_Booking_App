package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import flights.Flight;
import flights.Itinerary;
import group_0733.csc207project.R;

/** The activity used to view Itinerary Flights */
public class ViewItineraryFlightsActivity extends AppCompatActivity {

    /** The List of Flights in the Itinerary */
    public List<Flight> listFlights = new ArrayList<Flight>();

    /** The array adapter */
    ArrayAdapter<String> arrayAdapter;

    /** Sets up ViewItineraryFlightsActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itinerary_flights);

        Intent intent = getIntent();

        Itinerary tempItinerary = (Itinerary)
                intent.getSerializableExtra("flightsList");
        listFlights = tempItinerary.getFlights();

        addItemsOnSpinner();
        launchSpinnerListener();
    }

    /** Adds the Flights on the Spinner */
    public void addItemsOnSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);

        List<String> stringFlights = new ArrayList<>();
        for (Flight current : listFlights) {
            String currentHeader = "";
            currentHeader += String.valueOf(current.getCost()) + ", ";
            currentHeader += String.valueOf(current.getOrigin()) + ", ";
            currentHeader += String.valueOf(current.getDestination()) + ", ";
            currentHeader += String.valueOf(current.getTravelTime()) + ", ";
            currentHeader += String.valueOf(current.getAirline());
            stringFlights.add(currentHeader);
        }

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, stringFlights);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    /** Populates the activity with the selected Flight */
    public void populateActivityWithSelectedFlight(Flight selectedFlight) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        //get values of selected
        String FlightAirlineNumber = selectedFlight.getAirline();
        String FlightDepartDate = selectedFlight.getDepartureDateTime();
        String FlightArrivalDate = selectedFlight.getArrivalDateTime();
        String FlightOrigin = selectedFlight.getOrigin();
        String FlightDestination = selectedFlight.getDestination();
        double FlightCost = selectedFlight.getCost();
        int FlightTravelTime = selectedFlight.getTravelTime();

        //Attach to the fields on the layout
        TextView flightNumber =
                (TextView)findViewById(R.id.flightNumber);
        TextView destinationCity =
                (TextView)findViewById(R.id.destinationCity);
        TextView originCity =
                (TextView)findViewById(R.id.originCity);
        TextView costOfFlight =
                (TextView)findViewById(R.id.costOfFlight);
        TextView airlineProvider =
                (TextView)findViewById(R.id.airlineProvider);
        TextView departDateAndTime =
                (TextView)findViewById(R.id.departDateAndTime);
        TextView arrivalDateAndTime =
                (TextView)findViewById(R.id.arrivalDateAndTime);

        //Combine from the two above
        flightNumber.setText(String.valueOf(FlightTravelTime));
        destinationCity.setText(FlightDestination);
        originCity.setText(String.valueOf(FlightOrigin));
        costOfFlight.setText(String.valueOf(FlightCost));
        airlineProvider.setText(FlightAirlineNumber);
        departDateAndTime.setText(FlightDepartDate);
        arrivalDateAndTime.setText(FlightArrivalDate);

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
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        showToast("Spinner of Itineraries: " +
                                "position=" + position);
                        populateActivityWithSelectedFlight
                                (listFlights.get(position));
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
    }

}