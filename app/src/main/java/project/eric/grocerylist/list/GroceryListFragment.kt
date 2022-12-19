package project.eric.grocerylist.list

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
import project.eric.grocerylist.databinding.FragmentListBinding


class GroceryListFragment : Fragment() {

    private val TAG = "GroceryListFragment"
    private lateinit var groceryListViewModel: GroceryListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // binding and application
        val binding = FragmentListBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        // database and viewmodel
        val dataSource = GroceryDatabase.getInstance(application).groceryDatabaseDao
        val viewModelFactory = GroceryListViewModelFactory(dataSource, application)
        groceryListViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            GroceryListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = groceryListViewModel

        // adapter
        val adapter = GroceryAdapter(
            GroceryAdapter.AddGroceryListener { grocery ->
            groceryListViewModel.onAddClick(grocery)
        }, GroceryAdapter.SubGroceryListener { grocery ->
            groceryListViewModel.onSubClick(grocery)
        })

        binding.rvGroceryList.adapter = adapter
        binding.rvGroceryList.layoutManager = LinearLayoutManager(context)


        // Observes and updates recyclerview if it changes
        groceryListViewModel.groceries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.i(TAG, "grocery list updated")
        })

        // Observer to navigate to shopping cart screen
        groceryListViewModel.navigateToShoppingCart.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(GroceryListFragmentDirections.actionGroceryListFragmentToShoppingCartFragment())
                groceryListViewModel.onNavigatedToShoppingCart()
            }
        })

        setHasOptionsMenu(true)
        activity?.title = "Grocery List"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groceryListViewModel.init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.glmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        groceryListViewModel.onNavigateToShoppingCart()

        return true
    }
}