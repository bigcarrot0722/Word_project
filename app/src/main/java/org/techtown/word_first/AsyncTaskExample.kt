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
                    meaning += " "

                    Log.d("meaning", "$meaning")
                }
            }
        }
        else {
            val word: Elements? = doc.select("div.search_box").eq(0)
            Log.d("word!!!!!!!!!!!!!!!!", "$word")
//            val aaa = word?.select("span.txt_emph1")!!.text()
//            val bbb = word.select("ul.list_search li")!!.text()
            Word = word?.select("a.txt_cleansch")?.eq(0)!!.text()
            val k : Elements? = word.select("ul.list_search li span")
            val a = k?.size
            Log.d("kk","${k}")
            Log.d("aa","$a")
            var i = 0 //for문에 사용할 변수
            if (a != null) {
                if(a != 1) {
                    for (j in 0..(a/2 - 2)) {
                        val m = word.select("ul.list_search li span.num_search").eq(j).text().replace(".", "")
                        val m2 = word.select("ul.list_search li span.num_search").eq(j + 1).text().replace(".", "")
                        if (m.toInt() >= m2.toInt()) {
                            Log.d(SearchFragment.TAG, "해결하기2 ${m}/${m2}")
                            break
                        } else {
                            Log.d(SearchFragment.TAG, "해결하기1 ${m}/${m2}")
                            i++
                        }
                    }

                    for (l in 0..i){
                        meaning += word.select("ul.list_search li span.num_search")?.eq(l)?.text()
                        meaning += word.select("ul.list_search li span.txt_search")?.eq(l)?.text()
                        meaning += " "
                        Log.d(SearchFragment.TAG, "단어뜻-word ${meaning}")
                    }

                }
                else{
                    meaning += word.select("ul.list_search li span.txt_search")?.eq(0)?.text()
                }
            }
            else{
                meaning="뜻이 없습니다."
            }
            Log.d(SearchFragment.TAG, "i의 값은? ${i}")
//            for (l in 0..i){
//                meaning += word.select("ul.list_search li span")?.eq(l*2-1)?.text()
//                meaning += word.select("ul.list_search li span")?.eq(l*2)?.text()
//                meaning += " "
//                Log.d(SearchFragment.TAG, "단어뜻-word ${meaning}")
//            }
        //select부분 문법 찾아서 고치기, $meaning
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