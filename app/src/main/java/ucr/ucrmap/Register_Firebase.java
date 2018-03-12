package ucr.ucrmap;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Firebase extends AppCompatActivity {


    private Button sendtodata;
    private EditText reg_email,reg_password,reg_firstname,reg_lastname;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_firebase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendtodata = (Button)findViewById(R.id.Button_Firebase);
        reg_email = (EditText)findViewById(R.id.Reg_Email);
        reg_password= (EditText)findViewById(R.id.Reg_Password);
        reg_firstname =(EditText)findViewById(R.id.Reg_First_Name);
        reg_lastname=(EditText)findViewById(R.id.Reg_LastName);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mRef = FirebaseAuth.getInstance();
        //mDatabase = new Firebase("https://ucrmap-63651.firebaseio.com/Email"); //connect database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendtodata.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    private void signUp(){
        String pass = reg_password.getText().toString().trim();
        String email = reg_email.getText().toString();
        final String firstname = reg_firstname.getText().toString();
        String lastname  = reg_lastname.getText().toString();
        if(email.isEmpty())
        {
            reg_email.setError("Email is required");
            reg_email.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            reg_email.setError("Enter valid email address");
            reg_email.requestFocus();
        }
        else if(pass.isEmpty())
        {
            reg_password.setError("Password is required");
            reg_password.requestFocus();
        }
        else if(pass.length()<6){
            reg_password.setError("Password length must be greater than 6 characters");
            reg_password.requestFocus();
        }
        else if(firstname.isEmpty())
        {
            reg_firstname.setError("First Name is required");
            reg_firstname.requestFocus();
        }
        else if(lastname.isEmpty())
        {
            reg_lastname.setError("Last Name is required");
            reg_lastname.requestFocus();
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            mRef.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        sendEmail();
                        Intent intent = new Intent(getApplicationContext(), Login_Firebase.class);
                        mDatabase.child("users").setValue(firstname);
                        startActivity(intent);
                        // need to learn how to do bundles?
                        //store info into database
                        //login in taken care of
                    } else {
                        Toast.makeText(Register_Firebase.this, "didnt work", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void sendEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Register_Firebase.this,"Check email for verification",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }
}
