package org.yearup;

import org.apache.commons.dbcp2.BasicDataSource;
import org.yearup.application.CarDealershipApplication;

import javax.sql.DataSource;

public class Main
{
    public static void main(String[] args)
    {
        String username = "root";
        String password = "123!";
        String url = "jdbc:mysql://localhost:3306/car_dealership";

        DataSource basicDataSource = new BasicDataSource(){{
            setUsername(username);
            setPassword(password);
            setUrl(url);
        }};
        CarDealershipApplication app = new CarDealershipApplication(basicDataSource);
        app.run();
    }
}