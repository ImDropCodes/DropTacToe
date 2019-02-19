package in.dropcodes.droptactoe;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;

public class AboutApp extends AppCompatActivity {
    private ThemePrefrences themePrefrences;
    private ImageView mDCImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        themePrefrences = new ThemePrefrences(this);
        //Theme
        if (themePrefrences.loadDayModeState() == true) {
            setTheme(R.style.AppThemeLight);
        } else {
            setTheme(R.style.AppThemeDark);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        mDCImage = findViewById(R.id.dc_logo_img);

        if (themePrefrences.loadDayModeState() == true) {
            mDCImage.setImageDrawable(getDrawable(R.drawable.dc_logo_dark));
        } else {
            mDCImage.setImageDrawable(getDrawable(R.drawable.dc_logo_light));
        }
    }
}
