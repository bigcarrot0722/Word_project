package org.techtown.word_first

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class WordFragment: Fragment(){

lateinit var dbManager: DBManager
lateinit var sqlitedb:SQLiteDatabase
lateinit var layout:LinearLayout

    companion object{
        const val TAG : String = "로그"
        var selected_word:String = ""

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

            layout_item.setOnClickListener {
                /*val intent = Intent(this,PersonnelInfo::class.java)
                intent.putExtra("intent_name",str_name)
                startActivity(intent)*/
                selected_word = str_word
            }

            layout_item.setPadding(0,0,0,20)
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
            R.id.action_plus -> {
                openWordDialog()
                return true
            }//단어 추가
            R.id.action_reset ->{
                dbManager = DBManager(activity,"wordDB",null,1)
                sqlitedb = dbManager.writableDatabase

                dbManager.onUpgrade(sqlitedb,1,2)
                sqlitedb.close()
                dbManager.close()
                var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
                ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
                return true
            }//단어장 초기화
        }
        return super.onOptionsItemSelected(item)
    }

    fun openWordDialog(){ //단어를 추가하는 대화상자 실행
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.CustomAlertDialog)
            //alertdialog를 R.style.CustomAlertDialog의 테마로 만듦.
        }
        val dialogView: View = layoutInflater.inflate(R.layout.search_dialog,null)

        builder?.setIcon(R.drawable.logo_alertdialog)
        builder?.setTitle("단어추가")
        val dialog = builder?.setView(dialogView)?.show()

        val dialogWord = dialogView.findViewById<EditText>(R.id.dialog_word)
        val dialogMean = dialogView.findViewById<EditText>(R.id.dialog_mean)
        val plusbtn = dialogView.findViewById<Button>(R.id.plus_button)
        val calcelbtn = dialogView.findViewById<Button>(R.id.cancel_button)

        plusbtn.setOnClickListener {
            var str_word:String = dialogWord.text.toString()
            var str_mean:String = dialogMean.text.toString()

            dbManager = DBManager(activity,"wordDB",null,1)
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("INSERT INTO wordTBL VALUES ('"+str_word+"','"+str_mean+"')")
            sqlitedb.close()
            dbManager.close()

            var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
            ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
            Toast.makeText(activity,"추가 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        calcelbtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        activity?.menuInflater?.inflate(R.menu.menu_word_item,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                dbManager = DBManager(activity,"wordDB",null,1)
                sqlitedb = dbManager.writableDatabase

                sqlitedb.execSQL("DELETE FROM wordTBL WHERE word = '"+selected_word+"')")
                sqlitedb.close()
                dbManager.close()
                var ft: FragmentTransaction? = fragmentManager?.beginTransaction()
                ft?.detach(this)?.attach(this)?.commit() //프래그먼트 새로고침
                return true
            }//단어 삭제
            R.id.action_modify ->{

                return true
            }//단어 수정
        }
        return super.onContextItemSelected(item)
    }
}


