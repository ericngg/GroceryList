package project.eric.grocerylist.shoppingcart

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import project.eric.grocerylist.R
import project.eric.grocerylist.database.GroceryDatabase
import project.eric.grocerylist.databinding.FragmentShoppingcartBinding
import project.eric.grocerylist.list.GroceryListViewModel
import project.eric.grocerylist.list.GroceryListViewModelFactory


class ShoppingCartFragment : Fragment() {

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentShoppingcartBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        //val dataSource = GroceryDatabase.getInstance(application).groceryDatabaseDao
        val viewModelFactory = ShoppingCartViewModelFactory(application)
        shoppingCartViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        binding.lifecycleOwner = this

        activity?.title = "Shopping Cart"

        shoppingCartViewModel.navigateToGroceryList.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigateUp()
                shoppingCartViewModel.onNavigatedToGroceryList()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.scmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        shoppingCartViewModel.onNavigateToGroceryList()
        return super.onOptionsItemSelected(item)
    }
}