package com.example.sqlite_database_project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmployeeActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var password: TextView
    private lateinit var name: TextView
    private lateinit var designation: TextView
    private lateinit var salary: TextView
    private lateinit var address: TextView
    private lateinit var state: TextView
    private lateinit var country: TextView
    lateinit var nametv:String

    lateinit var mydb:MyHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        title = "Employee Home Page"

        email=findViewById(R.id.tvEmail)
        password=findViewById(R.id.tvPassword)
        name=findViewById(R.id.tvName)
        designation=findViewById(R.id.tvDesignation)
        salary=findViewById(R.id.tvSalary)
        address=findViewById(R.id.tvAddress)
        state=findViewById(R.id.tvState)
        country=findViewById(R.id.tvCountry)


        mydb= MyHelper(this)
        val intent=intent
        val emailextras=intent.getStringExtra("email")


        // FETCHING DATA FROM DATABASE
        var cursor=mydb.fetchallData(emailextras)

        if (cursor?.count==0){
            Toast.makeText(this,"No Data Available",Toast.LENGTH_SHORT).show()
        }
        // READING DATA FROM DATABASE
        else{
            while (cursor!!.moveToNext()){
             //   val obj = Employee(cursor.getString(1), cursor.getString(2), cursor.getString(3))
                name.setText(cursor.getString(0))
                nametv=cursor.getString(0)
                designation.setText(cursor.getString(1))
                email.setText(emailextras)
                password.setText(cursor.getString(3))
                salary.setText(cursor.getString(4))
                address.setText(cursor.getString(5))
                state.setText(cursor.getString(6))
                country.setText(cursor.getString(7))

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.menu_employee, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = intent
        val email = intent.getStringExtra("email")
        var helper = MyHelper(applicationContext)

        val int = item.itemId

        if (int == R.id.seeprofile) {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
            return true
        } else if (int == R.id.deleteprofile) {
            val checkudeletedata: Boolean = helper.deletedata(email.toString(),nametv)!!
            if (checkudeletedata)
                Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT).show()

            else
                Toast.makeText(this, "Entry Not Deleted", Toast.LENGTH_SHORT).show()

            return true

        } else if (int == R.id.logout) {
            Intent(this, LoginPage::class.java).also {
                startActivity(it)
            }
            finish()
            return true

        } else if (int == R.id.registrationform) {
            Intent(this, UpdateRegistrationForm::class.java).also {
                startActivity(it)
            }
            return true

        }

        return true

    }
}