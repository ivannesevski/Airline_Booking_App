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
import group_0733.csc207project.R;

public class SelectFlightActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    List<Flight> listFlights = new ArrayList<Flight>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flight);

        Intent intent = getIntent();
        String departureDate = (String) intent.getStringExtra("departureDate");
        String travelOrigin = (String) intent.getStringExtra("travelOrigin");
        String destination = (String) intent.getStringExtra("destination");
        addItemsOnSpinner(departureDate, travelOrigin, destination);
        launchSpinnerListener();
    }

    public void addItemsOnSpinner(String departureDate, String travelOrigin, String destination) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner4);

        listFlights = MainActivity.application.searchFlightsList(departureDate, travelOrigin, destination);

        List<String> stringItinerary = new ArrayList<String>();

        for (Flight current : listFlights) {
            //Make a custom Header for display
            String currentHeader = "";
            currentHeader += "#: " + String.valueOf(current.getFlightNumber() + ", ");
            currentHeader += "Cost: " + String.valueOf(current.getCost()) + ", ";
            currentHeader += "Time: " + String.valueOf(current.getTravelTime()) + ", ";
            stringItinerary.add(currentHeader);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringItinerary);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void populateActivityWithSelectedItinerary(Flight selectedFlight) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //get values of selected
        //get values of selected
        String FlightAirlineNumber = selectedFlight.getAirline();
        String FlightDepartDate = selectedFlight.getDepartureDateTime();
        String FlightArrivalDate = selectedFlight.getArrivalDateTime();
        String FlightOrigin = selectedFlight.getOrigin();
        String FlightDestination = selectedFlight.getDestination();
        double FlightCost = selectedFlight.getCost();
        int FlightTravelTime = selectedFlight.getTravelTime();

        //Attach to the fields on the layout
        TextView flightNumber = (TextView)findViewById(R.id.flightNumber);
        TextView destinationCity = (TextView)findViewById(R.id.destinationCity);
        TextView originCity = (TextView)findViewById(R.id.originCity);
        TextView costOfFlight = (TextView)findViewById(R.id.costOfFlight);
        TextView airlineProvider = (TextView)findViewById(R.id.airlineProvider);
        TextView departDateAndTime = (TextView)findViewById(R.id.departDateAndTime);
        TextView arrivalDateAndTime = (TextView)findViewById(R.id.arrivalDateAndTime);

        //Combine from the two above
        flightNumber.setText(String.valueOf(FlightTravelTime));
        destinationCity.setText(FlightDestination);
        originCity.setText(String.valueOf(FlightOrigin));
        costOfFlight.setText(String.valueOf(FlightCost));
        airlineProvider.setText(FlightAirlineNumber);
        departDateAndTime.setText(FlightDepartDate);
        arrivalDateAndTime.setText(FlightArrivalDate);
    }

    public void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void launchSpinnerListener() {
        final Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        populateActivityWithSelectedItinerary(listFlights.get(position));
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner1: unselected");
                    }
                });
    }
}
