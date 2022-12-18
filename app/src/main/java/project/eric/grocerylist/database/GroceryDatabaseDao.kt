package project.eric.grocerylist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDatabaseDao {
    @Update
    fun update(grocery: Grocery)

    @Query("SELECT * FROM grocery_table WHERE id = :key")
    fun get(key: Long): Grocery

    @Query("DELETE FROM grocery_table")
    fun clear()

    @Query("SELECT * FROM grocery_table")
    fun getAllGroceries(): List<Grocery>

    @Insert
    fun insert(grocery: Grocery)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(groceries: List<Grocery>)
}