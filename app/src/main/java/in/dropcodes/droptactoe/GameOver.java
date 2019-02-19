package in.dropcodes.droptactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.MultiPlayer.MultiPlayerBoard;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SinglePlayer.SinglePlayerBoardEasy;
import in.dropcodes.droptactoe.SinglePlayer.SinglePlayerBoardHard;
import in.dropcodes.droptactoe.SingleTon.DropAnimation;

public class GameOver extends AppCompatActivity {

    private Bundle bundle;
    private String winner, type;
    private TextView mText, mGameOverText;
    private ImageView mImageReset;
    private LinearLayout mLinearLayout;
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
        setContentView(R.layout.activity_game_over);

        bundle = getIntent().getExtras();
        winner = bundle.getString("winner");
        type = bundle.getString("type");

        mText = findViewById(R.id.textView);
        mImageReset = findViewById(R.id.restart_game);
        mGameOverText = findViewById(R.id.textView_game_over);
        mLinearLayout = findViewById(R.id.game_over_text_lly);

        DropAnimation animation = new DropAnimation();
        animation.RotateClock(this, mImageReset);
        animation.SlideFromRight(this, mGameOverText);
        animation.SlideFromLeft(this, mLinearLayout);

        if (type.equals("single_easy")) {
            if (winner.equals("X")) {
                mText.setText("You Won");
            } else if (winner.equals("O")) {
                mText.setText("Bot Won");
            } else if (winner.equals("D")) {
                mText.setText("Match Draw");
            }
        }

        if (type.equals("single_hard")) {
            if (winner.equals("X")) {
                mText.setText("You Won");
            } else if (winner.equals("O")) {
                mText.setText("Bot Won");
            } else if (winner.equals("D")) {
                mText.setText("Match Draw");
            }
        }

        if (type.equals("multi")) {
            if (winner.equals("X")) {
                mText.setText("Player X Won");
            } else if (winner.equals("O")) {
                mText.setText("Player O Won");
            } else if (winner.equals("D")) {
                mText.setText("Match Draw");
            }
        }

        mImageReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equals("single_easy")) {
                    startActivity(new Intent(GameOver.this, SinglePlayerBoardEasy.class));
                    finish();
                } else if (type.equals("single_hard")) {
                    startActivity(new Intent(GameOver.this, SinglePlayerBoardHard.class));
                    finish();
                } else if (type.equals("multi")) {
                    startActivity(new Intent(GameOver.this, MultiPlayerBoard.class));
                    finish();
                }
            }
        });

    }
}
