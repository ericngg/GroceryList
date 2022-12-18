package project.eric.grocerylist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val list = listOf<String>("Apple", "Banana", "Carrot", "Donuts", "Eggs", "Fish", "Garlic")
                var count = 1

                val groceries = mutableListOf<Grocery>()

                for (grocery in list) {
                    val grocery = Grocery(count.toLong(), grocery)
                    count++

                    groceries.add(grocery)
                }

                dao.insertAll(groceries)
            } catch (e: Exception) {
                Log.e(TAG, "Groceries list fetch error $e")
            }
        }
    }

    fun fetchGroceries() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _groceries.postValue(dao.getAllGroceries())
            } catch (e: Exception) {
                Log.e(TAG, "Groceries list fetch error $e")
            }
        }
    }
}