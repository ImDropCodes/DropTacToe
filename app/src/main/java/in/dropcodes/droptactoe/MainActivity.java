package in.dropcodes.droptactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.MultiPlayer.MultiPlayerBoard;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SinglePlayer.SinglePlayerLevelChooser;
import in.dropcodes.droptactoe.SingleTon.DropAnimation;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLayout;
    private Button settingsBtn, mSinglePlayer, mMultiPlayer;
    private ThemePrefrences themePrefrences;
    private ImageView mLOGO;


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
        setContentView(R.layout.activity_main);

        mLOGO = findViewById(R.id.main_app_logo_img);
        mLayout = findViewById(R.id.main_text_lly);
        settingsBtn = findViewById(R.id.main_settings);
        mSinglePlayer = findViewById(R.id.btn_single_player);
        mMultiPlayer = findViewById(R.id.btn_multi_player);

        if (themePrefrences.loadDayModeState() == true) {
            mLOGO.setImageDrawable(getDrawable(R.drawable.drop_tac_toe_logo_dark));
        } else {
            mLOGO.setImageDrawable(getDrawable(R.drawable.drop_tac_toe_logo_light));
        }

        //Singleton Animation
        DropAnimation dropAnimation = new DropAnimation();

        //FadeIn animation for LinearLayout
        dropAnimation.FadeIn(this, mLayout);

        //RotateClockWise animation for setting button
        dropAnimation.RotateClock(this, settingsBtn);

        dropAnimation.SlideFromLeft(this, mSinglePlayer);

        dropAnimation.SlideFromRight(this, mMultiPlayer);

    }

    public void SinglePlayer(View view) {
        startActivity(new Intent(MainActivity.this, SinglePlayerLevelChooser.class));
    }

    public void MultiPlayer(View view) {
        startActivity(new Intent(MainActivity.this, MultiPlayerBoard.class));

    }

    public void Settings(View view) {

        startActivity(new Intent(MainActivity.this, Settings.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
