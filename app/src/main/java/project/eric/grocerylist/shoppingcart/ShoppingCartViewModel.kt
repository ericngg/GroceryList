package project.eric.grocerylist.shoppingcart

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import project.eric.grocerylist.database.Grocery
import project.eric.grocerylist.database.GroceryDatabaseDao

class ShoppingCartViewModel(val dao: GroceryDatabaseDao, application: Application): AndroidViewModel(application) {
    private val TAG = "ShoppingCartViewModel"

    private val _navigateToGroceryList = MutableLiveData<Boolean>()
    val navigateToGroceryList
        get() = _navigateToGroceryList

    private val _shoppingCartList = MutableLiveData<List<Grocery>>()
    val shoppingCartList
        get() = _shoppingCartList

    fun onNavigateToGroceryList() {
        _navigateToGroceryList.value = true
    }

    fun onNavigatedToGroceryList() {
        _navigateToGroceryList.value = false
    }

    /**
     *  Fetch function to get data from the database
     */
    fun fetchShoppingCart() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _shoppingCartList.postValue(dao.getAllShoppingCart())
            } catch (e: Exception) {
                Log.e(TAG, "Groceries list fetch error $e")
            }
        }
    }
}