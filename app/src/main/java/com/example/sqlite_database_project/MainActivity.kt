package com.example.sqlite_database_project

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration_page.*

class MainActivity : AppCompatActivity() {

    lateinit var mydb:MyHelper

    lateinit var rv:RecyclerView
    lateinit var dataholder:ArrayList<Employee>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("See Another Person Profile")





        registerbutton.setOnClickListener {
            Intent(this,RegistrationPage::class.java).also {
                startActivity(it)
            }
        }

        loginbutton.setOnClickListener {
            Intent(this,LoginPage::class.java).also {
                startActivity(it)
            }
        }




        mydb= MyHelper(this)


        var cursor=mydb.readallData()
        dataholder = ArrayList()

        if (cursor?.count==0){
            Toast.makeText(this,"No Data Available",Toast.LENGTH_SHORT).show()
        }
        else{
            while (cursor!!.moveToNext()){
                val obj = Employee(cursor.getString(0), cursor.getString(1), cursor.getString(2))
                dataholder.add(obj)
            }
        }

        rv=findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter=myadapter(dataholder)
        rv.layoutManager=LinearLayoutManager(this)
    }


}