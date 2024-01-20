package com.dinesh.android.kotlin.retrofit.raw

import android.os.Bundle
import android.util.Log
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain
import com.dinesh.android.kotlin.retrofit.JsonData
import com.google.gson.Gson
import org.json.JSONObject

private val TAG = "log_" + ReadJSON::class.java.name.split(ReadJSON::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ReadJSON : ToolbarMain() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        user()
//        productWithoutGson()
        product()

    }

    private fun product() {
        val gson = Gson()
        val jsonString = resources.openRawResource(R.raw.product).bufferedReader().use { it.readText() }
        val data = gson.fromJson(jsonString, JsonData::class.java)

        Log.d(TAG, "Total count: ${data.totalCount}")
        //        Log.e(TAG,"Total count: ${data.data}")
        //        Log.e(TAG, "Total count: ${data.data[0].category[1].subCategories[1].products[1].pictures}")
        //        Log.e(TAG, "Total count: ${data.data[0].category[0].subCategories[1].products[1].size}")
        data.data.forEach { categoryData ->
            categoryData.category.forEach { category ->
    //                Log.d(TAG,"Category name: ${category.categoryName}")
                category.subCategories.forEach { subCategory ->
    //                    Log.d(TAG,"Sub-category name: ${subCategory.subCategoriesName}")
                    subCategory.products.forEach { product ->
                        Log.d(TAG, "Product ID: ${product.id}")
                    }
                }
            }
        }
    }

    private fun productWithoutGson() {
        val jsonStr = resources.openRawResource(R.raw.product).bufferedReader().use {
            it.readText()
        }

        val jsonObj = JSONObject(jsonStr)

        val totalCount = jsonObj.getInt("total_count")
        Log.d(TAG, "Total Count: $totalCount")

        val dataArr = jsonObj.getJSONArray("data")

        for (i in 0 until dataArr.length()) {
            val categoryObj = dataArr.getJSONObject(i)

            val categoryName = categoryObj.getJSONArray("category").getJSONObject(0).getString("category_name")
            Log.d(TAG, "Category Name: $categoryName")

            val subCategoriesArr = categoryObj.getJSONArray("category").getJSONObject(0).getJSONArray("sub_categories")

            for (j in 0 until subCategoriesArr.length()) {
                val subCategoryObj = subCategoriesArr.getJSONObject(j)

                val subCategoryName = subCategoryObj.getString("sub_categories_name")
                Log.d(TAG, "Sub Category Name: $subCategoryName")

                val productsArr = subCategoryObj.getJSONArray("products")

                for (k in 0 until productsArr.length()) {
                    val productObj = productsArr.getJSONObject(k)

                    val id = productObj.getString("id")
                    Log.d(TAG, "Product ID: $id")

                    val name = productObj.getString("name")
                    Log.d(TAG, "Product Name: $name")

                    val description = productObj.getString("description")
                    Log.d(TAG, "Product Description: $description")

                    val stock = productObj.getInt("stock")
                    Log.d(TAG, "Product Stock: $stock")

                    val sizeArr = productObj.getJSONArray("size")
                    Log.e(TAG, "Product Sizes: $sizeArr")

                    val colorsArr = productObj.getJSONArray("colors")
                    Log.e(TAG, "Product Colors: $colorsArr")

                    val thumbnailUrl = productObj.getString("display_thumbnail")
                    Log.d(TAG, "Product Thumbnail URL: $thumbnailUrl")

                    val price = productObj.getInt("price")
                    Log.d(TAG, "Product Price: $price")

                    val currency = productObj.getString("currency")
                    Log.d(TAG, "Product Currency: $currency")

                    val picturesArr = productObj.getJSONArray("pictures")

                    for (l in 0 until picturesArr.length()) {
                        val pictureUrl = picturesArr.getString(l)
                        Log.d(TAG, "Product Picture URL: $pictureUrl")
                    }

                    if (productObj.has("weight")) {
                        val weight = productObj.getInt("weight")
                        Log.d(TAG, "Product Weight: $weight")
                    }

                    if (productObj.has("dimensions")) {
                        val dimensionsObj = productObj.getJSONObject("dimensions")
                        val length = dimensionsObj.getDouble("length")
                        val width = dimensionsObj.getDouble("width")
                        val height = dimensionsObj.getDouble("height")
                        Log.d(TAG, "Product Dimensions: Length=$length, Width=$width, Height=$height")
                    }
                }
            }
        }
    }

    private fun user() {
        val inputStream = resources.openRawResource(R.raw.user)
        val json = inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(json)
        val usersArray = jsonObject.getJSONArray("data")
        for (i in 0 until usersArray.length()) {
            val userObject = usersArray.getJSONObject(i)
            val id = userObject.getInt("id")
            val name = userObject.getString("name")
            val email = userObject.getString("email")
            val phone = userObject.getString("phone")
            val addressObject = userObject.getJSONObject("address")
            val street = addressObject.getString("street")
            val city = addressObject.getString("city")
            val state = addressObject.getString("state")
            val zip = addressObject.getString("zip")

            Log.i(TAG, "User $id: $name ($email), $phone, $street, $city, $state $zip")
        }
    }

}


