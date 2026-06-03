package com.example.contextmenu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contextmenu.databinding.ActivityMainBinding
import com.example.contextmenu.popup.PopupActivity


/**
 * Created by Ankita Gaba on 03/06/26.
 */
val SPAN_COUNT = 2
val resource_id = "resource_id"
val position = "position"
val is_liked = "is_liked"
val cord_x = "cord_x"
val cord_y = "cord_y"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var list: MutableList<Pair<Int, Boolean>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = imageList()
        uiSetup()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.let {
                        val pos = it.getIntExtra(position, 0)
                        val isLiked = it.getBooleanExtra(is_liked, false)
                        val item = list[pos]
                        if(item.second != isLiked) {
                            list[pos] = item.copy(second = isLiked)
                            binding.imageList.adapter?.notifyItemChanged(pos)
                        }
                    }
                }
            }
    }

    private fun uiSetup() {
        binding.apply {
            imageList.apply {
                adapter =
                    ImageAdapter(this@MainActivity, list, listener = object : ImageClickListener {
                        override fun onLongPressImage(view: View, pos: Int) {
                            val location = IntArray(2)
                            view.getLocationInWindow(location)
                            Log.e("context_menu_coord", "${location[0]} | ${location[1]}")
                            val intent = Intent(this@MainActivity, PopupActivity::class.java)
                            intent.putExtra(resource_id, list[pos].first)
                            intent.putExtra(position, pos)
                            intent.putExtra(is_liked, list[pos].second)
                            intent.putExtra(cord_x, location[0].toFloat())
                            intent.putExtra(cord_y, location[1].toFloat())
                            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                this@MainActivity,
                                view, // The shared view
                                ViewCompat.getTransitionName(view) ?: "sharedImage"
                            )
                            resultLauncher.launch(intent, options)
                        }
                    })
                layoutManager = GridLayoutManager(this@MainActivity, SPAN_COUNT)
            }
        }
    }

    private fun imageList() = mutableListOf(
        Pair(R.drawable.img_1, false),
        Pair(R.drawable.img_2, false),
        Pair(R.drawable.img_3, false),
        Pair(R.drawable.img_4, false),
        Pair(R.drawable.img_5, false),
        Pair(R.drawable.img_6, false),
        Pair(R.drawable.img_7, false),
        Pair(R.drawable.img_8, false),
        Pair(R.drawable.img_9, false),
        Pair(R.drawable.img_10, false),
        Pair(R.drawable.img_11, false),
        Pair(R.drawable.img_12, false),
        Pair(R.drawable.img_13, false),
        Pair(R.drawable.img_14, false),
        Pair(R.drawable.img_15, false),
        Pair(R.drawable.img_1, false),
        Pair(R.drawable.img_2, false),
        Pair(R.drawable.img_3, false),
        Pair(R.drawable.img_4, false),
        Pair(R.drawable.img_5, false),
        Pair(R.drawable.img_6, false),
        Pair(R.drawable.img_7, false),
        Pair(R.drawable.img_8, false),
        Pair(R.drawable.img_9, false),
        Pair(R.drawable.img_10, false),
        Pair(R.drawable.img_11, false),
        Pair(R.drawable.img_12, false),
        Pair(R.drawable.img_13, false),
        Pair(R.drawable.img_14, false),
        Pair(R.drawable.img_15, false),
        Pair(R.drawable.img_1, false),
        Pair(R.drawable.img_2, false),
        Pair(R.drawable.img_3, false),
        Pair(R.drawable.img_4, false),
        Pair(R.drawable.img_5, false),
        Pair(R.drawable.img_6, false),
        Pair(R.drawable.img_7, false),
        Pair(R.drawable.img_8, false),
        Pair(R.drawable.img_9, false),
        Pair(R.drawable.img_10, false),
        Pair(R.drawable.img_11, false),
        Pair(R.drawable.img_12, false),
        Pair(R.drawable.img_13, false),
        Pair(R.drawable.img_14, false),
        Pair(R.drawable.img_15, false),
        Pair(R.drawable.img_1, false),
        Pair(R.drawable.img_2, false),
        Pair(R.drawable.img_3, false),
        Pair(R.drawable.img_4, false),
        Pair(R.drawable.img_5, false),
        Pair(R.drawable.img_6, false),
        Pair(R.drawable.img_7, false),
        Pair(R.drawable.img_8, false),
        Pair(R.drawable.img_9, false),
        Pair(R.drawable.img_10, false),
        Pair(R.drawable.img_11, false),
        Pair(R.drawable.img_12, false),
        Pair(R.drawable.img_13, false),
        Pair(R.drawable.img_14, false),
        Pair(R.drawable.img_15, false),
    )
}