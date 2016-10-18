package group_0733.AirlineBooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import group_0733.csc207project.R;
import users.User;

/** The activity used by the User to edit their information */
public class EditInfoActivity extends AppCompatActivity {

    /** The current user */
    private User current_user;

    /** Sets up EditInfoActivity and sets the content view.
     *
     * @param savedInstanceState a Bundle object to allow the activity to be
     *                           recreated.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        Intent intent = getIntent();
        this.current_user = (User) intent.getSerializableExtra
                (MainActivity.USER_KEY);

        setTextFields();

        //Attach Listener's to buttons
        Button ButtonEditSave = (Button) findViewById
                (R.id.buttonClientEditSave);
        ButtonEditSave.setOnClickListener(onClickListener);
    }

    /** Writes the changes to file */
    public void saveFields() {
        // writes to file
        try {
            MainActivity.application.getClientManager().saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Your personal and billing information " +
                "has been saved");
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
                               EditText EditTextField){
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
                               EditText EditTextField){
        String currentString = EditTextField.getText().toString();
        EditTextField.setVisibility(View.GONE);

        //Now the TextView
        textViewField.setVisibility(View.VISIBLE);
        textViewField.setText(currentString);
    }

    /** Sets the text fields */
    public void setTextFields() {
        TextView firstName = (TextView) findViewById
                (R.id.textClientFirstName);
        TextView lastName = (TextView) findViewById
                (R.id.textClientLastName);
        TextView email = (TextView) findViewById
                (R.id.textClientEmail);
        TextView address = (TextView) findViewById
                (R.id.textClientAddress);
        TextView cardNumber = (TextView) findViewById
                (R.id.textClientCardNumber);
        TextView cardExpiryDate = (TextView) findViewById
                (R.id.textClientCardExpiryDate);

        firstName.setText(current_user.getUserInfo().getFirstName());
        lastName.setText(current_user.getUserInfo().getLastName());
        email.setText(current_user.getUserInfo().getEmail());
        address.setText(current_user.getUserInfo().getAddress());
        cardNumber.setText(current_user.getUserInfo().getCardNumber());
        cardExpiryDate.setText(current_user.getUserInfo().getExpiryDate());
    }

    /** Waits for the edit button to be pressed, then the fields become
     *  editable. When the save button is pressed the changes to the fields
     *  get saved to the file.
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.buttonClientEditSave:
                    //find View fields and Edit fields
                    TextView firstName = (TextView) findViewById
                            (R.id.textClientFirstName);
                    TextView lastName = (TextView) findViewById
                            (R.id.textClientLastName);
                    TextView address = (TextView) findViewById
                            (R.id.textClientAddress);
                    TextView cardNumber = (TextView) findViewById
                            (R.id.textClientCardNumber);
                    TextView cardExpiryDate = (TextView) findViewById
                            (R.id.textClientCardExpiryDate);

                    EditText firstNameField = (EditText) findViewById
                            (R.id.editClientFirstName);
                    EditText lastNameField = (EditText) findViewById
                            (R.id.editClientLastName);
                    EditText addressField = (EditText) findViewById
                            (R.id.editClientAddress);
                    EditText cardNumberField = (EditText) findViewById
                            (R.id.editClientCardNumber);
                    EditText cardExpiryDateField = (EditText) findViewById
                            (R.id.editClientCardExpiryDate);

                    //Find Button for save/edit
                    Button ButtonEditSave = (Button) findViewById
                            (R.id.buttonClientEditSave);

                    //Open edit view
                    if (firstName.getVisibility() == View.VISIBLE) {
                        transferToSave(firstName, firstNameField);
                        transferToSave(lastName, lastNameField);
                        transferToSave(address, addressField);
                        transferToSave(cardNumber, cardNumberField);
                        transferToSave(cardExpiryDate, cardExpiryDateField);

                        //Enable Save Button
                        ButtonEditSave.setText("SAVE");
                    }

                    //Open save view
                    else{
                        transferToEdit(firstName, firstNameField);
                        transferToEdit(lastName, lastNameField);
                        transferToEdit(address, addressField);
                        transferToEdit(cardNumber, cardNumberField);
                        transferToEdit(cardExpiryDate, cardExpiryDateField);

                        //Disable Save Button
                        ButtonEditSave.setText("EDIT");

                        String newFirstName = firstNameField.getText()
                                .toString();
                        String newLastName = lastNameField.getText()
                                .toString();
                        String newAddress = addressField.getText()
                                .toString();
                        String newCardNumber = cardNumberField
                                .getText().toString();
                        String newCardExpiryDate = cardExpiryDateField
                                .getText().toString();

                        current_user.getUserInfo().setFirstName
                                (newFirstName);
                        current_user.getUserInfo().setLastName
                                (newLastName);
                        current_user.getUserInfo().setAddress
                                (newAddress);
                        current_user.getUserInfo().setCardNumber
                                (newCardNumber);
                        current_user.getUserInfo().setExpiryDate
                                (newCardExpiryDate);

                        MainActivity.application.getClientManager()
                                .add(current_user);

                        setTextFields();
                        saveFields();

                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.USER_KEY, current_user);
                        setResult(RESULT_OK, intent);
                    }
                    break;

            }

        }
    };
}
