package com.appiadev.ituneschallenge.ui.main.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appiadev.ituneschallenge.R
import com.appiadev.ituneschallenge.data.model.ResultAlbum
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*


class MainAdapter(
    private val users: ArrayList<ResultAlbum>,
    private val onClickListener: View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener
    ) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            album: ResultAlbum,
            onClickListener: View.OnClickListener,
            onContextClickListener: View.OnContextClickListener
        ) {
            itemView.apply {
                setOnClickListener(onClickListener)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setOnContextClickListener(onContextClickListener)
                }
                track_name.text = album.trackName
                album_band_name.text = album.artistName
                Glide.with(itemView.context)
                    .load(album.artworkUrl100)
                    .into(album_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position],onClickListener,onContextClickListener)
    }

    fun addUsers(users: List<ResultAlbum>) {
        this.users.apply {
            clear()
            addAll(users)
        }
    }
}