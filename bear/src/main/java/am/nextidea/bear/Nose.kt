package am.nextidea.bear

import android.content.Context
import android.view.View

internal class Nose(
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

    private val noseActiveMove = context.toPx(R.dimen.space_16)

    private val noseMove = context.toPx(R.dimen.space_1_75)

     fun activeState() {
        with(view) {
            animateMove(x - noseActiveMove + moveViewX, viewY, moveDuration, view)
        }
    }

    fun typing(isTyping: Boolean) {
        if (isTyping) {
            animateMove(viewX + noseMove, viewY, 0, view)
        }else{
            animateMove(viewX - noseMove, viewY, 0, view)
        }
    }

    fun defaultState() {
        animateMove(viewX + noseActiveMove - moveViewX, viewY, moveDuration, view)
    }

    override fun resetMoveViewX(count: Float) {
        super.moveViewX = count * noseMove
    }

}