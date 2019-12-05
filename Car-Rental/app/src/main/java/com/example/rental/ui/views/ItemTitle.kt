package com.example.rental.ui.views

import android.widget.TextView
import com.example.rental.R
import com.example.rental.ui.views.helpers.KotlinModel

data class ItemTitle(
    val title: String
) : KotlinModel(R.layout.item_title) {

    val titleView by bind<TextView>(R.id.title)

    override fun bind() {
        titleView.text = title
    }
}