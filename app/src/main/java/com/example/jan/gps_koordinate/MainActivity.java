package com.example.jan.gps_koordinate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {

    private Button btnKviz, btnZemljevid, btnDefibrilator, btnRezultati,odjava;
    SignInButton prijava;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnKviz = (Button) findViewById(R.id.button_kviz);
        btnZemljevid = (Button) findViewById(R.id.button_zemljevid);
        btnDefibrilator = (Button) findViewById(R.id.button_defibrilator);
        btnRezultati = (Button) findViewById(R.id.button_rezultati);
        prijava=(SignInButton) findViewById(R.id.googleBtn);
        odjava = (Button) findViewById(R.id.googleOdjava);
        prijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                onStart();
            }
        });

        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                onStart();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        btnKviz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kviz = new Intent(MainActivity.this, KvizActivity.class);
                startActivity(kviz);
            }
        });

        btnZemljevid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zemljevid = new Intent(MainActivity.this, ZemljevidActivity.class);
                startActivity(zemljevid);
            }
        });

        btnDefibrilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent defibrilator = new Intent(MainActivity.this, DefibrilatorActivity.class);
                startActivity(defibrilator);
            }
        });

        btnRezultati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rezultati = new Intent(MainActivity.this, RezultatiActivity.class);
                startActivity(rezultati);
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            prijava.setEnabled(true);
            odjava.setEnabled(false);
        }
        else
        {
            prijava.setEnabled(false);
            odjava.setEnabled(true);
        }
    }

    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("LOGIN FAIL", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            prijava.setEnabled(false);
                            odjava.setEnabled(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            prijava.setEnabled(true);
                            odjava.setEnabled(false);
                        }
                    }
                });
    }
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        prijava.setEnabled(true);
                        odjava.setEnabled(false);
                    }
                });
    }
    private void revokeAccess()
    {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //updateUI(null);
                    }
                });
    }
}