//
//data class Product(
//    @SerializedName("total_count") var totalCount: Int? = null,
//    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
//)
//
//data class Data(
//    @SerializedName("category") var category: ArrayList<Category> = arrayListOf()
//)
//
//data class Category(
//    @SerializedName("category_name") var categoryName: String? = null,
//    @SerializedName("sub_categories") var subCategories: ArrayList<SubCategories> = arrayListOf()
//)
//
//data class SubCategories(
//    @SerializedName("sub_categories_name") var subCategoriesName: String? = null,
//    @SerializedName("products") var products: ArrayList<Products> = arrayListOf()
//)
//
//data class Products(
//    @SerializedName("id") var id: String? = null,
//    @SerializedName("name") var name: String? = null,
//    @SerializedName("description") var description: String? = null,
//    @SerializedName("stock") var stock: Int? = null,
//    @SerializedName("size") var size: ArrayList<String> = arrayListOf(),
//    @SerializedName("colors") var colors: ArrayList<String> = arrayListOf(),
//    @SerializedName("display_thumbnail") var displayThumbnail: String? = null,
//    @SerializedName("price") var price: Int? = null,
//    @SerializedName("currency") var currency: String? = null,
//    @SerializedName("pictures") var pictures: ArrayList<String> = arrayListOf()
//)



