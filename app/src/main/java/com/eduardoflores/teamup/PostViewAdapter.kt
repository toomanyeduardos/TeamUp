package com.eduardoflores.teamup

import android.content.Context
import com.eduardoflores.teamup.model.Post
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference

/**
 * Created by eduardo on 8/15/17.
 */
class PostViewAdapter(modelClass: Class<Post>,
                      layout: Int,
                      viewHolderClass: Class<PostHolder>,
                      databaseReference: DatabaseReference,
                      context: Context): FirebaseRecyclerAdapter<Post, PostHolder>(modelClass,
        layout,
        viewHolderClass,
        databaseReference) {

    override fun populateViewHolder(viewHolder: PostHolder, model: Post, position: Int) {
        viewHolder.postTitle.text = model.title
    }

}