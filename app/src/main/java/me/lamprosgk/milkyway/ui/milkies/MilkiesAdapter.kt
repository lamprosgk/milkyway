package me.lamprosgk.milkyway.ui.milkies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.milky_item.*
import me.lamprosgk.milkyway.R
import me.lamprosgk.milkyway.domain.model.Milky

class MilkiesAdapter(
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<MilkiesAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(milky: Milky, image: ImageView)
    }

    private var items: List<Milky>? = null

    fun setItems(items: List<Milky>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.milky_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = items?.size ?: 0

    /**
     * Using experimental LayoutContainer
     * https://kotlinlang.org/docs/tutorials/android-plugin.html#layoutcontainer-support
     */
    class ViewHolder(override val containerView: View, private val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(milky: Milky) {
            with(milky) {
                milkyTitleTextView.text = title
                milkyCenterTextView.text = center
                milkyDateTextView.text = dateCreated
                milkyImageView.transitionName = nasaId

                imageUrl.let {
                    Picasso.get()
                        .load(it)
                        .fit()
                        .centerCrop()
                        .into(milkyImageView)
                }

                itemView.setOnClickListener {
                    itemClickListener.onItemClick(this, milkyImageView)
                }
            }
        }
    }
}