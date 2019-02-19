package in.dropcodes.droptactoe;

import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.MultiPlayer.MultiPlayerBoard;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SinglePlayer.SinglePlayerLevelChooser;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

            Intent multi_player = new Intent(this, MultiPlayerBoard.class);
            multi_player.setAction(Intent.ACTION_VIEW);

            Intent single_player = new Intent(this, SinglePlayerLevelChooser.class);
            single_player.setAction(Intent.ACTION_VIEW);


            ShortcutInfo multi_player_shortcutInfo = new ShortcutInfo.Builder(this, "Shortcut_1").setShortLabel("MultiPlayer").setLongLabel("MultiPlayer").setIcon(Icon.createWithResource(this, R.drawable.ic_outline_people_outline)).setIntent(multi_player).build();
            ShortcutInfo single_player_shortcutInfo = new ShortcutInfo.Builder(this, "Shortcut_2").setShortLabel("SinglePlayer").setLongLabel("SinglePlayer").setIcon(Icon.createWithResource(this, R.drawable.ic_outline_person)).setIntent(single_player).build();

            shortcutManager.setDynamicShortcuts(Arrays.asList(multi_player_shortcutInfo, single_player_shortcutInfo));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    }

