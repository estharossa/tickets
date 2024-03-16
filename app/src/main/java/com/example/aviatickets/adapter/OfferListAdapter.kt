package com.example.aviatickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ItemOfferBinding
import com.example.aviatickets.model.entity.Offer

class OfferListAdapter : ListAdapter<Offer, OfferListAdapter.ViewHolder>(OfferDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemOfferBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(offer: Offer) {
            val flight = offer.flight

            with(binding) {
                departureTime.text = flight.departureTimeInfo
                arrivalTime.text = flight.arrivalTimeInfo
                route.text = context.getString(
                    R.string.route_fmt,
                    flight.departureLocation.code,
                    flight.arrivalLocation.code
                )
                duration.text = context.getString(
                    R.string.time_fmt,
                    getTimeFormat(flight.duration).first.toString(),
                    getTimeFormat(flight.duration).second.toString()
                )
                direct.text = context.getString(R.string.direct)
                price.text = context.getString(R.string.price_fmt, offer.price.toString())

                Glide.with(context)
                    .load(
                        when (flight.airline.name) {
                            "SCAT Airlines" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/7/75/SCAT_Air_Company_Logo.svg/2560px-SCAT_Air_Company_Logo.svg.png"
                            "FlyArystan" -> "https://upload.wikimedia.org/wikipedia/ru/thumb/4/4c/FlyArystan_regular_logo.png/640px-FlyArystan_regular_logo.png"
                            "Air Astana" -> "https://upload.wikimedia.org/wikipedia/ru/thumb/a/a7/Air_Astana_logo.svg/2560px-Air_Astana_logo.svg.png"
                            "QazaqAir" -> "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/QA.png/640px-QA.png"
                            else -> ""
                        }
                    )
                    .into(airlineImage)
            }
        }

        private fun getTimeFormat(minutes: Int): Pair<Int, Int> = Pair(
            first = minutes / 60,
            second = minutes % 60
        )

    }
}