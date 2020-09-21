package com.andy.andyyohanes.data.network

import com.andy.andyyohanes.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        } else {
            val errorCode = response.code()
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            if(errorCode == 403)
                message.append("API calls have a limit, please wait")
            else {
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {

                    }
                }
            }

            throw ApiException(message.toString())
        }
    }
}