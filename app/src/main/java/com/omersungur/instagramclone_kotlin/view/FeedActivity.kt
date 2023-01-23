package com.omersungur.instagramclone_kotlin.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.omersungur.instagramclone_kotlin.R
import com.omersungur.instagramclone_kotlin.adapters.InstaAdapter
import com.omersungur.instagramclone_kotlin.databinding.ActivityFeedBinding
import com.omersungur.instagramclone_kotlin.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>
    private lateinit var feedAdapter : InstaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        postList = ArrayList<Post>()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = InstaAdapter(postList)
        binding.recyclerView.adapter = feedAdapter

        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {

        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            // gönderileri, tarihlerine göre sıraladık (yeni atılan gönderi en üstte çıkacak).
            if(value != null) {
                value.let {
                    val documents = it.documents

                    postList.clear() // Gönderilerin iki kez gösterilebilir, bir bug olmaması için listeyi temizliyoruz.

                    for(document in documents) {

                        val comment = document.get("comment") as String
                        val userEmail = document.get("userEmail") as String
                        val downloadUrl = document.get("downloadUrl") as String

                        val post = Post(userEmail,comment,downloadUrl)
                        postList.add(post)
                    }
                    feedAdapter.notifyDataSetChanged()
                }
            }
            else {
                error?.let {
                    Toast.makeText(this@FeedActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_post) {
            val intent = Intent(this@FeedActivity,UploadActivity::class.java)
            startActivity(intent)
        }

        if(item.itemId == R.id.signout) {
            auth.signOut()
            val intent = Intent(this@FeedActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}