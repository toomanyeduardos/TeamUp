package com.eduardoflores.teamup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.eduardoflores.teamup.model.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.new_post_activity.*

class NewPost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_post_activity)

        title = getString(R.string.new_post_actionbar_title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_new_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.new_post_save -> saveNewPost()
        else -> super.onOptionsItemSelected(item)
    }

    private fun saveNewPost(): Boolean {
        val postTitle = post_title_et.text.toString()
        val postBody = post_body_et.text.toString()
        val postCompensation = post_compensation_et.text.toString()

        val post = Post(postTitle, postBody, postCompensation)

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference
        databaseReference
                .push()
                .child("post")
                .child(FirebaseAuth.getInstance().currentUser?.uid)
                .setValue(post)
                .addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Post creation succeeded!!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Account creation failed! " + task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        return true
    }
}
