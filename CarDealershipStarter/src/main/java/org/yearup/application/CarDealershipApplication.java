package org.yearup.application;

import org.yearup.data.MySqlVehicleDao;
import org.yearup.data.VehicleDao;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
        displayVehiclesInPriceRange();
    }

    private void displayVehiclesInPriceRange()
    {
        System.out.print("Enter the min price range: ");
        BigDecimal min = scanner.nextBigDecimal();

        System.out.print("Enter the max price range: ");
        BigDecimal max = scanner.nextBigDecimal();

        VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);

        System.out.println();
        System.out.println("Results");
        System.out.println("-".repeat(35));
        var vehicles = vehicleDao.getByPriceRange(min,max);

        for (var vehicle: vehicles)
        {
            System.out.println("Vin: "+vehicle.getVin());
            System.out.println("Make (Model): "+vehicle.getMake()+" ("+vehicle.getModel()+")");
            System.out.println("Color: "+vehicle.getColor());
            System.out.println("Mileage: "+vehicle.getMiles());
            System.out.println("In Stock: "+!(vehicle.isSold()));
            System.out.println("Price: "+vehicle.getPrice());
            System.out.println("-".repeat(35));
        }

    }
}
