package in.dropcodes.droptactoe.MultiPlayer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.GameOver;
import in.dropcodes.droptactoe.MainActivity;
import in.dropcodes.droptactoe.R;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SingleTon.DropAnimation;

public class MultiPlayerBoard extends AppCompatActivity {

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int turn = 1;
    private Button player_1_btn_m, player_2_btn_m;
    private TextView mVs, mMatchCountTV;
    private DropAnimation animation;
    private TextView mPlayer_1_Score_tv, mPlayer_2_Score_tv;
    private int player_1_score = 0;
    private int player_2_score = 0;
    private int match_count = 3;
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
        setContentView(R.layout.activity_multi_player_board);

        b1 = findViewById(R.id.button_1_m);
        b2 = findViewById(R.id.button_2_m);
        b3 = findViewById(R.id.button_3_m);
        b4 = findViewById(R.id.button_4_m);
        b5 = findViewById(R.id.button_5_m);
        b6 = findViewById(R.id.button_6_m);
        b7 = findViewById(R.id.button_7_m);
        b8 = findViewById(R.id.button_8_m);
        b9 = findViewById(R.id.button_9_m);

        player_1_btn_m = findViewById(R.id.player_1_btn_m);
        player_2_btn_m = findViewById(R.id.player_2_btn_m);

        mVs = findViewById(R.id.multi_player_vs);

        mPlayer_1_Score_tv = findViewById(R.id.player_1_score_multi);
        mPlayer_2_Score_tv = findViewById(R.id.player_2_score_multi);

        mMatchCountTV = findViewById(R.id.match_count_multi);

        mPlayer_1_Score_tv.setText("0");
        mPlayer_2_Score_tv.setText("0");

        animation = new DropAnimation();

        animation.SlideFromLeft(this, player_1_btn_m);
        animation.SlideFromRight(this, player_2_btn_m);

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
        animation.FadeIn(this, mMatchCountTV);
        animation.FadeIn(this, mPlayer_1_Score_tv);
        animation.FadeIn(this, mPlayer_2_Score_tv);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void GameClick(View view) {

        Button button_view = (Button) view;
        int Button_Clicked = 0;

        switch (button_view.getId()) {
            case R.id.button_1_m:
                Button_Clicked = 1;
                break;
            case R.id.button_2_m:
                Button_Clicked = 2;
                break;
            case R.id.button_3_m:
                Button_Clicked = 3;
                break;
            case R.id.button_4_m:
                Button_Clicked = 4;
                break;
            case R.id.button_5_m:
                Button_Clicked = 5;
                break;
            case R.id.button_6_m:
                Button_Clicked = 6;
                break;
            case R.id.button_7_m:
                Button_Clicked = 7;
                break;
            case R.id.button_8_m:
                Button_Clicked = 8;
                break;
            case R.id.button_9_m:
                Button_Clicked = 9;
                break;
        }

        PlayGame(Button_Clicked, button_view);
        DrawCheck();

    }


    ArrayList<Integer> player1ArrayList = new ArrayList<Integer>();
    ArrayList<Integer> player2ArrayList = new ArrayList<Integer>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PlayGame(int button_clicked, Button button_view) {

        if (turn == 1) {
            button_view.setText("X");
            button_view.setTextColor(getColor(R.color.white));
            player1ArrayList.add(button_clicked);
            player_1_btn_m.setBackground(getDrawable(R.drawable.button_bg_left_curv_disable));
            player_2_btn_m.setBackground(getDrawable(R.drawable.button_bg_right_curv_enable));
            turn = 2;
        } else if (turn == 2) {
            button_view.setText("O");
            button_view.setTextColor(getColor(R.color.colorPrimary));
            player2ArrayList.add(button_clicked);
            player_1_btn_m.setBackground(getDrawable(R.drawable.button_bg_left_curv_enable));
            player_2_btn_m.setBackground(getDrawable(R.drawable.button_bg_right_curv_disable));
            turn = 1;
        }

        button_view.setEnabled(false);
        FindWinner();

    }

