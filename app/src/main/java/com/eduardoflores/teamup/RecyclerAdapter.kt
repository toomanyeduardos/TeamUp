package com.eduardoflores.teamup

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eduardoflores.teamup.model.Post
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

/**
 * Created by eduardo on 8/7/17.
 */
class RecyclerAdapter (val posts : List<Post>) : RecyclerView.Adapter<RecyclerAdapter.PostHolder>() {

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts.get(position)
        holder.bindPost(post)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PostHolder {
        val inflatedView = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_row, parent, false)
        return PostHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val TAG = PostHolder::class.java.simpleName
        var mItemDate : TextView = view.item_date
        var mItemDescription : TextView = view.item_description

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d(TAG, "on Click!")
//            val context = itemView.context
//            val showPhotoIntent = Intent(context, PhotoActivity::class.java)
//            showPhotoIntent.putExtra(PHOTO_KEY, mPhoto)
//            context.startActivity(showPhotoIntent)
        }

        fun bindPost(post: Post) {
            mItemDate.text = post.author
            mItemDescription.text = post.body
        }
    }
}