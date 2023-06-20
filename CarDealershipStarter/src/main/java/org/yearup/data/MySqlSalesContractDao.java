package org.yearup.data;

import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlSalesContractDao implements SalesContractDao{

    private DataSource dataSource;

    public MySqlSalesContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SalesContract getContractByID(int id) {

        String sql = """
                     Select sales_id, 
                     vin, 
                     customer_name, 
                     customer_email, 
                     sales_price, 
                     recording_fee, 
                     processing_fee, 
                     sales_tax
                     From Sales_contracts
                     where sales_id = ?;
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
                SalesContract salesContract = new SalesContract(){{
                    setSalesId(row.getInt("sales_id"));
                    setVin(row.getString("vin"));
                    setCustomerName(row.getString("customer_name"));
                    setCustomerEmail(row.getString("customer_email"));
                    setSalesPrice(row.getBigDecimal("sales_price"));
                    setRecordingFee(row.getBigDecimal("recording_fee"));
                    setProcessingFee(row.getBigDecimal("processing_fee"));
                    setSalesTax(row.getBigDecimal("sales_tax"));
                }};

                return salesContract;
            }

        }catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public List<SalesContract> getAllContracts()
    {
        List<SalesContract> contracts = new ArrayList<>();
        String sql = """
                    Select sales_id, 
                     vin, 
                     customer_name, 
                     customer_email, 
                     sales_price, 
                     recording_fee, 
                     processing_fee, 
                     sales_tax
                     From Sales_contracts;
                    """;


        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                )
        {
            ResultSet rows = statement.executeQuery(sql);

            while(rows.next())
            {
                int sales_id = rows.getInt("sales_id");
                String vin = rows.getString("vin");
                String customer_name = rows.getString("customer_name");
                String customer_email = rows.getString("customer_email");
                BigDecimal sales_price = rows.getBigDecimal("sales_price");
                BigDecimal recording_fee = rows.getBigDecimal("recording_fee");
                BigDecimal processing_fee = rows.getBigDecimal("processing_fee");
                BigDecimal sales_tax = rows.getBigDecimal("sales_tax");

                SalesContract contract = new SalesContract(){{
                    setSalesId(sales_id);
                    setVin(vin);
                    setCustomerName(customer_name);
                    setCustomerEmail(customer_email);
                    setRecordingFee(recording_fee);
                    setProcessingFee(processing_fee);
                    setSalesTax(sales_tax);
                    setSalesPrice(sales_price);
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
    public SalesContract makePurchase(SalesContract contract) {

        String sql = """
                     Insert Into Sales_contracts (vin,customer_name,customer_email,
                     sales_price,recording_fee,processing_fee,sales_tax) Values 
                     (?,?,?,?,?,?,?);
                     """;

        String sql1 = """
                    select * from sales_contracts as s 
                    right join vehicles as v on v.vin = s.vin
                    where v.sold = 0 and v.vin = ?;
                      """;

        String updateSql = """
                            UPDATE vehicles SET sold = 1 WHERE vin = ?;                           
                            """;

        SalesContract salesContract = contract;
        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                        PreparedStatement preparedStatement2 = connection.prepareStatement(updateSql);
                )
        {


            preparedStatement1.setString(1,salesContract.getVin());
            ResultSet resultSet = preparedStatement1.executeQuery();

            if(!resultSet.next()){
                System.out.println("Vehicle is not available for sale");
            }
            else
            {
                preparedStatement.setString(1, salesContract.getVin());
                preparedStatement.setString(2, salesContract.getCustomerName());
                preparedStatement.setString(3, salesContract.getCustomerEmail());
                preparedStatement.setBigDecimal(4, salesContract.getSalesPrice());
                preparedStatement.setBigDecimal(5, salesContract.getRecordingFee());
                preparedStatement.setBigDecimal(6, salesContract.getProcessingFee());
                preparedStatement.setBigDecimal(7,salesContract.getSalesTax());

                int row = preparedStatement.executeUpdate();

                if(row != 0)
                {

                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys())
                    {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            salesContract.setSalesId(generatedId);
                        }
                    }catch (SQLException e)
                    {
                        System.out.println(e);
                    }

                    System.out.printf("Added %d row(s)\n",row);
                    preparedStatement2.setString(1,salesContract.getVin());
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
                     DELETE FROM sales_contracts 
                     WHERE sales_id = ?;
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
