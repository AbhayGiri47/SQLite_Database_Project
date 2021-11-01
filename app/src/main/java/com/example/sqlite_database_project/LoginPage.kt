package com.example.sqlite_database_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_registration_page.*
import kotlinx.android.synthetic.main.changepassworddialog.view.*

class LoginPage : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var checkBox: CheckBox
    private lateinit var changepassword:TextView
    private lateinit var login:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        setTitle("Login Page")


        var helper=MyHelper(applicationContext)


        email=findViewById(R.id.etLoginEmail)
        password=findViewById(R.id.etLoginPassword)
        checkBox=findViewById(R.id.logincheckBox)
        changepassword=findViewById(R.id.tvchangepassword)
        login=findViewById(R.id.btnLogin)


        var preferences=getSharedPreferences("MyPref", MODE_PRIVATE)
        var emailtext=preferences.getString("Email","")
        var passwordtext=preferences.getString("Password","")

        email.setText(emailtext)
        password.setText(passwordtext)


        // WHEN CHANGE PASSWORD BUTTON IS CLICKED


        changepassword.setOnClickListener {
            val mDialogView= LayoutInflater.from(this).inflate(R.layout.changepassworddialog,null)

            val mBuilder= AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Change Password Dialog")

            val mAlertDialog=mBuilder.show()

            mDialogView.btnSubmit.setOnClickListener {


                val email=mDialogView.etEmail.text.toString()
                val oldpass=mDialogView.etoldpass.text.toString()
                val newpass=mDialogView.etnewPassword.text.toString()



                val checkuser: Boolean = helper.checkusernamepassword(email, oldpass)!!
                if (checkuser == true) {

                    val checkpasswordupdate:Boolean=helper.updatepassword(email,newpass)!!
                    if (checkpasswordupdate==true) {
                        mAlertDialog.dismiss()
                        Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(getApplicationContext(), LoginPage::class.java)
                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Password Not Changed Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Email and old Password did not matched..", Toast.LENGTH_SHORT).show();
                }



            }
            mDialogView.btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
                val intent= Intent(this,LoginPage::class.java)
                startActivity(intent)
            }
        }


        // WHEN LOGIN BUTTON IS CLICKED

        btnLogin.setOnClickListener {
            var email=email.text.toString()
            var pass=password.text.toString()

            if (email.equals("")|| pass.equals("")){
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            }
            else{

                if (checkBox.isChecked){

                    storeddatausingSharedPreferences(email,pass)
                }

                val checkuserpass:Boolean=helper.checkusernamepassword(email,pass)!!
                if (checkuserpass==true){
                    Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show()

                    val intent  =  Intent(getApplicationContext(),EmployeeActivity::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        }

        textViewSignUp.setOnClickListener {
            val intent  =  Intent(getApplicationContext(),RegistrationPage::class.java)
            startActivity(intent)
        }


    }

    private fun storeddatausingSharedPreferences(email: String, pass: String) {

        var preferences=getSharedPreferences("MyPref", MODE_PRIVATE)
        var editor=preferences.edit()


        editor.putString("Email",email)
        editor.putString("Password",pass)
        editor.apply()
    }
}

