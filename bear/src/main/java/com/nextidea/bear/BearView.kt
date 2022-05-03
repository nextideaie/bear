package com.nextidea.bear

import android.content.Context
import android.os.Parcelable
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.nextidea.bear.databinding.BearViewBinding
import kotlinx.parcelize.Parcelize


class BearView : FrameLayout {


    private val binding: BearViewBinding =
        BearViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var password: EditText? = null
    private var isRotate: Boolean = false
    private var inputSymbolsCount = 0
    private var maxSymbolsCount = 21
    private var animationDuration = 200L
    private val muzzle = Muzzle(binding.muzzle, context)
    private val eyeLeft = EyeLeft(binding.eyeLeft, context)
    private val eyeRight = EyeRight(binding.eyeRight, context)
    private val nose = Nose(binding.nose, context)
    private val mouth = Mouth(binding.mouth, context)
    private val handLeft = HandLeft(binding.handLeft, context)
    private val handRight = HandRight(binding.handRight, context)

    private val skew = Rotate3dAnimation()

    private var moveViewY: Float = -context.resources.getDimension(R.dimen.space_3)
    private var moveViewZ: Float = context.resources.getDimension(R.dimen.space_2)
    private var moveViewX: Float = context.resources.getDimension(R.dimen.space_2)
    private val typingMoveSizeY = context.resources.getDimension(R.dimen.space_0_25)
    private val typingMoveSizeZ = context.resources.getDimension(R.dimen.space_0_20)

    private var bearDefaultViewX: Float = 0f
    private var bearDefaultViewY: Float = 0f
    private var bearDefaultViewZ: Float = 0f

    private var isVisiblePassword = true

    constructor(context: Context) : super(context) {
        setAttribute()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setAttribute()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAttribute()
    }

