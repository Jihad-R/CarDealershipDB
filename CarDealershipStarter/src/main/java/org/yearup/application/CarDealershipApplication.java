package org.yearup.application;

import org.yearup.data.MySqlSalesContractDao;
import org.yearup.data.MySqlVehicleDao;
import org.yearup.data.SalesContractDao;
import org.yearup.data.VehicleDao;
import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
        addSalesContract();
    }

    private void addSalesContract() {
//
//        private int salesId;
//        private String vin;
//        private String customerName;
//        private String customerEmail;
//        private BigDecimal salesPrice;
//        private BigDecimal recordingFee;
//        private BigDecimal processingFee;
//        private BigDecimal salesTax;
        BigDecimal processingfee_ = BigDecimal.valueOf(0);

        System.out.println("Enter the vin: ");
        String vin = scanner.nextLine();

        System.out.println("Enter the name: ");
        String name = scanner.nextLine();

        System.out.println("Enter the email: ");
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
            if(salesContract != null)
            System.out.println(salesContract.displayInfo());
        }
        else{
            System.out.println("Vehicle don't exist");
        }
    }

    private void deleteVehicle() {
        System.out.print("Enter the vin: ");
        String vin = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);
        vehicleDao.delete(vin);

    }

    private void createVehicle()
    {
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

        System.out.println(vehicle.displayVehicleInfo());
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        System.out.println();
        System.out.println("Results");
        System.out.println("-".repeat(35));

        for (var vehicle : vehicles) {
            System.out.print(vehicle.displayVehicleInfo());
            System.out.println("-".repeat(35));
        }
    }

    private void displayVehiclesByType()
    {
        System.out.print("Enter the Color: ");
        String type = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByType(type);

        displayVehicles(vehicles);

    }

    private void displayVehiclesInMilesRange()
    {
        System.out.print("Enter the lowest mileage: ");
        int min = scanner.nextInt();

        System.out.print("Enter the largest mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByYearRange(min,max);

        displayVehicles(vehicles);

    }

    private void displayVehiclesByColor() {
        System.out.print("Enter the Color: ");
        String color = scanner.nextLine();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        var vehicles = vehicleDao.getByColor(color);

        displayVehicles(vehicles);
    }

    private void displayVehiclesInYearRange() {
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
