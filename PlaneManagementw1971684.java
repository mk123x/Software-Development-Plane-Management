/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.planemanagementw1971684;
import java.util.Scanner;           //scanner is used to read input from user
/**
 *
 * @author maruk
 */
public class PlaneManagementw1971684 {       //declares class
    //the following lines declare various constants
private static final int ROWS = 4;           
    private static final char[] ROW_LABELS = {'A', 'B', 'C', 'D'};
    private static final int[] SEATS_PER_ROW = {14, 12, 12, 14};
    private static final int AVAILABLE = 0;
    private static final int SOLD = 1;
    private static int[][] seats = new int[ROWS][]; // initialize values at 0
    private static final int MAX_NUMBER_OF_TICKETS = 52;
    
    private static int numTickets=0; // this tracks the number of tickets added as they are bought
    private static Ticket[] ticketList = new Ticket[MAX_NUMBER_OF_TICKETS]; //the array is called ticketList and defines the maximum no of tickets it can hold, stores a reference to a ticket object for retrival later

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");
        initializeSeats(); //calls method that sets the seats initially at 0
        handleUserMenu(); // calls method that shows the user a menu of options and it handles user input
    }

    private static void initializeSeats(){
        for (int i=0;i<ROWS;i++){ // loop condition that specifies that the loop should continue as long as the value of i is less than ROWS
            seats[i] = new int[SEATS_PER_ROW[i]]; //seats being assigned to  a new array,creates a row where i represents the current row index
            for (int j=0;j < SEATS_PER_ROW[i]; j++) { //loop iterates over each seat in the row 
                seats[i][j] = AVAILABLE; // this initializes all seats as available to buy aka as 0
            }
        }
    }

    private static void displaySeatPlan(){
        System.out.println("Plane Seating Plan:");
        for (int i = 0; i < ROWS; i++) {
            System.out.print(ROW_LABELS[i] + " ");        //this prints the row that is selected (a b c or d)
            for (int j = 0 ; j <SEATS_PER_ROW[i]; j++){     //goes through each seat in the selected row
                if (seats[i][j] ==AVAILABLE){           //if else to show if the selected seat is available or has been sold
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    private static void handleUserMenu(){
        Scanner userInput = new Scanner(System.in);
        int option;
        
        //do while loop so the menu displays repeatedly and prompts user for input until the users quits program
        do {
            System.out.println("*************************************************");
            System.out.println("*                    MENU                       *");
            System.out.println("*************************************************");
            System.out.println("1. Buy a seat");
            System.out.println("2. Cancel a seat");
            System.out.println("3. Find first available seat");
            System.out.println("4. Show seating plan");
            System.out.println("5. Print tickets information and total sales");
            System.out.println("6. Search ticket");
            System.out.println("0. Quit");
            System.out.println("*************************************************");
            System.out.println("Please select an option:");
            option = userInput.nextInt();            //prompts user to enter choice and itll be stored in the option variable
            

            if (option == 1){
                buySeat(userInput);
            } else if (option == 2){
                cancelSeat(userInput);
            } else if (option == 3){
                findFirstAvailableSeat();
            } else if (option == 4){
                showSeatingPlan();
            } else if (option == 5){
            print_tickets_info(); // call the method to print ticket information
        } else if (option == 6){
            search_Ticket(userInput);
            } else if (option == 0){
                System.out.println("Exiting the program. Goodbye!");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        } while (option != 0);

        userInput.close();
    }

    
    
    
    private static void buySeat(Scanner userInput){
        System.out.println("Buy a Seat:");
        System.out.print("Enter row letter (A, B, C, or D): ");
        char rowLetter = userInput.next().toUpperCase().charAt(0);    //converts the user input for row letter into uppercase

        if (rowLetter< 'A'||rowLetter > 'D'){ //checks if row number is valid
            System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            return;
        }

        int rowIndex=rowLetter-'A'; // convert the row letter to the correct index (a=0 b=1 c=2 d=3)

        System.out.print("Enter seat number: ");
        int seatNumber=userInput.nextInt();

        if (seatNumber< 1 ||seatNumber>SEATS_PER_ROW[rowIndex]){ //checks that the chosen seat number is valid
            System.out.println("Invalid seat number. Please enter a seat number between 1 and " + SEATS_PER_ROW[rowIndex] + ".");
            return;          //exist the method if seat number is valid
        }

        if (seats[rowIndex][seatNumber-1]==SOLD){      //checks to see if the seat is available
            System.out.println("Seat "+ rowLetter+seatNumber +" has already been sold.");
        }else{
            // prompt user for person information in order to buy
            System.out.print("Enter person's name: ");
            String name = userInput.next();
            System.out.print("Enter person's surname: ");
            String surname = userInput.next();
            System.out.print("Enter person's email: ");
            String email = userInput.next();

            // create a new Person object
            Person person = new Person(name, surname, email);

            // calculate ticket price based od chosen row and seat
             int price;
        if (seatNumber <= 4) {
            price = 200;
        } else if (seatNumber <= 9) {
            price = 150;
        } else {
            price = 180;
        }
    
            seats[rowIndex][seatNumber-1] = SOLD; //marking the chosen seat as sold
            // Create a new Ticket object for the sold seat
        Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);

        
        if (numTickets < MAX_NUMBER_OF_TICKETS) {
            ticketList[numTickets] = ticket; // add the ticket to the array
            numTickets++; // keeps track of  total no of tickets sold, by incrementing it u update the count to show the addition of a new ticket
        } else {
            System.out.println("Ticket limit reached. Cannot add more tickets.");
            return;
          }

        seats[rowIndex][seatNumber-1] = SOLD; //marking the chosen seat as sold
        System.out.println("Seat " +rowLetter + seatNumber+ " has been successfully sold for £" + price + ".");
    }
}


    
    
    private static void cancelSeat(Scanner userInput){
        System.out.println("Cancel a Seat:");
        // this prompt the user to enter a row letter
        System.out.print("Enter row letter (A, B, C, or D): ");
        char rowLetter=userInput.next().toUpperCase().charAt(0); //user input into upper case and takes the first character( has an index of 0)

        // converts the row letter to a row number
        int rowNumber=rowLetter - 'A'+1;

        // checks if row number is valid
        if (rowNumber<1 ||rowNumber>ROWS) {
            System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            return; // exit the method if row number is invalid
        }

        int rowIndex= rowNumber-1;

        // prompts user for seat number
        System.out.print("Enter seat number: ");
        int seatNumber = userInput.nextInt();

        // checks if seat number is valid
        if (seatNumber< 1||seatNumber> SEATS_PER_ROW[rowIndex]){
            System.out.println("Invalid seat number. Please enter a seat number between 1 and " +SEATS_PER_ROW[rowIndex]+".");
            return; // exit the method if seat number is invalid
        }

        // check if the chosen seat is available
        if (seats[rowIndex][seatNumber-1]== AVAILABLE){
            System.out.println("Seat "+rowLetter+seatNumber+" was never booked and is available.");
        } else {
            // this maarks the seat as now available
            seats[rowIndex][seatNumber-1]=AVAILABLE;
            System.out.println("Seat "+rowLetter+ seatNumber+" has been successfully cancelled and is now available to buy.");
        }
    }


    
    
    private static void findFirstAvailableSeat(){
        System.out.println("Finding the first available seat..."); 
        for (int i =0;i <ROWS;i++){ //from the start it goes through each seat and checks if its available 
            char rowLetter=ROW_LABELS[i];
            for (int j= 0;j <SEATS_PER_ROW[i]; j++){
                if (seats[i][j]==AVAILABLE){
                    System.out.println("The first available seat is: " +rowLetter+ (j + 1));
                    return;
                }
            }
        }
        System.out.println("Sorry, all seats are sold out.");
    }

    
    
 
     
    
    private static void showSeatingPlan(){
        System.out.println("Plane Seating Plan:");
        for (int i=0;i <ROWS; i++){ 
            System.out.print(ROW_LABELS[i] + "");
            for (int j = 0; j<SEATS_PER_ROW[i]; j++){
                if (seats[i][j]==AVAILABLE){
                    System.out.print("O "); // all available seat will be represented as O
                }else{
                    System.out.print("X "); // all unavailable seats will be represented as X
}            }
            System.out.println();
        }
    }

    
    
    
    
    private static void print_tickets_info() {  //method is void because it doesnt return a value
System.out.println("Ticket Information:");

    int totalPrice = 0; // the intial total price of tickets is 0

    // goes through the array of tickets
    for (Ticket ticket : ticketList){
        if (ticket != null){ //checks if the ticket is not null before printing
        // get method to retrieve the element from the array
        System.out.println("Row letter: "+String.valueOf(ticket.getRow())+", Seat number: " + ticket.getSeat());
        System.out.println("Price: £" +ticket.getPrice());
        System.out.println("Person: "+ ticket.getPerson().getName() + " " + ticket.getPerson().getSurname() + " (" + ticket.getPerson().getEmail() + ")");
        System.out.println("---");      //separating tickets information from the other to avoid confusion

        // add the price of the ticket to the total price
        totalPrice +=ticket.getPrice();
}
    }
    System.out.println("The total price of all sold tickets: £" + totalPrice);
}

    
    
    
    private static void searchTicket(Scanner userInput) {
System.out.println("Search for a ticket and its information:");
    
    // prompt the user to input a row letter
    System.out.print("Enter row letter (A, B, C, or D): ");
    String rowInput=userInput.next().toUpperCase(); //  the users input is a string and convert it to uppercase
    
    // check if the inputed string is one of the valid row letters a b c or d
    if (!rowInput.equals("A") && !rowInput.equals("B") && !rowInput.equals("C") && !rowInput.equals("D")) {
        System.out.println("Invalid row letter. Please enter A, B, C, or D.");
        return; // exit the method if the input is an invalid row letter
    }

    char rowLetter =rowInput.charAt(0); // this assign the first character of user input (index of 0) string to rowLetter
    
    // prompts the user to input a seat number
    System.out.print("Enter a seat number: ");
    int seatNumber=userInput.nextInt(); //user input will be a integer

    // check if the seat number is valid and in bounds. it has to be between one and the maxmimum seats in the row either 12 or 14 
    if (seatNumber < 1 || seatNumber> SEATS_PER_ROW[rowLetter -'A']) {
        System.out.println("Invalid seat number. Please enter a seat number between 1 and " + SEATS_PER_ROW[rowLetter - 'A'] + ".");
        return; // exit the method if the seat number is invalid
    }

    // check if the seat is available in the array and -1 because of the index
    if (seats[rowLetter -'A'][seatNumber-1] ==AVAILABLE) {
        System.out.println("Seat "+rowLetter + seatNumber+ " is available.");
    } else {
        // associate a ticket with the seat
        Ticket ticket = getTicketBySeat(rowLetter, seatNumber); //method that searches for  ticket corresponding to the inpputted row letterand seat number

        if (ticket != null) { //checks if a ticket is found for this seat
            // print ticket and the person information
            System.out.println("Ticket found:");
            ticket.printInfo();
        } else {
            System.out.println("Ticket not found for seat " + rowLetter+seatNumber + ".");
        }
    }
}

    private static Ticket getTicketBySeat(char rowLetter, int seatNumber) {
    for (int i = 0; i < numTickets; i++) {
        Ticket ticket = ticketList[i]; //for loop goes through the ticket list array up until num tickets which are the tickets stored in the ticket list array
        if (ticket.getRow() == rowLetter && ticket.getSeat() == seatNumber) { //checks that the ticket corresponds with the inputted row letter and seat number, && logical and operator where both conditions hv to be true 
            return ticket;
        }
    }
    return null; //  if ticket isnt found
    
    }
    
    
    

    private static void search_Ticket(Scanner userInput) {
 System.out.println("Search for a ticket and its information...");

    // prompt the user to input a row letter
    System.out.print("Enter row letter (A, B, C, or D): ");
    String rowInput = userInput.next().toUpperCase(); //reads user input string and converts into upper case

    // checks if the inputted letter is one of the valid row letters (A, B, C, or D)
    if (rowInput.length() != 1 || rowInput.charAt(0) < 'A' || rowInput.charAt(0) > 'D') {
        System.out.println("Entered row letter is invalid. Please enter A, B, C, or D.");
        return; // exit the method if the input is invalid row letter
    }

    char rowLetter = rowInput.charAt(0);

    // prompts the user to input a seat number
    System.out.print("Enter seat number: ");
    int seatNumber = userInput.nextInt();

    // check if the seat number is valid and in the  seat bounds for the specific row
    int rowIndex = rowLetter - 'A'; //calculates the index of the row based on the letter entered by the user as the index always starts at 0
    if (seatNumber < 1 || seatNumber > SEATS_PER_ROW[rowIndex]) {
        System.out.println("Entered seat number is invalid. Please enter a seat number between 1 and " + SEATS_PER_ROW[rowIndex] + ".");
        return; // exit the method if the seat number is invalid
    }

    // check if the seat is available
    if (seats[rowIndex][seatNumber - 1] == AVAILABLE) {
        System.out.println("This seat is available.");
    } else {
        // this will associate ticket with the seat
        Ticket ticket = getTicketBySeat(rowLetter, seatNumber);

        if (ticket != null) {
            // print ticket and person information
            System.out.println("Ticket found...");
            ticket.printInfo();
            ticket.getPerson().printInfo();
        } else {
            System.out.println("Ticket not found for seat " + rowLetter + seatNumber + ".");
        }
    }
    }
}

