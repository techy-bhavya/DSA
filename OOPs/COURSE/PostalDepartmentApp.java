import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* =========================================================
   CUSTOM EXCEPTIONS
   ========================================================= */

class InvalidPostDataException extends Exception {

    public InvalidPostDataException(String message) {
        super(message);
    }
}

class DuplicatePostException extends Exception {

    public DuplicatePostException(String message) {
        super(message);
    }
}

class PostNotFoundException extends Exception {

    public PostNotFoundException(String message) {
        super(message);
    }
}


/* =========================================================
   ADDRESS CLASS
   Stores the complete address of sender or receiver
   ========================================================= */

class Address {

    private final String personName;
    private final String houseNumber;
    private final String city;
    private final String state;
    private final String pinCode;

    public Address(
            String personName,
            String houseNumber,
            String city,
            String state,
            String pinCode
    ) throws InvalidPostDataException {

        if (isEmpty(personName)) {
            throw new InvalidPostDataException(
                    "Person name cannot be empty."
            );
        }

        if (isEmpty(houseNumber)) {
            throw new InvalidPostDataException(
                    "House number cannot be empty."
            );
        }

        if (isEmpty(city)) {
            throw new InvalidPostDataException(
                    "City cannot be empty."
            );
        }

        if (isEmpty(state)) {
            throw new InvalidPostDataException(
                    "State cannot be empty."
            );
        }

        if (pinCode == null || !pinCode.matches("\\d{6}")) {
            throw new InvalidPostDataException(
                    "PIN code must contain exactly 6 digits."
            );
        }

        this.personName = personName.trim();
        this.houseNumber = houseNumber.trim();
        this.city = city.trim();
        this.state = state.trim();
        this.pinCode = pinCode.trim();
    }

    private static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String getPersonName() {
        return personName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public String toString() {
        return personName
                + ", House No. " + houseNumber
                + ", " + city
                + ", " + state
                + " - " + pinCode;
    }
}


/* =========================================================
   POST CLASS
   Represents one postal record
   ========================================================= */

class Post {

    private final int postId;
    private final Address senderAddress;
    private final Address receiverAddress;

    public Post(
            int postId,
            Address senderAddress,
            Address receiverAddress
    ) throws InvalidPostDataException {

        if (postId <= 0) {
            throw new InvalidPostDataException(
                    "Post ID must be greater than zero."
            );
        }

        if (senderAddress == null) {
            throw new InvalidPostDataException(
                    "Sender address cannot be null."
            );
        }

        if (receiverAddress == null) {
            throw new InvalidPostDataException(
                    "Receiver address cannot be null."
            );
        }

        this.postId = postId;
        this.senderAddress = senderAddress;
        this.receiverAddress = receiverAddress;
    }

    public int getPostId() {
        return postId;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    public void display() {
        System.out.println("\n---------------------------------------------");
        System.out.println("Post ID          : " + postId);
        System.out.println("Sender Address   : " + senderAddress);
        System.out.println("Receiver Address : " + receiverAddress);
        System.out.println("---------------------------------------------");
    }
}


/* =========================================================
   REPOSITORY INTERFACE
   Demonstrates abstraction
   ========================================================= */

interface PostRepository {

    void addPost(Post post)
            throws SQLException, DuplicatePostException;

    List<Post> findBySenderName(String senderName)
            throws SQLException, InvalidPostDataException;

    List<Post> findByReceiverName(String receiverName)
            throws SQLException, InvalidPostDataException;

    List<Post> findAll()
            throws SQLException, InvalidPostDataException;
}


/* =========================================================
   JDBC REPOSITORY
   Performs database operations
   ========================================================= */

class JdbcPostRepository implements PostRepository {

    /*
     * Change the username and password according to your
     * MySQL configuration.
     */
    private static final String URL =
            "jdbc:mysql://localhost:3306/postal_db"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Asia/Kolkata";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_mysql_password";

    public JdbcPostRepository()
            throws ClassNotFoundException, SQLException {

        loadMySQLDriver();
        createPostTable();
    }

