package org.techtown.word_first

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.techtown.word_first.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){

    //멤버 변수를 선언함

    private lateinit var searchFragment : SearchFragment
    private lateinit var studyFragment : StudyFragment
    private lateinit var wordFragment : WordFragment
    private lateinit var binding: ActivityMainBinding


    var mBackWait:Long = 0

    companion object{
        const val TAG: String = "로그"
    }

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.logo_alertdialog)
        setTitle("단어장 앱")


        //layout과 연결시키는 것
        //setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity - onCreate() called")

        //바인딩을 사용한 것.
        binding.bottomNav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)
        searchFragment = SearchFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, searchFragment).commit()
        searchFragment = SearchFragment.newInstance()
        studyFragment = StudyFragment.newInstance()
        wordFragment = WordFragment.newInstance()


    }

    private val onBottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when(it.itemId){
            R.id.menu_search -> {
                Log.d(TAG, "MainActivity - 검색 클릭")
                transaction.replace(R.id.fragments_frame, searchFragment).commit()


            }
            R.id.menu_study -> {
                Log.d(TAG, "MainActivity - 공부 클릭")
                transaction.replace(R.id.fragments_frame, studyFragment).commit()


            }
            R.id.menu_word -> {
                Log.d(TAG, "MainActivity - 단어장 클릭")
                transaction.replace(R.id.fragments_frame, wordFragment).commit()

            }
        }

        true
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish() //액티비티 종료
        }
    }

}


