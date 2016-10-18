package users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import flights.Itinerary;

/** A class used to store the users as objects. */
public class User implements Serializable{

    private static final long serialVersionUID = -3481851447399276227L;

    /** Stores an object containing personal, billing, and
     *  information that pertains to the user(s) of the application. */
    private UserInformation userInfo;
    /** The itineraries that this User has booked */
    private List<Itinerary> bookedItineraries;

    /**
     * Stores the object information of the user under the class User.
     *
     * @param info the user information as an object.
     */
    public User(UserInformation info) {
        this.userInfo = info;
        this.bookedItineraries = new ArrayList<>();
    }

    /** Returns the user information associated to this Client.
     *
     * @return the user information of this Client.
     */
    public UserInformation getUserInfo() {
        return this.userInfo;
    }

    /** Returns a String representation of this User.
     *
     * @return a Sting representation of this User.
     */
    @Override
    public String toString() {
        return this.userInfo.toString();
    }
    
    /** Books an itinerary for this User.
     * 
     * @param itinerary the itinerary to be booked.
     */
    public void bookItinerary(Itinerary itinerary) {
    	this.bookedItineraries.add(itinerary);
    }
    
    /** Returns this Users booked itineraries.
     * 
     * @return this Users booked itineraries.
     */
    public List<Itinerary> getBookedItineraries() {
    	return this.bookedItineraries;
    }

}
