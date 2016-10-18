package managers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import users.User;
import users.UserInformation;

/** Manages the saving and loading of Users. */
public class ClientManager implements Serializable {

    /** The serial version UID of this ClientManager */
    private static final long serialVersionUID = 8064503742047642309L;

    /** The logger of this ClientManager */
    private static final Logger logger =
            Logger.getLogger(ClientManager.class.getName());

    /** The console handler of this ClientManager */
    private static final Handler consoleHandler = new ConsoleHandler();

    /** A mapping of emails to Users */
    private Map<String, User> clients;

    /** The filePath of this ClientManager */
    private String filePath;

    /** Creates a new empty Client manager with given file path filePath.
     *
     * @param filePath the file path of this ClientManager.
     * @throws ClassNotFoundException if class is not valid.
     * @throws IOException if there is an I/O error.
     */
    public ClientManager(String filePath) throws ClassNotFoundException, IOException {
        this.clients = new HashMap<>();
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }

        this.filePath = file.getPath();
    }

    public ClientManager() {
        this.clients = new HashMap<>();
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        this.filePath = "";
    }

    /** Returns the map of emails to Users.
     *
     * @return the map of emails to Users.
     */
    public Map<String, User> getClients() {
        return this.clients;
    }

    /** Uploads the user data in path filePath and adds all users found in
     * the file to clients. The email is the key which is mapped to each
     * User Object.
     *
     * @param filePath the path where the to be uploaded data is.
     * @throws FileNotFoundException in-case the path does not exist.
     */
    public void readFromCSVFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(filePath));
        String[] record;
        UserInformation userInfo;
        User user;

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            userInfo = new UserInformation(record[1], record[0], record[2],
                    record[3], record[4], record[5]);
            user = new User(userInfo);
            this.clients.put(userInfo.getEmail(), user);
        }
        scanner.close();

        try {
            saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Reads the a map of emails to Users from path.
     *
     * @param path the file to be read from.
     * @throws ClassNotFoundException if the class is not found.
     */
    @SuppressWarnings("unchecked")
    private void readFromFile(String path) throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            this.clients = (Map<String,User>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Input cannot be read", ex);
        }
    }

    /** Adds a User to this ClientManager.
     *
     * @param user the User to be added to this ClientManager.
     */
    public void add(User user) {
        this.clients.put(user.getUserInfo().getEmail(), user);
        logger.log(Level.FINE, "Added a new client: " + user.toString());
    }

    /** Writes the Users to file at filePath.
     *
     * @throws IOException if there is an I/O error.
     */
    public void saveToFile() throws IOException {
        if (!filePath.equals("")) {
            OutputStream file = new FileOutputStream(this.filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(this.clients);
            output.close();
        }
    }

    /** Returns the String representation of this ClientManager.
     *
     * @return the String representation of this ClientManager.
     */
    @Override
    public String toString() {
        String result = "";
        for (User user : this.clients.values()) {
            result += user.toString() + "\n";
        }
        return result;
    }
}
