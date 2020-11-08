package cinema;
import java.util.Scanner;
public class Cinema {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int basePrice = 10;
        int reducedPrice = 8;
        Floor f1;
        boolean quit = false;

        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = sc.nextInt();
        Theatre martin1 = new Theatre(rows, seats, basePrice, reducedPrice);

        do {
            printMenu();
            int menu = sc.nextInt();
            switch(menu){
                case 0:
                    System.out.println("Bye bye");
                    quit = true;
                    break;
                case 1:
                    martin1.printTheatre();
                   // quit = true;
                    break;
                case 2:
                    buyTicket(martin1);
                   // quit = true;
                    break;
                case 3:
                    statistics(martin1);
                    break;
                default:
                    System.out.println("Command unknown. try again");
                    break;
            }
        } while (!quit);

    }

    public static void printMenu(){
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void buyTicket(Theatre t1){
        System.out.println("Enter a row number:");
        int requestRow = sc.nextInt() - 1;
        System.out.println("Enter a seat number in that row:");
        int requestSeat = sc.nextInt() - 1;
        Floor fl1 = t1.getSeatInfo(requestRow, requestSeat);
        System.out.printf("Ticket price: $%.0f%n",fl1.getP());
        t1.setSeatInfo(requestRow, requestSeat);
    }

    public static void statistics(Theatre t1){

    }

}
class Theatre {
    private int rows;
    private int seats;
    private Floor[][] floor;
    private static double basePrice;
    private static double reducedPrice;
    private int purchasedTickets;
    private double relativePurchasedTickets;
    private double income;
    private double totalIncome;
    
    public Theatre(int rows, int seats, double basePrice, double reducedPrice){
        this.rows = rows;
        this.seats = seats;
        Theatre.basePrice = basePrice;
        Theatre.reducedPrice = reducedPrice;
        floor = new Floor[rows][seats];
        initFloor();
    }

    public Floor getSeatInfo(int row, int seat){
        return floor[row][seat];
    }

    public void setSeatInfo(int row, int seat){
        this.floor[row][seat].setS('B');
    }

    public void calculate(){
        int count = 0;
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++){
                if (floor[i][j].getS() == 'B') {
                    count++;
                    sum += floor[i][j].getP();
                }
            }
        }
        this.purchasedTickets = count;
        this.income = sum;
        this.relativePurchasedTickets = (rows * seats) / (double) this.purchasedTickets;
        if ( rows * seats <= 60) this.totalIncome = rows * seats * basePrice;
        else {
            this.totalIncome = (rows / 2) * basePrice;
            this.totalIncome += (rows - (rows /2)) * reducedPrice;
        }
    }

    public double getRelativePurchasedTickets() {
        return relativePurchasedTickets;
    }

    public double getIncome() {
        return income;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public int getPurchasedTickets(){
        return this.purchasedTickets;
    }

    private void initFloor(){
        int part1 = rows / 2;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < seats; j++){
                if (rows * seats <= 60) {
                    floor[i][j] = Floor.addFloor('S', basePrice);
                } else {
                    if (i < part1) {
                        floor[i][j] = Floor.addFloor('S', basePrice);
                    } else {
                        floor[i][j] = Floor.addFloor('S', reducedPrice);
                    }
                }
            }
        }
    }
    
    public void printTheatre(){
        System.out.println("Cinema:");
        for (int k = 0; k < seats; k++) System.out.print(((k == 0)?" ":"") + " " + (k + 1)
                + ((k == seats -1)?"\n":""));
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < seats; j++){
                System.out.print(((j == 0)?(i + 1) + " " + floor[i][0].getS() + " ":floor[i][j].getS() + " ")
                        + ((j == seats - 1)?"\n":""));
            }
        }
        System.out.print("\n");
    }
    
}

class Floor {
    private char s;
    private double p;
    
    private Floor(char s, double p){
        this.s = s;
        this.p = p;
    }
    
    public char getS(){
        return this.s;
    }
    
    public double getP(){
        return p;
    }
    
    public void setS(char s){
        this.s = s;
    }
    
    public void setP(double p){
        this.p = p;
    }
    
    public static Floor addFloor(char s, double p){
        return new Floor(s, p);
    }
}
