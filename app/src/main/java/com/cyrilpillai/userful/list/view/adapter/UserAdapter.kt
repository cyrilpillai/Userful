package com.cyrilpillai.userful.list.view.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.cyrilpillai.userful.R
import com.cyrilpillai.userful.extensions.inflate
import com.cyrilpillai.userful.extensions.setRemoteImage
import com.cyrilpillai.userful.list.UserEntity
import com.cyrilpillai.userful.utils.CircleTransform
import kotlinx.android.synthetic.main.item_user.view.*


class UserAdapter(private val data: MutableList<UserEntity> = mutableListOf()) :
        RecyclerView.Adapter<UserAdapter.RewardViewHolder>() {

    private val circleTransform: CircleTransform by lazy { CircleTransform() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder =
            RewardViewHolder(parent.inflate(R.layout.item_user))

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<UserEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class RewardViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserEntity) = with(itemView) {
            tvName.text = item.name
            tvLocation.text = item.location
            imProfilePicture.setRemoteImage(
                    imageUrl = item.profileImage,
                    placeholderDrawable = R.drawable.placeholder_gradient_circle,
                    errorDrawable = R.drawable.placeholder_gradient_circle,
                    transformation = circleTransform)
        }
    }
}
