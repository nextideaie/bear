package com.nextidea.bear

import android.graphics.Matrix
import android.graphics.PointF
import android.view.animation.Animation
import android.view.animation.Transformation


internal class MatrixAnimation internal constructor(
    startMatrix: Matrix,
    endMatrix: Matrix,
    animated: Boolean
) : Animation() {

    private val scaleStart: PointF
    private val scaleEnd: PointF
    private val translateStart: PointF
    private val translateEnd: PointF
    private var animated = true

    internal constructor(startMatrix: Matrix, endMatrix: Matrix) : this(
        startMatrix,
        endMatrix,
        true
    ) {
    }

    protected fun setAnimated(animated: Boolean): MatrixAnimation {
        this.animated = animated
        duration = if (animated) 300 else 0
        return this
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        val matrix: Matrix = t.getMatrix()
        val sFactor = PointF(
            scaleEnd.x * interpolatedTime / scaleStart.x + 1 - interpolatedTime,
            scaleEnd.y * interpolatedTime / scaleStart.y + 1 - interpolatedTime
        )
        val tFactor = PointF(
            (translateEnd.x - translateStart.x) * interpolatedTime,
            (translateEnd.y - translateStart.y) * interpolatedTime
        )

        //Log.d(TAG, "applyTransformation: " + sFactor + " " + tFactor);
        matrix.postScale(scaleStart.x, scaleStart.y, 0f, 0f)
        matrix.postScale(sFactor.x, sFactor.y, 0f, 0f)
        matrix.postTranslate(translateStart.x, translateStart.y)
        matrix.postTranslate(tFactor.x, tFactor.y)
    }

    override fun start() {
        setAnimated(true)
        super.start()
    }

    fun start(animated: Boolean) {
        setAnimated(animated)
        super.start()
    }

    init {
        val a = FloatArray(9)
        val b = FloatArray(9)
        startMatrix.getValues(a)
        endMatrix.getValues(b)
        scaleStart = PointF(a[Matrix.MSCALE_X], a[Matrix.MSCALE_Y])
        scaleEnd = PointF(b[Matrix.MSCALE_X], b[Matrix.MSCALE_Y])
        translateStart = PointF(a[Matrix.MTRANS_X], a[Matrix.MTRANS_Y])
        translateEnd = PointF(b[Matrix.MTRANS_X], b[Matrix.MTRANS_Y])
        setFillAfter(true)
        //setInterpolator(new DecelerateInterpolator());
    }
}
