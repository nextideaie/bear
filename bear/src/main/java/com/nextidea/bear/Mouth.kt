package com.nextidea.bear

import android.content.Context
import android.widget.ImageView

internal class Mouth(
    private val view: ImageView,
    context: Context
) : AnimationBase() {

    override val viewX: Float get() = view.x

    override val viewY: Float get() = view.y

    override var moveViewX: Float
        get() = super.moveViewX
        set(value) {
            super.moveViewX = value
        }
    private val moveDuration: Long = 200

    private val mouthActiveMove = context.toPx(R.dimen.space_10)

    private val mouthMove = context.toPx(R.dimen.space_1_25)

    fun activeState() {
        with(view) {
            animateMove(x - mouthActiveMove + moveViewX, viewY, moveDuration, view)
        }
    }

    fun typing(isTyping: Boolean) {
        if (isTyping) {
            animateMove(viewX + mouthMove, viewY, 0, view)
        } else {
            animateMove(viewX - mouthMove, viewY, 0, view)
        }
    }

    fun defaultState() {
        animateMove(viewX + mouthActiveMove - moveViewX, viewY, moveDuration, view)
    }

    override fun resetMoveViewX(count: Float) {
        super.moveViewX = count * mouthMove
    }
}