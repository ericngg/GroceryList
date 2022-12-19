package project.eric.grocerylist.shoppingcart

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import project.eric.grocerylist.R
import project.eric.grocerylist.database.GroceryDatabase
import project.eric.grocerylist.databinding.FragmentShoppingcartBinding
import project.eric.grocerylist.list.GroceryAdapter
import project.eric.grocerylist.list.GroceryListViewModel
import project.eric.grocerylist.list.GroceryListViewModelFactory


class ShoppingCartFragment : Fragment() {

    private val TAG = "ShoppingCartFragment"

    private lateinit var shoppingCartViewModel: ShoppingCartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // binding and application
        val binding = FragmentShoppingcartBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        // database and viewmodel
        val dataSource = GroceryDatabase.getInstance(application).groceryDatabaseDao
        val viewModelFactory = ShoppingCartViewModelFactory(dataSource, application)
        shoppingCartViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = shoppingCartViewModel

        // Adapter
        val adapter = ShoppingCartAdapter()
        binding.rvShoppingCart.adapter = adapter
        binding.rvShoppingCart.layoutManager = LinearLayoutManager(context)

        // Observer to navigate to grocery list screen
        shoppingCartViewModel.navigateToGroceryList.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigateUp()
                shoppingCartViewModel.onNavigatedToGroceryList()
            }
        })

        // Observes and updates recyclerview if it changes
        shoppingCartViewModel.shoppingCartList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.i(TAG, "shopping list updated")
        })

        activity?.title = "Shopping Cart"
        setHasOptionsMenu(true)

        shoppingCartViewModel.fetchShoppingCart()

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