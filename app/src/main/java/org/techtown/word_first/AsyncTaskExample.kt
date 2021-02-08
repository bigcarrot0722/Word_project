package org.techtown.word_first

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class AsyncTaskExample(private var fragment: SearchFragment) : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(1)")
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(1)-${params[0]}")
        val doc: Document = Jsoup.connect("${params[0]}").timeout(1000 * 100).get()
        val word = doc.select("span.txt_emph1").eq(1).text()
        Log.d(SearchFragment.TAG, "뜻-meaning ${word}")
        val k : Elements? = doc.select("ul.list_search li span.num_search")
        val a = k?.size
        Log.d("kk","${k}")
        Log.d("aa","$a")
        var i = 0 //for문에 사용할 변수
        var meaning = "" //반환값으로 사용할 변수
        if (a != null) {
            if(a != 1) {
                for (j in 0..(a - 2)) {
                    val m = doc.select("ul.list_search li span.num_search").eq(j).text().replace(".", "")
                    val m2 = doc.select("ul.list_search li span.num_search").eq(j + 1).text().replace(".", "")
                    if (m.toInt() >= m2.toInt()) {
                        Log.d(SearchFragment.TAG, "해결하기2 ${m}/${m2}")
                        break
                    } else {
                        Log.d(SearchFragment.TAG, "해결하기1 ${m}/${m2}")
                        i++
                    }
                }
            }
        }
        else{
            meaning="뜻이 없습니다."
        }
        Log.d(SearchFragment.TAG, "i의 값은? ${i}")
        for (k in 0..i){
            meaning += doc.select("ul.list_search li span.num_search").eq(k).text()
            meaning += doc.select("ul.list_search li span.txt_search").eq(k).text()
            meaning += " "
            Log.d(SearchFragment.TAG, "단어뜻-word ${meaning}")
        }
        //select부분 문법 찾아서 고치기

        return "$word /$meaning"
    }

    override fun onPostExecute(result: String?) {
        Log.d(SearchFragment.TAG, "loadApplications 실행됨(2)")
        super.onPostExecute(result)
        //Log.d(SearchFragment.TAG, "$result")
        Log.d(SearchFragment.TAG, "단어뜻-word ${result}")

    }
}