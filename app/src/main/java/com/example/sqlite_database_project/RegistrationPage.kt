package com.example.sqlite_database_project

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast




class RegistrationPage : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var name:EditText
    private lateinit var designation:EditText
    private lateinit var salary:EditText
    private lateinit var address:EditText
    private lateinit var state:EditText
    private lateinit var country:EditText
    private lateinit var submit:Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)

        setTitle("Registration Form")

        email=findViewById(R.id.etEmail)
        password=findViewById(R.id.etPassword)
        repassword=findViewById(R.id.etrePassword)
        name=findViewById(R.id.etName)
        designation=findViewById(R.id.etDesignation)
        salary=findViewById(R.id.etSalary)
        address=findViewById(R.id.etAddress)
        state=findViewById(R.id.etState)
        country=findViewById(R.id.etCountry)
        submit=findViewById(R.id.btnSubmit)

        // INSERTING DATA INTO DATABASE

        var helper=MyHelper(applicationContext)
        var db=helper.writableDatabase


        submit.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View?) {

                    val name=name.text.toString()
                    val email=email.text.toString()
                    val designation=designation.text.toString()
                    val address=address.text.toString()
                    val salary=salary.text.toString()
                    val state=state.text.toString()
                    val country=country.text.toString()
                    val pass = password.text.toString()
                    val repass = repassword.text.toString()


                    if (name == "" || pass == "" || email == "" || designation == "" || address == "" || salary == "" || state == "" || country == "" || repass=="")
                        Toast.makeText(this@RegistrationPage, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                    else {
                        if (pass == repass) {
                            val checkuser: Boolean = helper.checkusername(name) !!
                            if (checkuser == false) {
                                val insert: Boolean = helper.insertData(name, designation,email,pass)!!
                                helper.insertDatainAddress(name, address, state, country)
                                helper.insertDatainSalary(name, salary)
                                if (insert == true) {
                                    Toast.makeText(this@RegistrationPage, "Registered successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(applicationContext, LoginPage::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this@RegistrationPage, "Registration failed", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@RegistrationPage, "User already exists! please sign in", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@RegistrationPage, "Password and RePassword did not match", Toast.LENGTH_SHORT).show()

                        }

                    }

                }
        })
    }
}