package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import group_0733.csc207project.R;
import users.User;

/** The activity used to select the Client to edit. */
public class EnterClientNameActivity extends AppCompatActivity {

    /** Sets up EnterClientNameActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_client_name);
    }

    /** Starts the EditInfo activity.
     *
     * @param view the View of the activity.
     */
    public void goToEdit(View view) {
        EditText usernameField = (EditText) findViewById(R.id
                .editClientUsername);
        String username = usernameField.getText().toString();

        if (MainActivity.application.getClientManager()
                .getClients().containsKey(username)) {
            User user = MainActivity.application.getClientManager()
                    .getClients().get(username);

            Intent intent = new Intent(this, EditInfoActivity.class);

            intent.putExtra(MainActivity.USER_KEY, user);

            startActivity(intent);
        }
    }

    /** Starts the BookItinerary activity.
     *
     * @param view the View of the activity.
     */
    public void goToBooking(View view) {
        EditText usernameField = (EditText) findViewById
                (R.id.editClientUsername);
        String username = usernameField.getText().toString();

        if (MainActivity.application.getClientManager().getClients()
                .containsKey(username)) {
            User user = MainActivity.application.getClientManager()
                    .getClients().get(username);

            Intent intent = new Intent(this, BookItineraryActivity.class);

            intent.putExtra(MainActivity.USER_KEY, user);

            startActivity(intent);
        }

    }

    /** Starts the ViewBookedItinerary activity.
     *
     * @param view the View of the activity.
     */
    public void viewBooked(View view) {
        EditText usernameField = (EditText) findViewById
                (R.id.editClientUsername);
        String username = usernameField.getText().toString();

        if (MainActivity.application.getClientManager().getClients()
                .containsKey(username)) {
            User user = MainActivity.application.getClientManager()
                    .getClients().get(username);

            Intent intent = new Intent(this,
                    ViewBookedItinerariesActivity.class);

            intent.putExtra(MainActivity.USER_KEY, user);

            startActivity(intent);
        }
    }

}
