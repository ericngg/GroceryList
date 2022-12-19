package project.eric.grocerylist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.eric.grocerylist.database.Grocery
import project.eric.grocerylist.databinding.ItemGroceryBinding

class GroceryAdapter(
    private val addClickListener: AddGroceryListener,
    private val subClickListener: SubGroceryListener) : ListAdapter<Grocery, GroceryAdapter.ViewHolder>(GroceryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addClickListener, subClickListener, getItem(position)!!)
    }

    class ViewHolder(private val binding: ItemGroceryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(addClickListener: AddGroceryListener, subClickListener: SubGroceryListener, grocery: Grocery) {
            binding.grocery = grocery
            binding.addClickListener = addClickListener
            binding.subClickListener = subClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGroceryBinding.inflate(layoutInflater, parent, false)

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

    /**
     *  clickListener for add button
     */
    class AddGroceryListener(val clickListener: (grocery: Grocery) -> Unit) {
        fun onAddClick(grocery: Grocery) = clickListener(grocery)
    }

    /**
     *  clickListener for subtract button
     */
    class SubGroceryListener(val clickListener: (grocery: Grocery) -> Unit) {
        fun onSubClick(grocery: Grocery) = clickListener(grocery)
    }
}