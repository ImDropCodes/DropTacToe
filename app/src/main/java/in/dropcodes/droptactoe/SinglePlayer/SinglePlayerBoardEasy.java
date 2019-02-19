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

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import in.dropcodes.droptactoe.GameOver;
import in.dropcodes.droptactoe.MainActivity;
import in.dropcodes.droptactoe.R;
import in.dropcodes.droptactoe.SharedPreferences.ThemePrefrences;
import in.dropcodes.droptactoe.SingleTon.DropAnimation;

public class SinglePlayerBoardEasy extends AppCompatActivity {

    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;
    int turn = 1;
    private Button player_1_btn, player_2_btn;
    boolean Button_disable = false;
    private TextView mVs;
    private DropAnimation animation;
    private ThemePrefrences themePrefrences;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        setContentView(R.layout.activity_single_player_board_easy);


        b1 = findViewById(R.id.button_1_s);
        b2 = findViewById(R.id.button_2_s);
        b3 = findViewById(R.id.button_3_s);
        b4 = findViewById(R.id.button_4_s);
        b5 = findViewById(R.id.button_5_s);
        b6 = findViewById(R.id.button_6_s);
        b7 = findViewById(R.id.button_7_s);
        b8 = findViewById(R.id.button_8_s);
        b9 = findViewById(R.id.button_9_s);

        player_1_btn = findViewById(R.id.player_1_btn_s_e);
        player_2_btn = findViewById(R.id.player_2_btn_s_e);

        mVs = findViewById(R.id.single_player_vs_e);


        animation = new DropAnimation();

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


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void GameClick(View view) {

        Button button_view = (Button) view;
        int Button_Clicked = 0;

        switch (button_view.getId()) {
            case R.id.button_1_s:
                Button_Clicked = 1;
                break;
            case R.id.button_2_s:
                Button_Clicked = 2;
                break;
            case R.id.button_3_s:
                Button_Clicked = 3;
                break;
            case R.id.button_4_s:
                Button_Clicked = 4;
                break;
            case R.id.button_5_s:
                Button_Clicked = 5;
                break;
            case R.id.button_6_s:
                Button_Clicked = 6;
                break;
            case R.id.button_7_s:
                Button_Clicked = 7;
                break;
            case R.id.button_8_s:
                Button_Clicked = 8;
                break;
            case R.id.button_9_s:
                Button_Clicked = 9;
                break;
        }

        PlayGame(Button_Clicked, button_view);
        DrawCheck();

    }


