package com.example.testapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.model.CakeModel
import com.example.testapp.R
import com.facebook.drawee.view.SimpleDraweeView
import io.reactivex.rxjava3.annotations.NonNull


internal class ViewAdapter(private var cakeList: List<CakeModel>, context: Context) :
    RecyclerView.Adapter<ViewAdapter.RVViewHolder>() {

    private lateinit var mListener: onItemClickListener
    val animFadein = AnimationUtils.loadAnimation(
        context,
        R.anim.fade_in)
    internal inner class RVViewHolder(view: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener(View.OnClickListener { clickListener.onItemClick(adapterPosition) })
        }

        val itemView = view
        var titleView: TextView = view.findViewById(R.id.title)
        var draweeView = view.findViewById(R.id.imageView) as SimpleDraweeView
    }

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener:onItemClickListener) {
        mListener = clickListener
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return RVViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        val item = cakeList.get(position).title
        holder.itemView.startAnimation(animFadein)
        holder.titleView.text = item
        val uri: Uri =
            Uri.parse(cakeList.get(position).image)
        holder.draweeView.setImageURI(uri)
    }

    override fun getItemCount(): Int {
        return cakeList.size
    }
}