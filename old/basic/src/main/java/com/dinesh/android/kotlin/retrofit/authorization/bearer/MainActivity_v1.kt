package com.dinesh.android.kotlin.retrofit.authorization.bearer

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.File

private val TAG = "log_" + MainActivity_v1::class.java.name.split(MainActivity_v1::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity_v1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://chennainextlevel.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MyApiService::class.java)

        val orderDetails = listOf(
            OrderDetail(
                name = "testing",
                specification = "testing",
                price = "150",
                image = File("/Users/gulshan/Downloads/home.png"),
                quantity = "12",
                imageurl = ""
            ),
            OrderDetail(
                name = "testing1",
                specification = "testing1",
                price = "250",
                image = File("/Users/gulshan/Downloads/peakpx.jpg"),
                quantity = "15",
                imageurl = ""
            )
        )

        val orderData = OrderData(
            customer_id = "38",
            order_details = orderDetails
        )

        val authToken = "81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30"
        val authHeader = "Bearer $authToken"

        lifecycleScope.launch {
            try {
                val response: Response<OrderResponse> = apiService.createOrder(authHeader, orderData)
                if (response.isSuccessful) {
                    val orderResponse: OrderResponse? = response.body()
                    Log.d(TAG, "onCreate: Order created successfully")
                    Log.d(TAG, "onCreate: Message: ${orderResponse?.message}")
                    Log.d(TAG, "onCreate: Response Code: ${orderResponse?.reponsecode}")
                    Log.d(TAG, "onCreate: Data: ${orderResponse?.data}")
                } else {
                    Log.e(TAG, "onCreate: Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.message}")
            }
        }
    }
}


data class OrderData(
    val customer_id: String,
    val order_details: List<OrderDetail>
)

data class OrderDetail(
    val name: String,
    val specification: String,
    val price: String,
    val image: File,
    val quantity: String,
    val imageurl: String
)

data class OrderResponse(
    val status: Boolean,
    val message: String,
    val reponsecode: Int,
    val data: String
)

interface MyApiService {
    @Headers("Accept: application/json")
    @POST("orders")
    suspend fun createOrder(
        @Header("Authorization") authHeader: String,
        @Body orderData: OrderData
    ): Response<OrderResponse>
}



//  pc_bearer_post.json
/*


{
	"info": {
		"_postman_id": "a3f04115-adb6-4c74-9946-c6c44683c9a5",
		"name": "onepixcel",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "27788171"
	},
	"item": [
		{
			"name": "Create Orders",
			"request": {
				"auth": {
					"type": "bearer",
					"bearer": [
						{
							"key": "token",
							"value": "81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30",
							"type": "string"
						}
					]
				},
				"method": "POST",
				"header": [
					{
						"key": "Accept",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "customer_id",
							"value": "38",
							"type": "text"
						},
						{
							"key": "order_details[0][name]",
							"value": "testing",
							"type": "text"
						},
						{
							"key": "order_details[0][specification]",
							"value": "testing",
							"type": "text"
						},
						{
							"key": "order_details[0][price]",
							"value": "150",
							"type": "text"
						},
						{
							"key": "order_details[0][image]",
							"type": "file",
							"src": "/Users/gulshan/Downloads/home.png"
						},
						{
							"key": "order_details[1][name]",
							"value": "testing1",
							"type": "text"
						},
						{
							"key": "order_details[1][specification]",
							"value": "testing1",
							"type": "text"
						},
						{
							"key": "order_details[1][price]",
							"value": "250",
							"type": "text"
						},
						{
							"key": "order_details[1][image]",
							"type": "file",
							"src": "/Users/gulshan/Downloads/peakpx.jpg"
						},
						{
							"key": "order_details[0][quantity]",
							"value": "12",
							"type": "text"
						},
						{
							"key": "order_details[1][quantity]",
							"value": "15",
							"type": "text"
						},
						{
							"key": "order_details[0][imageurl]",
							"value": "",
							"type": "text"
						},
						{
							"key": "order_details[1][imageurl]",
							"value": "",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "https://chennainextlevel.com/api/v1/orders",
					"protocol": "https",
					"host": [
						"chennainextlevel",
						"com"
					],
					"path": [
						"api",
						"v1",
						"orders"
					]
				}
			},
			"response": []
		}
	]
}


 */