package org.techtown.word_first

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class WordFragment: Fragment() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    companion object {
        const val TAG: String = "로그"
        var selected_word: String = ""
        var selected_mean: String = ""

        fun newInstance(): WordFragment {
            return WordFragment()
        }
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "WordFragment - onCreate() called")


    }

    //fragment를 안고 있는 액티비티에 붙었을 때
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Log.d(TAG, "WordFragment - onAttach() called")
//    }

    // 뷰가 생성되었을 때 화면과 연결해주는 것
    //fragment와 레이아웃을 연결시켜주는 부분임.
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "WordFragment - onCreateView() called")

        /*view에 layout fragment_word 연결*/
        val view = inflater.inflate(R.layout.fragment_word, container, false)


        dbManager = DBManager(activity, "wordDB", null, 1)
        sqlitedb = dbManager.readableDatabase

        layout = view.findViewById(R.id.word)

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
                /*val intent = Intent(this,PersonnelInfo::class.java)
                intent.putExtra("intent_name",str_name)
                startActivity(intent)*/
                selected_word = str_word
                selected_mean = str_mean
                modifywordDialog(selected_word, selected_mean)
            }

            layout_item.setPadding(70, 55, 70, 25)
            layout.addView(layout_item)

            registerForContextMenu(layout)
            num++
        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_word_frag, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //action bar override 한 것

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_plus -> {//단어 추가
                addWordDialog()
                return true
            }
            R.id.action_reset -> {//단어장 초기화
                dbManager = DBManager(activity, "wordDB", null, 1)
                sqlitedb = dbManager.writableDatabase

                dbManager.onUpgrade(sqlitedb, 1, 2)
                sqlitedb.close()
                dbManager.close()
                var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
                ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
                Toast.makeText(activity, "초기화 되었습니다", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addWordDialog() { //단어를 추가하는 대화상자 실행
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.CustomAlertDialog)
            //alertdialog를 R.style.CustomAlertDialog의 테마로 만듦.
        }
        val dialogView: View = layoutInflater.inflate(R.layout.wordplus_dialog, null)

        val dialog = builder?.setView(dialogView)?.show()

        val dialogWord = dialogView.findViewById<EditText>(R.id.dialog_wordWordFragment)
        val dialogMean = dialogView.findViewById<EditText>(R.id.dialog_meanWordFragment)
        val btnadd = dialogView.findViewById<Button>(R.id.plus_buttonWordFragment)
        val btncancel = dialogView.findViewById<Button>(R.id.cancel_buttonWordFragment)


        btnadd.setOnClickListener {
            var str_word: String = dialogWord.text.toString()
            var str_mean: String = dialogMean.text.toString()

            dbManager = DBManager(activity, "wordDB", null, 1)
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("INSERT INTO wordTBL VALUES ('" + str_word + "','" + str_mean + "')")
            sqlitedb.close()
            dbManager.close()

            var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
            Toast.makeText(activity, "추가 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        btncancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun modifywordDialog(w: String, m: String) { //단어를 추가 및 삭제하는 대화상자 실행
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.CustomAlertDialog)
            //alertdialog를 R.style.CustomAlertDialog의 테마로 만듦.
        }
        val dialogView: View = layoutInflater.inflate(R.layout.word_dialog, null)

        //builder?.setIcon(R.drawable.logo_alertdialog)
        //builder?.setTitle("단어 수정 및 삭제")
        val dialog = builder?.setView(dialogView)?.show()

        val dialogWord = dialogView.findViewById<EditText>(R.id.dialog_word)
        val dialogMean = dialogView.findViewById<EditText>(R.id.dialog_mean)
        val btnModify = dialogView.findViewById<Button>(R.id.btn_modify)
        val btnDelete = dialogView.findViewById<Button>(R.id.btn_delete)

        dialogWord.setText(w)
        dialogMean.setText(m)

        btnModify.setOnClickListener {//단어 수정
            var str_word: String = dialogWord.text.toString()
            var str_mean: String = dialogMean.text.toString()

            dbManager = DBManager(activity, "wordDB", null, 1)
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("UPDATE wordTBL SET word='" + str_word + "',mean='" + str_mean + "' WHERE word='" + w + "' AND mean='"+m+"';")
            sqlitedb.close()
            dbManager.close()

            var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
            Toast.makeText(activity, "수정 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        btnDelete.setOnClickListener {//단어 삭제
            dbManager = DBManager(activity, "wordDB", null, 1)
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("DELETE FROM wordTBL WHERE word = '" + w + "' AND mean='"+ m +"';")
            sqlitedb.close()
            dbManager.close()

            var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
            Toast.makeText(activity, "삭제 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
    }
}
