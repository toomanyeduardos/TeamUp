package com.eduardoflores.teamup

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

/**
 * Created by eduardo on 8/7/17.
 */
class RecyclerAdapter (val photos : List<Photo>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>() {

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos.get(position)
        holder.bindPhoto(itemPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoHolder {
        val inflatedView = LayoutInflater.from(parent?.context).inflate(R.layout.recyclerview_item_row, parent, false)
        return PhotoHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class PhotoHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val TAG = PhotoHolder::class.java.simpleName
        val PHOTO_KEY = "PHOTO"
        var mItemImage : ImageView = view.item_image
        var mItemDate : TextView = view.item_date
        var mItemDescription : TextView = view.item_description
        lateinit var mPhoto : Photo

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

        fun bindPhoto(photo: Photo) {
            mPhoto = photo
            mItemDate.text = mPhoto.id
            mItemDescription.text = "Some description"
        }
    }
}