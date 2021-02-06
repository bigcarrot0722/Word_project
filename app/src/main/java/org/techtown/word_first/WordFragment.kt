package org.techtown.word_first

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class WordFragment: Fragment() {



lateinit var dbManager: DBManager
lateinit var sqlitedb:SQLiteDatabase
lateinit var layout:LinearLayout

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : WordFragment{
            return WordFragment()
        }
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "WordFragment - onCreate() called")

        
    }

    //fragment를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "WordFragment - onAttach() called")
    }

    // 뷰가 생성되었을 때 화면과 연결해주는 것
    //fragment와 레이아웃을 연결시켜주는 부분임.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "WordFragment - onCreateView() called")

        val view = inflater.inflate(R.layout.fragment_word, container , false)

        dbManager = DBManager(activity,"wordDB",null,1)
        sqlitedb = dbManager.readableDatabase

        layout = view.findViewById(R.id.word)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL",null)

        var num:Int = 0
        while(cursor.moveToNext()){
            var str_word = cursor.getString(cursor.getColumnIndex("word")).toString()
            var str_mean = cursor.getString(cursor.getColumnIndex("mean")).toString()

            var layout_item:LinearLayout = LinearLayout(activity)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvWord: TextView = TextView(activity)
            tvWord.text = str_word
            tvWord.textSize = 30f
            tvWord.setBackgroundColor(Color.LTGRAY)
            layout_item.addView(tvWord)

            var tvMean: TextView = TextView(activity)
            tvMean.text = str_mean
            tvMean.textSize = 20f
            layout_item.addView(tvMean)

            /*layout_item.setOnClickListener{
                val intent = Intent(this,PersonnelInfo::class.java)
                intent.putExtra("intent_name",str_name)
                startActivity(intent)
            }*/

            layout.addView(layout_item)
            num++
        }
        return view
    }

}