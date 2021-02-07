package org.techtown.word_first

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class AsyncTaskExample(private var fragment: SearchFragment) : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(1)")
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(1)-${params[0]}")
        val doc: Document = Jsoup.connect("${params[0]}").timeout(1000 * 100).get()
        val word = doc.select("span.txt_emph1").eq(1).text()
        Log.d(SearchFragment.TAG,"뜻-meaning ${word}")
        val meaning1 = doc.select("ul.list_search li span.txt_search").eq(1).text()
        val meaning2 = doc.select("ul.list_search li span.txt_search").eq(2).text()
        val meaning3 = doc.select("ul.list_search li span.txt_search").eq(3).text()
        val meaning4 = doc.select("ul.list_search li span.txt_search").eq(4).text()
        //select부분 문법 찾아서 고치기
        Log.d(SearchFragment.TAG,"단어뜻-word ${meaning1}")

        return "$word / $meaning1 + $meaning2 + $meaning3 + $meaning4"
    }

    override fun onPostExecute(result: String?) {
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(2)")
        super.onPostExecute(result)
        //Log.d(SearchFragment.TAG, "$result")

    }
}