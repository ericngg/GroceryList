package project.eric.grocerylist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import project.eric.grocerylist.database.GroceryDatabase
import project.eric.grocerylist.databinding.FragmentListBinding


class GroceryListFragment : Fragment() {

    private val TAG = "GroceryListFragment"

    private lateinit var groceryListViewModel: GroceryListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        val dataSource = GroceryDatabase.getInstance(application).groceryDatabaseDao
        val viewModelFactory = GroceryListViewModelFactory(dataSource, application)
        groceryListViewModel = ViewModelProviders.of(this, viewModelFactory).get(GroceryListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = groceryListViewModel

        val adapter = GroceryAdapter()
        binding.rvGroceryList.adapter = adapter
        binding.rvGroceryList.layoutManager = LinearLayoutManager(context)

        groceryListViewModel.groceries.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.i(TAG, "grocery list updated")
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Run once when building the app for the first time, afterwards can comment out
        //groceryListViewModel.init()

        groceryListViewModel.fetchGroceries()
    }

}