package org.yearup.data;

import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlVehicleDao implements VehicleDao{

    private DataSource dataSource;

    public MySqlVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> getByPriceRange(BigDecimal min, BigDecimal max) {

        List<Vehicle> vehicles = new ArrayList<>();

        String sql = """
                Select * from vehicles where price Between ? and ?;
                     """;

        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {

            preparedStatement.setBigDecimal(1,min);
            preparedStatement.setBigDecimal(2,max);
            ResultSet rows = preparedStatement.executeQuery();

            while (rows.next())
            {
                String vin = rows.getString("vin"); // vim
                String make = rows.getString("make"); // make
                String model = rows.getString("model"); // model
                String color = rows.getString("color"); // color
                int year = rows.getInt("year");// year
                int miles = rows.getInt("miles"); // miles
                BigDecimal price = rows.getBigDecimal("price");// price
                boolean isSold = rows.getBoolean("sold"); // sold

                Vehicle vehicle = new Vehicle(){{
                    setVin(vin);
                    setMake(make);
                    setModel(model);
                    setColor(color);
                    setYear(year);
                    setMiles(miles);
                    setPrice(price);
                    setSold(isSold);
                }};

                vehicles.add(vehicle);
            }

            return vehicles;

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;


    }

    @Override
    public List<Vehicle> getByMakeModel(String make, String model) {
        return null;
    }

    @Override
    public List<Vehicle> getByYearRange(int min, int max) {
        return null;
    }

    @Override
    public List<Vehicle> getByColor(String color) {
        return null;
    }

    @Override
    public List<Vehicle> getByMileageRange(int min, int max) {
        return null;
    }

    @Override
    public List<Vehicle> getByType(String type) {
        return null;
    }
}
