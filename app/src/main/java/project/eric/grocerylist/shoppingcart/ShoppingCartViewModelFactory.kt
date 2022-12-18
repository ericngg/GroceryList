package project.eric.grocerylist.shoppingcart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingCartViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            @Suppress("uncheck_cast")
            return ShoppingCartViewModel(app) as T
        }
        throw IllegalArgumentException("Error creating ShoppingCartViewModel")
    }
}