package julius.barde.com.dreamlisterapp;

import android.graphics.Bitmap;

/**
 * Created by JBluee on 10/7/2017.
 */

public class DreamItem {
    private int ID;
    public Bitmap imageID;
    public String price;
    public String name;
    public String description;





    public  int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Bitmap getImageID() {
        return imageID;
    }

    public void setImageID(Bitmap imageID) {
        this.imageID = imageID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
