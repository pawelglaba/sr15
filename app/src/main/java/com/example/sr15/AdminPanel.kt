package com.example.sr15


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin_panel.*



class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class AdminPanel : AppCompatActivity(){


    fun getPointedDocument(position: Int): User{

        var user = User("test","test","test@test.pl")

        FirebaseFirestore.getInstance().collection("users").get()
                .addOnSuccessListener { documents ->
                    var i=0
                    for (document in documents){
                        if(i==position) {

                            user = document.toObject(User::class.java)
                            Log.i("override test","${user.name}")
                            Log.i("name test","${document.toObject(User::class.java).name}")
                            Log.i("position","$i")
                        }
                        i+=1
                    }

                }
        return user
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_admin_panel)


        val query = FirebaseFirestore.getInstance()
            .collection("users")
        val options  = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
            .setLifecycleOwner(this).build()
        val adapter = object: FirestoreRecyclerAdapter<User, UserViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(this@AdminPanel).inflate(android.R.layout.simple_list_item_2, parent, false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {

                val tvEmail: TextView = holder.itemView.findViewById(android.R.id.text1)
                val tvName: TextView = holder.itemView.findViewById(android.R.id.text2)
                tvName.text = model.name
                tvEmail.text = model.email
                var testUser: User

                holder.itemView.setOnClickListener{
                    Log.i(Constants.TEST, "Tapped on position $position")
                    testUser=getPointedDocument(position)
                }
            }
        }

        usersList.adapter = adapter
        usersList.layoutManager = LinearLayoutManager(this)
    }
}


