package in.dropcodes.droptactoe.Model;

import android.widget.ImageView;

public class SettingModel {

    private String name;
    private int imageView;

    public SettingModel(String name, int imageView) {
        this.name = name;
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public int getImageView() {
        return imageView;
    }
}
