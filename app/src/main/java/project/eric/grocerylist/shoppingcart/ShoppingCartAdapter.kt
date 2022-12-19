package project.eric.grocerylist.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.eric.grocerylist.database.Grocery
import project.eric.grocerylist.databinding.ItemShoppingBinding

class ShoppingCartAdapter: ListAdapter<Grocery, ShoppingCartAdapter.ViewHolder>(GroceryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(private val binding: ItemShoppingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(grocery: Grocery) {
            binding.grocery = grocery
            binding.tvCount.text = grocery.count.toString()
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemShoppingBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    /**
     *  DiffUtil for recyclelist changes
     */
    class GroceryDiffCallback: DiffUtil.ItemCallback<Grocery>() {
        override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean {
            return oldItem == newItem
        }
    }
}