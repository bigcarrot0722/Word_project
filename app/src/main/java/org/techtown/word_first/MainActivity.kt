package org.techtown.word_first

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.techtown.word_first.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    //멤버 변수를 선언함
    private lateinit var searchFragment : SearchFragment
    private lateinit var studyFragment : StudyFragment
    private lateinit var wordFragment : WordFragment
    private lateinit var binding: ActivityMainBinding

    companion object{

        const val TAG: String = "로그"
    }
    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //layout과 연결시키는 것
        //setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity - onCreate() called")

        //바인딩을 사용한 것.
        binding.bottomNav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)
        searchFragment = SearchFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, searchFragment).commit()

    }

    private val onBottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.menu_search->{
                Log.d(TAG, "MainActivity - 검색 클릭")
                searchFragment = SearchFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, searchFragment).commit()
            }
            R.id.menu_study->{
                Log.d(TAG, "MainActivity - 공부 클릭")
                studyFragment = StudyFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, studyFragment).commit()
            }
            R.id.menu_word->{
                Log.d(TAG, "MainActivity - 단어장 클릭")
                wordFragment = WordFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, wordFragment).commit()
            }
        }
        true
    }

}