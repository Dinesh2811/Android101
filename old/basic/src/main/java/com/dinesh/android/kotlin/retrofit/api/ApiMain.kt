package com.dinesh.android.kotlin.retrofit.api

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dinesh.android.kotlin.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


private val TAG = "log_" + ApiMain::class.java.name.split(ApiMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ApiMain : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            apiInterface = withContext(Dispatchers.IO) {
                ApiClient.getApiInterface("http://192.168.1.6:3000/")
            }
            fetchData()
        }

    }

    private fun fetchData() {
        getCustomers()
//        getCustomerById("1")
//        searchCustomers("john", null)
//        getCustomerField("2", "products", "Product 3")

//        val token = "81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30"   //  Bearer token
//        getBearerAuthenticatedCustomers("Bearer $token")

//        addProductToCustomerCart("1", "Product 4", "49.99", 1, null)
//        pickImage.launch(PickVisualMediaRequest())
    }

    private fun getCustomers() {
        val call = apiInterface.getCustomers("api/customers")

        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if (response.isSuccessful) {
                    val customers = response.body()
                    if (customers != null) {
                        for (customer in customers) {
                            Log.d(TAG, "Customer: $customer")
                        }
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    private fun getCustomerById(id: String) {
        val call = apiInterface.getCustomerById("api/customers/$id")

        call.enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val customer = response.body()
                    if (customer != null) {
                        Log.d(TAG, "Customer: $customer")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    private fun searchCustomers(name: String?, mobileNumber: String?) {
        val queryParams = mutableListOf<String>()
        name?.let { queryParams.add("name=$it") }
        mobileNumber?.let { queryParams.add("mobileNumber=$it") }
        val query = queryParams.joinToString("&")

        val call = apiInterface.searchCustomers("api/customers_search?$query")
        /*

        http://localhost:3000/api/customers/search?name=john
        http://localhost:3000/api/customers/search?mobileNumber=123456
        http://localhost:3000/api/customers/search?name=john&mobileNumber=123456

         */

        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if (response.isSuccessful) {
                    val customers = response.body()
                    if (customers != null) {
                        for (customer in customers) {
                            Log.d(TAG, "Customer: $customer")
                        }
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    private fun getCustomerField(id: String, field: String, value: String) {
        val call = apiInterface.getCustomerField("api/customers/$id/$field/$value")
        /*

        http://localhost:3000/api/customers/1/details/name
        http://localhost:3000/api/customers/1/products/Product%202
        http://localhost:3000/api/customers/1/products/19.99

         */

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val fieldData = response.body()
                    if (fieldData != null) {
                        Log.d(TAG, "Field Data: $fieldData")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    private fun getBearerAuthenticatedCustomers(token: String) {
        val call = apiInterface.getBearerAuthenticatedCustomers(token)

        call.enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                if (response.isSuccessful) {
                    val customers = response.body()
                    if (customers != null) {
                        for (customer in customers) {
                            Log.d(TAG, "Customer: $customer")
                        }
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })
    }

    private fun addProductToCustomerCart(customerId: String, productName: String, productPrice: String, productCount: Int, productImage: MultipartBody.Part?) {
        val nameRequestBody = productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceRequestBody = productPrice.toRequestBody("text/plain".toMediaTypeOrNull())
        val countRequestBody = productCount.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val call = apiInterface.addProductToCustomerCart(customerId, nameRequestBody, priceRequestBody, countRequestBody, productImage)

        call.enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful) {
                    val customer = response.body()
                    if (customer != null) {
                        Log.d(TAG, "Customer: $customer")
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                    Log.e(TAG, "response: $response")
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }
        })

    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            Log.d(TAG, "uri --> ${uri}")
            val inputStream = contentResolver.openInputStream(uri)
            val file = File(cacheDir, "productImage.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("productImage", file.name, requestBody)

            addProductToCustomerCart("1", "Product 4", "49.99", 1, part)
        } else {
            Log.e(TAG, "Image URI is null")
        }
    }

}


//  productServer_v1.0.js
/*

const express = require('express');
const bodyParser = require('body-parser');
const multer = require('multer');

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Hardcoded initial customer data
let customers = [
  {
    customerId: '1',
    customer: {
      details: {
        name: 'John Doe',
        mobileNumber: '1234567890'
      },
      products: [
        {
          productName: 'Product 1',
          productPrice: '19.99',
          productCount: 2,
          productImage: null
        },
        {
          productName: 'Product 2',
          productPrice: '29.99',
          productCount: 1,
          productImage: null
        }
      ]
    }
  },
  {
    customerId: '2',
    customer: {
      details: {
        name: 'Jane Smith',
        mobileNumber: '9876543210'
      },
      products: [
        {
          productName: 'Product 3',
          productPrice: '39.99',
          productCount: 3,
          productImage: null
        }
      ]
    }
  }
];

// Multer configuration for file uploads
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/');
  },
  filename: function (req, file, cb) {
    cb(null, file.originalname);
  }
});
const upload = multer({ storage });

// Middleware to log API requests
app.use((req, res, next) => {
  console.log(`[${new Date().toISOString()}] ${req.method} ${req.originalUrl}`);
  next();
});

// GET endpoint to retrieve all customer data
app.get('/api/customers', (req, res) => {
  res.json(customers);
});

// GET endpoint to retrieve customer details by ID
app.get('/api/customers/:id', (req, res) => {
  const customerId = req.params.id;
  const customer = customers.find(cust => cust.customerId === customerId);

  if (!customer) {
    res.status(404).json({ error: 'Customer not found' });
  } else {
    res.json(customer);
  }
});

// GET endpoint with query parameters
app.get('/api/customers_search', (req, res, next) => {
  const { name, mobileNumber } = req.query;
  const filteredCustomers = customers.filter(customer => {
    return (
      (name && customer.customer.details.name.toLowerCase().includes(name.toLowerCase())) ||
      (mobileNumber && customer.customer.details.mobileNumber.includes(mobileNumber))
    );
  });
  if (filteredCustomers.length === 0) {
    const error = new Error('Customer not found');
    error.status = 404;
    console.error(`[${new Date().toISOString()}] Error: ${error.message}`);
    return res.status(404).json({ error: 'Customer not found', criteria: { name, mobileNumber } });
  }
  res.json(filteredCustomers);
});

// GET endpoint with dynamic path segments
app.get('/api/customers/:id/:field/:value', (req, res) => {
  const customerId = req.params.id;
  const field = req.params.field;
  const value = req.params.value;

  const customer = customers.find(cust => cust.customerId === customerId);
  if (!customer) {
    res.status(404).json({ error: 'Customer not found' });
  } else {
    const getFieldData = (obj, fields) => {
      let data = obj;
      for (let i = 0; i < fields.length; i++) {
        const currentField = fields[i];
        if (data && data.hasOwnProperty(currentField)) {
          data = data[currentField];
        } else {
          return null;
        }
      }
      return data;
    };

    const fieldData = getFieldData(customer.customer, field.split('/'));
    if (!fieldData) {
      res.status(404).json({ error: `${field} not found` });
    } else {
      if (Array.isArray(fieldData)) {
        const matchedProduct = fieldData.find(product => {
          return (
            product.productName.toLowerCase() === value.toLowerCase() ||
            product.productPrice === value ||
            product.productCount.toString() === value
          );
        });

        if (!matchedProduct) {
          res.status(404).json({ error: 'Product not found' });
        } else {
          res.json(matchedProduct);
        }
      } else {
        res.json(fieldData);
      }
    }
  }
});

// GET endpoint with headers
app.get('/api/bearer_authenticated_customers', (req, res) => {
  const authorizationHeader = req.header('Authorization');
  if (!authorizationHeader) {
    console.error(`[${new Date().toISOString()}] Error: Unauthorized`);
    res.status(401).json({ error: 'Unauthorized' });
  } else {
    const token = authorizationHeader.replace('Bearer ', '');
    if (token !== '81|rvf3ZWny9w88tXYAOWCwXh6VBNbTO5ZBHroSbW30') {
      console.error(`[${new Date().toISOString()}] Error: Invalid token`);
      res.status(401).json({ error: 'Unauthorized' });
    } else {
      // Authentication successful
      console.log(`[${new Date().toISOString()}] Authentication successful`);

      // Perform authentication logic here
      // ...

      // Return the customers data
      console.log(`[${new Date().toISOString()}] Returning customers data`);
      res.json(customers);
    }
  }
});


// POST endpoint to add a product to a customer's cart
app.post('/api/post_customers/:id/products', upload.single('productImage'), (req, res, next) => {
  const customerId = req.params.id;
  const customer = customers.find(cust => cust.customerId === customerId);

  if (!customer) {
    res.status(404).json({ error: 'Customer not found' });
  } else {
    const { productName, productPrice, productCount } = req.body;
//    const productImage = req.file;
    const productImage = req.file ? req.file.path : null; // Save file path instead of the file object

    const product = {
      productName,
      productPrice,
      productCount,
      productImage
    };
    customer.customer.products.push(product);

    // Log the new product added
    console.log(`[${new Date().toISOString()}] New product added to customer ${customerId}`);
    console.log(product);

    res.json(customer);
  }
});


// Error handling middleware
app.use((err, req, res, next) => {
  console.error(`[${new Date().toISOString()}] Error: ${err.message}`);
  res.status(500).json({ error: 'Internal Server Error' });
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});


 */