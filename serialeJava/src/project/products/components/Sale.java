package project.products.components;

import java.util.Date;

public class Sale {
    private Date startDate;
    private Date endDate;
    private float reduction;

    public Sale(Date startDate, Date endDate, float reduction) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reduction = reduction;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getReduction() {
        return reduction;
    }
}
