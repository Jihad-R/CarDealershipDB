package org.yearup.data;

import org.yearup.models.SalesContract;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlSalesContractDao implements SalesContractDao{

    private DataSource dataSource;

    public MySqlSalesContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public SalesContract makePurchase(SalesContract contract) {

        String sql = """
                     Insert Into Sales_contracts (vin,customer_name,customer_email,
                     sales_price,recording_fee,processing_fee,sales_tax) Values 
                     (?,?,?,?,?,?,?);
                     """;

        String sql1 = """
                    select * from sales_contracts as s\s
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
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
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


}
