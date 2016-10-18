package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import group_0733.csc207project.R;

/** The activity used by admins */
public class AdminActivity extends AppCompatActivity {

    /** Sets up AdminActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    /** Starts the EnterClientName activity.
     *
     * @param view the View of the Activity.
     */
    public void enterClientName(View view) {
        Intent intent = new Intent(this, EnterClientNameActivity.class);

        startActivity(intent);
    }

    /** Starts the EnterFlightNumber activity.
     *
     * @param view the View of the Activity.
     */
    public void enterFlightNumber(View view) {
        Intent intent = new Intent(this, EnterFlightNumberActivity.class);

        startActivity(intent);
    }

    /** Starts the EnterFileName activity.
     *
     * @param view the View of the Activity.
     */
    public void enterFileName(View view) {
        Intent intent = new Intent(this, UploadDataActivity.class);

        startActivity(intent);
    }

    /** Starts the SearchFlightsName activity.
     *
     * @param view the View of the Activity.
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, SearchFlightsActivity.class);

        startActivity(intent);
    }
}
