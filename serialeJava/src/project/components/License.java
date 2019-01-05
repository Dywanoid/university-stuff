package project.components;

public class License {
    private boolean monthly;
    private float fee;
    private float monthlyFee;
    private int endDate;

    public License(boolean monthly, float fee, float monthlyFee, int endDate) {
        this.monthly = monthly;
        this.fee = fee;
        this.monthlyFee = monthlyFee;
        this.endDate = endDate;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public float getFee() {
        return fee;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public int getEndDate() {
        return endDate;
    }
}
