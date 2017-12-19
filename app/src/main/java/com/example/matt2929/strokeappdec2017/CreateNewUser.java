package com.example.matt2929.strokeappdec2017;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateNewUser extends AppCompatActivity {
    User newUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        RadioGroup handChoice = (RadioGroup) findViewById(R.id.enterRadios);
        final EditText nameEnter = (EditText) findViewById(R.id.enterName);
        final EditText ageEnter = (EditText) findViewById(R.id.enterAge);
        final EditText goalsEnter = (EditText) findViewById(R.id.enterGoals);
        Button enterSave = (Button) findViewById(R.id.enterSave);

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
        //Save but only if data has been entered into each field
        enterSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    ReadWriteUserData readWriteUserData = new ReadWriteUserData(getApplicationContext());
                    readWriteUserData.saveUser(newUser);
                }
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