    private void loadMySQLDriver()
            throws ClassNotFoundException {

        try {
            // For MySQL Connector/J 8 or newer
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            // For older MySQL Connector versions
            Class.forName("com.mysql.jdbc.Driver");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }

    private void createPostTable() throws SQLException {

        String sql =
                "CREATE TABLE IF NOT EXISTS posts ("
                + "post_id INT PRIMARY KEY,"

                + "sender_name VARCHAR(100) NOT NULL,"
                + "sender_house_no VARCHAR(50) NOT NULL,"
                + "sender_city VARCHAR(100) NOT NULL,"
                + "sender_state VARCHAR(100) NOT NULL,"
                + "sender_pin CHAR(6) NOT NULL,"

                + "receiver_name VARCHAR(100) NOT NULL,"
                + "receiver_house_no VARCHAR(50) NOT NULL,"
                + "receiver_city VARCHAR(100) NOT NULL,"
                + "receiver_state VARCHAR(100) NOT NULL,"
                + "receiver_pin CHAR(6) NOT NULL"
                + ")";

        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void addPost(Post post)
            throws SQLException, DuplicatePostException {

        String sql =
                "INSERT INTO posts ("
                + "post_id, "
                + "sender_name, sender_house_no, sender_city, "
                + "sender_state, sender_pin, "
                + "receiver_name, receiver_house_no, receiver_city, "
                + "receiver_state, receiver_pin"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            Address sender = post.getSenderAddress();
            Address receiver = post.getReceiverAddress();

            statement.setInt(1, post.getPostId());

            statement.setString(2, sender.getPersonName());
            statement.setString(3, sender.getHouseNumber());
            statement.setString(4, sender.getCity());
            statement.setString(5, sender.getState());
            statement.setString(6, sender.getPinCode());

            statement.setString(7, receiver.getPersonName());
            statement.setString(8, receiver.getHouseNumber());
            statement.setString(9, receiver.getCity());
            statement.setString(10, receiver.getState());
            statement.setString(11, receiver.getPinCode());

            statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException exception) {
            throw new DuplicatePostException(
                    "A post with Post ID "
                    + post.getPostId()
                    + " already exists."
            );
        }
    }

    @Override
    public List<Post> findBySenderName(String senderName)
            throws SQLException, InvalidPostDataException {

        String sql =
                "SELECT * FROM posts "
                + "WHERE LOWER(sender_name) LIKE LOWER(?) "
                + "ORDER BY post_id";

        return searchByName(sql, senderName);
    }

    @Override
    public List<Post> findByReceiverName(String receiverName)
            throws SQLException, InvalidPostDataException {

        String sql =
                "SELECT * FROM posts "
                + "WHERE LOWER(receiver_name) LIKE LOWER(?) "
                + "ORDER BY post_id";

        return searchByName(sql, receiverName);
    }

    private List<Post> searchByName(
            String sql,
            String personName
    ) throws SQLException, InvalidPostDataException {

        List<Post> posts = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    "%" + personName.trim() + "%"
            );

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    posts.add(createPostFromResultSet(resultSet));
                }
            }
        }

        return posts;
    }

    @Override
    public List<Post> findAll()
            throws SQLException, InvalidPostDataException {

        List<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM posts ORDER BY post_id";

        try (
                Connection connection = getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                posts.add(createPostFromResultSet(resultSet));
            }
        }

        return posts;
    }

    private Post createPostFromResultSet(ResultSet resultSet)
            throws SQLException, InvalidPostDataException {

        Address senderAddress = new Address(
                resultSet.getString("sender_name"),
                resultSet.getString("sender_house_no"),
                resultSet.getString("sender_city"),
                resultSet.getString("sender_state"),
                resultSet.getString("sender_pin")
        );

        Address receiverAddress = new Address(
                resultSet.getString("receiver_name"),
                resultSet.getString("receiver_house_no"),
                resultSet.getString("receiver_city"),
                resultSet.getString("receiver_state"),
                resultSet.getString("receiver_pin")
        );

        return new Post(
                resultSet.getInt("post_id"),
                senderAddress,
                receiverAddress
        );
    }
}


/* =========================================================
   SERVICE CLASS
   Contains the main business logic
   ========================================================= */

class PostalService {

    private final PostRepository repository;

    public PostalService(PostRepository repository) {
        this.repository = repository;
    }

    public void addPost(Post post)
            throws SQLException, DuplicatePostException {

        repository.addPost(post);
    }

    public List<Post> searchBySenderName(String senderName)
            throws SQLException,
            InvalidPostDataException,
            PostNotFoundException {

        validateSearchName(senderName);

        List<Post> posts =
                repository.findBySenderName(senderName);

        if (posts.isEmpty()) {
            throw new PostNotFoundException(
                    "No post was found for sender: "
                    + senderName
            );
        }

        return posts;
    }

    public List<Post> searchByReceiverName(String receiverName)
            throws SQLException,
            InvalidPostDataException,
            PostNotFoundException {

        validateSearchName(receiverName);

        List<Post> posts =
                repository.findByReceiverName(receiverName);

        if (posts.isEmpty()) {
            throw new PostNotFoundException(
                    "No post was found for receiver: "
                    + receiverName
            );
        }

        return posts;
    }

    public List<Post> getAllPosts()
            throws SQLException, InvalidPostDataException {

        return repository.findAll();
    }

    private void validateSearchName(String name)
            throws InvalidPostDataException {

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPostDataException(
                    "Search name cannot be empty."
            );
        }
    }
}


/* =========================================================
   MAIN APPLICATION CLASS
   ========================================================= */

public class PostalDepartmentApp {

    public static void main(String[] args) {

        try (
                Scanner scanner = new Scanner(System.in)
        ) {

            PostRepository repository =
                    new JdbcPostRepository();

            PostalService postalService =
                    new PostalService(repository);

            runApplication(scanner, postalService);

        } catch (ClassNotFoundException exception) {

            System.out.println(
                    "MySQL JDBC Driver was not found."
            );

            System.out.println(
                    "Please add the MySQL Connector/J JAR file "
                    + "to your project."
            );

        } catch (SQLException exception) {

            System.out.println(
                    "Unable to connect to the database."
            );

            System.out.println(
                    "Database error: " + exception.getMessage()
            );
        }
    }

