package com.eduardoflores.teamup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.eduardoflores.teamup.model.Post
import com.eduardoflores.teamup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid as String
//        val threadId = "$currentUserId$postTitle"
//        val post = Post(postTitle, postBody, postCompensation, threadId)

        val database = FirebaseDatabase.getInstance().reference

        database
                .child("users")
                .child(currentUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get user value
                        val user = dataSnapshot.getValue(User::class.java)

                        // Start exclude (not sure what that means yet
                        if (user == null) {
                            Toast.makeText(this@NewPost, "Error: could not fetch user", Toast.LENGTH_LONG).show()
                        } else {
                            // write new post
                            writeNewPost(database, currentUserId, user.username, postTitle, postBody)
                        }

                        // finish this activity
                        finish()
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                        Toast.makeText(this@NewPost, "Error: user canceled new post", Toast.LENGTH_LONG).show()
                    }
                })
//        database
//                .push()
//                .child("thread")
////                .child(threadId)
//                .child(currentUserId)
//                .setValue(post)
//                .addOnCompleteListener { task: Task<Void> ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(this, "Post creation succeeded!!", Toast.LENGTH_SHORT).show()
//                        finish()
//                    } else {
//                        Toast.makeText(this, "Account creation failed! " + task.exception?.message, Toast.LENGTH_LONG).show()
//                    }
//                }
        return true
    }

    private fun writeNewPost(database: DatabaseReference,
                             userId: String,
                             username: String,
                             title: String,
                             body: String) {
        // Create new post at /user-posts/$userid/$postid
        // and at /posts/$postid simultaneously
        val key = database
                .child("posts")
                .push().key
        val post = Post(userId, username, title, body, "1000")
        val postValues = post.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates.put("/posts/" + key, postValues)
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues)

        database.updateChildren(childUpdates)
    }
}
