package org.techtown.word_first

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class AsyncTaskExample(private var fragment: SearchFragment) : AsyncTask<String, String, String>() {
    override fun doInBackground(vararg params: String?): String {
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(1)")
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(1)-${params}")
        val doc: Document = Jsoup.connect("https://dic.daum.net/search.do?q=Cap").timeout(1000 * 100).get()
        val word = doc.select("span.txt_emph1").eq(1).text()
        Log.d(SearchFragment.TAG,"뜻-meaning ${word}")
        val meaning = doc.select("ul.list_search li span.txt_search").eq(1).text()
        Log.d(SearchFragment.TAG,"단어뜻-word ${meaning}")

        return "$word, $meaning"
    }

    override fun onPostExecute(result: String?) {
        Log.d(SearchFragment.TAG,"loadApplications 실행됨(2)")
        super.onPostExecute(result)
        Log.d(SearchFragment.TAG, "$result")

    }
}