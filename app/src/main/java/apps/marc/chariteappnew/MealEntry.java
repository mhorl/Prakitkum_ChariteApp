package apps.marc.chariteappnew;

import java.sql.Time;
import java.util.Date;

public class MealEntry {
    private int meal_id;
    private String meal_name;
    private Date meal_date;
    private boolean meal_committed;

    public MealEntry(int meal_id, String meal_name, Date meal_date, boolean meal_committed){
        this.meal_id = meal_id;
        this.meal_name = meal_name;
        this.meal_date = meal_date;
        this.meal_committed = meal_committed;
    }


    //Getter
    public int getMealId() {
        return meal_id;
    }
    public String getMealName() {
        return meal_name;
    }
    public Date getMealDate() {
        return meal_date;
    }
    public  boolean getMealCommitted() {
        return meal_committed;
    }

    //Setter
    public void setMealId(int meal_id) {
        this.meal_id = meal_id;
    }
    public void setMealName(String meal_product) {
        this.meal_name = meal_product;
    }
    public void setMealDate(Date meal_date) {
        this.meal_date = meal_date;
    }
    public void setMealCommitted(boolean meal_committed) {
        this.meal_committed = meal_committed;
    }
}
