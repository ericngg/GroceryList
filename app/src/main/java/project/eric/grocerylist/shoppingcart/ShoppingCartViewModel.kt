package project.eric.grocerylist.shoppingcart

import android.app.Application
import androidx.lifecycle.*

class ShoppingCartViewModel(application: Application): AndroidViewModel(application) {
    private val _navigateToGroceryList = MutableLiveData<Boolean>()
    val navigateToGroceryList
        get() = _navigateToGroceryList

    fun onNavigateToGroceryList() {
        _navigateToGroceryList.value = true
    }

    fun onNavigatedToGroceryList() {
        _navigateToGroceryList.value = false
    }
}