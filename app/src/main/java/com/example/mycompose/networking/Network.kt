package com.example.mycompose.networking

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import kotlinx.serialization.json.Json
import java.lang.Exception

suspend fun networking():String?{

    val TIME_OUT = 60_000
    val client = HttpClient(Android) {
        install(JsonFeature) {
            /*serializer = GsonSerializer() {
                setPrettyPrinting()
                disableHtmlEscaping()
            }*/
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }
    var answer:String? = null
    var answer2:String? = null

    try{
        answer = client.submitFormWithBinaryData(
            "http://192.168.178.33:8080/customer",
            formData = formData{
                append("first_name", "Jet")
            })

        answer2 = client.post(
            "http://192.168.178.33:8080/order"
        )

        //listOf(answer2)
    }catch (e: Exception){
        e.printStackTrace()
    }
    return answer
}