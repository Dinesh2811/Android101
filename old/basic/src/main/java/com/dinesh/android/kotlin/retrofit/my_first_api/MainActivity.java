package com.dinesh.android.kotlin.retrofit.my_first_api;

public class MainActivity {
}

/*


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "log_test";

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        postModel();
        postModelList();
        postByField();
    }

    private void postByField() {
        Call<Model> call = apiInterface.postByField("Dinesh","K","dk@gmail.com","8667024800","4");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                Log.i(TAG, "postModelList: " + response.isSuccessful());
                Log.i(TAG, "postModelList: " + response.code());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.i(TAG, "postModelList onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    private void postModelList() {
        Model model = new Model("Dinesh","K","dk@gmail.com","8667024800","4");
        Call<List<Model>> call = apiInterface.postModelList(model);
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                Log.i(TAG, "postModelList: " + response.isSuccessful());
                Log.i(TAG, "postModelList: " + response.code());
            }
            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                Log.i(TAG, "postModelList onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void postModel() {
        Model model = new Model("Dinesh","K","dk@gmail.com","8667024800","4");
        Call<Model> call = apiInterface.postModel(model);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.i(TAG, "onResponse: " + response.isSuccessful());
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.i(TAG, "postModel onFailure: " + t.getLocalizedMessage());
            }
        });
    }

}


 */

/*

public interface ApiInterface {
//    URL =192.168.35.52/testapi/register.php

    @POST("register.php")
    Call<Model> postModel(@Body Model todo);

    @POST("register.php")
    Call<List<Model>> postModelList(@Body Model todo);


    @POST("register.php")
    @FormUrlEncoded
    Call<Model> postByField(@Field("firstname") String firstname,
                        @Field("lastname") String lastname,
                        @Field("email") String email,
                        @Field("mobile") String mobile,
                        @Field("dob") String dob);


}

 */

/*

public class ApiClient {
//    192.168.35.52/testapi/register.php
    private static final String BASE_URL = "http://192.168.35.52/testapi/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}

 */

/*

public class Model {

    @SerializedName("firstname")
    @Expose
    public String firstname;
    @SerializedName("lastname")
    @Expose
    public String lastname;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("dob")
    @Expose
    public String dob;

    public Model(String firstname, String lastname, String email, String mobile, String dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
    }

    public Model() {
    }

    @Override
    public String toString() {
        return "Model{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

}

 */


/*

{
	"info": {
		"_postman_id": "413d0015-bd7e-425d-9c44-043af39b043b",
		"name": "testapi",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "8132379"
	},
	"item": [
		{
			"name": "192.168.35.52/testapi/register.php",
			"request": {
				"method": "POST",
				"header": [],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "firstname",
							"value": "testapi1",
							"type": "text"
						},
						{
							"key": "lastname",
							"value": "arjun",
							"type": "text"
						},
						{
							"key": "email",
							"value": "testapi@gmail.com",
							"type": "text"
						},
						{
							"key": "mobile",
							"value": "98745622",
							"type": "text"
						},
						{
							"key": "dob",
							"value": "1",
							"type": "text"
						}
					]
				},
				"url": {
					"raw": "192.168.35.52/testapi/register.php",
					"host": [
						"192",
						"168",
						"35",
						"52"
					],
					"path": [
						"testapi",
						"register.php"
					]
				}
			},
			"response": []
		}
	]
}

 */