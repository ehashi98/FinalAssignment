package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    EditText ETemail, et;
    Button login;
    SharedPreferences sp;
    String email;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETemail = findViewById(R.id.ETemail);
        login = findViewById(R.id.login);
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ETemail.getText().toString();

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", email);
                editor.commit();

                String password = et.getText().toString();

                checkPasswordComplexity(password);

            }

        });

    }
    public void checkPasswordComplexity(String password) {

        char ch;
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i=0;i < password.length();i++) {
            ch = password.charAt(i);
            if( Character.isDigit(ch)) {
                foundNumber = true;
            }
            else if (Character.isUpperCase(ch)) {
                foundUpperCase = true;
            }
            else if (Character.isLowerCase(ch)) {
                foundLowerCase = true;
            }
            else if (isSpecialCharacter(ch)) {
                foundSpecial = true;
            }
            if(foundNumber && foundUpperCase && foundLowerCase && foundSpecial){
                Toast.makeText(loginActivity.this,"Strong Password",Toast.LENGTH_SHORT).show();
                tv.setText("Your password meets the requirements");

                Intent intent = new Intent(loginActivity.this, loginPageActivity.class);
                startActivity(intent);

                return;
            }

        }
        if(!foundUpperCase)
        {
            Toast.makeText(loginActivity.this,"Missing an Upper case letter",Toast.LENGTH_SHORT).show();
            tv.setText("You shall not pass!");
        }
        else if(!foundLowerCase)
        {
            Toast.makeText(loginActivity.this,"Missing a Lower case letter",Toast.LENGTH_SHORT).show();
            tv.setText("You shall not pass!");
        }
        else if(!foundNumber)
        {
            Toast.makeText(loginActivity.this,"Missing a Number",Toast.LENGTH_SHORT).show();
            tv.setText("You shall not pass!");
        }
        else {
            Toast.makeText(loginActivity.this,"Missing a Symbol",Toast.LENGTH_SHORT).show();
            tv.setText("You shall not pass!");
        }

    }

    /** This Function has the symbols that will be used
     * to check how strong the password variable is.
     *
     * @param c has the symbols we'll use.
     * @return true if there is a symbol in the password
     * and false if there isn't.
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}
