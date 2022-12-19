package project.eric.grocerylist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDatabaseDao {
    /**
     *  @param: Grocery (grocery entity)
     *
     *  Updates database with new grocery
     */
    @Update
    fun update(grocery: Grocery)

    /**
     *  @param: key (long object)
     *
     *  Updates the database with new grocery
     *
     *  @return: grocery object
     */
    @Query("SELECT * FROM grocery_table WHERE id = :key")
    fun get(key: Long): Grocery

    /**
     *  Clears database
     */
    @Query("DELETE FROM grocery_table")
    fun clear()

    /**
     *  Gets all groceries from the database
     *
     *  @return: List of groceries
     */
    @Query("SELECT * FROM grocery_table")
    fun getAllGroceries(): List<Grocery>

    /**
     *  Gets all groceries from database that have a count greater than zero
     *
     *  @return: List of groceries with count greater than zero
     */
    @Query("SELECT * FROM grocery_table WHERE count > 0")
    fun getAllShoppingCart(): List<Grocery>

    /**
     *  @param: Grocery (grocery entity)
     *
     *  Inserts a grocery entity into the database
     */
    @Insert
    fun insert(grocery: Grocery)


    /**
     *  @param: List of Groceries (List of grocery entities)
     *
     *  Inserts a list of grocery entities into the database
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(groceries: List<Grocery>)
}