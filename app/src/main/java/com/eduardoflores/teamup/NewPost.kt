package com.eduardoflores.teamup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

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
}
