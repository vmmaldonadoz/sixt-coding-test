package com.example.rental.ui.views

import android.text.Spanned
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.example.rental.R
import com.example.rental.data.room.Car
import com.example.rental.ui.views.helpers.KotlinModel
import com.squareup.picasso.Picasso

data class CarItem(val car: Car) : KotlinModel(R.layout.item_car) {

    val name by bind<TextView>(R.id.name)
    val modelName by bind<TextView>(R.id.model_name)
    val color by bind<TextView>(R.id.color)
    val licensePlate by bind<TextView>(R.id.license_plate)
    val image by bind<ImageView>(R.id.image)

    override fun bind() {
        val context = name.context
        name.text = getFormattedText(
            context.getString(R.string.label_name, car.name)
        )
        modelName.text = getFormattedText(
            context.getString(R.string.label_model_name, car.modelName)
        )
        color.text = getFormattedText(
            context.getString(R.string.label_color, car.color)
        )
        licensePlate.text = getFormattedText(
            context.getString(R.string.label_license_plate, car.licensePlate)
        )

        Picasso.get()
            .load(car.carImageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_car_placeholder)
            .error(R.drawable.ic_car_placeholder)
            .into(image)

    }

    private fun getFormattedText(htmlString: String): Spanned {
        return HtmlCompat.fromHtml(
            htmlString,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }
}