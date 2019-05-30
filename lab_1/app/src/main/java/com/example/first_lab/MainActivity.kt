package com.example.first_lab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private fun toastActivityState(message: String){
        Toast.makeText(this, "$message" ,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toastActivityState("ON_CREATE")
    }

    override fun onStart(){
        super.onStart()
        toastActivityState("ON_START")
    }

    override fun onResume(){
        super.onResume()
        toastActivityState("ON_RESUME")
    }
    override fun onPause(){
        super.onPause()
        toastActivityState("ON_PAUSE")
    }
    override fun onStop(){
        super.onStop()
        toastActivityState("ON_STOP")
    }
    override fun onDestroy(){
        toastActivityState("ON_DESTROY")
        super.onDestroy()
    }
}
