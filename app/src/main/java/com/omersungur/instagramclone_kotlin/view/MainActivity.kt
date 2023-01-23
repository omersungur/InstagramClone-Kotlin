package com.omersungur.instagramclone_kotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omersungur.instagramclone_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser = auth.currentUser

        if(currentUser != null) {
            val intent = Intent(this@MainActivity,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signInClicked(view : View) {

        val userName = binding.textUserName.text.toString()
        val password = binding.textPassword.text.toString()

        if(userName.equals("") || password.equals("")) {
            Toast.makeText(this,"Kullanıcı adı veya şifreniz yanlış",Toast.LENGTH_LONG).show()
        }
        else {
            auth.signInWithEmailAndPassword(userName,password).addOnSuccessListener {
                val intent = Intent(this@MainActivity,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked(view : View) {

        val userName = binding.textUserName.text.toString()
        val password = binding.textPassword.text.toString()

        if(userName.equals("") || password.equals("")) {
            Toast.makeText(this,"Lütfen e-posta ve parolanızı doğru giriniz!",Toast.LENGTH_LONG).show()
        }
        else {
            auth.createUserWithEmailAndPassword(userName,password).addOnSuccessListener {

                val intent = Intent(this@MainActivity,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }
}