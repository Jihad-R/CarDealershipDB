package org.yearup.data;

import org.yearup.models.Dealership;
import org.yearup.models.Vehicle;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDealershipDao implements DealershipDao{

    private DataSource dataSource;

    public MySqlDealershipDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dealership getDealerShip(int id)
    {

        String sql = """
                     Select dealership_id, 
                            name, 
                            address, 
                            phone
                            From dealerships
                            where dealership_id = ?
                     """;
        Dealership dealership = null;
        try
                (
                        Connection connection = dataSource.getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                )
        {

            preparedStatement.setInt(1,id);
            ResultSet rows = preparedStatement.executeQuery();



            if (rows.next())
            {
                int dealershipId = rows.getInt("dealership_id");
                String name = rows.getString("name");
                String address = rows.getString("address");
                String phone = rows.getString("phone");
                dealership = new Dealership(){{
                    setDealershipId(dealershipId);
                    setName(name);
                    setAddress(address);
                    setPhone(phone);
                }};
            }

            return dealership;

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;
    }


}
