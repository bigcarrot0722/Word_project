package org.techtown.word_first

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class StudyFragment: Fragment() {
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    lateinit var wdbManager:WrongDBManager
    lateinit var tvWNum:TextView


    companion object{
        const val TAG : String = "로그"

        var selected_word: String = ""
        var selected_mean: String = ""

        fun newInstance() : StudyFragment{
            return StudyFragment()
        }
    }


    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "StudyFragment - onCreate() called")
        //var aaa: androidx.appcompat.app.ActionBar? =
        //    (getActivity() as AppCompatActivity?)!!.supportActionBar
        //aaa!!.hide()

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
        val view: View = inflater.inflate(R.layout.fragment_study, container, false)

        wdbManager = WrongDBManager(activity, "wrongDB", null, 1)
        sqlitedb = wdbManager.readableDatabase

        tvWNum=view.findViewById(R.id.tvWNum)
        layout = view.findViewById(R.id.wrong)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM wrongTBL", null)

        var num: Int = 0
        while (cursor.moveToNext()) {
            var str_word = cursor.getString(cursor.getColumnIndex("word")).toString()
            var str_mean = cursor.getString(cursor.getColumnIndex("mean")).toString()

            var layout_item: LinearLayout = LinearLayout(activity)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvWord: TextView = TextView(activity)
            tvWord.text = str_word
            tvWord.textSize = 20f
            tvWord.setBackgroundResource(R.drawable.word_rounded)
            tvWord.setPadding(45,10,45,15)
            layout_item.addView(tvWord)

            var tvMean: TextView = TextView(activity)
            tvMean.text = str_mean
            tvMean.textSize = 15f
            tvMean.setBackgroundResource(R.drawable.mean_rounded)
            tvMean.setPadding(45,15,45,20)
            layout_item.addView(tvMean)

            layout_item.setOnClickListener {
                selected_word = str_word
                selected_mean = str_mean
                deletewordDialog(selected_word, selected_mean)
            }

            layout_item.setPadding(70, 15, 70, 25)
            layout.addView(layout_item,0)

            num++
        }
        tvWNum.setText("총 "+cursor.count+"개")

        cursor.close()
        sqlitedb.close()
        wdbManager.close()

        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.add(0,1,0,"오답노트 초기화")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {//단어장 초기화
                wdbManager = WrongDBManager(activity, "wrongDB", null, 1)
                sqlitedb = wdbManager.writableDatabase

                wdbManager.onUpgrade(sqlitedb, 1, 2)
                sqlitedb.close()
                wdbManager.close()

                var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
                ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
                Toast.makeText(activity, "초기화 되었습니다", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletewordDialog(w: String, m: String) { //단어를 추가 및 삭제하는 대화상자 실행
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.CustomAlertDialog)
            //alertdialog를 R.style.CustomAlertDialog의 테마로 만듦.
        }
        val dialogView: View = layoutInflater.inflate(R.layout.study_dialog, null)
        val dialog = builder?.setView(dialogView)?.show()

        val btnOk = dialogView.findViewById<Button>(R.id.btn_ok)
        val btnNo = dialogView.findViewById<Button>(R.id.btn_no)

        btnNo.setOnClickListener {
            dialog?.dismiss()
        }

        btnOk.setOnClickListener {//단어 삭제
            wdbManager = WrongDBManager(activity, "wrongDB", null, 1)
            sqlitedb = wdbManager.writableDatabase

            sqlitedb.execSQL("DELETE FROM wrongTBL WHERE word = '" + w + "' AND mean='"+ m +"';")
            sqlitedb.close()
            wdbManager.close()

            var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
            Toast.makeText(activity, "삭제 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
    }
}