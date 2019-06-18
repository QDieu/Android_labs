package com.example.weatherapp

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }
        setContentView(R.layout.activity_detail)
        if (savedInstanceState == null) {

            val position = intent.getIntExtra(DetailFragment.ARG_POSITION, 0)
            val detailFragment = DetailFragment.newInstance(position)

            supportFragmentManager.beginTransaction()
                .replace(R.id.detailFragment, detailFragment)
                .commit()
        }
    }
}
