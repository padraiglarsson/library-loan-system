import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.List;

// Defining class named LibraryLoans
public class LibraryLoans{
    // Scanner object for user input
    private static Scanner scanner = new Scanner(System.in);

    // Method for the library loan system
    //////////////////////////////////
    // METHOD FOR start
    /////////////////////////////////
    public void start() {
        boolean exit = false;
        while (!exit) {
            // Display menu options
            System.out.println("1. Create a loan");
            System.out.println("2. View loans");
            System.out.println("3. Renew loans");
            System.out.println("4. Search loans");
            System.out.println("5. Return loan");
            System.out.println("6. Loan report");
            System.out.println("7. Exit program");
            System.out.println("Enter your choice: ");

            // Check if input is an integer
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                // user inputs number they wish to choose
                if (choice == 1) {
                    createLoan();
                } else if (choice == 2) {
                    viewLoans();
                } else if (choice == 3) {
                    renewLoan();
                } else if (choice == 4) {
                    searchLoan();
                } else if (choice == 5) {
                    returnLoan();
                }
                 else if (choice == 6) {
                    loanReport();
                } else if (choice == 7) {
                    // Exit the program
                    exit = true;
                    System.out.println("Thank you. Goodbye");
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        scanner.close();
    }
    //////////////////////////////////
    // METHOD FOR createLoan
    /////////////////////////////////
    public static void createLoan() {
        // printing message to show method is beginning
        System.out.println("Create loan, loading...");

        // Retrieving items, users, and loans data from files
        Items[] itemsArray = fileItems();
        Users[] userArray = fileUsers();
        String[] loanArray = fileLoans();

        // having user to the barcode of the item they wish to loan
        Scanner scanner = new Scanner(System.in);
        // Checking if the entered barcode exists
        boolean barcodefound = false;
        // validation so that the user inputs the correct barcode
        while(!barcodefound) {
            System.out.println("Enter Barcode of item you wish to Loan: ");
            String barcodeInput = scanner.nextLine();



            for (Items item : itemsArray) {
                if (item.getBarcode().equals(barcodeInput)) {
                    barcodefound = true;

                    // If barcode is found, have userenter their UserID
                    System.out.println("Item barcode found!");
                    boolean userfound = false;
                    while (!userfound) {
                        System.out.println("Enter the UserID of the person loaning the item: ");
                        String userInput = scanner.nextLine();
                        userInput = userInput.toUpperCase();

                        // Checking if the UserID exists

                        for (Users user : userArray) {
                            if (user.getUserID().equals(userInput)) {
                                userfound = true;
                                break;
                            }
                        }

                        // If the user is found, proceed with loan creation
                        if (userfound) {
                            System.out.println("UserID is found");
                            Calendar calendar = Calendar.getInstance();

                            // Calculating the due date depending on item
                            if (userfound) {
                                int loanDurationInWeeks = item.getType().equalsIgnoreCase("book") ? 4 : 1;
                                calendar.add(Calendar.WEEK_OF_YEAR, loanDurationInWeeks);
                            } else {
                                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                            }

                            // Initializing loan details
                            // setting num renews to 0 as it is their first time making loan
                            int NumRenews = 0;
                            Date IssueDate = new Date();
                            Date DueDate = calendar.getTime();

                            // Creating a new loan object
                            // showing user details of loan they just made
                            Loans loan = new Loans(barcodeInput, userInput, IssueDate, DueDate, NumRenews);
                            System.out.println("Loan created details: ");
                            System.out.println("Barcdoe: " + loan.getBarcode());
                            System.out.println("UserID: " + loan.getUserID());
                            System.out.println("Issue Date: " + IssueDate);
                            System.out.println("Due Date: " + DueDate);
                            System.out.println("Number of Renews: " + loan.getNumRenews());



                            // Writing loan information to a CSV file
                            try (PrintWriter writer = new PrintWriter(new FileWriter("src\\LOAN.csv", true))) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String issueDateStr = dateFormat.format(IssueDate);
                                String dueDateStr = dateFormat.format(DueDate);
                                writer.println(loan.Barcode + "," + loan.UserID + "," + issueDateStr +
                                        "," + dueDateStr + "," + loan.NumRenews);
                                System.out.println("Loan information written to LOAN.csv");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        // if user doesnt exist will ask user to try again
                        if (!userfound) {
                            System.out.println("UserID does not exist");
                            System.out.println("Try again.");
                         }

                    }
                }
            }
            // If the barcode does not exist, try again
            if (!barcodefound) {
                System.out.println("Barcode does not exist");
                System.out.println("Try again");
            }
        }// If the barcode does not exist, try again
    }
    //////////////////////////////////
    // METHOD FOR viewloans
    /////////////////////////////////
    private static void viewLoans(){
        // printing message to show method is beginning
        System.out.println("Viewing the loan, loading...");
// Retrieving loan data from file
        String[] loanArray = fileLoans();
// printing out all the information from the csv file
        for (String loanInfo : loanArray) {
            System.out.println(loanInfo);
        }

    }
    //////////////////////////////////
    // METHOD FOR renewLoan
    /////////////////////////////////
    private static void renewLoan() {


        // Printing message to show method is begining
        System.out.println("Renewing a loan, loading...");

// Retrieving items and loans data from files
        Items[] itemsArray = fileItems();
        String[] loanArray = fileLoans();

//  Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

//  User input barcode of item to renew loan

        System.out.println("Enter Barcode of item to renew their loan: ");
        String barcodeInput = scanner.nextLine();

// UserID of the borrower
        System.out.println("Enter the UserID of the borrower: ");
        String userInput = scanner.nextLine().toUpperCase();

//  Variables for tracking loan renewal and updated loan data
        boolean loanUpdated = false;
        StringBuilder updatedLoanData = new StringBuilder();

// Going through existing loan records
        for (String loanInfo : loanArray) {
            // Splitting loan info into fields
            String[] fields = loanInfo.split(",");

            // Checking if the current loan matches the input barcode and user ID
            if (fields[0].equals(barcodeInput) && fields[1].equals(userInput)) {
                // Parsing due date and number of renewals from loan info
                Date dueDate = parseDate(fields[3]);
                int numRenews = Integer.parseInt(fields[4]);

                // Searching for item corresponding to the barcode
                Items item = null;
                for (Items currentItem : itemsArray) {
                    if (currentItem.getBarcode().equals(barcodeInput)) {
                        item = currentItem;
                        break;
                    }
                }

                // Processing renewal if item is found
                if (item != null) {
                    int renewalWeeks = 0;
                    int maxRenews = 0;
                    // Setting max renews and duration of loan based on item type
                    if (item.getType().equalsIgnoreCase("book")) {
                        renewalWeeks = 2;
                        maxRenews = 3;
                    } else {
                        renewalWeeks = 1;
                        maxRenews = 2;
                    }
                    // Checking if renewal is within max renewals limit
                    if (numRenews < maxRenews) {
                        // Calculating due date
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(dueDate);
                        calendar.add(Calendar.WEEK_OF_YEAR, renewalWeeks);
                        Date newDueDate = calendar.getTime();

                        // Formatting due date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String newDueDateStr = dateFormat.format(newDueDate);

                        // Constructing updated loan data
                        updatedLoanData.append(fields[0]).append(",")
                                .append(fields[1]).append(",")
                                .append(fields[2]).append(",")
                                .append(newDueDateStr).append(",")
                                .append(numRenews + 1).append("\n");

                        // Given user confirmation loan has been created
                        loanUpdated = true;
                        System.out.println("Loan renewed");

                    } else {
                        // Printing message if max limit of renewals reached
                        System.out.println("Max Renewal limit" +
                                " You must return your book");
                    }
                } else {
                    // Printing message if item isn't found
                    System.out.println("Item not found." +
                            " Returning to menu");
                }
            } else {
                // Appending unchanged info into the file
                updatedLoanData.append(loanInfo).append("\n");
            }
        }

// Print message if loan not found
        if (!loanUpdated) {
            System.out.println("Loan not found");
            return;
        }

// Writing updated loan data to file
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\LOAN.csv"))) {
            writer.print(updatedLoanData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //////////////////////////////////
    // METHOD FOR searchLoan
    /////////////////////////////////
    private static void searchLoan() {
        // Printing message to show method is working
        System.out.println("Searching for the loan");
        // Retrieving loan data from the LOAN.csv file
        String[] loanArray = fileLoans();
        // Creating a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        // have user input search criteria (barcode or UserID)
        System.out.println("Enter Barcode or UserID to search for loan:  ");
        String searchInput = scanner.nextLine().toUpperCase();
        // seeing if any matching loans are found
        boolean loanfound = false;
        // Going through each loan info to find matches
        for (String loanInfo : loanArray) {
            // Splitting loan info into fields
            String[] fields = loanInfo.split(",");
            // Checking if the loan matches barcode or UserID
            if (fields[0].equals(searchInput) || fields[1].equals(searchInput)) {
                // Printing matching loan info
                System.out.println("Results found: "+loanInfo);
                loanfound = true;
            }
        }
        // If no loans are found, print a message
        if (!loanfound) {
            System.out.println("No loans found, make sure to input the correct details");
        }
    }
    //////////////////////////////////
    // METHOD FOR returnLoan
    /////////////////////////////////
    private static void returnLoan() {
        // Printing message to show the start of the method
        System.out.println("Returning the loan");
        // Retrieving data from LOAN.csv file
        String[] loanArray = fileLoans();
        // Creating a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        // Having user input Barcode and UserID of the loan to return
        System.out.println("Enter Barcode of the item to return: ");
        String BarcodeInput = scanner.nextLine();
        System.out.println("Enter the UserID of the borrower: ");
        String UserInput = scanner.nextLine().toUpperCase();
        // Checking if loan is found
        boolean LoanFound = false;
        // StringBuilder to store updated loan data
        StringBuilder updatedLoan = new StringBuilder();
        // Going through each loan info to find the loan to return
        for (String loanInfo : loanArray){
            // Splitting info into fields
            String[] fields = loanInfo.split(",");
            // Checking if the loan matches input barcode and user ID
            if (fields[0].equals(BarcodeInput) && fields[1].equals(UserInput)){
                LoanFound = true;
                System.out.println("Loan is found, Returning...");
            } else {
                // Appending unchanged info to updated loan data
                updatedLoan.append(loanInfo).append("\n");
            }
        }
        // if loan isnt found print the message
        if (!LoanFound){
            System.out.println("Loan not found");
            return;
        }
        // deleting returned loan from the loan.csv table
        try (PrintWriter writer = new PrintWriter(new FileWriter("src\\LOAN.csv"))){
            writer.print(updatedLoan.toString());
            System.out.println("Loan returned");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //////////////////////////////////
    // METHOD FOR loanReport
    /////////////////////////////////
    private static void loanReport(){
        // Retrieve items and loans data from files
        Items[] itemsArray = fileItems();
        String[] loanArray = fileLoans();

        // Variables to count loans and renewals
        int totalBooks = 0;
        int totalMultimedia = 0;
        int totalRenewed = 0;
        int totalLoans = 0;

        // Go through each loan in the loan array
        for (String loanInfo : loanArray) {
            // Skip the header line if present
            if (loanInfo.startsWith("Barcode")) {
                continue;
            }

            // Split loan info into fields
            String[] fields = loanInfo.split(",");
            // Increment total loans count
            totalLoans++;
            // Check if item type is book or multimedia
            for (Items item : itemsArray) {
                if (item.getBarcode().equals(fields[0])) {
                    if (item.getType().equalsIgnoreCase("book")) {
                        totalBooks++;
                    } else {
                        totalMultimedia++;
                    }
                    break;
                }
            }
            // Check NumRenews field exists and if it's greater than 1
            if (fields.length > 4 && !fields[4].isEmpty()) {
                try {
                    int numRenews = Integer.parseInt(fields[4]);
                    if (numRenews > 1) {
                        totalRenewed++;
                    }
                } catch (NumberFormatException e) {
                    // Handle parsing error
                    System.out.println("Error parsing NumRenews: " + e.getMessage());
                }
            }
        }
        // Calculate percentages
        double percentRenewedLoans = totalLoans > 0 ? ((double) totalRenewed / totalLoans) * 100 : 0;

        // Format percentage to 2 decimal places
        String formattedPercentRenewedLoans = String.format("%.2f", percentRenewedLoans);
        // Print report
        System.out.println("        Lagmore library");
        System.out.println("        Loan Report:");
        System.out.println("---------------------------------------");
        System.out.println("Total number of books loaned: " + totalBooks);
        System.out.println("Total number of multimedia loaned: " + totalMultimedia);
        System.out.println("Percentage of loans renewed more than once: " + formattedPercentRenewedLoans + "%");
    }


    //////////////////////////////////
    // METHOD FOR DATE
    /////////////////////////////////
    private static Date parseDate(String dateString) {
        try {
            // Create a SimpleDateFormat object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Parse the input string to obtain Date object
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            // If parsing fails print the stack trace
            e.printStackTrace();
            // Return null if parsing failed
            return null;
        }
    }


    ////////////////////////////////////
    // CSV HANDLING
    ////////////////////////////////////

    /////////////////////////
    // ITEMS CSV HANDLING ********************
    /////////////////////////
    // Method to read items data from a CSV file
    public static Items [] fileItems(){
        // reading from csv file and putting in into an array then putting them into objects
        Items[] itemsArray = Itemscsv("src\\ITEMS.csv");
        return itemsArray;
    }

    // Method to parse items data from a CSV file
    public static Items[] Itemscsv(String filename) {
        // Initialize a list to store Items objects
        List<Items> itemsList = new ArrayList<>();
        // Call ItemscsvFile method to read CSV data from file
        List<String[]> CsvData = ItemscsvFile(filename);
        // Iterate through each row of CSV data
        for (String[] fields : CsvData) {
            // Extract fields from current row
            String Barcode = fields[0];
            String Author = fields[1];
            String Title = fields[2];
            String Type = fields[3];
            String Year = fields[4];
            String ISBN = fields[5];
            // Create  new Items object with extracted fields
            Items item = new Items(Barcode, Author, Title, Type, Year, ISBN);
            // Add the Items object to list
            itemsList.add(item);
        }
        //convert objects into an array and return it
        return itemsList.toArray(new Items[0]);
    }

    // Method to read CSV data into a list
    public static List<String[]> ItemscsvFile(String filename) {
        // Initialize  list to store string arrays representing CSV
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line from CSV file
            while ((line = br.readLine()) != null) {
                // Split line by commas to extract fields
                String[] fields = line.split(",");
                // Add array of fields to data list
                data.add(fields);
            }
        } catch (IOException e) {
            // If an IOException occurs print the stack trace
            e.printStackTrace();
        }
        // Return list containing parsed CSV data
        return data;
    }
    ////////////////////////////////////////
    ///USERS CSV HANDLING ******************
    ////////////////////////////////////////

    public static Users[] fileUsers(){
     //   reading from csv file and putting in into an array then putting them into objects
        Users[] userArray = usersCsv("src\\USER.csv");
        return userArray;
    }

    // Method to parse users data from a CSV file
    public static Users[] usersCsv(String filename) {
        // Initialize list to store Users objects
        List<Users> usersList = new ArrayList<>();
        // Call UserscsvFile method to read CSV data from file
        List<String[]> CsvData = UserscsvFile(filename);
        // Go through each row of CSV data
        for (String[] fields : CsvData) {
            // Extract individual fields from current row
            String UserID = fields[0];
            String FirstName = fields[1];
            String LastName = fields[2];
            String Email = fields[3];
            // Create new Users object with extracted fields
            Users user = new Users(UserID, FirstName, LastName, Email);
            // Add  Users object to the list
            usersList.add(user);
        }
        // Convert  list of Users objects to array and return it
        return usersList.toArray(new Users[0]);
    }

    public static List<String[]> UserscsvFile(String filename){
        // create list to store CSV data
        List<String[]> data = new ArrayList<>();
        // Reading CSV file and parse data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            // Skiping header line
            br.readLine();
            // Read each line of CSV file
            while ((line = br.readLine()) != null) {
                // Split line into fields, add to data list
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e){
            // Print stack trace if error occurs
            e.printStackTrace();
        }
        // Return  list containing  parsed CSV data
        return data;
    }

    ////////////////////////////////////////
    ///LOAN CSV HANDLING ******************
    ////////////////////////////////////////

    public static String[] fileLoans(){
        // creating string to store loan data
        String dataLoan = "";
        try {
            // Open file for reading
            File myObj = new File("src\\LOAN.csv");
            Scanner myReader = new Scanner(myObj);
            // Read each line from file
            while (myReader.hasNextLine()) {
                // Append line to loan data string
                dataLoan += myReader.nextLine() + "\n";
            }
            myReader.close(); // Close the scanner
        } catch (FileNotFoundException e){
            // if file not found print stack trace and error message
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // Split data string into array of strings and return it
        String[] loanArray = dataLoan.split("\n");
        return loanArray;
    }

} // End of class LibraryLoans


