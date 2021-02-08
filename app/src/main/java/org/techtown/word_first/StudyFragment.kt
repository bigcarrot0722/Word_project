package org.techtown.word_first

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.techtown.word_first.WordFragment.Companion.selected_mean
import org.techtown.word_first.WordFragment.Companion.selected_word


class StudyFragment: Fragment() {
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    lateinit var button: Button
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var listview:ListView
    lateinit var mContext:Context
    lateinit var activity: Activity
    lateinit var dbManager:DBManager



    companion object{
        const val TAG : String = "로그"

        fun newInstance() : StudyFragment{
            return StudyFragment()
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//       listview=view.findViewById(R.id.listView)
//    }


    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "StudyFragment - onCreate() called")


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

        dbManager = DBManager(this.getActivity(), "wordDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        layout = view.findViewById(R.id.word_study)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL", null)

        var num: Int = 0
        while (cursor.moveToNext()) {
            var str_word = cursor.getString(cursor.getColumnIndex("word")).toString()
            var str_mean = cursor.getString(cursor.getColumnIndex("mean")).toString()

            var layout_item: LinearLayout = LinearLayout(activity)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvWord: TextView = TextView(activity)
            tvWord.text = str_word
            tvWord.textSize = 25f
            //tvWord.setBackgroundColor(R.color.main_blue)
            tvWord.setBackgroundResource(R.drawable.word_rounded)
            tvWord.setPadding(30,0,30,0)
            layout_item.addView(tvWord)

            var tvMean: TextView = TextView(activity)
            tvMean.text = str_mean
            tvMean.textSize = 20f
            tvMean.setBackgroundResource(R.drawable.mean_rounded)
            tvMean.setPadding(30,0,30,0)
            layout_item.addView(tvMean)



            layout_item.setPadding(0, 0, 0, 55)
            layout.addView(layout_item)

            registerForContextMenu(layout)
            num++
        }
        cursor.close()
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        setHasOptionsMenu(true)

        button=view.findViewById(R.id.btn1)
        button.setOnClickListener {dbManager = DBManager(this.getActivity(), "wordDB", null, 1)
            sqlitedb = dbManager.readableDatabase

            layout = view.findViewById(R.id.word_study1)

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL", null)

            var num: Int = 0
            while (cursor.moveToNext()) {

                var str_word = cursor.getString(cursor.getColumnIndex("word")).toString()


                var layout_item: LinearLayout = LinearLayout(activity)
                layout_item.orientation = LinearLayout.VERTICAL
                layout_item.id = num

                var tvWord: TextView = TextView(activity)
                tvWord.text = str_word
                tvWord.textSize = 25f
                //tvWord.setBackgroundColor(R.color.main_blue)
                tvWord.setBackgroundResource(R.drawable.word_rounded)
                tvWord.setPadding(30,0,30,0)
                layout_item.addView(tvWord)



                layout_item.setPadding(0, 0, 0, 55)
                layout.addView(layout_item)

                registerForContextMenu(layout)
                num++
            }
            cursor.close()
            cursor.close()
            sqlitedb.close()
            dbManager.close()

            setHasOptionsMenu(true)

        }
        button2=view.findViewById(R.id.btn3)
        button2.setOnClickListener {
            dbManager = DBManager(this.getActivity(), "wordDB", null, 1)
            sqlitedb = dbManager.readableDatabase

            layout = view.findViewById(R.id.word_study)

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL", null)

            var num: Int = 0
            while (cursor.moveToNext()) {
                var str_word = cursor.getString(cursor.getColumnIndex("word")).toString()
                var str_mean = cursor.getString(cursor.getColumnIndex("mean")).toString()

                var layout_item: LinearLayout = LinearLayout(activity)
                layout_item.orientation = LinearLayout.VERTICAL
                layout_item.id = num

                var tvWord: TextView = TextView(activity)
                tvWord.text = str_word
                tvWord.textSize = 25f
                //tvWord.setBackgroundColor(R.color.main_blue)
                tvWord.setBackgroundResource(R.drawable.word_rounded)
                tvWord.setPadding(30,0,30,0)
                layout_item.addView(tvWord)

                var tvMean: TextView = TextView(activity)
                tvMean.text = str_mean
                tvMean.textSize = 20f
                tvMean.setBackgroundResource(R.drawable.mean_rounded)
                tvMean.setPadding(30,0,30,0)
                layout_item.addView(tvMean)



                layout_item.setPadding(0, 0, 0, 55)
                layout.addView(layout_item)

                registerForContextMenu(layout)
                num++
            }
            cursor.close()
            cursor.close()
            sqlitedb.close()
            dbManager.close()

            setHasOptionsMenu(true)
        }
        button1=view.findViewById(R.id.btn2)
        button1.setOnClickListener {
            dbManager = DBManager(this.getActivity(), "wordDB", null, 1)
            sqlitedb = dbManager.readableDatabase

            layout = view.findViewById(R.id.word_study2)

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM wordTBL", null)

            var num: Int = 0
            while (cursor.moveToNext()) {


                var str_mean = cursor.getString(cursor.getColumnIndex("mean")).toString()

                var layout_item: LinearLayout = LinearLayout(activity)
                layout_item.orientation = LinearLayout.VERTICAL
                layout_item.id = num


                var tvMean: TextView = TextView(activity)
                tvMean.text = str_mean
                tvMean.textSize = 20f
                tvMean.setBackgroundResource(R.drawable.mean_rounded)
                tvMean.setPadding(30,0,30,0)
                layout_item.addView(tvMean)



                layout_item.setPadding(0, 0, 0, 55)
                layout.addView(layout_item)

                registerForContextMenu(layout)
                num++
            }
            cursor.close()
            cursor.close()
            sqlitedb.close()
            dbManager.close()

            setHasOptionsMenu(true)
        }


        return view
    }

}