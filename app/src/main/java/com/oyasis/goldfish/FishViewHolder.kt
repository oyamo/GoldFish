package com.oyasis.goldfish

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

public class FishViewHolder(itemView: View, val displayHeight: Int) :RecyclerView.ViewHolder(itemView) {


    fun bindView(fish: Fish): MaterialCardView{
        val imageButton = itemView.findViewById<ImageView>(R.id.fishPic)
        val fishContainer = itemView.findViewById<MaterialCardView>(R.id.fishContainer)

        imageButton.layoutParams = imageButton.layoutParams.apply {
            height = displayHeight
        }

        if(fish.hasBeenTapped){
            imageButton.setImageResource(fish.pic)
            fishContainer.setCardBackgroundColor(Color.parseColor("#00ffffff"))
        }else{
            imageButton.setImageResource(R.drawable.fish_0)
        }

        return  fishContainer
    }
}