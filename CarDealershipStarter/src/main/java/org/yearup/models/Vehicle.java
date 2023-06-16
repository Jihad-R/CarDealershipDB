package org.yearup.models;

import java.math.BigDecimal;

public class Vehicle
{
    private String vin;
    private String make;
    private String model;
    private String color;
    private int year;
    private int miles;
    private BigDecimal price;
    private boolean isSold;

    public String getVin()
    {
        return vin;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getMiles()
    {
        return miles;
    }

    public void setMiles(int miles)
    {
        this.miles = miles;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public boolean isSold()
    {
        return isSold;
    }

    public void setSold(boolean sold)
    {
        isSold = sold;
    }
}
