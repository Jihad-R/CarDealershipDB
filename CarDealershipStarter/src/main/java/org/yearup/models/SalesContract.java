package org.yearup.models;

import java.math.BigDecimal;

public class SalesContract
{
    private int salesId;
    private String vin;
    private String customerName;
    private String customerEmail;
    private BigDecimal salesPrice;
    private BigDecimal recordingFee;
    private BigDecimal processingFee;
    private BigDecimal salesTax;

    public int getSalesId()
    {
        return salesId;
    }

    public void setSalesId(int salesId)
    {
        this.salesId = salesId;
    }

    public String getVin()
    {
        return vin;
    }

    public void setVin(String vin)
    {
        this.vin = vin;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail)
    {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getSalesPrice()
    {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice)
    {
        this.salesPrice = salesPrice;

    }

    public BigDecimal getRecordingFee()
    {
        return recordingFee;
    }

    public void setRecordingFee(BigDecimal recordingFee)
    {
        this.recordingFee = BigDecimal.valueOf(100);
    }

    public BigDecimal getProcessingFee()
    {
        return processingFee;
    }

    public void setProcessingFee(BigDecimal processingFee)
    {
        this.processingFee = processingFee;
    }

    public BigDecimal getSalesTax()
    {
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax)
    {
        this.salesTax = salesTax;
    }

    public String displayInfo()
    {
        return String.format("|%3d|" +
                      "%20s|" +
                       "%50s|" +
                       "%50s|" +
                       "%10.2f|" +
                       "%10.2f|" +
                       "%10.2f|" +
                       "%10.2f|",getSalesId(),getVin(),getCustomerName(),
                                        getCustomerEmail(),getSalesPrice(),getRecordingFee(),
                                        getProcessingFee(),getSalesTax());

    }
}
