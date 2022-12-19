package project.eric.grocerylist.list

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import project.eric.grocerylist.database.Grocery
import project.eric.grocerylist.database.GroceryDatabaseDao

class GroceryListViewModel(val dao: GroceryDatabaseDao, application: Application) : AndroidViewModel(application){

    private val TAG = "GroceryListViewModel"

    private val _groceries = MutableLiveData<List<Grocery>>()
    val groceries: LiveData<List<Grocery>>
        get() = _groceries

    private val _navigateToShoppingCart = MutableLiveData<Boolean>()
    val navigateToShoppingCart
        get() = _navigateToShoppingCart

    /**
     *  Initialize the database with fake data if app runs for the first time, otherwise
     *  it populates the app with the data from the database
     */
    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = dao.getAllGroceries()

                if (result.size == 0) {
                    val list = listOf<String>("Apple", "Banana", "Carrot", "Donuts", "Eggs", "Fish", "Garlic")
                    var count = 1

                    val groceries = mutableListOf<Grocery>()

                    for (grocery in list) {
                        val grocery = Grocery(count.toLong(), grocery, 0)
                        count++
                        groceries.add(grocery)
                    }

                    dao.insertAll(groceries)
                    _groceries.postValue(dao.getAllGroceries())
                } else {
                    _groceries.postValue(result)
                }

            } catch (e: Exception) {
                Log.e(TAG, "Groceries list fetch error $e")
            }
        }
    }

    /**
     *  Starts navigation to shopping cart screen
     */
    fun onNavigateToShoppingCart() {
        _navigateToShoppingCart.value = true
    }

    /**
     *  Ends navigation to shopping cart screen
     */
    fun onNavigatedToShoppingCart() {
        _navigateToShoppingCart.value = false
    }

    /**
     *  onClick function for add button
     */
    fun onAddClick(grocery: Grocery) {
        Log.i("test", "Add")

        grocery.count++

        CoroutineScope(Dispatchers.IO).launch {
            try {
                dao.update(grocery)
                Log.i("test", "success")
            } catch (e: Exception) {
                Log.e(TAG, "Groceries list error adding a grocery")
            }
        }

        Toast.makeText(getApplication(), "${grocery.name} added!", Toast.LENGTH_SHORT).show()
    }

    /**
     *  onClick function for subtract button
     */
    fun onSubClick(grocery: Grocery) {
        Log.i("test", "Sub")
        if (grocery.count > 0) {
            grocery.count--

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    dao.update(grocery)
                    Log.i("test", "success")
                } catch (e: Exception) {
                    Log.e(TAG, "Shopping grocery subtract updated")
                }
            }

            Toast.makeText(getApplication(), "${grocery.name} subtracted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(getApplication(), "You don't have any ${grocery.name}!", Toast.LENGTH_SHORT).show()
        }
    }
}