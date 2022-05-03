package com.nextidea.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nextidea.login.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val images = arrayListOf(
            R.drawable.ic_mouth_open,
            R.drawable.ic_mouth_half,
            R.drawable.ic_mouth_closed
        )
        with(binding) {
            customBearView.setupView(login, password)
            eyeButton.setOnClickListener {
                customBearView.onViewPassword { isVisible ->
                    if (isVisible) {
                        eyeButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_eye_show
                            )
                        )
                    } else {
                        eyeButton.setImageDrawable(
                            ContextCompat.getDrawable(
                                it.context,
                                R.drawable.ic_eye_hidden
                            )
                        )
                    }
                }
                val cursorPosition = binding.password.text?.length ?: 0
                binding.password.setSelection(cursorPosition)
            }

            val fadeIn: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.left_to_right_in)
            val out: Animation =
                AnimationUtils.loadAnimation(this@MainActivity, R.anim.left_to_right_out)

            slideTransImageswitcher.setInAnimation(fadeIn)
            slideTransImageswitcher.setOutAnimation(out)

            var animationCounter = 0

            val imageSwitcherHandler = Handler(Looper.getMainLooper())
            imageSwitcherHandler.post(object : Runnable {
                override fun run() {
//                    when (animationCounter++) {
//                        1 -> slideTransImageswitcher.setImageResource(R.drawable.ic_mouth_open)
//                        2 -> slideTransImageswitcher.setImageResource(R.drawable.ic_mouth_half)
//                        3 -> slideTransImageswitcher.setImageResource(R.drawable.ic_mouth_closed)
//                    }
//                    animationCounter %= 4
//                    if (animationCounter == 0) animationCounter = 1
//                    imageSwitcherHandler.postDelayed(this, 2000)
                }
            })
//            slideTransImageswitcher.setImageDrawable(images[animationCounter])
//            slideTransImageswitcher.setOnClickListener{ View.OnClickListener {
//                if(animationCounter!=1){
//                    animationCounter++
//                }else {
//                    animationCounter = 0
//                }
//                slideTransImageswitcher.setImageResource(images[animationCounter])
//            } }
        }
    }


}