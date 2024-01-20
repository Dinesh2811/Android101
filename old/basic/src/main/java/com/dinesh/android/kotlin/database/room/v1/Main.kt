package com.dinesh.android.kotlin.database.room.v1

import android.app.Application
import android.content.Context
import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main : AppCompatActivity() {
    private lateinit var purchaseOrderViewModel: PurchaseOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        purchaseOrderViewModel = ViewModelProvider(this)[PurchaseOrderViewModel::class.java]

        val item1 = PurchaseOrderItem("Item 1", 3)
        val item2 = PurchaseOrderItem("Item 2", 5)
        val purchaseOrder = PurchaseOrder(orderNumber = "PO123", items = listOf(item1, item2))

        insertPurchaseOrder(purchaseOrder)

        purchaseOrderViewModel.allPurchaseOrders.observe(this) { purchaseOrders ->
            purchaseOrders.forEach { purchaseOrder ->
                Log.i(TAG, "onCreate: $purchaseOrder")
            }
        }
    }

    private fun insertPurchaseOrder(purchaseOrder: PurchaseOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            purchaseOrderViewModel.insertPurchaseOrder(purchaseOrder)
        }
    }
}

@Entity(tableName = "purchase_orders")
data class PurchaseOrder(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val orderNumber: String,
    @TypeConverters(PurchaseOrderItemTypeConverter::class)
    val items: List<PurchaseOrderItem>
)

data class PurchaseOrderItem(
    val itemName: String,
    val itemQuantity: Int
)

class PurchaseOrderItemTypeConverter {
    @TypeConverter
    fun fromPurchaseOrderItemList(itemList: List<PurchaseOrderItem>): String {
        val gson = Gson()
        return gson.toJson(itemList)
    }

    @TypeConverter
    fun toPurchaseOrderItemList(itemListString: String): List<PurchaseOrderItem> {
        val gson = Gson()
        val itemType = object : TypeToken<List<PurchaseOrderItem>>() {}.type
        return gson.fromJson(itemListString, itemType)
    }
}

@Dao
interface PurchaseOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchaseOrder(purchaseOrder: PurchaseOrder)

    @Query("SELECT * FROM purchase_orders")
    fun getAllPurchaseOrders(): LiveData<List<PurchaseOrder>>
}

@Database(entities = [PurchaseOrder::class], version = 1)
@TypeConverters(PurchaseOrderItemTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun purchaseOrderDao(): PurchaseOrderDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

class PurchaseOrderViewModel(application: Application) : AndroidViewModel(application) {
    private val purchaseOrderDao: PurchaseOrderDao = AppDatabase.getDatabase(application).purchaseOrderDao()

    fun insertPurchaseOrder(purchaseOrder: PurchaseOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            purchaseOrderDao.insertPurchaseOrder(purchaseOrder)
        }
    }

    val allPurchaseOrders: LiveData<List<PurchaseOrder>> = purchaseOrderDao.getAllPurchaseOrders()
}
