package group_0733.AirlineBooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import flights.Flight;
import group_0733.csc207project.R;

/** The activity used to edit Flight information */
public class EditFlightActivity extends AppCompatActivity {

    /** The Flight to be edited */
    private Flight flight;

    /** Sets up EditFlightActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);

        Intent intent = getIntent();
        this.flight = (Flight) intent.getSerializableExtra
                (MainActivity.FLIGHT_KEY);

        setTextFields();

    }

    /** Writes the changes to file */
    public void saveFields() {
        // writes to file
        try {
            MainActivity.application.getFlightManager().saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("This flight has been updated");
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /** Transfers the text view and the edit text to save.
     *
     * @param textViewField the text view fields.
     * @param EditTextField the edit text fields.
     */
    public void transferToSave(TextView textViewField,
                               EditText EditTextField) {
        String currentString = textViewField.getText().toString();
        textViewField.setVisibility(View.GONE);

        //Now the EditField
        EditTextField.setVisibility(View.VISIBLE);
        EditTextField.setText(currentString);
    }

    /** Transfers the text view and edit text to edit.
     *
     * @param textViewField the text view fields.
     * @param EditTextField the edit text fields.
     */
    public void transferToEdit(TextView textViewField,
                               EditText EditTextField) {
        String currentString = EditTextField.getText().toString();
        EditTextField.setVisibility(View.GONE);

        //Now the TextView
        textViewField.setVisibility(View.VISIBLE);
        textViewField.setText(currentString);
    }

    /** Sets the text fields */
    public void setTextFields() {
        TextView departureDate = (TextView) findViewById
                (R.id.textDepartureDate);
        TextView arrivalDate = (TextView) findViewById
                (R.id.textArrivalDate);
        TextView airline = (TextView) findViewById
                (R.id.textAirline);
        TextView origin = (TextView) findViewById
                (R.id.textOrigin);
        TextView destination = (TextView) findViewById
                (R.id.textDestination);
        TextView cost = (TextView) findViewById
                (R.id.textCost);
        TextView seats = (TextView) findViewById
                (R.id.textSeats);

        departureDate.setText(flight.getDepartureDateTime());
        arrivalDate.setText(flight.getArrivalDateTime());
        airline.setText(flight.getAirline());
        origin.setText(flight.getOrigin());
        destination.setText(flight.getDestination());

        Double numCost = flight.getCost();
        String formattedCost = String.format("%.2f", numCost);

        cost.setText(formattedCost);
        seats.setText(String.valueOf(flight.getNumSeats()));

    }

    /** Allows the fields to be edited and hte data to be saved.
     *
     * @param view the View of the activity.
     */
    public void editSave(View view) {
        //find View fields and Edit fields
        TextView departureDate = (TextView) findViewById
                (R.id.textDepartureDate);
        TextView arrivalDate = (TextView) findViewById
                (R.id.textArrivalDate);
        TextView airline = (TextView) findViewById
                (R.id.textAirline);
        TextView origin = (TextView) findViewById
                (R.id.textOrigin);
        TextView destination = (TextView) findViewById
                (R.id.textDestination);
        TextView cost = (TextView) findViewById
                (R.id.textCost);
        TextView seats = (TextView) findViewById
                (R.id.textSeats);

        EditText departureDateField = (EditText) findViewById
                (R.id.editDepartureDate);
        EditText arrivalDateField = (EditText) findViewById
                (R.id.editArrivalDate);
        EditText airlineField = (EditText) findViewById
                (R.id.editAirline);
        EditText originField = (EditText) findViewById
                (R.id.editOrigin);
        EditText destinationField = (EditText) findViewById
                (R.id.editDestination);
        EditText costField = (EditText) findViewById
                (R.id.editCost);
        EditText seatsField = (EditText) findViewById
                (R.id.editSeats);

        LinearLayout costSeat = (LinearLayout) findViewById
                (R.id.costSeatLayout);

        //Find Button for save/edit
        Button ButtonEditSave = (Button) findViewById
                (R.id.buttonFlightEditSave);

        //Open edit view
        if (departureDate.getVisibility() == View.VISIBLE) {
            transferToSave(departureDate, departureDateField);
            transferToSave(arrivalDate, arrivalDateField);
            transferToSave(airline, airlineField);
            transferToSave(origin, originField);
            transferToSave(destination, destinationField);
            transferToSave(cost, costField);
            transferToSave(seats, seatsField);

            costSeat.setVisibility(View.GONE);

            //Enable Save Button
            ButtonEditSave.setText("SAVE");
        }

        //Open save view
        else {
            transferToEdit(departureDate, departureDateField);
            transferToEdit(arrivalDate, arrivalDateField);
            transferToEdit(airline, airlineField);
            transferToEdit(origin, originField);
            transferToEdit(destination, destinationField);
            transferToEdit(cost, costField);
            transferToEdit(seats, seatsField);

            costSeat.setVisibility(View.VISIBLE);

            //Disable Save Button
            ButtonEditSave.setText("EDIT");

            String newDepartureDate = departureDateField.getText().toString();
            String newArrivalDate = arrivalDateField.getText().toString();
            String newAirline = airlineField.getText().toString();
            String newOrigin = originField.getText().toString();
            String newDestination = destinationField.getText().toString();
            String newCost = costField.getText().toString();
            String newSeats = seatsField.getText().toString();

            flight.setDepartureDateTime(newDepartureDate);
            flight.setArrivalDateTime(newArrivalDate);
            flight.setAirline(newAirline);
            flight.setOrigin(newOrigin);
            flight.setDestination(newDestination);
            flight.setCost(Double.parseDouble(newCost));
            flight.setNumSeats(Integer.parseInt(newSeats));

            MainActivity.application.getFlightManager().add(flight);

            setTextFields();
            saveFields();
        }
    }
}