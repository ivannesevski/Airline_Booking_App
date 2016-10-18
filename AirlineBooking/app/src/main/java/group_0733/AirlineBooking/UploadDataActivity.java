package group_0733.AirlineBooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import group_0733.csc207project.R;

/** The activity used to upload data */
public class UploadDataActivity extends AppCompatActivity {

    /** The file used to store the data */
    private File appdata;

    /** Sets up UploadDataActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);
        this.appdata = this.getApplicationContext()
                .getDir(MainActivity.DATADIR, MODE_PRIVATE);
    }

    /** Shows a message on screen with msg.
     *
     * @param msg the message to be shown on screen.
     */
    public void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /** Uploads Clients from a CSV file.
     *
     * @param view the view of the activity
     */
    public void uploadClients(View view) {
        EditText fileName = (EditText) findViewById(R.id.editFileName);
        String clientsFileName = fileName.getText().toString();

        File clientsFile = new File(this.appdata, clientsFileName);

        if (clientsFile.exists()) {
            try {
                MainActivity.application.getClientManager()
                        .readFromCSVFile(clientsFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            showToast("The clients have been uploaded into the app");
        }
        else{
            showToast("That file does not exist!");
        }


    }

    /** Uploads Flights from a CSV file.
     *
     * @param view the view of the activity
     */
    public void uploadFlights(View view) {
        EditText fileName = (EditText) findViewById(R.id.editFileName);
        String flightsFileName = fileName.getText().toString();

        File flightsFile = new File(this.appdata, flightsFileName);

        if (flightsFile.exists()) {
            MainActivity.application.uploadFlights(flightsFile.getPath());
            showToast("The flights have been uploaded into the app");
        }
        else {
            showToast("That file does not exist!");
        }

    }
}
