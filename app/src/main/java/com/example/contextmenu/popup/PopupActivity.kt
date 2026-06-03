package com.example.contextmenu.popup

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import com.example.contextmenu.R
import com.example.contextmenu.SPAN_COUNT
import com.example.contextmenu.cord_x
import com.example.contextmenu.cord_y
import com.example.contextmenu.databinding.ActivityPopupBinding
import com.example.contextmenu.is_liked
import com.example.contextmenu.position
import com.example.contextmenu.resource_id
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/**
 * Created by Ankita Gaba on 03/06/26.
 */
class PopupActivity : AppCompatActivity() {
    lateinit var binding: ActivityPopupBinding

    private var height = 0
    private var width = 0
    private var elementWidth = 0.0
    private var imageResourceId = 0
    private var pos = 0
    private var cordX = 0f
    private var cordY = 0f
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition =
            TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        super.onCreate(savedInstanceState)
        binding = ActivityPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val displayMetrics = Resources.getSystem().displayMetrics
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels
        elementWidth = width * 0.6
        ViewCompat.setTransitionName(binding.detailContainer, "sharedImage")
        parseExtras(intent.extras)
        uiSetup()
        clickListenerSetup()
    }

    private fun parseExtras(bundle: Bundle?) {
        bundle?.let {
            imageResourceId = it.getInt(resource_id, R.drawable.img_1)
            pos = it.getInt(position, 0)
            isLiked = it.getBoolean(is_liked, false)
            cordX = it.getFloat(cord_x, 0f)
            cordY = it.getFloat(cord_y, 0f)
        }
    }

    private fun uiSetup() {
        binding.apply {
            adjustPopupSize()
            placePopupOnScreen()
            mediaContainer.setImageResource(imageResourceId)
            updateLikeOption()
        }
    }

    private fun adjustPopupSize() {
        binding.apply {
            val params = detailContainer.layoutParams
            params.width = elementWidth.toInt()
            params.height = elementWidth.toInt()
            detailContainer.layoutParams = params
        }
    }

    private fun placePopupOnScreen() {
        binding.apply {
            val offsetHorizontal = (resources.displayMetrics.density * 70).toInt()
            val screenMargins = (resources.displayMetrics.density * 100).toInt()
            val xPlacement = cordX - offsetHorizontal
            val shiftLeft = xPlacement + elementWidth > width - 100
            val shiftAbove = cordY + elementWidth > height - 120
            val shiftBelow = cordY < 100
            detailContainer.x = if(shiftLeft) (width - elementWidth - screenMargins).toFloat() else xPlacement
            optionPopupContainer.x = if(shiftLeft) (width - elementWidth - screenMargins).toFloat() else xPlacement
            detailContainer.y = if(shiftAbove) (height - elementWidth - 250).toFloat() else if(shiftBelow) 120f else cordY
            optionPopupContainer.y = if(shiftAbove) (height - elementWidth - 250).toFloat() else if(shiftBelow) 120f else cordY
            detailContainer.requestLayout()
            optionPopupContainer.requestLayout()
        }
    }

    private fun updateLikeOption() {
        binding.apply {
            optionPopup.likeIcon.setImageResource(if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart)
            optionPopup.likeText.text = getString(if (isLiked) R.string.unlike else R.string.like)
        }
    }

    private fun clickListenerSetup() {
        binding.apply {
            container.setOnClickListener {
                val returnIntent = Intent()
                returnIntent.putExtra(is_liked, isLiked)
                returnIntent.putExtra(position, pos)
                setResult(RESULT_OK, returnIntent)
                finish()
                overridePendingTransition(0, 0)
            }
            optionPopup.likeContainer.setOnClickListener {
                isLiked = !isLiked
                updateLikeOption()
            }
        }
    }
}