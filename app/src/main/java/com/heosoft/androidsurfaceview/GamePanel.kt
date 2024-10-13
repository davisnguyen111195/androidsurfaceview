package com.heosoft.androidsurfaceview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.heosoft.androidsurfaceview.entities.GameCharacters
import com.heosoft.androidsurfaceview.helpers.GameConstaints

class GamePanel constructor(myContext: Context) : SurfaceView(myContext), SurfaceHolder.Callback {
    private val redPaint = Paint()

    //    private var x: Float = 0F
//
//    private var y: Float = 0F
    private var xDir = 1
    private var yDir = 1

    //    private val squares = ArrayList<Square>()
    private var random = java.util.Random()
    private lateinit var gameLoop: GameLoop
    private var position: PointF = PointF()
    private val skeletons = ArrayList<PointF>()
    private var playerFaceDirection: Int = GameConstaints.DOWN
    private var playerAniIndexY: Int = 0
    private var aniTick: Int = 0
    private var aniSpeed: Int = 10
    private var skeletonPos : PointF = PointF(0F, 0F)
    private var skeletonTime = System.currentTimeMillis()
    private var skeletonDir = GameConstaints.DOWN

    init {
        holder.addCallback(this)
        redPaint.setColor(Color.RED)
        gameLoop = GameLoop(this)
//        for (i in 1..10) {
//            skeletons.add(
//                PointF(
//                    random.nextInt(1080).toFloat(),
//                    random.nextInt(2220).toFloat()
//                )
//            )
//        }
    }

    fun render() {
        val canvas: Canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)
//        synchronized(squares) {
//            for (square in squares) {
//                square.onDrawSquare(canvas)
//            }
//        }

        //canvas.drawBitmap(GameCharacters.PLAYER.getSpriteSheet(), 500F, 500F, null)
        GameCharacters.PLAYER.getSprite(playerAniIndexY, playerFaceDirection)?.let {
            canvas.drawBitmap(it, position.x, position.y, null)
        }



        GameCharacters.SKELETON.getSprite(playerAniIndexY, skeletonDir)?.let{
            canvas.drawBitmap(it, skeletonPos.x, skeletonPos.y, null)
        }
//        for (pos in skeleton) {
//            GameCharacters.SKELETON.getSprite(6, 3)?.let {
//                canvas.drawBitmap(
//                    it,
//                    pos.x,
//                    pos.y,
//                    null
//                )
//            }
//        }
        holder.unlockCanvasAndPost(canvas)
    }

    fun update(delta: Double) {
//        synchronized(squares) {
//            for (square in squares) {
//                square.move(delta)
//            }
//        }

//        for (pos in skeleton) {
//            pos.y += (yDir * delta * 600).toFloat()
//            System.out.println(delta)
//            if (pos.y >= 2220) {
//                pos.y = 0F
//            }
//            //pos.x += (delta * 300).toFloat()
//        }


        if(System.currentTimeMillis() - skeletonTime >= 5000){
            skeletonDir = random.nextInt(4)
            skeletonTime = System.currentTimeMillis()
        }

        updateAnimation(delta)

        when(skeletonDir){
            GameConstaints.DOWN -> {
                skeletonPos.y += (delta * 300).toFloat()
                if(skeletonPos.y >= 2220)
                    skeletonDir = GameConstaints.UP
            }
            GameConstaints.UP -> {
                skeletonPos.y -= (delta * 300).toFloat()
                if(skeletonPos.y <= 0)
                    skeletonDir = GameConstaints.DOWN
            }

            GameConstaints.RIGHT -> {
                skeletonPos.x += (delta * 300).toFloat()
                if(skeletonPos.x >= 1080)
                    skeletonDir = GameConstaints.LEFT
            }
            GameConstaints.LEFT -> {
                skeletonPos.x -= (delta * 300).toFloat()
                if(skeletonPos.x <= 0)
                    skeletonDir = GameConstaints.RIGHT
            }
        }


    }

    private fun updateAnimation(delta: Double) {
        aniTick++
        if (aniTick >= aniSpeed) {
            aniTick = 0
            playerAniIndexY++
            if (playerAniIndexY >= 4) {
                playerAniIndexY = 0
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
//            val pos = PointF(event.x, event.y)
//            val size = 25F + random.nextInt(100)
//            val color = Color.rgb(
//                random.nextInt(256),
//                random.nextInt(256),
//                random.nextInt(256)
//            )
//            synchronized(squares) {
//                squares.add(Square(pos, size, color))
//            }
            val newX = event.x
            val newY = event.y

            val xDiff = Math.abs(newX - position.x)
            val yDiff = Math.abs(newY - position.y)
            if (xDiff > yDiff) {
                if (newX > position.x){
                    playerFaceDirection = GameConstaints.RIGHT
                    skeletonDir = GameConstaints.RIGHT
                }

                else{
                    playerFaceDirection = GameConstaints.LEFT
                    skeletonDir = GameConstaints.LEFT
                }

            } else {
                if (newY > position.y){
                    playerFaceDirection = GameConstaints.DOWN
                    skeletonDir = GameConstaints.DOWN
                }

                else{
                    playerFaceDirection = GameConstaints.UP
                    skeletonDir = GameConstaints.UP
                }

            }

            position.x = newX
            position.y = newY

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

//    class Square(
//        pos: PointF,
//        size: Float,
//        color: Int
//    ) {
//        private val inPos: PointF = pos
//        private val inSize: Float = size
//        private val inColor: Int = color
//        private val paint = Paint()
//        private var xDir = 1
//        private var yDir = 1
//
//        init {
//            paint.setColor(inColor)
//        }
//
//        fun move(delta: Double) {
//            inPos.x += (xDir * delta * 300).toFloat()
//            if (inPos.x + inSize >= 1080 || inPos.x <= 0) {
//                xDir *= -1
//            }
//
//            inPos.y += (yDir * delta * 300).toFloat()
//            if (inPos.y + inSize >= 2220 || inPos.y <= 0) {
//                yDir *= -1
//            }
//        }
//
//        fun onDrawSquare(canvas: Canvas) {
//            canvas.drawRect(inPos.x, inPos.y, inPos.x + inSize, inPos.y + inSize, paint)
//        }
//
//    }
}