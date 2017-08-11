package com.eduardoflores.teamup

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
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
 */

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    lateinit var mRecyclerView : RecyclerView
    lateinit var mLinearLayoutManager : LinearLayoutManager
    lateinit var mAdapter : RecyclerAdapter
    val REQUEST_CODE_SIGN_IN = 99

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

        // setup recycler adapter
        mAdapter = RecyclerAdapter(mPhotosList)
        mRecyclerView.adapter = mAdapter
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
