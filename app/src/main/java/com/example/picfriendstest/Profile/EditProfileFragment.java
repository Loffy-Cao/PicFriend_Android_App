package com.example.picfriendstest.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.picfriendstest.R;
import com.example.picfriendstest.Share.ShareActivity;
import com.example.picfriendstest.Utils.FirebaseMethods;
import com.example.picfriendstest.Utils.UniversalImageLoader;
import com.example.picfriendstest.models.User;
import com.example.picfriendstest.models.UserAccountSettings;
import com.example.picfriendstest.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;


    //EditProfile Fragment widgets
    private CircleImageView mProfilePhoto;
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;

    //varibles
    private UserSettings mUserSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName  = (EditText) view.findViewById(R.id.display_name_center);
        mUsername = (EditText) view.findViewById(R.id.username_center);
        mWebsite = (EditText) view.findViewById(R.id.website_center);
        mDescription = (EditText) view.findViewById(R.id.description_center);
        mEmail = (EditText) view.findViewById(R.id.email_center);
        mPhoneNumber = (EditText) view.findViewById(R.id.phoneNumber_center);
        mFirebaseMethods = new FirebaseMethods(getActivity());

        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);

        //setProfileImage();
        setupFirebaseAuth();

        //back arrow for navigating back to "ProfileAcvivity"
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        ImageView checkmark = (ImageView) view.findViewById(R.id.savechanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes");
                saveProfileSettings();
            }
        });

        return view;

    }

    /**
     * Retrieves the data contained in the widgets and submit it to the database
     * before doing so it checks to make sure the username chosen is unique
     */
    private void saveProfileSettings(){
        final String displayName = mDisplayName.getText().toString();
        final String userName = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //case 1: if the user made a change to their username
                if(!mUserSettings.getUser().getUsername().equals(userName)){

                    checkIfUsernameExists(userName);
                }
                //case 2: if the user change other information
                if (!mUserSettings.getUser().getEmail().equals(email)){
                    //update email
                    mFirebaseMethods.updateUserAccountSettings(email,null,null,null);
                }

                if(!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
                    //update displayname
                    mFirebaseMethods.updateUserAccountSettings(null,displayName,null,null);
                }

                if(!mUserSettings.getSettings().getWebsite().equals(website)){
                    //update website
                    mFirebaseMethods.updateUserAccountSettings(null,null,website,null);
                }

                if(!mUserSettings.getSettings().getDescription().equals(description)){
                    //update descriptiion
                    mFirebaseMethods.updateUserAccountSettings(null,null,null,description);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * check if @param username already exists in the database
     * @param userName
     */
    private void checkIfUsernameExists(String userName) {
        Log.d(TAG, "checkIfUsernameExists: Checking if "+ userName +"already exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(userName);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    //add the username
                    mFirebaseMethods.updateUsername(userName);
                    Toast.makeText(getActivity(), "saved username", Toast.LENGTH_SHORT).show();

                }
                for (DataSnapshot singleSnapShot: dataSnapshot.getChildren()){
                    if(singleSnapShot.exists()){
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH" + singleSnapShot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exists", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets (UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: "+ userSettings.toString());

        mUserSettings = userSettings;
        // User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));

        mChangeProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: changing profile photo");
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //268435456
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
    /************************firebase************************/

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        //Firebase initialize the instance
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: signed_in:" + user.getUid());
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: signed_out:");
                }

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // retrieve user information from the database
                 setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));
                //retrieve images for the user in question
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
