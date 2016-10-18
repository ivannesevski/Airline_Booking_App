package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import group_0733.csc207project.R;
import users.User;

/** The activity used by clients */
public class ClientActivity extends AppCompatActivity {

    /** the current user */
    private User current_user;

    /** Sets up ClientActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Intent intent = getIntent();

        this.current_user = (User) intent.getSerializableExtra
                (MainActivity.USER_KEY);
    }

    /** Starts the EditInfoActivity.
     *
     * @param view the View of the activity.
     */
    public void editInfo(View view) {
        Intent intent = new Intent(this, EditInfoActivity.class);
        intent.putExtra(MainActivity.USER_KEY, this.current_user);

        startActivityForResult(intent, 1);
    }

    /** Starts the BookItineraryActivity.
     *
     * @param view the View of the activity.
     */
    public void bookItinerary(View view) {
        Intent intent = new Intent(this, BookItineraryActivity.class);
        intent.putExtra(MainActivity.USER_KEY, this.current_user);

        startActivityForResult(intent, 1);
    }

    /** Starts the SearchFlightsActivity.
     *
     * @param view the View of the activity.
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, SearchFlightsActivity.class);

        startActivity(intent);
    }

    /** Starts the ViewBookedItinerary activity.
     *
     * @param view the view of the activity.
     */
    public void viewBooked(View view) {
        Intent intent = new Intent(this, ViewBookedItinerariesActivity.class);
        intent.putExtra(MainActivity.USER_KEY, this.current_user);

        startActivity(intent);
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
    }

}
