package org.techtown.word_first

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class study_first : AppCompatActivity() {

    lateinit var filp : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_first)

        getSupportActionBar()?.hide()
        filp = findViewById(R.id.filp_ic)


    }
}