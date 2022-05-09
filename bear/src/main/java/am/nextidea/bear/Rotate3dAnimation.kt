package am.nextidea.bear

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation

internal class Rotate3dAnimation : Animation() {
    var fromXDegrees: Float = 0f
    var toXDegrees: Float = 0f
    var fromYDegrees: Float = 0f
    var toYDegrees: Float = 0f
    var fromZDegrees: Float = 0f
    var toZDegrees: Float = 0f

    private lateinit var camera: Camera
    private var width = 0
    private var height = 0

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        this.width = width / 2
        this.height = height / 2
        camera = Camera()
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val xDegrees = fromXDegrees + (toXDegrees - fromXDegrees) * interpolatedTime
        val yDegrees = fromYDegrees + (toYDegrees - fromYDegrees) * interpolatedTime
        val zDegrees = fromZDegrees + (toZDegrees - fromZDegrees) * interpolatedTime
        val matrix = t.matrix
        camera.save()
        camera.rotateX(xDegrees)
        camera.rotateY(yDegrees)
        camera.rotateZ(zDegrees)
        camera.getMatrix(matrix)
        camera.restore()
        matrix.preTranslate(-width.toFloat(), -height.toFloat())
        matrix.postTranslate(width.toFloat(), height.toFloat())
    }

    fun addYDegrees(y: Float, z: Float) {
        toYDegrees += y
        toZDegrees += z
    }

    fun setLastDate() {
        fromXDegrees = toXDegrees
        fromYDegrees = toYDegrees
        fromZDegrees = toZDegrees
    }

}