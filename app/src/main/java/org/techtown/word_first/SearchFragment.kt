package org.techtown.word_first

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class SearchFragment: Fragment()  {

    private var webviewstate: Bundle? = null
    lateinit var myWebView: WebView



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

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId){
            R.id.homeNav -> {
                myWebView.loadUrl("https://dic.daum.net/")
                true
            }//homeNav가 선택 되면 다시 다음 사전으로 연결, 일종의 홈버튼과 같음
            else ->{
                //insert문
                false
            }//+버튼이 클릭되면 단어장에 추가되게 함
        }


}