package org.yearup.application;

import org.yearup.data.MySqlVehicleDao;
import org.yearup.data.VehicleDao;
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
