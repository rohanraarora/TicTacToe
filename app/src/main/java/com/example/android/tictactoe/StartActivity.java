package com.example.android.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    EditText editText;

    public static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button button = (Button)findViewById(R.id.startButton);
        editText = (EditText)findViewById(R.id.startEditText);

        final SharedPreferences sharedPreferences = getSharedPreferences("tictactoe",MODE_PRIVATE);

        String lastName = sharedPreferences.getString(KEY_USERNAME,"");
        editText.setText(lastName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editText.getEditableText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_USERNAME,name);
                editor.putInt("number",1);
                editor.commit();

                Intent intent = new Intent(StartActivity.this,MainActivity.class);

                Bundle b = new Bundle();
                b.putString("username",name);
                b.putInt("abc",2);

                intent.putExtras(b);
                startActivity(intent);
                //finish();

            }
        });
    }
}
