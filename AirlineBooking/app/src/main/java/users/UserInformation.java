package users;

import java.io.Serializable;

/** A class that takes in and gives personal and billing information
 * about each user. 
 */
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 6805836940719084300L;

    /** the first name of this Client */
    private String firstName;

    /** the last name of this Client */
    private String lastName;

    /** this Client's email address */
    private String email;

    /** this Client's home address */
    private String address;

    /** this Client's card number to be used to make payments */
    private String cardNumber;

    /** the expire date of the associated card with which
     *  this Client plans to pay with, holding the full date
     *  Year/Month/Day as YYYY/MM/DD */
    private String expiryDate;

    /** Constructor that assigns to the variables within
     *  UserInformation to the class.
     *
     * @param firstName the first name of this Client
     * @param lastName the last name of this Client
     * @param email this Client's email address
     * @param address this Client's home address
     * @param cardNumber this Client's card number to be used to make payments
     * @param expiryDate the expire date of the associated card with which
     *  		         this Client plans to pay with, holding the full date
     *  				 Year/Month/Day as YYYY/MM/DD
     */
    public UserInformation(String firstName, String lastName, String email,
                           String address, String cardNumber, String expiryDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    /** Returns the first name of this Client.
     *
     * @return the first name of this Client as a String.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /** Returns the last name of this Client.
     *
     * @return the last name of this Client as a String.
     */
    public String getLastName() {
        return this.lastName;
    }

    /** Returns the email address of this Client.
     *
     * @return the email address of this Client as a String.
     */
    public String getEmail() {
        return this.email;
    }

    /** Returns the home address of this Client.
     *
     * @return the mailing address of this Client as a String.
     */
    public String getAddress() {
        return this.address;
    }

    /** Returns the card number associated to this Client.
     *
     * @return the card number of this Client as a String.
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /** Returns the expire date associated to payment card of this Client.
     *
     * @return the expire date of this Client as a String
     */
    public String getExpiryDate() {
        return this.expiryDate;
    }

    /** Sets the first name of this Client.
     *
     * @param firstName the first name of this Client.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** Sets  the last name of this Client.
     *
     * @param lastName the last name of this Client.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Sets the email address of this Client.
     *
     * @param email the email address of this Client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Sets the home address of this Client.
     *
     * @param address the mailing address of this Client.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Sets the card number associated to this Client.
     *
     * @param cardNumber the card number of this Client.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /** Sets the expiry date of the payment card for this Client.
     *
     * @param expiryDate the expiry date of the payment card for this Client.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /** Returns the String representation of this Client's personal and
     *  billing information.
     *
     * @return the String representation of a String containing all
     *  	   of this Client's information
     */
    @Override
    public String toString() {
        return this.lastName + "," + this.firstName + "," + this.email + ","
                + this.address + "," + this.cardNumber + "," +
                this.expiryDate;
    }
}
