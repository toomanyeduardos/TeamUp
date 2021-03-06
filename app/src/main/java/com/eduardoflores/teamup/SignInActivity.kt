package com.eduardoflores.teamup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.eduardoflores.teamup.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_in_activity.*

class SignInActivity : AppCompatActivity() {
    val TAG = SignInActivity::class.java.simpleName
    lateinit var username: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)

        title = getString(R.string.sign_in_activity_actionbar_title)

        button_sign_in.setOnClickListener{signIn()}
        button_sign_up.setOnClickListener{signUp()}
    }

    private fun isLoginDataValid(): Boolean {
        if (!sign_in_username.text.toString().isEmpty() || !sign_in_password.text.toString().isEmpty()) {
            username = sign_in_username.text.toString()
            password = sign_in_password.text.toString()
            return true
        } else
            return false
    }

    private fun signIn() {
        if (isLoginDataValid()) {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener{task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sign in succeeded!! User = " + task.result.user, Toast.LENGTH_SHORT).show()
                            finishLoginProcess(task.result.user.uid)
                        } else {
                            Toast.makeText(this, "Sign in failed! " + task.exception?.message , Toast.LENGTH_LONG).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Can't sign in with empty username or password, man!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUp() {
        if (isLoginDataValid()) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener{task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Account creation succeeded!! User = " + task.result.user, Toast.LENGTH_SHORT).show()
                            finishLoginProcess(task.result.user.uid)
                        } else {
                            Toast.makeText(this, "Account creation failed! " + task.exception?.message , Toast.LENGTH_LONG).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Can't create new account with empty username or password, man!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun finishLoginProcess(userId: String){
        val user = User(username, username)
        val database = FirebaseDatabase.getInstance().reference
        database.child("users")
                .child(userId)
                .setValue(user)

        closeActivity()
    }

    private fun closeActivity() {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
