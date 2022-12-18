package project.eric.grocerylist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_table")
data class Grocery(
    @PrimaryKey
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String
)