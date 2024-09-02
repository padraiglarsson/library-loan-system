// Define a class named Users
public class Users {
    // Define instance variables
    public String UserID; // User ID
    public String FirstName; // First name of the user
    public String LastName; // Last name of the user
    public String Email; // Email address of the user

    // Constructor to initialize the instance variables
    public Users(String UserID, String FirstName, String LastName, String Email){
        this.UserID = UserID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
    }

    // Getter method to retrieve the user ID
    public String getUserID() {
        return UserID;
    }

    // Override the toString() method to provide a string representation of Users
    @Override
    public String toString() {
        return "Users{" +
                "UserID ='" + UserID + '\'' + // User ID
                "First Name ='" + FirstName + '\'' + // First name
                "Last Name ='" + LastName + '\'' + // Last name
                "Email ='" + Email + '\'' + // Email address
                '}';
    }
} // End of class Users