    private static void runApplication(
            Scanner scanner,
            PostalService postalService
    ) {

        while (true) {

            displayMenu();

            int choice = readInteger(
                    scanner,
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1:
                        addPost(scanner, postalService);
                        break;

                    case 2:
                        searchBySender(scanner, postalService);
                        break;

                    case 3:
                        searchByReceiver(scanner, postalService);
                        break;

                    case 4:
                        displayAllPosts(postalService);
                        break;

                    case 5:
                        System.out.println(
                                "Postal Department application closed."
                        );
                        return;

                    default:
                        System.out.println(
                                "Invalid choice. Enter a number from 1 to 5."
                        );
                }

            } catch (InvalidPostDataException exception) {

                System.out.println(
                        "Invalid data: " + exception.getMessage()
                );

            } catch (DuplicatePostException exception) {

                System.out.println(
                        "Duplicate record: " + exception.getMessage()
                );

            } catch (PostNotFoundException exception) {

                System.out.println(exception.getMessage());

            } catch (SQLException exception) {

                System.out.println(
                        "Database operation failed: "
                        + exception.getMessage()
                );
            }
        }
    }

    private static void displayMenu() {

        System.out.println("\n========== POSTAL DEPARTMENT ==========");
        System.out.println("1. Add a new post record");
        System.out.println("2. Search by sender name");
        System.out.println("3. Search by receiver name");
        System.out.println("4. Display all post records");
        System.out.println("5. Exit");
        System.out.println("=======================================");
    }

    private static void addPost(
            Scanner scanner,
            PostalService postalService
    ) throws InvalidPostDataException,
            SQLException,
            DuplicatePostException {

        System.out.println("\n--- Enter Post Information ---");

        int postId = readPositiveInteger(
                scanner,
                "Enter Post ID: "
        );

        System.out.println("\n--- Sender Address ---");
        Address senderAddress =
                readAddress(scanner, "Sender");

        System.out.println("\n--- Receiver Address ---");
        Address receiverAddress =
                readAddress(scanner, "Receiver");

        Post post = new Post(
                postId,
                senderAddress,
                receiverAddress
        );

        postalService.addPost(post);

        System.out.println(
                "\nPost record added successfully."
        );
    }

    private static Address readAddress(
            Scanner scanner,
            String personType
    ) throws InvalidPostDataException {

        String name = readNonEmptyString(
                scanner,
                "Enter " + personType + " Name: "
        );

        String houseNumber = readNonEmptyString(
                scanner,
                "Enter House Number: "
        );

        String city = readNonEmptyString(
                scanner,
                "Enter City: "
        );

        String state = readNonEmptyString(
                scanner,
                "Enter State: "
        );

        String pinCode = readPinCode(
                scanner,
                "Enter 6-digit PIN Code: "
        );

        return new Address(
                name,
                houseNumber,
                city,
                state,
                pinCode
        );
    }

    private static void searchBySender(
            Scanner scanner,
            PostalService postalService
    ) throws SQLException,
            InvalidPostDataException,
            PostNotFoundException {

        String senderName = readNonEmptyString(
                scanner,
                "Enter sender name to search: "
        );

        List<Post> posts =
                postalService.searchBySenderName(senderName);

        System.out.println(
                "\nPosts sent by \"" + senderName + "\":"
        );

        displayPostList(posts);
    }

    private static void searchByReceiver(
            Scanner scanner,
            PostalService postalService
    ) throws SQLException,
            InvalidPostDataException,
            PostNotFoundException {

        String receiverName = readNonEmptyString(
                scanner,
                "Enter receiver name to search: "
        );

        List<Post> posts =
                postalService.searchByReceiverName(receiverName);

        System.out.println(
                "\nPosts received by \"" + receiverName + "\":"
        );

        displayPostList(posts);
    }

    private static void displayAllPosts(
            PostalService postalService
    ) throws SQLException, InvalidPostDataException {

        List<Post> posts = postalService.getAllPosts();

        if (posts.isEmpty()) {
            System.out.println(
                    "No post records are available."
            );
            return;
        }

        System.out.println("\nAll Post Records:");

        displayPostList(posts);
    }

    private static void displayPostList(List<Post> posts) {

        for (Post post : posts) {
            post.display();
        }

        System.out.println(
                "Total records found: " + posts.size()
        );
    }

    private static int readInteger(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    private static int readPositiveInteger(
            Scanner scanner,
            String message
    ) {

        while (true) {

            int number = readInteger(scanner, message);

            if (number > 0) {
                return number;
            }

            System.out.println(
                    "The number must be greater than zero."
            );
        }
    }

    private static String readNonEmptyString(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "This field cannot be empty."
            );
        }
    }

    private static String readPinCode(
            Scanner scanner,
            String message
    ) {

        while (true) {

            System.out.print(message);
            String pinCode = scanner.nextLine().trim();

            if (pinCode.matches("\\d{6}")) {
                return pinCode;
            }

            System.out.println(
                    "PIN code must contain exactly 6 digits."
            );
        }
    }
}