package in.dropcodes.droptactoe.SinglePlayer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.GameOver;
import in.dropcodes.droptactoe.MainActivity;
import in.dropcodes.droptactoe.R;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SingleTon.DropAnimation;

public class SinglePlayerBoardHard extends AppCompatActivity implements View.OnClickListener {

    private static final int TURNS = 9;
    private Button[][] mButtons;
    private boolean mAIActive;
    private int mTurnsLeft;
    private DropAnimation animation;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    private Button player_1_btn, player_2_btn;
    private TextView mVs;
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
        setContentView(R.layout.activity_single_player_board_hard);

        mButtons = new Button[3][3];
        mAIActive = true;
        mTurnsLeft = TURNS;

        animation = new DropAnimation();

        b1 = findViewById(R.id.button_00);
        b2 = findViewById(R.id.button_01);
        b3 = findViewById(R.id.button_02);
        b4 = findViewById(R.id.button_10);
        b5 = findViewById(R.id.button_11);
        b6 = findViewById(R.id.button_12);
        b7 = findViewById(R.id.button_20);
        b8 = findViewById(R.id.button_21);
        b9 = findViewById(R.id.button_22);

        player_1_btn = findViewById(R.id.player_1_btn_s_h);
        player_2_btn = findViewById(R.id.player_2_btn_s_h);
        mVs = findViewById(R.id.single_player_vs_h);

        animation.SlideFromLeft(this, player_1_btn);
        animation.SlideFromRight(this, player_2_btn);

        animation.FadeIn(this, b1);
        animation.FadeIn(this, b4);
        animation.FadeIn(this, b7);

        animation.FadeIn(this, b3);
        animation.FadeIn(this, b6);
        animation.FadeIn(this, b9);

        animation.FadeIn(this, b2);
        animation.FadeIn(this, b5);
        animation.FadeIn(this, b8);

        animation.FadeIn(this, mVs);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
                mButtons[i][j] = findViewById(resourceID);
                mButtons[i][j].setOnClickListener(this);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {


        if (!((Button) view).getText().toString().equals("")) return;

        if (mAIActive) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(getColor(R.color.colorPrimary));
            --mTurnsLeft;
            if (isEndGame()) return;
            Button button = findViewById(findBestMove());
            button.setText("O");
            button.setTextColor(getColor(R.color.white));
            --mTurnsLeft;
            if (isEndGame()) return;
        }
    }


    private boolean isEndGame() {
        String result = evaluateBoard(new String[3][3], false);
        if (result.equals("X")) {
            playerWin();
            return true;
        } else if (result.equals("O")) {
            BotWin();
            return true;
        } else if (mTurnsLeft == 0) {
            tieGame();
            return true;
        }
        return false;
    }

    private String evaluateBoard(String[][] values, boolean minimax) {

        if (!minimax) {
            values = new String[3][3];
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    values[i][j] = mButtons[i][j].getText().toString();
        }

        for (int i = 0; i < 3; i++)
            if (!values[i][0].equals("") && values[i][0].equals(values[i][1]) && values[i][1].equals(values[i][2]))
                return values[i][0];


        for (int i = 0; i < 3; i++)
            if (!values[0][i].equals("") && values[0][i].equals(values[1][i]) && values[1][i].equals(values[2][i]))
                return values[0][i];


        if (!values[0][0].equals("") && values[0][0].equals(values[1][1]) && values[1][1].equals(values[2][2]))
            return values[0][0];


        if (!values[0][2].equals("") && values[0][2].equals(values[1][1]) && values[1][1].equals(values[2][0]))
            return values[0][2];

        return "";
    }

    private void playerWin() {
        Intent intent = new Intent(SinglePlayerBoardHard.this, GameOver.class);
        intent.putExtra("winner", "X");
        intent.putExtra("type", "single_hard");
        startActivity(intent);
        finish();

    }

    private void BotWin() {


        Intent intent = new Intent(SinglePlayerBoardHard.this, GameOver.class);
        intent.putExtra("winner", "O");
        intent.putExtra("type", "single_hard");
        startActivity(intent);
        finish();
    }

    private void tieGame() {
        Intent intent = new Intent(SinglePlayerBoardHard.this, GameOver.class);
        intent.putExtra("winner", "D");
        intent.putExtra("type", "single_hard");
        startActivity(intent);
        finish();
    }


    private int findBestMove() {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;

        String[][] values = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                values[i][j] = mButtons[i][j].getText().toString();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (values[i][j].equals("")) {
                    values[i][j] = "O";
                    --mTurnsLeft;
                    int currentValue = minimax(values, false);
                    ++mTurnsLeft;
                    values[i][j] = "";
                    if (currentValue > bestValue) {
                        bestValue = currentValue;
                        String buttonID = "button_" + i + j;
                        bestMove = getResources().getIdentifier(buttonID, "id", getPackageName());
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(String[][] values, boolean isMax) {
        String result = evaluateBoard(values, true);
        if (result.equals("X")) return -100;
        else if (result.equals("O")) return 100;
        else if (mTurnsLeft == 0) return 0;

        if (isMax) {
            int bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (values[i][j].equals("")) {
                        values[i][j] = "O";
                        --mTurnsLeft;
                        bestValue = Math.max(bestValue, minimax(values, !isMax));
                        ++mTurnsLeft;
                        values[i][j] = "";
                    }
                }
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (values[i][j].equals("")) {
                        values[i][j] = "X";
                        --mTurnsLeft;
                        bestValue = Math.min(bestValue, minimax(values, !isMax));
                        ++mTurnsLeft;
                        values[i][j] = "";
                    }
                }
            }
            return bestValue;
        }
    }

    @Override
    public void onBackPressed() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog,viewGroup,false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView dialog_tv = dialogView.findViewById(R.id.dialog_text_view);
        Button yes = dialogView.findViewById(R.id.dialog_yes);
        Button no = dialogView.findViewById(R.id.dialog_no);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SinglePlayerBoardHard.this, SinglePlayerLevelChooser.class));
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });

    }
}


