package com.sdv.kit.solvio.util

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthManagerUtil(private val mContext: Context) {
    val signInIntent: Intent
        get() = buildGoogleSignInClient().signInIntent

    val signedUserId: String
        get() = GoogleSignIn.getLastSignedInAccount(mContext)!!.id!!

    val isUserSignedIn: Boolean
        get() = GoogleSignIn.getLastSignedInAccount(mContext) != null

    private fun buildGoogleSignInClient(): GoogleSignInClient {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(mContext, googleSignInOptions)
    }
}