//  product.json
/*

{
  "total_count": 2,
  "data": [
    {
      "category": [
        {
          "category_name": "Clothing",
          "sub_categories": [
            {
              "sub_categories_name": "Men's Clothing",
              "products": [
                {
                  "id": "MC01",
                  "name": "Men's Slim Fit Shirt",
                  "description": "A stylish and comfortable shirt that will elevate any outfit.",
                  "stock": 50,
                  "size": [
                    "S",
                    "M",
                    "L",
                    "XL",
                    "XXL"
                  ],
                  "colors": [
                    "White",
                    "Blue",
                    "Black"
                  ],
                  "display_thumbnail": "https://example.com/images/ms01-black.jpg",
                  "price": 2999,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ms01-black-front.jpg",
                    "https://example.com/images/ms01-black-back.jpg"
                  ]
                },
                {
                  "id": "MC02",
                  "name": "Men's Slim Fit Jean",
                  "description": "A stylish and comfortable Jean.",
                  "stock": 100,
                  "size": [
                    "S",
                    "M",
                    "L",
                    "XL",
                    "XXL"
                  ],
                  "colors": [
                    "White",
                    "Blue",
                    "Black"
                  ],
                  "display_thumbnail": "https://example.com/images/ms02-gray.jpg",
                  "price": 2799,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ms02-gray-front.jpg",
                    "https://example.com/images/ms02-gray-back.jpg"
                  ]
                }
              ]
            },
            {
              "sub_categories_name": "Women's Clothing",
              "products": [
                {
                  "id": "WC01",
                  "name": "Women's Summer Dress",
                  "description": "A light and airy dress perfect for the summer months",
                  "stock": 10,
                  "size": [
                    "S",
                    "M",
                    "L",
                    "XL",
                    "XXL"
                  ],
                  "colors": [
                    "White",
                    "Blue",
                    "Black",
                    "Red",
                    "Green"
                  ],
                  "display_thumbnail": "https://example.com/images/ws01-red.jpg",
                  "price": 3999,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ws01-red-front.jpg",
                    "https://example.com/images/ws01-red-back.jpg"
                  ]
                },
                {
                  "id": "WC02",
                  "name": "Women's T-Shirt",
                  "description": "Comfortable T-Shirt for women.",
                  "stock": 5,
                  "size": [
                    "S",
                    "M",
                    "L",
                    "XL",
                    "XXL"
                  ],
                  "colors": [
                    "White",
                    "Blue",
                    "Light Blue",
                    "Black",
                    "Red",
                    "Green"
                  ],
                  "display_thumbnail": "https://example.com/images/ws02-blue.jpg",
                  "price": 4499,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ws02-blue-front.jpg",
                    "https://example.com/images/ws02-blue-back.jpg"
                  ]
                }
              ]
            }
          ]
        },
        {
          "category_name": "Electronics",
          "sub_categories": [
            {
              "sub_categories_name": "Smartphone",
              "products": [
                {
                  "id": "MP01",
                  "name": "Samsung Galaxy S21",
                  "description": "A high-end smartphone with top-of-the-line features.",
                  "stock": 50,
                  "colors": [
                    "White",
                    "Blue",
                    "Black",
                    "Phantom Gray"
                  ],
                  "display_thumbnail": "https://example.com/images/ms01-black.jpg",
                  "price": 4999,
                  "currency": "USD",
                  "weight": 169,
                  "dimensions": {
                    "length": 151.7,
                    "width": 71.2,
                    "height": 7.9
                  },
                  "pictures": [
                    "https://example.com/images/ms01-black-front.jpg",
                    "https://example.com/images/ms01-black-back.jpg"
                  ]
                },
                {
                  "id": "MP02",
                  "name": "Apple iPhone 13",
                  "description": "The latest iPhone with a new A15 chip, improved cameras, and longer battery life.",
                  "stock": 500,
                  "colors": [
                    "White",
                    "Black"
                  ],
                  "display_thumbnail": "https://example.com/images/ms01-black.jpg",
                  "price": 9999,
                  "currency": "USD",
                  "weight": 179,
                  "dimensions": {
                    "length": 151.7,
                    "width": 71.2,
                    "height": 7.9
                  },
                  "pictures": [
                    "https://example.com/images/ms01-black-front.jpg",
                    "https://example.com/images/ms01-black-back.jpg"
                  ]
                }
              ]
            },
            {
              "sub_categories_name": "Laptop",
              "products": [
                {
                  "id": "L01",
                  "name": "Apple MacBook Pro",
                  "description": "13 inch Retina Display Laptop, Apple M1 Chip with 8-Core CPU and 8-Core GPU, 8 GB RAM, 256 GB SSD.",
                  "stock": 50,
                  "colors": [
                    "White",
                    "Phantom Gray"
                  ],
                  "display_thumbnail": "https://example.com/images/ms01-black.jpg",
                  "price": 14999,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ms01-black-front.jpg",
                    "https://example.com/images/ms01-black-back.jpg"
                  ]
                },
                {
                  "id": "L02",
                  "name": "HP Pavilion x360",
                  "description": "14 inch Full HD Touchscreen Laptop, Intel Core i5-1135G7, 8 GB DDR4 RAM, 256 GB SSD.",
                  "stock": 50,
                  "colors": [
                    "White",
                    "Black"
                  ],
                  "display_thumbnail": "https://example.com/images/ms01-black.jpg",
                  "price": 9999,
                  "currency": "USD",
                  "pictures": [
                    "https://example.com/images/ms01-black-front.jpg",
                    "https://example.com/images/ms01-black-back.jpg"
                  ]
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}

 */

//  product.json for chatGPT
/*

{
  "total_count": 2,
  "data": [
    {
      "category": [
        {
          "category_name": "Clothing",
          "sub_categories": [
            {
              "sub_categories_name": "Men's Clothing",
              "products": [
                {
                  "id": "MC01",
                  "name": "Men's Slim Fit Shirt"
                },
                {
                  "id": "MC02",
                  "name": "Men's Slim Fit Jean",
                  "description": "A stylish and"
                }
              ]
            },
            {
              "sub_categories_name": "Women's Clothing",
              "products": [
                {
                  "id": "WC01",
                  "name": "Women's Summer Dress"
                },
                {
                  "id": "WC02",
                  "name": "Women's T-Shirt"
                }
              ]
            }
          ]
        },
        {
          "category_name": "Electronics",
          "sub_categories": [
            {
              "sub_categories_name": "Smartphone",
              "products": [
                {
                  "id": "MP01",
                  "name": "Samsung Galaxy S21"
                },
                {
                  "id": "MP02",
                  "name": "Apple iPhone 13"
                }
              ]
            },
            {
              "sub_categories_name": "Laptop",
              "products": [
                {
                  "id": "L01",
                  "name": "Apple MacBook Pro"
                },
                {
                  "id": "L02",
                  "name": "HP Pavilion x360"
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}

 */

//  user.json
/*

{
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "phone": "+1 (123) 456-7890",
      "address": {
        "street": "123 Main St",
        "city": "Anytown",
        "state": "CA",
        "zip": "12345"
      }
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "phone": "+1 (234) 567-8901",
      "address": {
        "street": "456 Elm St",
        "city": "Anycity",
        "state": "NY",
        "zip": "67890"
      }
    }
  ]
}

 */