    private fun setAttribute() {
        isFocusableInTouchMode = true
        descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
    }


    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()
        superState?.let {
            val state = SavedState(superState)
            Log.e(
                "INSTANCE",
                "onSaveInstanceState: ${skew.fromXDegrees} .. ${skew.fromYDegrees} .. ${skew.fromZDegrees}"
            )
            state.bearDefaultViewX = -bearDefaultViewX
            state.bearDefaultViewY = bearDefaultViewY
            state.bearDefaultViewZ = bearDefaultViewZ
            state.bearViewX = if (bearDefaultViewX != 0f) -bearDefaultViewX else skew.fromXDegrees
            state.bearViewY = if (bearDefaultViewY != 0f) bearDefaultViewY else skew.fromYDegrees
            state.bearViewZ = if (bearDefaultViewZ != 0f) bearDefaultViewZ else skew.fromZDegrees
            state.muzzleX = binding.muzzle.translationX
            state.muzzleY = binding.muzzle.translationY
            state.muzzleMoveViewX = muzzle.moveViewX
            state.noseX = binding.nose.translationX
            state.noseY = binding.nose.translationY
            state.noseMoveViewX = nose.moveViewX
            state.mouthX = binding.mouth.translationX
            state.mouthY = binding.mouth.translationY
            state.mouthMoveViewX = mouth.moveViewX
            state.eyeRightX = binding.eyeRight.translationX
            state.eyeRightY = binding.eyeRight.translationY
            state.eyeRightMoveViewX = eyeRight.moveViewX
            state.eyeLeftX = binding.eyeLeft.translationX
            state.eyeLeftY = binding.eyeLeft.translationY
            state.eyeLeftMoveViewX = eyeLeft.moveViewX
            state.handLeftX = binding.handLeft.translationX
            state.handLeftY = binding.handLeft.translationY
            state.handRightX = binding.handRight.translationX
            state.handRightY = binding.handRight.translationY
            state.handRightIsVisible = binding.handRight.isVisible
            state.handLeftIsVisible = binding.handLeft.isVisible
            state.inputSymbolsCount = inputSymbolsCount
            state.isVisiblePassword = isVisiblePassword
            return state
        } ?: run {
            return superState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                Log.e(
                    "INSTANCE",
                    "onRestoreInstanceState: ${state.bearViewX} .. ${state.bearViewY} .. ${state.bearViewZ}"
                )
                binding.muzzle.x = state.muzzleX
                binding.muzzle.y = state.muzzleY
                muzzle.moveViewX = state.muzzleMoveViewX
                binding.nose.x = state.noseX
                binding.nose.y = state.noseY
                nose.moveViewX = state.noseMoveViewX
                binding.mouth.x = state.mouthX
                binding.mouth.y = state.mouthY
                mouth.moveViewX = state.mouthMoveViewX
                binding.eyeRight.x = state.eyeRightX
                binding.eyeRight.y = state.eyeRightY
                eyeRight.moveViewX = state.eyeRightMoveViewX
                binding.eyeLeft.x = state.eyeLeftX
                binding.eyeLeft.y = state.eyeLeftY
                eyeLeft.moveViewX = state.eyeLeftMoveViewX
                binding.handLeft.x = state.handLeftX
                binding.handLeft.y = state.handLeftY
                binding.handRight.x = state.handRightX
                binding.handRight.y = state.handRightY
                inputSymbolsCount = state.inputSymbolsCount
                isVisiblePassword = state.isVisiblePassword

                if (state.handRightIsVisible) {
                    binding.handRight.visibility = View.VISIBLE
                }
                if (state.handLeftIsVisible) {
                    binding.handLeft.visibility = View.VISIBLE
                }
                if (state.bearDefaultViewX == 0f && state.bearViewX != 0f) {
                    skew.toXDegrees = state.bearViewX
                    skew.addYDegrees(
                        y = state.bearViewY,
                        z = state.bearViewZ
                    )
                } else {
                    moveViewX = state.bearDefaultViewX
                    moveViewY = state.bearDefaultViewY
                    moveViewZ = state.bearDefaultViewZ
                    bearDefaultViewX = state.bearDefaultViewX
                    bearDefaultViewY = state.bearDefaultViewY
                    bearDefaultViewZ = state.bearDefaultViewZ
                    skew.toXDegrees = 0f
                    skew.addYDegrees(
                        y = 0f,
                        z = 0f
                    )
                }
                skew.fillAfter = true
                skew.duration = 0L
                skew.onAnimationEnd {
                    skew.setLastDate()
                }
                binding.bearView.startAnimation(skew)
                invalidate()
                isRotate = true
            }
            else -> {
                super.onRestoreInstanceState(state)
            }
        }
    }

    @Parcelize
    internal class SavedState(
        private val superSavedState: Parcelable,
        var bearViewX: Float = 0f,
        var bearViewY: Float = 0f,
        var bearViewZ: Float = 0f,
        var bearDefaultViewX: Float = 0f,
        var bearDefaultViewY: Float = 0f,
        var bearDefaultViewZ: Float = 0f,
        var muzzleX: Float = 0f,
        var muzzleY: Float = 0f,
        var muzzleMoveViewX: Float = 0f,
        var noseX: Float = 0f,
        var noseY: Float = 0f,
        var noseMoveViewX: Float = 0f,
        var mouthX: Float = 0f,
        var mouthY: Float = 0f,
        var mouthMoveViewX: Float = 0f,
        var eyeRightX: Float = 0f,
        var eyeRightY: Float = 0f,
        var eyeRightMoveViewX: Float = 0f,
        var eyeLeftX: Float = 0f,
        var eyeLeftY: Float = 0f,
        var eyeLeftMoveViewX: Float = 0f,
        var handLeftY: Float = 0f,
        var handLeftX: Float = 0f,
        var handRightY: Float = 0f,
        var handRightX: Float = 0f,
        var handRightIsVisible: Boolean = false,
        var handLeftIsVisible: Boolean = false,
        var inputSymbolsCount: Int = 0,
        var isVisiblePassword: Boolean = false

    ) : BaseSavedState(superSavedState), Parcelable

    private fun typing() {
        eyeLeft.typing(true)
        eyeRight.typing(true)
        muzzle.typing(true)
        nose.typing(true)
        mouth.typing(true)
        skew.addYDegrees(
            y = typingMoveSizeY,
            z = -typingMoveSizeZ
        )
        skew.fillAfter = true
        skew.duration = 0L
        skew.onAnimationEnd {
            skew.setLastDate()
        }
        binding.bearView.startAnimation(skew)
    }

    private fun removeTyping() {
        eyeLeft.typing(false)
        eyeRight.typing(false)
        muzzle.typing(false)
        nose.typing(false)
        mouth.typing(false)
        skew.addYDegrees(
            y = -typingMoveSizeY,
            z = typingMoveSizeZ
        )

        skew.fillAfter = true
        skew.duration = 0L
        skew.onAnimationEnd {
            skew.setLastDate()
        }
        binding.bearView.startAnimation(skew)
    }

    private fun activeMode() {
        Log.e("INSTANCE", "activeMode: ${moveViewX} .. ${moveViewY} .. ${moveViewZ}")
        if (moveViewX == 0f || moveViewY == 0f || moveViewZ == 0f) {
            moveViewX = context.resources.getDimension(R.dimen.space_2)
            moveViewY = -context.resources.getDimension(R.dimen.space_3)
            moveViewZ = context.resources.getDimension(R.dimen.space_2)
        }
        eyeLeft.activeState()
        eyeRight.activeState()
        muzzle.activeState()
        nose.activeState()
        mouth.activeState()
        bearDefaultViewX = 0f
        bearDefaultViewY = 0f
        bearDefaultViewZ = 0f
        skew.fromXDegrees = 0f
        skew.toXDegrees = -moveViewX
        skew.fromYDegrees = 0f
        skew.fromZDegrees = 0f
        skew.addYDegrees(moveViewY, moveViewZ)
        skew.duration = animationDuration
        skew.fillAfter = true
        skew.onAnimationEnd {
            skew.setLastDate()
        }
        binding.bearView.startAnimation(skew)

    }

    private fun defaultMode() {
        Log.e("INSTANCE", "activeMode: ${moveViewX} .. ${moveViewY} .. ${moveViewZ}")
        mouth.defaultState()
        eyeLeft.defaultState()
        eyeRight.defaultState()
        nose.defaultState()
        muzzle.defaultState()
        bearDefaultViewX = moveViewX
        bearDefaultViewY = moveViewY
        bearDefaultViewZ = moveViewZ
        skew.fromXDegrees = -moveViewX
        skew.fromYDegrees = moveViewY
        skew.fromZDegrees = moveViewZ
        skew.toXDegrees = 0f
        skew.toYDegrees = 0f
        skew.toZDegrees = 0f
        skew.duration = animationDuration
        skew.fillAfter = true
        skew.onAnimationEnd {
            skew.setLastDate()
        }
        binding.bearView.startAnimation(skew)

    }

    private fun onMouthFormat(state: Boolean) {
        with(binding) {
            if (state) {
                binding.mouth.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_mouth_smile
                    )
                )
            } else {
                binding.mouth.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_mouth_open
                    )
                )
            }
        }
    }

    private fun onMailFormat(hasMail: Boolean) {
        with(binding) {
            if (hasMail) {
                eyeRight.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_eye_doe
                    )
                )
                eyeLeft.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_eye_doe
                    )
                )
                mouth.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_mouth_half
                    )
                )
            } else {
                eyeRight.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_eye
                    )
                )
                eyeLeft.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_eye
                    )
                )
                mouth.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_mouth_smile
                    )
                )
            }
        }
    }

    fun setupView(login: EditText, password: EditText? = null) {
        this.password = password
        login.filters = arrayOf(object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                if (end == 1) {
                    if (Character.isWhitespace(source?.get(0)!!)) {
                        return ""
                    }
                }
                return null
            }
        })

        login.doOnTextChanged { text, start, before, count ->
            Log.i("MainActivity", "onCreate: $text $start $before $count")
            if (!isRotate && before != count) {
                text?.let {
                    Log.i("MainActivity", "onCreate: textChange $inputSymbolsCount")
                    if (it.length < maxSymbolsCount) {
                        if (it.length > inputSymbolsCount) {
                            typing()
                            if (it.contains("@")) {
                                onMailFormat(true)
                            }
                        } else {
                            removeTyping()
                            if (!it.contains("@")) {
                                onMailFormat(false)
                            }
                            if (it.isEmpty()) {
                                for (i in 1..inputSymbolsCount - 2)
                                    removeTyping()
                            }
                        }
                        inputSymbolsCount = it.length

                    }
                }

            }
            //else isRotate = false
        }

        login.setOnFocusChangeListener { _, hasFocus ->
            if (!isRotate) {
                if (hasFocus) {
                    activeMode()
                } else {
                    val count = if (login.length() > maxSymbolsCount) {
                        maxSymbolsCount.toFloat()
                    } else {
                        login.length().toFloat()
                    }

                    eyeRight.resetMoveViewX(count)
                    eyeLeft.resetMoveViewX(count)
                    mouth.resetMoveViewX(count)
                    muzzle.resetMoveViewX(count)
                    nose.resetMoveViewX(count)

                    if (count != 0f) {
                        moveViewY = -context.toPx(R.dimen.space_2_75)
                            .toFloat() + count * typingMoveSizeY
                        moveViewZ = context.toPx(R.dimen.space_2)
                            .toFloat() - count * typingMoveSizeZ
                    } else {
                        moveViewY =
                            -context.toPx(R.dimen.space_2_75)
                                .toFloat()
                        moveViewZ =
                            context.toPx(R.dimen.space_2).toFloat()
                    }
                    defaultMode()
                }

            } else isRotate = false
        }

        password?.setOnFocusChangeListener { _, hasFocus ->
            Log.i("TAG", "initEditsViews: $hasFocus")
            if (!isRotate) {
                if (hasFocus) {
                    handRight.showHand()
                    handLeft.showHand()
                      onMouthFormat(false)
                } else {
                    handLeft.hideHand()
                    handRight.hideHand()
                     onMouthFormat(true)
                }
            } else isRotate = false
        }
    }

    fun openCloseMouth(state: Boolean) {
        val x = if (state) 1f else 0f
        val y = if (state) 1f else 0f
        val scale = ScaleAnimation(
            1f,
            1f,
            0f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scale.duration = 300
        scale.fillAfter = true
        scale.fillBefore = true
        binding.mouth.startAnimation(scale)
    }

    fun hidePassword() {
        handLeft.closeEye()
        handRight.closeEye()
    }

    fun showPassword() {
        handLeft.openEye()
        handRight.openEye()
    }

    fun onViewPassword(isShowPassword:(Boolean)->Unit) {
        isVisiblePassword = if (isVisiblePassword) {
            password?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            showPassword()
            isShowPassword(false)
            false
        } else {
            password?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            hidePassword()
            isShowPassword(true)
            true
        }
        val cursorPosition = password?.text?.length ?: 0
        password?.setSelection(cursorPosition)
    }
}