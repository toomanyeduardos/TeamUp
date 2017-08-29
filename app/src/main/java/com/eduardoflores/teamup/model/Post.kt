package com.eduardoflores.teamup.model

/**
 * Created by eduardo on 8/10/17.
 */
data class Post(val uid: String = "",
                val author: String = "",
                val title: String = "",
                val body: String = "",
                val category: String = "",
                val location: String = "",
                val compensation: String = "") {

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any> ()
        result.put("uid", uid)
        result.put("author", author)
        result.put("title", title)
        result.put("body", body)
        result.put("category", category)
        result.put("location", location)
        result.put("compensation", compensation)

        return result
    }
}