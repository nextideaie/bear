package am.nextidea.login

import am.nextidea.login.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            customBearView.setupView(login,password)
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
        }
    }


}