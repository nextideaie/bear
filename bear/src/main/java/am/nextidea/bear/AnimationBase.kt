package am.nextidea.bear

import android.view.View

internal abstract class AnimationBase {

    abstract val viewX: Float

    abstract val viewY: Float

    open var moveViewX: Float = 0f

    open fun resetMoveViewX(count: Float){}

    fun animateMove(x: Float, y: Float, duration: Long, view: View) {
        with(view) {
            animate()
                .x(x)
                .y(y)
                .setDuration(duration)
                .start()
        }
    }
}