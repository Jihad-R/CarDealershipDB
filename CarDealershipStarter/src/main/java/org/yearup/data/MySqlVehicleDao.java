package org.yearup.data;

import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlVehicleDao implements VehicleDao{

    private DataSource dataSource;
    private List<Vehicle> vehicles = new ArrayList<>();


    public MySqlVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Vehicle> getByPriceRange(BigDecimal min, BigDecimal max) {

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
                Vehicle vehicle = createVehicleFromResultSet(rows);
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

        String sql = """
                Select * from vehicles 
                where make = ? and model = ?;
                     """;

        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {

            preparedStatement.setString(1,make);
            preparedStatement.setString(2,model);
            ResultSet rows = preparedStatement.executeQuery();

            while (rows.next())
            {
                Vehicle vehicle = createVehicleFromResultSet(rows);
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
    public List<Vehicle> getByYearRange(int min, int max) {

        String sql = """
                Select * from vehicles 
                where year Between ? and ?;
                     """;

        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {

            preparedStatement.setInt(1,min);
            preparedStatement.setInt(2,max);

            ResultSet rows = preparedStatement.executeQuery();

            while (rows.next())
            {
                Vehicle vehicle = createVehicleFromResultSet(rows);
                vehicles.add(vehicle);
            }

            return vehicles;

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;    }

    @Override
    public List<Vehicle> getByColor(String color) {

        String sql = """
                Select * from vehicles 
                where color = ?;
                     """;

        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {

            preparedStatement.setString(1,color);
            ResultSet rows = preparedStatement.executeQuery();

            while (rows.next())
            {
                Vehicle vehicle = createVehicleFromResultSet(rows);
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
    public List<Vehicle> getByMileageRange(int min, int max) {
        return null;
    }

    @Override
    public List<Vehicle> getByType(String type) {
        return null;
    }

    private Vehicle createVehicleFromResultSet(ResultSet rows) throws SQLException {
        String vin = rows.getString("vin");
        String make = rows.getString("make");
        String model = rows.getString("model");
        String color = rows.getString("color");
        int year = rows.getInt("year");
        int miles = rows.getInt("miles");
        BigDecimal price = rows.getBigDecimal("price");
        boolean isSold = rows.getBoolean("sold");

        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vin);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setYear(year);
        vehicle.setMiles(miles);
        vehicle.setPrice(price);
        vehicle.setSold(isSold);

        return vehicle;
    }
}
