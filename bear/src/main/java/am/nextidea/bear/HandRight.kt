package am.nextidea.bear

import android.content.Context
import android.view.View

internal class HandRight(
    private val view: View,
    context: Context
) : AnimationBase() {

    override val viewX: Float get() = view.x

    override val viewY: Float get() = view.y

    private val moveDuration: Long = 200

    private val handActiveMove = context.toPx(R.dimen.space_20)

    fun closeEye() {
        with(view) {
            animateMove(
                x,
                y - handActiveMove,
                moveDuration,
                view
            )
        }
    }

    fun openEye() {
        with(view) {
            animateMove(
                x,
                y + handActiveMove,
                moveDuration,
                view
            )
        }
    }

    fun hideHand() {
            with(view) {
                animateMove(
                    x,
                    y + view.height,
                    moveDuration,
                    view
                )
            }
    }

    fun showHand() {
        view.visibility = View.VISIBLE
        with(view) {
            animateMove(
                x,
                y - view.height,
                moveDuration,
                view
            )
        }
    }
}