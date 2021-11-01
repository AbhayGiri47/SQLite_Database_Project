package com.example.sqlite_database_project

import android.R

import android.widget.TextView

import androidx.annotation.NonNull

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup


class myadapter(dataholder: ArrayList<Employee>) : RecyclerView.Adapter<myadapter.myviewholder>() {
    var dataholder: ArrayList<Employee>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(com.example.sqlite_database_project.R.layout.singlerow,parent,false)
        return myviewholder(view)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.dname.setText(dataholder[position].empname)
        holder.ddesignation.setText(dataholder[position].empdesignation)
        holder.demail.setText(dataholder[position].empemail)
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dname: TextView
        var ddesignation: TextView
        var demail: TextView

        init {
            dname = itemView.findViewById(com.example.sqlite_database_project.R.id.displayname)
            ddesignation = itemView.findViewById(com.example.sqlite_database_project.R.id.displaydesignation)
            demail = itemView.findViewById(com.example.sqlite_database_project.R.id.displayemail)
        }
    }

    init {
        this.dataholder = dataholder
    }
}