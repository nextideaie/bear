package am.nextidea.bear

import android.content.Context
import android.view.animation.Animation

internal fun Animation.onAnimationEnd(onAnimationEnd: () -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {}
        override fun onAnimationEnd(p0: Animation?) {
            onAnimationEnd()
        }

        override fun onAnimationStart(p0: Animation?) {}
    })
}

internal fun Context?.toPx(id: Int): Int = this?.resources?.getDimensionPixelSize(id) ?: 0

internal fun Context?.show(id: Int): Int = this?.resources?.getDimensionPixelSize(id) ?: 0