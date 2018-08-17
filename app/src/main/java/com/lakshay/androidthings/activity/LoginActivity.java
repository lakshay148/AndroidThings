package com.lakshay.androidthings.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lakshay.androidthings.R;
import com.lakshay.androidthings.base.BaseActivity;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

public class LoginActivity extends BaseActivity implements ITrueCallback {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, this)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();

        TrueSDK.init(trueScope);

        if(TrueSDK.getInstance().isUsable()){
            Log.d(TAG, "onCreate: available");
            TrueSDK.getInstance().getUserProfile(this);
        } else {
            Log.d(TAG, "onCreate: false");
        }
    }

    @Override
    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
        Log.d(TAG, "onSuccessProfileShared: " + trueProfile.firstName);
    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Log.d(TAG, "onFailureProfileShared: " + trueError.getErrorType());
    }

    @Override
    public void onOtpRequired() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
    }
}
