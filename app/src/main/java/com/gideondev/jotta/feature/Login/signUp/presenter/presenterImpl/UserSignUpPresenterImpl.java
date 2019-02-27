package com.gideondev.jotta.feature.Login.signUp.presenter.presenterImpl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.gideondev.jotta.R;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserSignUpPresenter;
import com.gideondev.jotta.feature.Login.signUp.presenter.view.UserSignUpView;
import com.gideondev.jotta.feature.main.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 5/3/2017.
 */

public class UserSignUpPresenterImpl
    implements UserSignUpPresenter {
    UserSignUpView mView;
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(@NonNull UserSignUpView view) {
        this.mView = view;
    }

    @Override
    public void SignUp(String email, String password,String retypePassword,final FirebaseAuth mAuth) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(mView.getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!emailValidator(email)) {
            Toast.makeText(mView.getContext(), "check email format!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mView.getContext(),  "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(retypePassword)) {
            Toast.makeText(mView.getContext(),  "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(mView.getContext(),  "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        mView.showLineLoading();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(mView.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               mView.hideLineLoading();
                if (!task.isSuccessful()) {
                    Toast.makeText(mView.getContext(), "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                   // mView.getContext().startActivity(new Intent(mView.getContext(), DoctorMainActivity.class));
                    //create a child node of within doctor node using the unique id of the particular doctor user
                    //Toast.makeText(mView.getContext(), "You Need to setup your account", Toast.LENGTH_LONG).show();
                    Intent setupIntent = new Intent(mView.getContext(), MainActivity.class);
                    mView.getContext().startActivity(setupIntent);
                    mView.getActivity().finish();
                }

            }

        }).addOnFailureListener(mView.getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.hideLineLoading();
                Toast.makeText(mView.getContext(),
                    mView.getContext().getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
            }
        });;

    }

//    private void checkUserExist(FirebaseAuth mAuth,DatabaseReference mDatabaseUsers) {
//
//        if (mAuth.getCurrentUser() != null) {
//            final String user_id = mAuth.getCurrentUser().getUid();
//
//            mDatabaseUsers.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    if (dataSnapshot.hasChild(user_id)) {
//
//                        Intent mainIntent = new Intent(mView.getContext(), DoctorMainActivity.class);
//                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        mView.getActivity().startActivity(mainIntent);
//
//                    } else {
//                        Toast.makeText(mView.getContext(), "You Need to setup your account", Toast.LENGTH_LONG).show();
//                        Intent setupIntent = new Intent(mView.getContext(), DoctorPreviewProfileActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("add", "adddoctor");
//                        setupIntent.putExtras(bundle);
//                        setupIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        mView.getContext().startActivity(setupIntent);
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//    }

    private boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
