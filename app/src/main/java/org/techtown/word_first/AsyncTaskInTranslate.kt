package org.techtown.word_first

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class AsyncTaskInTranslate (private var fragment : TranslateFragment) : AsyncTask<String, Void, String>() {

    var translatedMean = ""

    override fun doInBackground(vararg params: String?): String {

        val d = Log.d("params값은>?", "${params[0]}")

        lateinit var str : String

        val allWord : List<String> = params[0]!!.split(",")

        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull() //json을 사용함

        val client = OkHttpClient()
        val url = "https://openapi.naver.com/v1/papago/n2mt" //번역 api요청 연결 url

        Log.d("params값은>?","${allWord[0]}")
        Log.d("params값은>?","${allWord[1]}")
        Log.d("params값은>?","${allWord[2]}")

        //papago url
        val json = JSONObject()
        json.put("source", "${allWord[0]}")
        json.put("target", "${allWord[1]}")
        json.put("text", "${allWord[2]}")//Edittext부분에 적은 부분을 검색함
        //번역할 내용을 담는 부분임

        val body = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
                .header("X-Naver-Client-Id", "ARlRfQh8jMOqRnJIALqo")//내 api id
                .addHeader("X-Naver-Client-Secret", "k69RHlesQC")//내 api 비밀번호
                .url(url)
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }//실패시

            override fun onResponse(call: Call, response: Response) {
                val str = response!!.body!!.string()
                val papagoTM = Gson().fromJson<PapagoTM>(str, PapagoTM::class.java)
                Log.d("papagoTM","$papagoTM")
                translatedMean = papagoTM.message!!.result?.translatedText.toString() // papagoTM을 통해서 원하는 결과만 뽑음
                Log.d("papagoTM", translatedMean)


            }//성공시
         })
        return translatedMean
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        Log.d("result","$result")
    }
}