package project.eric.grocerylist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.eric.grocerylist.database.GroceryDatabaseDao

class GroceryListViewModelFactory(val dao: GroceryDatabaseDao, val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryListViewModel::class.java)) {
            @Suppress("uncheck_cast")
            return GroceryListViewModel(dao, app) as T
        }
        throw IllegalArgumentException("Error creating mainViewModel")
    }
}