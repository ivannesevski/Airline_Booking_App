package group_0733.AirlineBooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import group_0733.csc207project.R;

public class SearchFlightsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
    }

    public void searchFlight(View view) {
        EditText departureDateField = (EditText) findViewById(R.id.editText);
        EditText travelOriginField = (EditText) findViewById(R.id.editText2);
        EditText destinationField = (EditText) findViewById(R.id.editText3);
        String departureDate = departureDateField.getText().toString();
        String travelOrigin = travelOriginField.getText().toString();
        String destination = destinationField.getText().toString();

        boolean success = false;

        if (MainActivity.application.searchFlightsList(departureDate,
                travelOrigin, destination).size() > 0){
            success = true;
        }
        if (success) {
            Intent intent = new Intent(this, SelectFlightActivity.class);
            intent.putExtra("departureDate", departureDate);
            intent.putExtra("travelOrigin", travelOrigin);
            intent.putExtra("destination", destination);
            startActivity(intent);
        }
        else {
            Toast.makeText(SearchFlightsActivity.this,
                    "No such flights exist" ,
                    Toast.LENGTH_SHORT).show();
        }
    }
}