    private void DrawCheck() {
        ArrayList<Integer> emptyBlock = new ArrayList<Integer>();

        for (int i = 1; i <= 9; i++) {

            if (!(player1ArrayList.contains(i) || player2ArrayList.contains(i))) {
                emptyBlock.add(i);
            }
        }

        if (emptyBlock.size() == 0) {
            FindWinner();
            resetGame();
        }
    }


    private void FindWinner() {

        // ****************** Player 1 ****************//

        //Checking Rows
        if (player1ArrayList.contains(1) && player1ArrayList.contains(2) && player1ArrayList.contains(3)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }
        if (player1ArrayList.contains(4) && player1ArrayList.contains(5) && player1ArrayList.contains(6)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }
        if (player1ArrayList.contains(7) && player1ArrayList.contains(8) && player1ArrayList.contains(9)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }

        //Checking Column
        if (player1ArrayList.contains(1) && player1ArrayList.contains(4) && player1ArrayList.contains(7)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }
        if (player1ArrayList.contains(2) && player1ArrayList.contains(5) && player1ArrayList.contains(8)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }
        if (player1ArrayList.contains(3) && player1ArrayList.contains(6) && player1ArrayList.contains(9)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }

        //Checking Diagonal
        if (player1ArrayList.contains(1) && player1ArrayList.contains(5) && player1ArrayList.contains(9)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }
        if (player1ArrayList.contains(3) && player1ArrayList.contains(5) && player1ArrayList.contains(7)) {
            player_1_score++;
            mPlayer_1_Score_tv.setText(Integer.toString(player_1_score));
            match_count--;
            resetGame();
        }


        // ****************** Player 2 ****************//

        //Checking Rows
        if (player2ArrayList.contains(1) && player2ArrayList.contains(2) && player2ArrayList.contains(3)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }
        if (player2ArrayList.contains(4) && player2ArrayList.contains(5) && player2ArrayList.contains(6)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }
        if (player2ArrayList.contains(7) && player2ArrayList.contains(8) && player2ArrayList.contains(9)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }

        //Checking Column
        if (player2ArrayList.contains(1) && player2ArrayList.contains(4) && player2ArrayList.contains(7)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }
        if (player2ArrayList.contains(2) && player2ArrayList.contains(5) && player2ArrayList.contains(8)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }
        if (player2ArrayList.contains(3) && player2ArrayList.contains(6) && player2ArrayList.contains(9)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }

        //Checking Diagonal
        if (player2ArrayList.contains(1) && player2ArrayList.contains(5) && player2ArrayList.contains(9)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }
        if (player2ArrayList.contains(3) && player2ArrayList.contains(5) && player2ArrayList.contains(7)) {
            player_2_score++;
            mPlayer_2_Score_tv.setText(Integer.toString(player_2_score));
            match_count--;
            resetGame();
        }


        if (match_count == 0) {
            if (player_1_score > player_2_score && match_count == 0) {
                gameOver("X");

            } else if (player_2_score > player_1_score && match_count == 0) {
                gameOver("O");
            }
        }
    }

    private void resetGame() {

        mMatchCountTV.setText("Match Left: " + Integer.toString(match_count));
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");

        player1ArrayList.clear();
        player2ArrayList.clear();
        turn = 1;

        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
        b8.setEnabled(true);
        b9.setEnabled(true);
    }


    private void gameOver(String winner) {

        Intent intent = new Intent(MultiPlayerBoard.this, GameOver.class);
        intent.putExtra("winner", winner);
        intent.putExtra("type", "multi");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView dialog_tv = dialogView.findViewById(R.id.dialog_text_view);
        Button yes = dialogView.findViewById(R.id.dialog_yes);
        Button no = dialogView.findViewById(R.id.dialog_no);

        animation.FadeIn(this, dialog_tv);
        animation.FadeIn(this, yes);
        animation.FadeIn(this, no);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MultiPlayerBoard.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
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