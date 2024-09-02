import java.util.*;

// Define a class named Loans
public class Loans {
    // Define instance variables
    public String Barcode; // Barcode of the item
    public String UserID; // ID of the user who loaned the item
    public Date IssueDate; // Date the item was issued
    public Date DueDate; // Date the item is due for return
    public int NumRenews; // Number of times the item has been renewed

    // Constructor to initialize the instance variables
    public Loans (String Barcode, String UserID, Date IssueDate,
                  Date DueDate, int NumRenews) {
        this.Barcode = Barcode;
        this.UserID = UserID;
        this.IssueDate = IssueDate;
        this.DueDate = DueDate;
        this.NumRenews = NumRenews;
    }

    //Method to get barcode in Loans
    public String getBarcode(){
        return Barcode;
    }
    //Method to get UserID in Loans
    public String getUserID(){
        return UserID;
    }
    public int getNumRenews(){
        return NumRenews;
    }

    // Method to print loan details
    public void PrintLoan(){
        System.out.println("Item Barcode is: " + Barcode);
        System.out.println("ID of user who loaned item: " + UserID);
        System.out.println("Issue date of the item is: " + IssueDate);
        System.out.println("Item return date: " + DueDate);
        System.out.println("Item has been renewed " + NumRenews + " time/s");
    }

} // End of class Loans

