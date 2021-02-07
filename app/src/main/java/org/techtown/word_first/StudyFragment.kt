package org.techtown.word_first

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment



class StudyFragment: Fragment() {
    lateinit var layout: ConstraintLayout
    lateinit var button: Button
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var listview:ListView

    val item = Array(20,{ i -> "$i + list" })
    val item1 = Array(20,{ i -> "$i + 단어" })
    val item2 = Array(20,{ i -> "$i + 뜻" })


    companion object{
        const val TAG : String = "로그"

        fun newInstance() : StudyFragment{
            return StudyFragment()
        }
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "StudyFragment - onCreate() called")


    }

    //fragment를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "StudyFragment - onAttach() called")

    }

    // 뷰가 생성되었을 때 화면과 연결해주는 것
    //fragment와 레이아웃을 연결시켜주는 부분임.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "StudyFragment - onCreateView() called")

        val view: View = inflater.inflate(R.layout.fragment_study, container , false)

        listview.adapter=HBaseAdapter(requireContext(),item)
        button=view.findViewById(R.id.btn1)
        button.setOnClickListener {
            listview.adapter=HbaseAdapter_word(requireContext(),item)
        }
        button2=view.findViewById(R.id.btn3)
        button2.setOnClickListener {
            listview.adapter=HBaseAdapter(requireContext(),item2)
        }
        button1=view.findViewById(R.id.btn2)
        button1.setOnClickListener {
            listview.adapter=HbaseAdapter_meaning(requireContext(),item1)
        }
        return view

}




}