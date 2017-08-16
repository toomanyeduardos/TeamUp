package com.eduardoflores.teamup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.eduardoflores.teamup.model.Post
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Firebase project:
 * https://console.firebase.google.com/project/teamup-eb947/overview
 *
 * Github repo:
 * https://github.com/toomanyeduardos/TeamUp
 *
 * Learning resources (for RecyclerView, because I can never remember them):
 * https://www.raywenderlich.com/126528/android-recyclerview-tutorial
 * ended on Taking A Spacewalk: Adding Scrolling support
 *
 * DB setup from:
 * https://github.com/firebase/quickstart-android/tree/master/database
 */

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var mRecyclerView : RecyclerView
    lateinit var mLinearLayoutManager : LinearLayoutManager
    lateinit var mAdapter : PostViewAdapter
    val REQUEST_CODE_SIGN_IN = 99
    var mFirebaseUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mPhotosList = listOf(Photo("123"),
                Photo("222"),
                Photo("111"))

        // setup recyclerview
        mRecyclerView = recyclerView
        mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLinearLayoutManager

        mFirebaseUser = FirebaseAuth.getInstance().currentUser
        if (mFirebaseUser != null) {
//            fetchPostsFromFirebase().addValueEventListener(object : ValueEventListener{
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    Log.d(TAG, "fetchPostsFromFirebase. Data: " + dataSnapshot)
//
//
//                    // setup recycler adapter
//                    mAdapter = RecyclerAdapter(mPhotosList)
//                    mRecyclerView.adapter = mAdapter
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.d(TAG, "fetchPostsFromFirebase error! Error: " + error)
//                }
//            })
            mAdapter = PostViewAdapter(Post::class.java,
                    R.layout.activity_main,
                    PostHolder::class.java,
                    FirebaseDatabase.getInstance().reference,
                    this)
        }
    }

    private fun fetchPostsFromFirebase(): Query {
        val database = FirebaseDatabase.getInstance().reference
        return database.child("user-posts")
                .child(mFirebaseUser?.uid)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            menu?.findItem(R.id.menu_sign_in)?.isVisible = false
        } else {
            menu?.findItem(R.id.menu_new_post)?.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.menu_sign_in -> signIn()
        R.id.menu_about -> showAboutDialog()
        R.id.menu_new_post -> newPost()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> invalidateOptionsMenu()
        }
    }

    private fun signIn():Boolean {
        val intent = Intent(this, SignInActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
        return true
    }

    private fun showAboutDialog(): Boolean {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(R.string.about_title)
        alertDialog.setMessage(getString(R.string.about_message))
        alertDialog.setCancelable(true)
        alertDialog.show()
        return true
    }

    private fun newPost(): Boolean {
        val intent = Intent(this, NewPost::class.java)
        startActivity(intent)

        return true
    }


}
