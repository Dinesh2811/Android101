package com.dinesh.android.kotlin.retrofit.authorization.bearer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit

private val TAG = "log_" + MainActivity_v2::class.java.name.split(MainActivity_v2::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]


class MainActivity_v2 : AppCompatActivity() {
    private lateinit var productNameEditText: EditText
    private lateinit var specificationEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var quantityEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var addProductButton: Button
    private lateinit var submitButton: Button
    private lateinit var orderList: MutableList<OrderDetail>
    private lateinit var apiService: MyApiService

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retrofit_main)

        productNameEditText = findViewById(R.id.productNameEditText)
        specificationEditText = findViewById(R.id.specificationEditText)
        priceEditText = findViewById(R.id.priceEditText)
        quantityEditText = findViewById(R.id.quantityEditText)
        imageView = findViewById(R.id.imageView)
        addProductButton = findViewById(R.id.addProductButton)
        submitButton = findViewById(R.id.submitButton)

        orderList = mutableListOf()

        imageView.setOnClickListener { openCamera() }
        addProductButton.setOnClickListener { addProductToList() }
        submitButton.setOnClickListener { uploadImage() }

    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun addProductToList() {
        val productName = productNameEditText.text.toString()
        val specification = specificationEditText.text.toString()
        val price = priceEditText.text.toString()
        val quantity = quantityEditText.text.toString()
        val imageBitmap = imageView.drawable.toBitmap()

        val imageFile = createImageFile(imageBitmap)

        val orderDetail = OrderDetail(name = productName, specification = specification, price = price, image = imageFile, quantity = quantity, imageurl = "temp.jpg")

        orderList.add(orderDetail)

        productNameEditText.text.clear()
        specificationEditText.text.clear()
        priceEditText.text.clear()
        quantityEditText.text.clear()
        imageView.setImageResource(R.drawable.ic_launcher_foreground)

        Log.d(TAG, "Product added to list: $orderDetail")
    }

    private fun createImageFile(bitmap: Bitmap): File {
        val tempFile = File(cacheDir, "tempImage.jpg")
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        try {
            tempFile.createNewFile()
            tempFile.writeBytes(byteArray)
        } catch (e: Exception) {
            Log.e(TAG, "createImageFile: ${e.message}")
            e.printStackTrace()
        }
        return tempFile
    }

    private fun uploadImage() {
        apiService = ApiClient.getApiInterface()
        val authToken = "81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30"
        val authHeader = "Bearer $authToken"

        val orderDetails: List<OrderDetail> = orderList

        val orderData = OrderData(
            customer_id = "38",
            order_details = orderDetails
        )

        Log.d(TAG, "orderList (${orderDetails.size}) --> $orderDetails")
        Log.e(TAG, "orderData (${orderData.order_details.size}) --> $orderData")

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
                    Log.e(TAG, "onCreate: Unsuccessful response: ${response}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ${e.message}")
            }
        }

    }
}


object ApiClient {
    private const val TIMEOUT_SECONDS = 30L
    private val loggingInterceptor = HttpLoggingInterceptor {
//            message -> Log.d(TAG, message)
    }.apply {
//        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getApiInterface(apiInterface: Class<T>, baseUrl: String = "https://chennainextlevel.com/api/v1/"): T {
        return createRetrofit(baseUrl).create(apiInterface)
    }

    inline fun <reified T> getApiInterface(baseUrl: String = "https://chennainextlevel.com/api/v1/"): T {
        return getApiInterface(T::class.java, baseUrl)
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