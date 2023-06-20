package org.yearup.data;

import org.yearup.models.LeaseContract;
import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlLeaseContractDao implements LeaseContractDao{

    private DataSource dataSource;

    public MySqlLeaseContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public LeaseContract getContractByID(int id) {
        String sql = """
                     Select lease_id, 
                     vin, 
                     customer_name, 
                     customer_email, 
                     sales_price, 
                     ending_value, 
                     lease_fee, 
                     sales_tax,
                     monthly_payment
                     From lease_contracts
                     where lease_id = ?;
                     """;

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        )
        {
            preparedStatement.setInt(1,id);
            ResultSet row = preparedStatement.executeQuery();

            if (row.next())
            {
                LeaseContract salesContract = new LeaseContract(){{
                    setLeaseId(row.getInt("lease_id"));
                    setVin(row.getString("vin"));
                    setCustomerName(row.getString("customer_name"));
                    setCustomerEmail(row.getString("customer_email"));
                    setSalesPrice(row.getBigDecimal("sales_price"));
                    setEndingValue(row.getBigDecimal("ending_value"));
                    setLeaseFee(row.getBigDecimal("lease_fee"));
                    setSalesTax(row.getBigDecimal("sales_tax"));
                    setMonthlyPayment(row.getBigDecimal("monthly_payment"));
                }};

                return salesContract;
            }

        }catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;    }

    @Override
    public List<LeaseContract> getAllContracts() {
        List<LeaseContract> contracts = new ArrayList<>();
        String sql = """
                     Select lease_id, 
                     vin, 
                     customer_name, 
                     customer_email, 
                     sales_price, 
                     ending_value, 
                     lease_fee, 
                     sales_tax,
                     monthly_payment
                     From lease_contracts;
                     """;


        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
        )
        {
            ResultSet rows = statement.executeQuery(sql);

            while(rows.next())
            {
                LeaseContract contract = new LeaseContract()
                {{
                    setLeaseId(rows.getInt("lease_id"));
                    setVin(rows.getString("vin"));
                    setCustomerName(rows.getString("customer_name"));
                    setCustomerEmail(rows.getString("customer_email"));
                    setSalesPrice(rows.getBigDecimal("sales_price"));
                    setEndingValue(rows.getBigDecimal("ending_value"));
                    setLeaseFee(rows.getBigDecimal("lease_fee"));
                    setSalesTax(rows.getBigDecimal("sales_tax"));
                    setMonthlyPayment(rows.getBigDecimal("monthly_payment"));
                }};

                contracts.add(contract);
            }

            return contracts;
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public LeaseContract makeLease(LeaseContract contract) {

        String sql = """
                     INSERT INTO lease_contracts 
                     (vin, customer_name, customer_email, 
                     sales_price, ending_value, 
                     lease_fee, sales_tax, monthly_payment) 
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                     """;

        String sql1 = """
                    select * from lease_contracts as l 
                    right join vehicles as v on v.vin = l.vin
                    where v.sold = 0 and v.vin = ?;
                      """;

        String updateSql = """
                            UPDATE vehicles SET sold = 1 WHERE vin = ?;                           
                            """;

        LeaseContract leaseContract = contract;
        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                        PreparedStatement preparedStatement2 = connection.prepareStatement(updateSql);
                )
        {


            preparedStatement1.setString(1,leaseContract.getVin());
            ResultSet resultSet = preparedStatement1.executeQuery();

            if(!resultSet.next()){
                System.out.println("Vehicle is not available for sale");
            }
            else
            {
                preparedStatement.setString(1, leaseContract.getVin());
                preparedStatement.setString(2, leaseContract.getCustomerName());
                preparedStatement.setString(3, leaseContract.getCustomerEmail());
                preparedStatement.setBigDecimal(4, leaseContract.getSalesPrice());
                preparedStatement.setBigDecimal(5, leaseContract.getLeaseFee());
                preparedStatement.setBigDecimal(6, leaseContract.getEndingValue());
                preparedStatement.setBigDecimal(7,leaseContract.getMonthlyPayment());
                preparedStatement.setBigDecimal(8,leaseContract.getSalesTax());

                int row = preparedStatement.executeUpdate();

                if(row != 0)
                {

                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                    {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            leaseContract.setLeaseId(generatedId);
                        }
                    }catch (SQLException e)
                    {
                        System.out.println(e);
                    }

                    System.out.printf("Added %d row(s)\n",row);
                    preparedStatement2.setString(1,leaseContract.getVin());
                    preparedStatement2.executeUpdate();

                }

                return contract;
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;

    }

    @Override
    public void remove(int id) {
        String sql = """
                     DELETE FROM lease_contracts 
                     WHERE lease_id = ?;
                     """;

        String updateSql = """
                            UPDATE vehicles SET sold = 0 WHERE vin = ?;                           
                            """;
        String sql2 = """
                    Select vin from sales_contracts where sales_id = ?;
                    """;
        Vehicle vehicle = null;
        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
                        PreparedStatement preparedStatement2 = connection.prepareStatement(updateSql);

                ) {

            preparedStatement1.setInt(1,id);

            ResultSet row = preparedStatement1.executeQuery();

            if (row.next())
            {
                VehicleDao vehicleDao = new MySqlVehicleDao(dataSource);
                vehicle = vehicleDao.getVehicleByVin(row.getString("vin"));
                if (vehicle.getVin() != null)
                {
                    preparedStatement2.setString(1,vehicle.getVin());
                    preparedStatement2.executeUpdate();
                }
            }


            preparedStatement.setInt(1, id);
            int deleted = preparedStatement.executeUpdate();

            if(deleted>-1)
            {
                System.out.printf("Updated %d row(s)\n",deleted);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

    }
}
