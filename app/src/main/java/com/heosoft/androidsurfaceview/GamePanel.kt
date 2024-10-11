package com.heosoft.androidsurfaceview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GamePanel constructor(myContext: Context) : SurfaceView(myContext), SurfaceHolder.Callback {
    private val redPaint = Paint()
//    private var x: Float = 0F
//
//    private var y: Float = 0F

    private val squares = ArrayList<Square>()
    private var random = java.util.Random()
    private lateinit var gameLoop: GameLoop

    init {
        holder.addCallback(this)
        redPaint.setColor(Color.RED)
        gameLoop = GameLoop(this)
    }

    fun render() {
        val canvas: Canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        for (square in squares) {
            square.onDrawSquare(canvas)
        }

        holder.unlockCanvasAndPost(canvas)
    }

    fun update(delta: Double) {
        for (square in squares) {
            square.move(delta)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            val pos = PointF(event.x, event.y)
            val size = 5F + random.nextInt(100)
            val color = Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

            squares.add(Square(pos, size, color))

            //render()
            //update()
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameLoop.startGameLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    class Square(
        pos: PointF,
        size: Float,
        color: Int
    ) {
        private val inPos: PointF = pos
        private val inSize: Float = size
        private val inColor: Int = color
        private val paint = Paint()
        private var xDir = 1
        private var yDir = 1

        init {
            paint.setColor(inColor)
        }

        fun move(delta: Double) {
            inPos.x += (xDir * delta * 300).toFloat()
            if (inPos.x >= 1080 || inPos.x <= 0) {
                xDir *= -1
            }

                inPos.y += (yDir * delta * 300).toFloat()
                if (inPos.y >= 2220 || inPos.y <= 0) {
                    yDir *= -1
                }
        }
        fun onDrawSquare(canvas: Canvas) {
            canvas.drawRect(inPos.x, inPos.y, inPos.x + inSize, inPos.y + inSize, paint)
        }

    }
}