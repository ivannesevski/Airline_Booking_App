package group_0733.AirlineBooking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import application.Application;
import group_0733.csc207project.R;
import managers.ClientManager;
import managers.FlightManager;
import users.User;

/** The login activity */
public class MainActivity extends AppCompatActivity {

    /** The name of the file where the passwords are stored */
    public static final String PASSWORDFILENAME = "passwords.txt";

    /** The name of the file where the Clients are stored */
    public static final String INITCLIENTFILENAME = "clients.txt";

    /** The name of the file where the Flights are stored */
    public static final String INITFLIGHTFILENAME = "flights.txt";

    /** The name of the file where the map of emails to Clients
     *  are stored
     */
    public static final String CLIENTSFILENAME = "clients.ser";

    /** The name of the file where the map of flight number to
     * Flights are stored
     */
    public static final String FLIGHTSFILENAME = "flights.ser";

    /** The name of the directory where the data is stored */
    public static final String DATADIR = "appdata";

    /** The user key for this MainActivity */
    public static final String USER_KEY = "userKey";

    /** The flight key for this MainActivity */
    public static final String FLIGHT_KEY = "flightKey";

    /** The Application used to run the backend */
    public static Application application;

    /** The Client file for this MainActivity */
    public static File clientsFile;

    /** The Flight file for this MainActivity */
    public static File flightsFile;

    /** The map of emails to passwords */
    private Map<String,String> passwords;

    /** The ClientManager for this MainActivity */
    private ClientManager clientManager;

    /** The FlightManger for this MainActivity */
    private FlightManager flightManager;

    /** Sets up MainActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File appdata = this.getApplicationContext().getDir(DATADIR,
                MODE_PRIVATE);
        File passwordsFile = new File(appdata, PASSWORDFILENAME);
        clientsFile = new File(appdata, CLIENTSFILENAME);
        flightsFile = new File(appdata, FLIGHTSFILENAME);
        File initClientsFile = new File(appdata, INITCLIENTFILENAME);
        File initFlightsFile = new File(appdata, INITFLIGHTFILENAME);

        // Creates managers for clients and flights
        try {
            this.clientManager = new ClientManager(clientsFile.getPath());
            this.flightManager = new FlightManager(flightsFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Uploads initial clients and flights from CSV files into manager
        if (this.clientManager.getClients().size() == 0) {
            try {
                this.clientManager.readFromCSVFile(initClientsFile.getPath());
                this.flightManager.readFromCSVFile(initFlightsFile.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // creates the application with managers for clients and flights
        application = new Application(clientManager, flightManager);

        this.passwords = new HashMap<>();

        try {
           this.uploadPasswords(passwordsFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Uploads the username and passwords from file filepath to fill in
     * the passwords map.
     *
     * @param filePath the path of the file to have usernames and passwords
     *                 uploaded from.
     * @throws IOException if there is an I/O error.
     */
    public void uploadPasswords(String filePath) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            this.passwords.put(record[0], record[1]);
        }
        scanner.close();
    }

    /** Waits for the user to enter their username and password and to press
     *  login. If the information matches a Client or Admin the Client
     *  Activity or Admin Activity.
     *
     * @param view the View of the activity.
     */
    public void login(View view) {
        EditText usernameField = (EditText) findViewById(R.id.username_field);
        String username = usernameField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.password_field);
        String password = passwordField.getText().toString();

        if (passwords.containsKey(username)) {
            if (passwords.get(username).equals(password)) {
                if (application.getClientManager().getClients()
                        .containsKey(username)) {
                    Intent intent = new Intent(this, ClientActivity.class);
                    User current_user = application.getClientManager()
                            .getClients().get(username);
                    intent.putExtra(USER_KEY, current_user);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, AdminActivity.class);
                    startActivity(intent);
                }
            }
        }

    }
}
