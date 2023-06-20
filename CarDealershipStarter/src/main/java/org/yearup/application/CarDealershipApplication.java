package org.yearup.application;

import org.yearup.data.*;
import org.yearup.models.LeaseContract;
import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarDealershipApplication
{
    private DataSource dataSource;
    private Scanner scanner = new Scanner(System.in);
    public CarDealershipApplication(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void run()
    {
        UI();
    }

    private void UI()
    {
        String selection = "";
        do {
            System.out.println("Dealership Management System");
            System.out.println("1) View Dealerships' Vehicles");
            System.out.println("2) View Admin Options");
            System.out.println("99) Exit");
            System.out.print("Make a Selection: ");
            selection = scanner.nextLine();

            switch (selection) {
                case "1": {
                    dealershipUI();
                    break;
                }
                case "2": {
                    adminUI();
                    break;
                }
                case "99":
                {
                    System.exit(1);
                }
                default: {
                    System.out.println("Invalid Selection");
                }
            }
        }while (!selection.equals("99"));
    }

    private void adminUI()
    {
        String selection = "";
        String title = "Admin Page";
        
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));
        System.out.println("1) Lease Contract Options");
        System.out.println("2) Sale Contract Options");
        System.out.print("Enter a selection (1-2): ");
        selection = scanner.nextLine();
        
        adminSelection(selection);

    }

    private void adminSelection(String selection) 
    {
        switch (selection)
        {
            case "1":
            {
                leaseContractOptions();break;
            }
            case "2":
            {
                salesContractOptions();break;
            }
            default:
            {
                System.out.println("Invalid Option");
            }
        }
    }

    private void adminVehicleMngmnt(){

        String selection = "";
        System.out.println("1) Add a vehicle");
        System.out.println("2) Remove a vehicle");
        System.out.print("Enter a selection(1-2): ");
        selection = scanner.nextLine();

        switch (selection)
        {
            case "1":
            {
                createVehicle();break;
            }
            case "2":
            {
                deleteVehicle();break;
            }
            default:
            {
                System.out.println("Invalid Input!");
            }
        }

    }

    private void salesContractOptions() {
        String selection = "";
        System.out.println("\n1) Get sale contract by ID");
        System.out.println("2) Get all sale contracts");
        System.out.println("3) Create a sale contract");
        System.out.println("4) Remove a sale contract");
        System.out.print("Enter a selection(1-4): ");
        selection = scanner.nextLine();

        switch (selection) {
            case "1": {
                getSalesContractById();
                break;
            }
            case "2": {
                getSalesAllContract();
                break;
            }
            case "3": {
                addSalesContract();
                break;
            }
            case "4": {
                deleteSalesContract();
                break;
            }
            default: {
                System.out.println("Invalid Selection");
            }
        }
    }

    private void leaseContractOptions()
    {
        String selection = "";

        System.out.println("\n1) Get lease contract by ID");
        System.out.println("2) Get all lease contracts");
        System.out.println("3) Create a lease contract");
        System.out.println("4) Remove a lease contract");
        System.out.print("Enter a selection(1-4): ");
        selection = scanner.nextLine();

        switch (selection)
        {
            case "1":
            {
                getLeaseContractById();break;
            }
            case "2":
            {
                getAllLeaseContracts();break;
            }
            case "3":
            {
                addLeaseContract();break;
            }
            case "4":
            {
                deleteLeaseContract();break;
            }
            default:
            {
                System.out.println("Invalid Selection");
            }
        }

    }

    private void dealershipUI()
    {
        System.out.println("\nWelcome to dealership");
        System.out.println("1) Display vehicles by price range");
        System.out.println("2) Display vehicles by make/model");
        System.out.println("3) Display vehicles by year range");
        System.out.println("4) Display vehicles by color");
        System.out.println("5) Display vehicles by mileage range");
        System.out.println("6) Add a vehicle");
        System.out.println("7) Remove a vehicle");
        System.out.print("Enter a selection (1-6): ");
        String selection = scanner.nextLine();

        dealershipSelection(selection);
    }

    private void dealershipSelection(String selection)
    {
        switch (selection){
            case "1":
            {
                displayVehiclesInPriceRange();
                break;
            }
            case "2":
            {
                displayVehiclesByMakeModel();
                break;
            }
            case "3":
            {
                displayVehiclesInYearRange();
                break;
            }
            case "4":
            {
                displayVehiclesByColor();
                break;
            }
            case "5":
            {
                displayVehiclesInMilesRange();
                break;
            }
            case "6":
            {
                createVehicle();
                break;
            }
            case "7":
            {
                deleteVehicle();
                break;
            }
            default:
            {
                System.out.println("Invalid Selection");
            }
        }
    }

    private void leaseTableHeader()
    {

        System.out.println("-".repeat(183));
        System.out.printf("|%-3s|%-20s|%-50s|%-50s|%-10s|%-10s|%-10s|%-10s|%-10s|\n","ID","VIN","CustomerName",
                "CustomerEmail","SalesPrice","EndValue","LeaseFee","SalesTax","monthPay");
        System.out.println("-".repeat(183));
    }

    private void deleteLeaseContract()
    {
        System.out.print("Enter the lease id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        LeaseContractDao contractDao = new MySqlLeaseContractDao(dataSource);
        contractDao.remove(id);
    }

    private void addLeaseContract()
    {
        String title = "Add Lease Contract";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        BigDecimal leasingFee = BigDecimal.valueOf(295);

        System.out.println("Enter the vin: ");
        String vin = scanner.nextLine();

        System.out.println("Enter the name: ");
        String name = scanner.nextLine();

        System.out.println("Enter the email: ");
        String email = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if(vehicle != null) {

            double monthlyPercentageDepreciation = (4.0 / 12) / 100;
            double principle = vehicle.getPrice().doubleValue();
            BigDecimal monthlyPay = BigDecimal.valueOf(principle * monthlyPercentageDepreciation / (1 - Math.pow(1+monthlyPercentageDepreciation,-36)));
            BigDecimal salesTax = BigDecimal.valueOf(0.08).multiply(vehicle.getPrice());
            BigDecimal endingValue = BigDecimal.valueOf(0.5).multiply(vehicle.getPrice());
            LeaseContract contract = new LeaseContract() {{
                setVin(vin);
                setCustomerName(name);
                setCustomerEmail(email);
                setLeaseFee(leasingFee);
                setSalesTax(salesTax);
                setEndingValue(endingValue);
                setMonthlyPayment(monthlyPay);
                setSalesPrice(vehicle.getPrice());
            }};

            LeaseContractDao leaseContractDao = new MySqlLeaseContractDao(dataSource);
            LeaseContract leaseContract = leaseContractDao.makeLease(contract);

            if(leaseContract != null) {
                leaseTableHeader();
                System.out.println(leaseContract.displayInfo());
                System.out.println("-".repeat(183));
            }
            }
        else{
            System.out.println("Vehicle don't exist");
        }
    }

    private void getAllLeaseContracts()
    {
        String title = "Get all lease contract";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        LeaseContractDao leaseContractDao = new MySqlLeaseContractDao(dataSource);
        List<LeaseContract> leaseContracts = leaseContractDao.getAllContracts();

        leaseTableHeader();
        for (var leaseContract: leaseContracts)
        {
            System.out.println(leaseContract.displayInfo());
            System.out.println("-".repeat(183));
        }
    }

    private void getLeaseContractById()
    {
        String title = "Get lease contract by ID";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        LeaseContractDao leaseContractDao = new MySqlLeaseContractDao(dataSource);
        System.out.print("Enter lease contract id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        LeaseContract leaseContract = leaseContractDao.getContractByID(id);

        leaseTableHeader();
        System.out.println(leaseContract.displayInfo());
        System.out.println("-".repeat(183));
    }

    private void vehicleTableHeader()
    {

        System.out.println("-".repeat(175));
        System.out.printf("|%-20s|%-50s|%-50s|%-20s|%-8s|%-5s|%-5s|%-8s|\n","VIN","Make","Model","Color","Mileage","Sold","Year","Price[$]");
        System.out.println("-".repeat(175));

    }
    private void salesContractTableHeader()
    {
        System.out.println("-".repeat(172));
        System.out.printf("|%3s|%20s|%50s|%50s|%10s|%10s|%10s|%10s|\n","ID","VIN","CustomerName","CustomerEmail",
                "SalesPrice","RecordFee","ProcessFee","salesTax");
        System.out.println("-".repeat(172));
    }
    private void getSalesContractById()
    {
        String title = "Get sale contract by ID";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        SalesContractDao salesContractDao = new MySqlSalesContractDao(dataSource);
        System.out.print("Enter sales contract id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        SalesContract salesContract = salesContractDao.getContractByID(id);

        salesContractTableHeader();
        System.out.println(salesContract.displayInfo());
        System.out.println("-".repeat(172));
    }

    private void getSalesAllContract()
    {
        String title = "Get all sales contract";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        SalesContractDao salesContractDao = new MySqlSalesContractDao(dataSource);
        List<SalesContract> salesContracts = salesContractDao.getAllContracts();

        salesContractTableHeader();

        for (var saleContract: salesContracts)
        {
            System.out.println(saleContract.displayInfo());
            System.out.println("-".repeat(172));
        }
    }

    private void deleteSalesContract() {

        String title = "Delete sales contract";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the sales id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        SalesContractDao contractDao = new MySqlSalesContractDao(dataSource);
        contractDao.remove(id);
    }

    private void addSalesContract() {

        String title = "Add sales contract";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        BigDecimal processingfee_ = BigDecimal.valueOf(0);

        System.out.print("Enter the vin: ");
        String vin = scanner.nextLine();

        System.out.print("Enter the name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the email: ");
        String email = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if(vehicle != null) {
            if(vehicle.getPrice().compareTo(BigDecimal.valueOf(10000)) < 0)
            {
                processingfee_ = BigDecimal.valueOf(295);
            }
            else processingfee_ = BigDecimal.valueOf(495);

            BigDecimal finalProcessingfee_ = processingfee_;
            BigDecimal salesTax = BigDecimal.valueOf(0.05).multiply(vehicle.getPrice());
            SalesContract contract = new SalesContract() {{
                setVin(vin);
                setCustomerName(name);
                setCustomerEmail(email);
                setRecordingFee(getRecordingFee());
                setProcessingFee(finalProcessingfee_);
                setSalesTax(salesTax);
                setSalesPrice(vehicle.getPrice());
            }};

            SalesContractDao salesContractDao = new MySqlSalesContractDao(dataSource);
            SalesContract salesContract = salesContractDao.makePurchase(contract);
            if(salesContract != null) {
                salesContractTableHeader();
                System.out.println(salesContract.displayInfo());
                System.out.println("-".repeat(172));
            }
            }
        else{
            System.out.println("Vehicle don't exist");
        }
    }

    private void deleteVehicle() {

        String title = "Delete vehicle";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the vin: ");
        String vin = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);
        vehicleDao.delete(vin);

    }

    private void createVehicle()
    {
        String title = "Create a vehicle";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the vin: ");
        String vin = scanner.nextLine();

        System.out.print("Enter the make: ");
        String make = scanner.nextLine();

        System.out.print("Enter the model: ");
        String model = scanner.nextLine();

        System.out.print("Enter the color: ");
        String color = scanner.nextLine();

        System.out.print("Enter the year: ");
        int year = scanner.nextInt();

        System.out.print("Enter the miles: ");
        int miles = scanner.nextInt();

        System.out.print("Enter the price: ");
        BigDecimal price = scanner.nextBigDecimal();

        Vehicle vehicleToInsert = new Vehicle(){{
            setVin(vin);
            setMake(make);
            setModel(model);
            setYear(year);
            setMiles(miles);
            setPrice(price);
            setColor(color);
            setSold(false);
        }};
        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicle = vehicleDao.create(vehicleToInsert);

        vehicleTableHeader();
        System.out.println(vehicle.displayVehicleInfo());
        System.out.println("-".repeat(175));
    }

    private void displayVehicles(List<Vehicle> vehicles) {

        String title = "Display vehicles";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.println();
        vehicleTableHeader();
        for (var vehicle : vehicles) {
            System.out.println(vehicle.displayVehicleInfo());
            System.out.println("-".repeat(175));
        }
    }

    private void displayVehiclesByType()
    {
        String title = "Display vehicle by type";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the Color: ");
        String type = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByType(type);

        displayVehicles(vehicles);

    }

    private void displayVehiclesInMilesRange()
    {

        String title = "Display vehicle by mileage range";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the lowest mileage: ");
        int min = scanner.nextInt();

        System.out.print("Enter the largest mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByMileageRange(min,max);

        displayVehicles(vehicles);

    }

    private void displayVehiclesByColor() {

        String title = "Display vehicle by color";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the Color: ");
        String color = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByColor(color);

        displayVehicles(vehicles);
    }

    private void displayVehiclesInYearRange() {

        String title = "Display vehicle by year range";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the start year: ");
        int min = scanner.nextInt();

        System.out.print("Enter the end year: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByYearRange(min,max);

        displayVehicles(vehicles);
    }

    private void displayVehiclesByMakeModel() {

        String title = "Display vehicle by make model";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the make: ");
        String make = scanner.nextLine();

        System.out.print("Enter the model: ");
        String model = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByMakeModel(make,model);

        displayVehicles(vehicles);
    }

    private void displayVehiclesInPriceRange()
    {
        String title = "Display vehicle by price range";

        System.out.println();
        System.out.println("=".repeat(title.length()));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(title.length()));

        System.out.print("Enter the min price range: ");
        BigDecimal min = scanner.nextBigDecimal();

        System.out.print("Enter the max price range: ");
        BigDecimal max = scanner.nextBigDecimal();
        scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByPriceRange(min,max);

        displayVehicles(vehicles);

    }
}
