package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import flights.Flight;
import group_0733.csc207project.R;

/** The activity used to select a Flight used to edit */
public class EnterFlightNumberActivity extends AppCompatActivity {

    /** Sets up EnterFlightNumberActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_flight_number);
    }

    /** Starts the EditFlight activity with the given flight number.
     *
     * @param view the View of the activity.
     */
    public void goToEdit(View view) {
        EditText flightNumberField = (EditText) findViewById
                (R.id.editFlightNumber);
        String flightNumber = flightNumberField.getText().toString();

        if (MainActivity.application.getFlightManager().getFlights()
                .containsKey(flightNumber)) {
            Flight flight = MainActivity.application
                    .getFlightManager().getFlights().get(flightNumber);

            Intent intent = new Intent(this, EditFlightActivity.class);

            intent.putExtra(MainActivity.FLIGHT_KEY, flight);

            startActivity(intent);
        }
    }

}
