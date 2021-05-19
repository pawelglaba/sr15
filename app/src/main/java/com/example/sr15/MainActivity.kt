package com.example.sr15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    private var userInput: EditText? = null
    private var button: Button? = null
    private var textView: TextView? = null
    private var nextViewButton: Button? = null

    private var numTimesClicked = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInput = findViewById(R.id.userInput)
        button = findViewById(R.id.button)
        nextViewButton = findViewById(R.id.nextView)


        nextViewButton?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                openActivity2()
            }
        })



        textView = findViewById(R.id.printText)
        textView?.text=""
        textView?.movementMethod=ScrollingMovementMethod();


        button?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
              numTimesClicked +=1
                textView?.append("\n The button got tapped by ${userInput?.text} $numTimesClicked time")
                if(numTimesClicked !=1){
                    textView?.append("s")
                }
            }
        })


    }

    private fun openActivity2(){
        val intent = Intent(this, secondActivity::class.java)
        startActivity(intent)
    }


}