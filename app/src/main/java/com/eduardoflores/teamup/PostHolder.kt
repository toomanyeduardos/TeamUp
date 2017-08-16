package com.eduardoflores.teamup

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * Created by eduardo on 8/15/17.
 *
 * https://inducesmile.com/android/android-firebaseui-with-custom-firebaserecycleradapter-example-tutorial/
 */
class PostHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var postTitle: TextView
    var postOwner: TextView

    init {
        postTitle = itemView.findViewById(R.id.post_title_et)
        postOwner = itemView.findViewById(R.id.post_body_tv)
    }
}