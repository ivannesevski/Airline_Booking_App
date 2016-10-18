package group_0733.AirlineBooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;

import group_0733.csc207project.R;
import users.User;

/** The activity used to book Itineraries */
public class BookItineraryActivity extends AppCompatActivity {

    /** The current user */
    private User current_user;

    /** Sets up BookItineraryActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_itinerary);

        Intent intent = getIntent();

        this.current_user = (User) intent.getSerializableExtra
                (MainActivity.USER_KEY);

    }

    /** Starts the SelectItineraryActivity if there are Itineraries that
     *  match the selected destination, origin, and depart date else a
     *  message appears declaring that there are no such Itineraries.
     *
     * @param view the View of the activity.
     */
    public void proceedToSelection(View view) {
        boolean success = false;

        EditText destinationCity = (EditText) findViewById
                (R.id.destinationCity);
        EditText originCity = (EditText) findViewById
                (R.id.originCity);
        EditText departYear = (EditText) findViewById
                (R.id.departYear);
        EditText departMonth = (EditText) findViewById
                (R.id.departMonth);
        EditText departDay = (EditText) findViewById
                (R.id.departDay);

        String departureDate = departYear.getText().toString()
                + "-" + departMonth.getText().toString() + "-"
                + departDay.getText().toString();
        String origin = originCity.getText().toString();
        String destination = destinationCity.getText().toString();

        try {
            MainActivity.application.searchItineraries(departureDate,
                    origin, destination);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (MainActivity.application.getSearchedItineraries().size() > 0) {
            success = true;
        }
        if (success) {
            Intent intent = new Intent(this, SelectItineraryActivity.class);
            intent.putExtra(MainActivity.USER_KEY, this.current_user);
            startActivityForResult(intent, 1);
        }
        else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("We can't find a single Itinerary that" +
                    " matches this description!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }

    /** Gets a result from the activity.
     *
     * @param requestCode the request code.
     * @param resultCode the result code.
     * @param data the Intent.
     */
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.current_user = (User) data.getSerializableExtra
                        (MainActivity.USER_KEY);
            }
        }

        Intent intent = new Intent();
        intent.putExtra(MainActivity.USER_KEY, current_user);
        setResult(RESULT_OK, intent);
    }


}