// Define a class named Items
public class Items {
    // Define instance variables
    public String Barcode; // Barcode of the item
    public String Author; // Author of the item
    public String Title; // Title of the item
    public String Type; // Type of the item
    public String Year; // Year of publication
    public String ISBN; // ISBN of the item

    // Constructor to initialize the instance variables
    public Items (String Barcode, String Author, String Title,
                  String Type, String Year, String ISBN){
        this.Barcode = Barcode;
        this.Author = Author;
        this.Title = Title;
        this.Type = Type;
        this.Year = Year;
        this.ISBN = ISBN;
    }

    // Getter method to retrieve the barcode
    public String getBarcode(){
        return Barcode;
    }
    public String getType(){
        return Type;
    }

    // Override the toString() method to provide a string representation of Items
    @Override
    public String toString(){
        return "Items{" +
                "Barcode = " + Barcode + '\'' + // Barcode
                "Author = " + Author + '\'' + // Author
                "Title = " + Title + '\'' + // Title
                "Type = " + Type + '\'' + // Type
                "Year = " + Year + '\'' + // Year
                "ISBN = " + ISBN + '\'' + // ISBN
                '}';
    }
} // End of class Items

