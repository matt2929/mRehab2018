package com.example.matt2929.strokeappdec2017.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.matt2929.strokeappdec2017.R;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.SaveAndWriteUserInfo;
import com.example.matt2929.strokeappdec2017.SaveAndLoadData.User;
import com.example.matt2929.strokeappdec2017.Values.WorkoutData;

public class CreateNewUserActivity extends AppCompatActivity {
    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        RadioGroup handChoice = findViewById(R.id.enterRadios);
        final EditText nameEnter = findViewById(R.id.enterName);
        final EditText ageEnter = findViewById(R.id.enterAge);
        final EditText goalsEnter = findViewById(R.id.enterGoals);
        Button enterSave = findViewById(R.id.enterSave);

        //Name
        nameEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newUser.setName(nameEnter.getText().toString());

            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        //Goals
        goalsEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newUser.setGoals(goalsEnter.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        //Left Hand = 0 || Right Hand = 1
        handChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.enterLeft) {
                    newUser.setHand(0);
                } else {
                    newUser.setHand(1);
                }
            }
        });
        //Age
        ageEnter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    newUser.setAge(Integer.valueOf(ageEnter.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "The age given is not a number", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
	    //saveAllData but only if data has been entered into each field
	    enterSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    SaveAndWriteUserInfo saveAndWriteUserInfo = new SaveAndWriteUserInfo(getApplicationContext());
                    saveAndWriteUserInfo.saveUser(newUser);
	                WorkoutData.UserData = newUser;
	                WorkoutData.UserName = newUser.getName();

                }
	            Intent intent = new Intent(getApplicationContext(), WorkoutOrHistoryOrCalendarActivity.class);
	            startActivity(intent);
            }
        });
    }

    //check to see if newUser filled in all text fields
    boolean validateInput() {
        int count = 0;
        String issues = "The Following Errors Were Found:\n";
        if (newUser.getHand() == -1) {
            count++;
            issues += ("\nNo Hand Selected");
        }
        if (newUser.getName().equals("000")) {
            count++;
            issues += ("\nNo Name Set");
        }
        if (newUser.getGoals().equals("000")) {
            count++;
            issues += ("\nNo goals set");
        }
        if (newUser.getAge() == -1) {
            count++;
            issues += ("\nNo age set");

        }
        if (count == 0) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), issues, Toast.LENGTH_SHORT).show();
        }

        return false;
    }

}
