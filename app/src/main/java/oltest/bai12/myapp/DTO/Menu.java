package oltest.bai12.myapp.DTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


public class Menu {

    int FoodId;
    String FoodName;
    String FoodCost;
    int sl;
    byte[] FoodImage;

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public byte[] getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(byte[] foodImage) {
        FoodImage = foodImage;
    }

    public String getFoodCost() {
        return FoodCost;
    }

    public void setFoodCost(String foodCost) {
        FoodCost = foodCost;
    }
}
