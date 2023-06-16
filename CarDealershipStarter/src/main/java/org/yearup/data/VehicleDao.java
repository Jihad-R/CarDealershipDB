package org.yearup.data;

import org.yearup.models.Vehicle;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleDao {

    Vehicle getVehicleByVin(String vin);
    List<Vehicle> getByPriceRange(BigDecimal min, BigDecimal max);

    List<Vehicle> getByMakeModel(String make,String model);

    List<Vehicle> getByYearRange(int min,int max);

    List<Vehicle> getByColor(String color);

    List<Vehicle> getByMileageRange(int min, int max);

    List<Vehicle> getByType(String type);

    Vehicle create(Vehicle vehicle);
    void delete (String vin);

}
