package com.dinesh.android.kotlin.retrofit.v4_test

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.ApiClient
import com.dinesh.android.kotlin.retrofit.ApiInterface
import com.dinesh.android.kotlin.retrofit.Category
import com.dinesh.android.kotlin.retrofit.Data
import com.dinesh.android.kotlin.retrofit.JsonData
import com.dinesh.android.kotlin.retrofit.Products
import com.dinesh.android.kotlin.retrofit.SubCategories
import com.dinesh.android.kotlin.retrofit.logJsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : ToolbarMain() {
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface("http://192.168.1.4:3000/api/")
            }
            fetchData()
        }

    }

    private fun fetchData() {
//        productAsObject()
//        productAsArray()
        postJsonData()
    }

        private fun postJsonData() {
        lifecycleScope.launch {
            try {
                val jsonData = createJsonData()
                val response = withContext(Dispatchers.IO) { apiInterface.postJsonData(jsonData) }
                if (response.isSuccessful) {
                    Log.e(TAG, "JSON data posted successfully")
                } else {
                    Log.e(TAG, "Error posting JSON data: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error posting JSON data: ${e.message}")
            }
        }
    }

    private fun createJsonData(): JsonData {
        val jsonData = JsonData()
        jsonData.totalCount = 2

        val data = ArrayList<Data>()

        val category1 = Category()
        category1.categoryName = "Clothing"

        val subCategories1 = ArrayList<SubCategories>()
        val subCategory1 = SubCategories()
        subCategory1.subCategoriesName = "Men's Clothing"

        val products1 = ArrayList<Products>()
        val product1 = Products()
        product1.id = "MC01"
        product1.name = "Men's Slim Fit Shirt"

        val product2 = Products()
        product2.id = "MC02"
        product2.name = "Men's Slim Fit Jean"
        product2.description = "A stylish and"

        products1.add(product1)
        products1.add(product2)

        subCategory1.products = products1
        subCategories1.add(subCategory1)

        val subCategory2 = SubCategories()
        subCategory2.subCategoriesName = "Women's Clothing"

        val products2 = ArrayList<Products>()
        val product3 = Products()
        product3.id = "WC01"
        product3.name = "Women's Summer Dress"

        val product4 = Products()
        product4.id = "WC02"
        product4.name = "Women's T-Shirt"

        products2.add(product3)
        products2.add(product4)

        subCategory2.products = products2
        subCategories1.add(subCategory2)

        val categories1 = ArrayList<Category>()
        category1.subCategories = subCategories1
        categories1.add(category1)

        val category2 = Category()
        category2.categoryName = "Electronics"

        val subCategories2 = ArrayList<SubCategories>()
        val subCategory3 = SubCategories()
        subCategory3.subCategoriesName = "Smartphone"

        val products3 = ArrayList<Products>()
        val product5 = Products()
        product5.id = "MP01"
        product5.name = "Samsung Galaxy S21"

        val product6 = Products()
        product6.id = "MP02"
        product6.name = "Apple iPhone 13"

        products3.add(product5)
        products3.add(product6)

        subCategory3.products = products3
        subCategories2.add(subCategory3)

        val subCategory4 = SubCategories()
        subCategory4.subCategoriesName = "Laptop"

        val products4 = ArrayList<Products>()
        val product7 = Products()
        product7.id = "L01"
        product7.name = "Apple MacBook Pro"

        val product8 = Products()
        product8.id = "L02"
        product8.name = "HP Pavilion x360"

        products4.add(product7)
        products4.add(product8)

        subCategory4.products = products4
        subCategories2.add(subCategory4)

        val categories2 = ArrayList<Category>()
        category2.subCategories = subCategories2
        categories2.add(category2)

        data.add(Data(categories1))
        data.add(Data(categories2))

        jsonData.data = data

        return jsonData
    }


    private fun productAsObject() {
        lifecycleScope.launch {
            try {
                val jsonData = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsObject() }
                Log.e(TAG, "productAsObject: ")
                logJsonData(jsonData)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONObject: ${e.message}")
            }
        }
    }

    private fun productAsArray() {
        lifecycleScope.launch {
            try {
                val jsonDataList = withContext(Dispatchers.IO) { apiInterface.getProductJsonDataAsArray() }
                if (jsonDataList.isNotEmpty()) {
                    val jsonData = jsonDataList[0]
                    Log.e(TAG, "productAsArray: ")
                    logJsonData(jsonData)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting JSONArray: ${e.message}")
            }
        }
    }

}