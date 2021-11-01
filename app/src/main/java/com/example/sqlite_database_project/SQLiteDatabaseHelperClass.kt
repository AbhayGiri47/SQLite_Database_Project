package com.example.sqlite_database_project

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues




class MyHelper(context: Context): SQLiteOpenHelper(context,"SQLDatabase",null,1){

    override fun onCreate(db: SQLiteDatabase?) {
        // CREATING TABLE EMPLOYEE
        db?.execSQL("CREATE TABLE EMPLOYEE(NAME TEXT primary key,DESIGNATION TEXT,EMAIL TEXT,PASSWORD TEXT)")

        // CREATING TABLE SALARY
        db?.execSQL("CREATE TABLE SALARY(NAME TEXT,EMPSALARY INTEGER,CONSTRAINT fk_salary FOREIGN KEY (NAME) REFERENCES EMPLOYEE(NAME)ON DELETE CASCADE)")

        // CREATING TABLE ADDRESS
        db?.execSQL("CREATE TABLE ADDRESS(NAME TEXT,EMPADDRESS TEXT,STATE TEXT,COUNTRY TEXT,CONSTRAINT fk_address FOREIGN KEY (NAME) REFERENCES EMPLOYEE(NAME)ON DELETE CASCADE)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }



    fun readallData(): Cursor? {
        var db=this.writableDatabase

        val query="select DISTINCT * from EMPLOYEE"
        var cursor: Cursor? =null
        if (db!=null){
            cursor= db.rawQuery(query,null)

        }
        return cursor
    }

    // INSERTING RECORDS IN EMPLOYEE TABLE
    fun insertData(name: String?, designation: String?,email: String?, password: String?): Boolean? {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("DESIGNATION",designation)
        contentValues.put("EMAIL",email)
        contentValues.put("PASSWORD",password)
        val result = MyDB.insert("EMPLOYEE", null, contentValues)
        return if (result == -1L) false else true
    }

    // INSERTING RECORDS IN SALARY TABLE

    fun insertDatainSalary(name: String?,salary:String): Boolean? {
        val MyDB = this.writableDatabase
        var cv2=ContentValues()
        cv2.put("NAME",name)
        cv2.put("EMPSALARY",salary)
        val result=MyDB.insert("SALARY",null,cv2)
        return if (result == -1L) false else true
    }

    // INSERTING RECORDS IN ADDRESS TABLE

    fun insertDatainAddress(name: String?, address: String?,state: String?, country: String?): Boolean? {
        val MyDB = this.writableDatabase
        var cv3=ContentValues()
        cv3.put("NAME",name)
        cv3.put("EMPADDRESS",address)
        cv3.put("STATE",state)
        cv3.put("COUNTRY",country)
        val result=MyDB.insert("ADDRESS",null,cv3)
        return if (result == -1L) false else true
    }

    // checking user exists or not
    fun checkusername(name: String): Boolean? {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select DISTINCT * from EMPLOYEE where NAME = ?", arrayOf(name))
        return if (cursor.count > 0) true else false
    }

    //  CHECKING USERNAME AND PASSWORD MATCHING OR NOT
    fun checkusernamepassword(email: String, password: String): Boolean? {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "Select DISTINCT * from EMPLOYEE where EMAIL = ? and PASSWORD = ?", arrayOf(email, password)
        )
        return if (cursor.count > 0) true else false
    }

    // UPDATING PASSWORD
    fun updatepassword(email: String?,password: String?):Boolean?{
        val mydb=this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("PASSWORD",password)
        var result= mydb.update("EMPLOYEE", contentValues,"email=?", arrayOf(email))
        return if (result ==-1) {
            false
        } else true

    }

    // DELETING RECORDS IN TABLE
    fun deletedata(email: String,name: String?): Boolean? {
        val DB = this.writableDatabase
        val cursor = DB.rawQuery("Select DISTINCT * from EMPLOYEE where EMAIL = ?", arrayOf(email))
        return if (cursor.count > 0) {
            val result = DB.delete("EMPLOYEE", "EMAIL=?", arrayOf(email)).toLong()
            val result2 = DB.delete("SALARY", "NAME=?", arrayOf(name)).toLong()
            val result3 = DB.delete("ADDRESS", "NAME=?", arrayOf(name)).toLong()

            if (result == -1L) {
                false
            }
            else {
                true
            }
        }
        else {
            false
        }
    }


    // UPDATING RECORDS IN TABLE

    fun updateDataintoEmployeeTable(name: String?, designation: String?,email: String?, password: String?): Boolean? {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("DESIGNATION",designation)
        contentValues.put("EMAIL",email)
        contentValues.put("PASSWORD",password)
        val cursor = MyDB.rawQuery("Select DISTINCT * from EMPLOYEE where NAME = ?", arrayOf(name))
        return if (cursor.count > 0) {
            val result = MyDB.update("EMPLOYEE", contentValues, "name=?", arrayOf(name)).toLong()
            if (result == -1L) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }

    fun updateDataintoSalaryTable(name: String?,salary:String): Boolean? {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("SALARY",salary)
        val cursor = MyDB.rawQuery("Select DISTINCT * from SALARY where NAME = ?", arrayOf(name))
        return if (cursor.count > 0) {
            val result = MyDB.update("SALARY", contentValues, "name=?", arrayOf(name)).toLong()
            if (result == -1L) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }

    fun updateDataintoAddressTable(name: String?, address: String?,state: String?, country: String?): Boolean? {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("ADDRESS",address)
        contentValues.put("STATE",state)
        contentValues.put("COUNTRY",country)
        val cursor = MyDB.rawQuery("Select DISTINCT * from ADDRESS where NAME = ?", arrayOf(name))
        return if (cursor.count > 0) {
            val result = MyDB.update("ADDRESS", contentValues, "name=?", arrayOf(name)).toLong()
            if (result == -1L) {
                false
            } else {
                true
            }
        } else {
            false
        }
    }



    fun fetchallData(EMAIL: String?): Cursor? {
        var db=this.writableDatabase

        val query="SELECT DISTINCT EMPLOYEE.NAME AS NAME,DESIGNATION,EMAIL,PASSWORD,EMPSALARY,EMPADDRESS,STATE,COUNTRY FROM SALARY INNER JOIN EMPLOYEE ON EMPLOYEE.NAME = SALARY.NAME INNER JOIN ADDRESS ON ADDRESS.NAME = EMPLOYEE.NAME WHERE EMPLOYEE.EMAIL = ?"
        var cursor: Cursor? =null
        if (db!=null){
            cursor= db.rawQuery(query, arrayOf(EMAIL))

        }
        return cursor
    }



}