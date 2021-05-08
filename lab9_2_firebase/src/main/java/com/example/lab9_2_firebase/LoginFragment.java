package com.example.lab9_2_firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private static final  String TAG = "TAG_EMAIL_LOGIN";

    private TextView tvStatus;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser, "Innlogget.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        tvStatus = view.findViewById(R.id.tvStatus);

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSignInIntent();
            }
        });

        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUserFromFirebase();
            }
        });

        return view;
    }

    // FIREBASE SIGN IN & OUT:
    public void createSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUI(user, "Innlogget.");
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                updateUI(null, "Ikke innlogget.");
            }
        }
    }

    private void updateUI(FirebaseUser user, String status) {
        tvStatus.setText(status);
        if (user != null) {
            // Name, email address, and profile photo Url
            tvName.setText(user.getDisplayName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhoneNumber());
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

    public void signOut() {
        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        tvName.setText("");
                        tvEmail.setText("");
                        tvPhone.setText("");

                        tvName.setEnabled(false);
                        tvEmail.setEnabled(false);
                        tvPhone.setEnabled(false);

                        updateUI(null, "Logget ut");
                    }
                });
    }

    /**
     * NB! Man må autentisere bruker på nytt FØR sletting fra Firebase.
     *
     */
    private void deleteUserFromFirebase() {

        final FirebaseUser user = FirebaseAuth
                .getInstance()
                .getCurrentUser();

        //Antar bruk av EmailAuthProvider:
        //NB! LEGG INN DIN BRUKERINFO. Her bør/må man spørre bruker etter brukernavn og passord (hardkodet her):
        AuthCredential credential = EmailAuthProvider
                .getCredential("dinbruker@domene.no", "pwd123");

        //  reauthenticate før sletting:
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Utfør sletting:
                doDeleteFromFirebase(user);
            }
        });
    }

    private void doDeleteFromFirebase(FirebaseUser user) {
        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                tvName.setText("");
                tvEmail.setText("");
                tvPhone.setText("");

                tvName.setEnabled(false);
                tvEmail.setEnabled(false);
                tvPhone.setEnabled(false);

                updateUI(null, "Bruker slettet fra Firebase");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateUI(null, "Feil: " + e.getMessage() );
                    }
                });
    }
}