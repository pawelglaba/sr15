package com.example.sr15

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var loginButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword2)
        loginButton = findViewById(R.id.loginButton)


        loginButton?.setOnClickListener{
            //validateRegisterDetails()
            logInRegisteredUser()
        }

    }

    override fun onClick(view: View?) {
        if(view !=null){
            when (view.id){

                R.id.RegisterText ->{
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }




    private fun validateLoginDetails(): Boolean {

        return when{
            TextUtils.isEmpty(inputEmail?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(inputPassword?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }

            else -> {
                //showErrorSnackBar("Your details are valid",false)
                true
            }
        }


    }

    private fun logInRegisteredUser(){

        if(validateLoginDetails()){
            val email = inputEmail?.text.toString().trim(){ it<= ' '}
            val password = inputPassword?.text.toString().trim(){ it<= ' '}

            //Log-in using FirebaseAuth

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->

                        if(task.isSuccessful && email == "admin@admin.pl"){
                            showErrorSnackBar("You are logged in successfully to administration panel.", false)
                            goToAdminPanel()
                            finish()
                        }

                        else if(task.isSuccessful){
                            FireStoreClass().getUserDetails(this)
                            showErrorSnackBar("You are logged in successfully.", false)

                        } else{
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
        }
    }

    open fun goToAdminPanel() {
        val intent = Intent(this, AdminPanel::class.java)
        startActivity(intent)
    }


    open fun goToMainActivity() {

        val user = FirebaseAuth.getInstance().currentUser;
        val uid = user?.email.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uID",uid)
        startActivity(intent)
    }

    fun userLoggedInSuccess(user: User){

        Log.i("Email: ", user.email)
        Log.i("name: ", user.name)
        Log.i("phone number: ", user.phoneNumber.toString())

        goToMainActivity()
        finish()
    }

}