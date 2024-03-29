package org.techtown.word_first

import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.jsoup.Jsoup
import org.jsoup.nodes.Document



class SearchFragment: Fragment()  {

    private var webviewstate: Bundle? = null
    lateinit var myWebView: WebView
    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var text: String
    lateinit var allWord: String
    //allWord변수는 비동기식으로 돌려 parsing된 문자열이 저장되는 변수임!! 매우 중요


    companion object{
        const val TAG : String = "로그"

        fun newInstance() : SearchFragment{
            return SearchFragment()
        }

    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SearchFragment - onCreate() called")
        Log.d(TAG, "onCreate -Bundle- ${webviewstate}")
        var aaa: androidx.appcompat.app.ActionBar? =
            (getActivity() as AppCompatActivity?)!!.supportActionBar
        aaa!!.show()


    }

    //fragment를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "SearchFragment - onAttach() called")
        Log.d(TAG, "onAttach -Bundle- ${webviewstate}")
    }


    // 뷰가 생성되었을 때 화면과 연결해주는 것
    //fragment와 레이아웃을 연결시켜주는 부분임.
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "SearchFragment - onCreateView() called")

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        myWebView = view.findViewById(R.id.webView)

        myWebView.webViewClient = WebViewClient()

        myWebView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }


        if(webviewstate==null){
            myWebView.loadUrl("https://dic.daum.net/");
            Log.d(TAG, "null")
            Log.d(TAG, "onCreateView -Bundle- ${webviewstate}")
        }//처음 로딩했을 때는 첫화면 보여줌
        else{
            myWebView.restoreState(webviewstate);
            Log.d(TAG, "Not null")
        }//webviewstate가 null값이 아니면 저장된 웹페이지를 보여줌

        setHasOptionsMenu(true)

        return view
    }

    override fun onPause() {
        super.onPause()

        webviewstate = Bundle()
        myWebView.saveState(webviewstate)
        Log.d(TAG, "onPause() -Bundle-${webviewstate}")

    }//다른 프래그먼트로 이동 시에 웹페이지 정보 저장


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    //action bar override 한 것


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeNav -> {
                myWebView.loadUrl("https://dic.daum.net/")
                true
            }//homeNav가 선택 되면 다시 다음 사전으로 연결, 일종의 홈버튼과 같음
            R.id.addNav ->{
                val currentSite = myWebView.getUrl().toString()
                Log.d(TAG,"url 가져옴 $currentSite")
                allWord = AsyncTaskExample(this).execute("$currentSite").get()
                openWordDialog()
                true
            }//+버튼이 클릭되면 단어장에 추가되게 함
        }
        return super.onOptionsItemSelected(item)
    }


    fun openWordDialog(){ //단어를 추가하는 대화상자 실행
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.CustomAlertDialog)
            //alertdialog를 R.style.CustomAlertDialog의 테마로 만듦.
        }
        val dialogView: View = layoutInflater.inflate(R.layout.search_dialog, null)

        val dialog = builder?.setView(dialogView)?.show()

        val dialogWord = dialogView.findViewById<TextView>(R.id.dialog_word)
        val dialogMean = dialogView.findViewById<TextView>(R.id.dialog_mean)
        val btnadd = dialogView.findViewById<Button>(R.id.btn_add)
        val btncancel = dialogView.findViewById<Button>(R.id.btn_cancel)

        val kk : List<String> = allWord.split("/")

        dialogWord.text = kk[0].toString()
        dialogMean.text = kk[1].toString()

 //       Log.d(TAG,"zzzzzzz${kk}")
//        k.text = allWord

        btnadd.setOnClickListener {
            var str_word:String = dialogWord.text.toString()
            var str_mean:String = dialogMean.text.toString()

            dbManager = DBManager(activity, "wordDB", null, 1)
            sqlitedb = dbManager.writableDatabase

            sqlitedb.execSQL("INSERT INTO wordTBL VALUES ('" + str_word + "','" + str_mean + "')")
            sqlitedb.close()
            dbManager.close()

            Toast.makeText(activity, "추가 되었습니다", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }

        btncancel.setOnClickListener {
            dialog?.dismiss()
        }
    }
}