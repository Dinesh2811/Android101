package com.dinesh.android.kotlin.retrofit.authorization.bearer

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.kotlin.retrofit.ApiClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            uploadImage(uri)
        } else {
            Log.e(TAG, "Image URI is null")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickImage.launch(PickVisualMediaRequest())
    }

    private fun uploadImage(imageFileUri: Uri) {
        apiService = ApiClient.getApiInterface("https://chennainextlevel.com/")

        val imagePart = MultipartBody.Part.createFormData(
            "order_details[0][image]",
            "image.jpg",
            contentResolver.openInputStream(imageFileUri)!!.readBytes().toRequestBody("image/*".toMediaTypeOrNull())
        )

        val customerId = "38"
        val token = "81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30"

        val orderName = "testing"
        val orderSpecification = "testing"
        val orderPrice = "150"
        val orderQuantity = "12"

        val acceptHeader = "application/json"
        val authorizationHeader = "Bearer $token"
        val customerIdPart = createPartFromString(customerId)
        val orderNamePart = createPartFromString(orderName)
        val orderSpecificationPart = createPartFromString(orderSpecification)
        val orderPricePart = createPartFromString(orderPrice)
        val orderQuantityPart = createPartFromString(orderQuantity)
        val orderImageUrlPart = createPartFromString("")

        val requestMessage = "Request: customerId=$customerId, orderName=$orderName, orderSpecification=$orderSpecification, " +
                "orderPrice=$orderPrice, orderQuantity=$orderQuantity"

        Log.d(TAG, requestMessage)
        Log.d(TAG, "Sending create order request...")
        lifecycleScope.launch {
            try {
                val response = apiService.createOrder(
                    acceptHeader,
                    authorizationHeader,
                    customerIdPart,
                    orderNamePart,
                    orderSpecificationPart,
                    orderPricePart,
                    orderQuantityPart,
                    orderImageUrlPart,
                    imagePart
                )

                if (response.isSuccessful) {
                    Log.d(TAG, "response ---> $response")
                    Log.d(TAG, "body ---> ${response.body()}")
                    Toast.makeText(this@MainActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                } else {
                    Log.e(TAG, "Failed to create order: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network request failed: ${e.message}")
            }
        }
    }

    private fun createPartFromString(string: String): RequestBody {
        return string.toRequestBody("text/plain".toMediaTypeOrNull())
    }
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