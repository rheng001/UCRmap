package ucr.ucrmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Firebase extends AppCompatActivity {
    private Button Login,Register;
    private EditText Email,Password;
    private Firebase mRef;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_firebase);
        Firebase.setAndroidContext(this);
        Login = (Button) findViewById(R.id.Button_Login);
        Register = (Button) findViewById(R.id.Button_Register);
        Email = (EditText) findViewById(R.id.Edit_Email);
        Password = (EditText) findViewById(R.id.Edit_Password);
        mRef = new Firebase("https://ucrmap-63651.firebaseio.com/"); //connect database
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);



        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // RESET LIFE CYCLE
            startActivity(intent);
            finish();

            //Toast.makeText(Login_Firebase.this,auth.getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
        } else {
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Register_Firebase.class);
                    startActivity(intent);
                }
            });
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Login();
                }
            });
        }
    }
    private void Login()
    {

        String pass = Password.getText().toString().trim();
        String email = Email.getText().toString();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        /*if(email.isEmpty())
        {
            Email.setError("Email is required");
            Email.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Email.setError("Enter valid email address");
            Email.requestFocus();
        }
        else if(pass.isEmpty())
        {
            Password.setError("Password is required");
            Password.requestFocus();
        }
        else if(pass.length()<6){
            Password.setError("Password length must be greater than 6 characters");
            Password.requestFocus();
        }
        else
        {

            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) // user successfully logged in
                    {
                        user = auth.getCurrentUser();
                        if(!user.isEmailVerified())
                        {
                            Toast.makeText(Login_Firebase.this,"Please verify your email",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            auth.signOut();
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Email.setText("");
                        Password.setText("");
                        Password.clearFocus();
                        Password.clearComposingText();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }*/
    }


}
