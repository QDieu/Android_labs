package com.example.lab2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.Exception


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun buttonPressed(view: View){
        if (firstNumber.text.isEmpty() or secondNumber.text.isEmpty())
            Snackbar.make(view, "Input numbers", Snackbar.LENGTH_LONG).show()
        else {
            try{
            val first = firstNumber.text.toString().toInt()
            val second = secondNumber.text.toString().toInt()
            when (view.id) {
                R.id.b1 -> edit.text = (first + second).toString()
                R.id.b2 -> edit.text = (first - second).toString()
                R.id.b3 -> edit.text = (first * second).toString()
                R.id.b4 -> if (second != 0) edit.text = (first / second).toString()
                else edit.text = "inf"
                }
            }
            catch(e: Exception){
                Snackbar.make(view,"Введены слишком большие числа",Snackbar.LENGTH_LONG).show()
            }
            }
        }
    }