    ArrayList<Integer> player1ArrayList = new ArrayList<Integer>();
    ArrayList<Integer> player2ArrayList = new ArrayList<Integer>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void DrawCheck() {
        ArrayList<Integer> emptyBlock = new ArrayList<Integer>();

        for (int i = 1; i <= 9; i++) {

            if (!(player1ArrayList.contains(i) || player2ArrayList.contains(i))) {
                emptyBlock.add(i);
            }
        }

        if (emptyBlock.size() == 0) {
            FindWinner();
            gameOver("D");

        } else {

            Random random = new Random();
            int randomIndex = random.nextInt(emptyBlock.size());
            int selectedButton = emptyBlock.get(randomIndex);
            Button btn = findViewById(R.id.button_1_s);

            switch (selectedButton) {
                case 1:
                    btn = findViewById(R.id.button_1_s);
                    break;
                case 2:
                    btn = findViewById(R.id.button_2_s);
                    break;
                case 3:
                    btn = findViewById(R.id.button_3_s);
                    break;
                case 4:
                    btn = findViewById(R.id.button_4_s);
                    break;
                case 5:
                    btn = findViewById(R.id.button_5_s);
                    break;
                case 6:
                    btn = findViewById(R.id.button_6_s);
                    break;
                case 7:
                    btn = findViewById(R.id.button_7_s);
                    break;
                case 8:
                    btn = findViewById(R.id.button_8_s);
                    break;
                case 9:
                    btn = findViewById(R.id.button_9_s);
                    break;
            }
            PlayGame(selectedButton, btn);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void PlayGame(int button_clicked, Button button_view) {

        if (turn == 1) {
            button_view.setText("X");
            button_view.setTextColor(getColor(R.color.white));
            player1ArrayList.add(button_clicked);
            player_1_btn.setBackground(getDrawable(R.drawable.button_bg_left_curv_disable));
            player_2_btn.setBackground(getDrawable(R.drawable.button_bg_right_curv_enable));
            turn = 2;
        } else if (turn == 2) {
            button_view.setText("O");
            button_view.setTextColor(getColor(R.color.colorPrimary));
            player2ArrayList.add(button_clicked);
            player_2_btn.setBackground(getDrawable(R.drawable.button_bg_right_curv_disable));
            player_1_btn.setBackground(getDrawable(R.drawable.button_bg_left_curv_enable));
            turn = 1;
        }

        button_view.setEnabled(false);
        FindWinner();

    }

    private void FindWinner() {

        int winner = 0;

        // ****************** Player 1 ****************//

        //Checking Rows
        if (player1ArrayList.contains(1) && player1ArrayList.contains(2) && player1ArrayList.contains(3)) {
            winner = 1;
            Button_disable = true;
        }
        if (player1ArrayList.contains(4) && player1ArrayList.contains(5) && player1ArrayList.contains(6)) {
            winner = 1;
            Button_disable = true;
        }
        if (player1ArrayList.contains(7) && player1ArrayList.contains(8) && player1ArrayList.contains(9)) {
            winner = 1;
            Button_disable = true;
        }

        //Checking Column
        if (player1ArrayList.contains(1) && player1ArrayList.contains(4) && player1ArrayList.contains(7)) {
            winner = 1;
            Button_disable = true;
        }
        if (player1ArrayList.contains(2) && player1ArrayList.contains(5) && player1ArrayList.contains(8)) {
            winner = 1;
            Button_disable = true;
        }
        if (player1ArrayList.contains(3) && player1ArrayList.contains(6) && player1ArrayList.contains(9)) {
            winner = 1;
            Button_disable = true;
        }

        //Checking Diagonal
        if (player1ArrayList.contains(1) && player1ArrayList.contains(5) && player1ArrayList.contains(9)) {
            winner = 1;
            Button_disable = true;
        }
        if (player1ArrayList.contains(3) && player1ArrayList.contains(5) && player1ArrayList.contains(7)) {
            winner = 1;
            Button_disable = true;
        }


        // ****************** Player 2 ****************//

        //Checking Rows
        if (player2ArrayList.contains(1) && player2ArrayList.contains(2) && player2ArrayList.contains(3)) {
            winner = 2;
            Button_disable = true;
        }
        if (player2ArrayList.contains(4) && player2ArrayList.contains(5) && player2ArrayList.contains(6)) {
            winner = 2;
            Button_disable = true;
        }
        if (player2ArrayList.contains(7) && player2ArrayList.contains(8) && player2ArrayList.contains(9)) {
            winner = 2;
            Button_disable = true;
        }

        //Checking Column
        if (player2ArrayList.contains(1) && player2ArrayList.contains(4) && player2ArrayList.contains(7)) {
            winner = 2;
            Button_disable = true;
        }
        if (player2ArrayList.contains(2) && player2ArrayList.contains(5) && player2ArrayList.contains(8)) {
            winner = 2;
            Button_disable = true;
        }
        if (player2ArrayList.contains(3) && player2ArrayList.contains(6) && player2ArrayList.contains(9)) {
            winner = 2;
            Button_disable = true;
        }

        //Checking Diagonal
        if (player2ArrayList.contains(1) && player2ArrayList.contains(5) && player2ArrayList.contains(9)) {
            winner = 2;
            Button_disable = true;
        }
        if (player2ArrayList.contains(3) && player2ArrayList.contains(5) && player2ArrayList.contains(7)) {
            winner = 2;
            Button_disable = true;
        }

        if (Button_disable) {
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(false);
            b5.setEnabled(false);
            b6.setEnabled(false);
            b7.setEnabled(false);
            b8.setEnabled(false);
            b9.setEnabled(false);
        }

        if (winner == 1) {
            gameOver("X");
        } else if (winner == 2) {
            gameOver("O");

        }

    }

    private void gameOver(String winner) {

        Intent intent = new Intent(SinglePlayerBoardEasy.this, GameOver.class);
        intent.putExtra("winner", winner);
        intent.putExtra("type", "single_easy");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog,viewGroup,false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        TextView  dialog_tv = dialogView.findViewById(R.id.dialog_text_view);
        Button yes = dialogView.findViewById(R.id.dialog_yes);
        Button no = dialogView.findViewById(R.id.dialog_no);

        animation.FadeIn(this,dialog_tv);
        animation.FadeIn(this,yes);
        animation.FadeIn(this,no);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SinglePlayerBoardEasy.this,SinglePlayerLevelChooser.class));
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


