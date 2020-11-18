package model;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;

public class DynamicAchievementsView {
    Context context;

    public DynamicAchievementsView(Context context) {
        this.context = context;
    }

    public EditText receivedTitleOfAchievement(Context context){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id = 0 ;
        editText.setId(id);
        editText.setMinEms(2);
        editText.setLayoutParams(layoutParams);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint("Title");
        return editText;
    }
    public EditText receivedDateOfAchievement(Context context){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id = 2 ;
        editText.setId(id);
        editText.setMinEms(2);
        editText.setLayoutParams(layoutParams);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint("Date Of Achievement");
        return editText;
    }
    public EditText receivedDescriptionOfAchievement(Context context){
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        int id =1 ;
        editText.setLayoutParams(layoutParams);
        editText.setId(id);
        editText.setMinEms(3);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setMaxLines(7);
        editText.setHint("Description");
        return editText;
    }
}
