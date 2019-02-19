package in.dropcodes.droptactoe.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePrefrences {

    SharedPreferences sharedPreferences;

    public ThemePrefrences(Context context){
        sharedPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }

    public void setDayModeState(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DayMode",state);
        editor.commit();
    }

    public Boolean loadDayModeState(){
        Boolean state = sharedPreferences.getBoolean("DayMode",false);
        return state;
    }
}
