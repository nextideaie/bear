package am.nextidea.bear

import android.content.Context
import android.view.View

internal class EyeLeft(
    private val view: View,
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

    private val eyeActiveMoveX = context.toPx(R.dimen.space_10)

    private val eyeActiveMoveY = context.toPx(R.dimen.space_5)

    private val eyeMove = context.toPx(R.dimen.space_1_25)

    fun activeState() {
        with(view) {
            animateMove(
                x - eyeActiveMoveX + moveViewX,
                y + eyeActiveMoveY,
                moveDuration,
                view
            )
        }
    }

    fun typing(isTyping: Boolean) {
        if (isTyping) {
            animateMove(viewX + eyeMove, viewY, 0, view)
        } else {
            animateMove(viewX - eyeMove, viewY, 0, view)
        }
    }

    fun defaultState() {
        animateMove(viewX + eyeActiveMoveX - moveViewX, viewY - eyeActiveMoveY, moveDuration, view)
    }

    override fun resetMoveViewX(count: Float) {
        super.moveViewX = count * eyeMove
    }
}