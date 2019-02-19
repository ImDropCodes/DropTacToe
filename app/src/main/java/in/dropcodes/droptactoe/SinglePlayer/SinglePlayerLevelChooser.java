package in.dropcodes.droptactoe.SinglePlayer;

import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.R;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SinglePlayerLevelChooser extends AppCompatActivity {

    private ThemePrefrences themePrefrences;
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
        setContentView(R.layout.activity_single_player_level_choser);
    }

    public void SinglePlayerEasy(View view) {
        startActivity(new Intent(SinglePlayerLevelChooser.this, SinglePlayerBoardEasy.class));
        finish();
    }

    public void SinglePlayerHard(View view) {
        startActivity(new Intent(SinglePlayerLevelChooser.this, SinglePlayerBoardHard.class));
        finish();
    }
}
