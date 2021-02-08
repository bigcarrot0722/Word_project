package org.techtown.word_first

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson


class TranslateFragment: Fragment() {

    lateinit var transbutton : Button
    lateinit var sentenceTranslate : EditText
    lateinit var sentenceComplete : TextView
    lateinit var translateWord : String
    lateinit var kk : List<String>

    var selectInNation : String = "null"
    var selectOutNation : String = "null"
    //api사용할 때 쓰는 변수임 쓴 언어 / 번역할 언어



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


        Log.d(TAG, "TranslateFragment - onCreateView() called")

        val view = inflater.inflate(R.layout.fragment_translate, container, false)

        transbutton = view.findViewById(R.id.btnTranslate)
        sentenceComplete = view.findViewById(R.id.complete_sentence)
        sentenceTranslate = view.findViewById(R.id.translate_sentence)

        if(sentenceTranslate.text != null){
            sentenceTranslate.setText(null)
        }

        //Log.d(TAG,"senteceTranslate의 값은? - ${sentenceTranslate.text}")

        /*번역하기 버튼을 눌렀을 때 naver api와 연결해서 원하는 값을 가져오는 코드임*/
        transbutton.setOnClickListener {
            /*쓰거나 번역할 언어를 선택하지 않았거나 아무 텍스트도 입력하지 않았다면 번역이 안됨*/
            if(!sentenceTranslate.getText().toString().toString().isEmpty()) {
                Log.d(TAG,"senteceTranslate의 값은?22 - ${sentenceTranslate.text}")
                Log.d(TAG,"selectInNation의 값은?22 - ${selectInNation}")
                Log.d(TAG,"selectOutNation의 값은?22 - ${selectOutNation}")

                if(selectInNation != selectOutNation) {
                    /*번역기 부분 코드*/
                        translateWord = AsyncTaskInTranslate(this).execute("${selectInNation},$selectOutNation,${sentenceTranslate.text}").get()
                        val papagoTM = Gson().fromJson<PapagoTM>(translateWord, PapagoTM::class.java)
                        sentenceComplete.text = papagoTM.message!!.result?.translatedText.toString()
                }
                else{
                    sentenceComplete.text = "번역할 언어를 다시 설정하세요."
                }
            }
            else{
                sentenceComplete.text = "번역할 문자를 입력하세요"
            }
        }

        //번역하기 버튼 클릭시 papago api실행
        return view
    }

    /*spinner연결 부분*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "TranslateFragment - onViewCreated() called")
        /*Find the id of spinner*/
        val spinner_setIn = view.findViewById<Spinner>(R.id.setInLanguage)
        val spinner_setOut = view.findViewById<Spinner>(R.id.setOutLanguage)

        val setIndata = resources.getStringArray(R.array.setInData)
        val setOutdata = resources.getStringArray(R.array.setOutData)

        /*스피너 어댑터 설정*/
        spinner_setIn.adapter = ArrayAdapter(this.activity!!, R.layout.support_simple_spinner_dropdown_item, setIndata)
        spinner_setOut.adapter = ArrayAdapter(this.activity!!, R.layout.support_simple_spinner_dropdown_item, setOutdata)
        Log.d(TAG, "TranslateFragment - onViewCreated() called-(1)")
        /* 작성할 언어가 선택 됐을 때 url로 보내기 위한 단어로 설정하는 코드임*/
        spinner_setIn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "TranslateFragment - onItemSelected() called11")
                selectInNation = spinner_setIn.getItemAtPosition(position).toString()
                when(selectInNation){
                    "한국어" -> selectInNation = "ko"
                    "영어" -> selectInNation = "en"
                    "중국어 번체" -> selectInNation = "zh-CN"
                    "중국어 간체" -> selectInNation = "zh-TW"
                    "일본어" -> selectInNation = "ja"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectInNation = "null"
            }
        }


        /* 번역할 언어가 선택 됐을 때 url로 보내기 위한 단어로 설정하는 코드임*/
        spinner_setOut.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "TranslateFragment - onItemSelected() called22")
                selectOutNation = spinner_setOut.getItemAtPosition(position).toString()
                when(selectOutNation){
                    "한국어" -> selectOutNation = "ko"
                    "영어" -> selectOutNation = "en"
                    "중국어 번체" -> selectOutNation = "zh-CN"
                    "중국어 간체" -> selectOutNation = "zh-TW"
                    "일본어" -> selectOutNation = "ja"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectInNation = "null"
            }
        }
    }
}


