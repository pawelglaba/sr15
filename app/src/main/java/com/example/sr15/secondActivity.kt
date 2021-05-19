package com.example.sr15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class secondActivity : AppCompatActivity() {

    private var backButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        backButton = findViewById(R.id.backButton)

        backButton?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                backToMain()
            }
        })

    }

    private fun backToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}