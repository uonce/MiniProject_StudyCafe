package dto;

public class RatePlanDTO {
    private int planId;
    private String planName;
    private int price;
    private String planType; // ENUM('TIME_PASS', 'PERIOD_PASS')
    private Integer providedTime; // 분 단위
    private Integer validDays; // 일 단위

    public RatePlanDTO() {}

    public RatePlanDTO(int planId, String planName, int price, String planType, Integer providedTime, Integer validDays) {
        this.planId = planId;
        this.planName = planName;
        this.price = price;
        this.planType = planType;
        this.providedTime = providedTime;
        this.validDays = validDays;
    }

    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }

    public Integer getProvidedTime() { return providedTime; }
    public void setProvidedTime(Integer providedTime) { this.providedTime = providedTime; }

    public Integer getValidDays() { return validDays; }
    public void setValidDays(Integer validDays) { this.validDays = validDays; }

    @Override
    public String toString() {
        return "RatePlanDTO [planId=" + planId + ", name=" + planName + ", price=" + price + ", type=" + planType + "]";
    }
}

