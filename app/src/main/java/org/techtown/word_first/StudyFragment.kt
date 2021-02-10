package org.techtown.word_first

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


class StudyFragment: Fragment() {
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    lateinit var mContext:Context
    lateinit var activity: Activity
    lateinit var dbManager:DBManager
    lateinit var studyFirst : CardView
    lateinit var studySecond: CardView





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
        var aaa: androidx.appcompat.app.ActionBar? =
            (getActivity() as AppCompatActivity?)!!.supportActionBar
        aaa!!.hide()

    }

    //fragment를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) activity = context
        mContext = context;

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
        val view: View = inflater.inflate(R.layout.fragment_study, container, false)

        studyFirst = view.findViewById(R.id.studyFirstCardView)
        studySecond = view.findViewById(R.id.studySecondCardView)

//        studyFirst.setOnClickListener{
//            val intent = Intent(activity, study_first::class.java)
//            startActivity(intent)
//        }
//
//        studySecond.setOnClickListener{
//            val intent = Intent(activity, study_second::class.java)
//            startActivity(intent)
//        }

        dbManager = DBManager(this.getActivity(), "wordDB", null, 1)
        sqlitedb = dbManager.readableDatabase


        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL", null)

        var num: Int = 0



        return view
    }

}