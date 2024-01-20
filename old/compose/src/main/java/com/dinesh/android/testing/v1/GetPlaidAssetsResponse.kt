package com.dinesh.android.testing.v1

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetPlaidAssetsResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("success")
    val success: Boolean
) {
    @Keep
    data class Data(
        @SerializedName("AssetAccount")
        val assetAccount: List<AssetAccount>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("PlaidItem")
        val plaidItem: PlaidItem
    ) {
        @Keep
        data class AssetAccount(
            @SerializedName("account_id")
            val accountId: String,
            @SerializedName("balance_available")
            val balanceAvailable: String?,
            @SerializedName("balance_current")
            val balanceCurrent: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("mask")
            val mask: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("subtype")
            val subtype: String,
            @SerializedName("type")
            val type: String
        )

        @Keep
        data class PlaidItem(
            @SerializedName("id")
            val id: Int,
            @SerializedName("ins_id")
            val insId: String,
            @SerializedName("ins_name")
            val insName: String
        )
    }
}


val hardcodedPlaidAssetsResponse = GetPlaidAssetsResponse(
    data = listOf(
        GetPlaidAssetsResponse.Data(
            assetAccount = listOf(
                GetPlaidAssetsResponse.Data.AssetAccount(
                    accountId = "bnrWrrVElVI1aGAW79XNcNpAN8qEQnfmjen7o",
                    balanceAvailable = "100",
                    balanceCurrent = "110",
                    id = 7,
                    mask = "0000",
                    name = "Plaid Checking",
                    subtype = "checking",
                    type = "depository"
                ),
                GetPlaidAssetsResponse.Data.AssetAccount(
                    accountId = "Rn6D66g3PgIrLQN8Pm9jtnD1nyX9xJCayENAZ",
                    balanceAvailable = null,
                    balanceCurrent = "320.76",
                    id = 4,
                    mask = "5555",
                    name = "Plaid IRA",
                    subtype = "ira",
                    type = "investment"
                )
            ),
            id = 1,
            plaidItem = GetPlaidAssetsResponse.Data.PlaidItem(
                id = 8,
                insId = "ins_2",
                insName = "Chase"
            )
        ),
        GetPlaidAssetsResponse.Data(
            assetAccount = listOf(
                GetPlaidAssetsResponse.Data.AssetAccount(
                    accountId = "DQMXZGBMlnIElNRJLjoqc1jkb5LaPeH3lgPLM",
                    balanceAvailable = null,
                    balanceCurrent = "410",
                    id = 21,
                    mask = "3333",
                    name = "Plaid Credit Card",
                    subtype = "credit card",
                    type = "credit"
                ),
                GetPlaidAssetsResponse.Data.AssetAccount(
                    accountId = "1qqrGDzavkUQeBqvpPXpHdxrELDg4GFqe8AvJ",
                    balanceAvailable = "43200",
                    balanceCurrent = "43200",
                    id = 10,
                    mask = "4444",
                    name = "Plaid Money Market",
                    subtype = "money market",
                    type = "depository"
                )
            ),
            id = 2,
            plaidItem = GetPlaidAssetsResponse.Data.PlaidItem(
                id = 10,
                insId = "ins_56",
                insName = "Chase"
            )
        )
    ),
    message = "Plaid assets fetched successfully",
    statusCode = 200,
    success = true
)