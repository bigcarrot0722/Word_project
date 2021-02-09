package org.techtown.word_first

import android.app.PendingIntent.getActivity
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class AsyncTaskExample(private var fragment: SearchFragment) : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(1)")
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(1)-${params[0]}")
        val doc: Document = Jsoup.connect("${params[0]}").timeout(1000 * 100).get()
        val Siteaddress = params[0].toString().replace("https://dic.daum.net/","")
        var Word : String = ""
        var meaning : String = ""

        Log.d("siteaddress", Siteaddress)


        if("word/" in Siteaddress){
            val word: Elements? = doc.select("div.inner_top").eq(0)
            Word = word!!.select("span.txt_cleanword").eq(0).text()
            var k =  word.select("ul.list_mean li span").size
            Log.d("k의 값","$k")
            if(k==1){
                meaning += word.select("ul.list_mean li span.txt_mean").eq(0).text()
            }
            else {
                k = (k / 2) - 1
                for (i in 0..k) {
                    meaning += word.select("ul.list_mean li span.num_mean").eq(i).text()
                    meaning += word.select("ul.list_mean li span.txt_mean").eq(i).text()
                    meaning += "  "

                    Log.d("meaning", "$meaning")
                }
            }
        }
        else {
            //
        }
        return "$Word /$meaning"
    }


    override fun onPostExecute(result: String?) {
        Log.d(SearchFragment.TAG, "단어뜻-word ${result}")
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(2)")
        super.onPostExecute(result)
        //Log.d(SearchFragment.TAG, "$result")

    }
}