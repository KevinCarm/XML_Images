package com.kevcs.xml_images

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray


class MainActivity : AppCompatActivity() {
    private lateinit var recyle: androidx.recyclerview.widget.RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var list: ArrayList<Image>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
        petition()
    }

    private fun setup() {
        list = ArrayList<Image>()
        recyle = findViewById(R.id.recycleViewHeroes)
        layoutManager = GridLayoutManager(applicationContext, 1)
        recyle.layoutManager = layoutManager
    }

    private fun petition(): Unit {
        val url = "https://simplifiedcoding.net/demos/view-flipper/heroes.php"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val arra = response.getJSONArray("heroes")
                for (i in 0 until arra.length()) {
                    list.add(
                        Image(
                            arra.getJSONObject(i).getString("name"),
                            arra.getJSONObject(i).getString("imageurl")
                        )
                    )
                    Log.d(
                        "VOLL",
                        "${arra.getJSONObject(i).getString("name")} \n ${
                            arra.getJSONObject(i).getString("imageurl")
                        }"
                    )
                }
                val adapter = adapter(applicationContext, list)
                recyle.adapter = adapter
            },
            { error ->
                // TODO: Handle error
                Log.d("VOLL", error.toString())
            }
        )

        MySingleton.getInstance(this.applicationContext).addToRequestQueue(jsonObjectRequest)
    }

    class Image(
        var name: String,
        var imageurl: String
    )
}