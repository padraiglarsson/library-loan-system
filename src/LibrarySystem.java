import java.io.*;
public class LibrarySystem {
    public static void main (String[] args) throws IOException{
        System.out.println("///////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");

        // Print the title line for the student points section
        System.out.println("==         Lagmore Library          ==");

        // Print the bottom border of the title using backslashes and forward slashes
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\///////////////////");
        System.out.println("Loaning options:");

        // loading all the csv files in

        LibraryLoans hiLo = new LibraryLoans();
        hiLo.fileItems();
        hiLo.fileUsers();
        hiLo.fileLoans();
        hiLo.start();
    }
}
