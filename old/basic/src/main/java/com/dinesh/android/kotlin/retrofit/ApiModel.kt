package com.dinesh.android.kotlin.retrofit

import com.google.gson.annotations.SerializedName

data class JsonData(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
)

data class Data(
    @SerializedName("category") var category: ArrayList<Category> = arrayListOf()
)

data class Category(
    @SerializedName("category_name") var categoryName: String? = null,
    @SerializedName("sub_categories") var subCategories: ArrayList<SubCategories> = arrayListOf()
)

data class SubCategories(
    @SerializedName("sub_categories_name") var subCategoriesName: String? = null,
    @SerializedName("products") var products: ArrayList<Products> = arrayListOf()
)

data class Products(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("size") var size: ArrayList<String> = arrayListOf(),
    @SerializedName("colors") var colors: ArrayList<String> = arrayListOf(),
    @SerializedName("display_thumbnail") var displayThumbnail: String? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("pictures") var pictures: ArrayList<String> = arrayListOf()
)