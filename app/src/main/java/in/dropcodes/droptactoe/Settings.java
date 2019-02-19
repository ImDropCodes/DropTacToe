package in.dropcodes.droptactoe;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.dropcodes.droptactoe.Adapter.SettingAdapter;
import in.dropcodes.droptactoe.Model.SettingModel;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;

public class Settings extends AppCompatActivity implements SettingAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SettingAdapter adapter;
    private List<SettingModel> model;
    private static final String URL = "https://play.google.com/store/apps/details?id=in.dropcodes.droptactoe";

    private Switch mSwitch;
    private ThemePrefrences themePrefrences;
    private ImageView mLOGO;

    private String names[] = {"Share App", "About", "Rate on Google Play"};
    private int images[] = {R.drawable.ic_share_grey_24dp, R.drawable.ic_group_black_24dp, R.drawable.ic_star_white_24dp};

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
        setContentView(R.layout.activity_settings);

        mLOGO = findViewById(R.id.drop_tac_toe_logo_img);

        if (themePrefrences.loadDayModeState() == true) {
            mLOGO.setImageDrawable(getDrawable(R.drawable.drop_tac_toe_logo_dark));
        } else {

            mLOGO.setImageDrawable(getDrawable(R.drawable.drop_tac_toe_logo_light));
        }

        mSwitch = findViewById(R.id.day_mode_switch);
        if (themePrefrences.loadDayModeState() == true) {
            mSwitch.setChecked(true);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    themePrefrences.setDayModeState(true);
                    restartApp();
                } else {
                    themePrefrences.setDayModeState(false);
                    restartApp();
                }
            }
        });

        mRecyclerView = findViewById(R.id.setting_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        model = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            SettingModel settingModel = new SettingModel(names[i], images[i]);
            model.add(settingModel);

            adapter = new SettingAdapter(model, this);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListner(this);
        }


    }

    private void restartApp() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void BuyCodes(View view) {

        Toast.makeText(this, "Coming soon ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {


        switch (position) {
            case 0:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Drop Tac Toe");
                    String text = "\n Hey I'm playing Drop Tac Toe and I would like to invite you to download too.\n\n";
                    text = text + "https://play.google.com/store/apps/details?id=in.dropcodes.droptactoe\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(i, "Choose one to share our app"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                startActivity(new Intent(this, AboutApp.class));
                break;

            case 2:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                break;

        }
    }

}
