package org.techtown.word_first

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class TranslateFragment: Fragment() {

    lateinit var transbutton : Button
    lateinit var sentenceTranslate : EditText
    lateinit var sentenceComplete : TextView

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : TranslateFragment{
            return TranslateFragment()
        }
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "StudyFragment - onCreate() called")

    }


    // 뷰가 생성되었을 때 화면과 연결해주는 것
    //fragment와 레이아웃을 연결시켜주는 부분임.
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "StudyFragment - onCreateView() called")

        val view = inflater.inflate(R.layout.fragment_translate, container, false)

        transbutton = view.findViewById(R.id.btnTranslate)
        sentenceComplete = view.findViewById(R.id.complete_sentence)
        sentenceTranslate = view.findViewById(R.id.translate_sentence)


        transbutton.setOnClickListener {

            val JSON = "application/json; charset=utf-8".toMediaTypeOrNull() //json을 사용함

            val client = OkHttpClient()
            var url = "https://openapi.naver.com/v1/papago/n2mt" //api요청 연결 url
            //papago url
            var json = JSONObject()
            json.put("source","en")
            json.put("target","ko")
            json.put("text","${sentenceTranslate.text}")//Edittext부분에 적은 부분을 검색함
            //번역할 내용을 담는 부분임

            val body = json.toString().toRequestBody(JSON);
            val request = Request.Builder()
                    .header("X-Naver-Client-Id", "ARlRfQh8jMOqRnJIALqo")//내 api id
                    .addHeader("X-Naver-Client-Secret","k69RHlesQC")//내 api 비밀번호
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }//실패시

                override fun onResponse(call: Call, response: Response) {
                    var str = response!!.body!!.string()
                    var papagoTM = Gson().fromJson<PapagoTM>(str, PapagoTM::class.java)
                    sentenceComplete.text = papagoTM.message!!.result?.translatedText.toString() // papagoTM을 통해서 원하는 결과만 뽑음
                }//성공시
            })
        }
        //번역하기 버튼 클릭시 papago api실행
        return view
    }
}


