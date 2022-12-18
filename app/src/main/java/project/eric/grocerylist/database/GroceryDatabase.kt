package project.eric.grocerylist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Grocery::class], version = 1, exportSchema = false)
abstract class GroceryDatabase : RoomDatabase() {
    abstract val groceryDatabaseDao: GroceryDatabaseDao

    companion object {
        private var INSTANCE: GroceryDatabase? = null

        fun getInstance(context: Context): GroceryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroceryDatabase::class.java,
                        "grocery_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}