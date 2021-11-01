package com.example.sqlite_database_project

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class UpdateRegistrationForm : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var name: EditText
    private lateinit var designation: EditText
    private lateinit var salary: EditText
    private lateinit var address: EditText
    private lateinit var state: EditText
    private lateinit var country: EditText
    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_registration_form)

        setTitle(" Update Registration Form")

        email=findViewById(R.id.et1Email)
        password=findViewById(R.id.et1Password)
        repassword=findViewById(R.id.et1rePassword)
        name=findViewById(R.id.et1Name)
        designation=findViewById(R.id.et1Designation)
        salary=findViewById(R.id.et1Salary)
        address=findViewById(R.id.et1Address)
        state=findViewById(R.id.et1State)
        country=findViewById(R.id.et1Country)
        submit=findViewById(R.id.btn1Submit)

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


                if (name == "" || pass == "" || email == "" || designation == "" || address == "" || salary == "" || state == "" || country == "")
                    Toast.makeText(this@UpdateRegistrationForm, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                else {
                    if (pass == repass) {

                        val insertEmployee: Boolean = helper.updateDataintoEmployeeTable(name, designation,email,pass)!!
//                        val insertAddress: Boolean = helper.updateDataintoAddressTable(name, address,state,country)!!
//                        val insertSalary: Boolean = helper.updateDataintoSalaryTable(name,salary)!!


                        if (insertEmployee == true ) {
                                Toast.makeText(this@UpdateRegistrationForm, "Registeration Form Updated successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, LoginPage::class.java)
                                startActivity(intent)
                        }
                        else {
                                Toast.makeText(this@UpdateRegistrationForm, "Registration Updation failed", Toast.LENGTH_SHORT).show()
                            }

                    }

                }

            }
        })
    }
}