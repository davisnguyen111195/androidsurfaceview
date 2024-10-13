package com.heosoft.androidsurfaceview.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.heosoft.androidsurfaceview.MainActivity
import com.heosoft.androidsurfaceview.R

enum class GameCharacters() {

    PLAYER(R.drawable.spritesheet_character),
    SKELETON(R.drawable.spritesheet_skeleton);

    private lateinit var spritesheet: Bitmap
    private lateinit var options: BitmapFactory.Options
    private val spritesArray = Array(7) { Array<Bitmap?>(4) { null } }

    constructor(resId: Int) : this() {
        options = BitmapFactory.Options()
        options.inScaled = false
        spritesheet =
            BitmapFactory.decodeResource(MainActivity.getGameContext().resources, resId, options)
        System.out.println("Width: " + spritesheet.width + " Height: " + spritesheet.height)
        for (j in spritesArray.indices) {
            for (i in spritesArray[j].indices) {
                spritesArray[j][i] =
                    getScaledBitmap(
                        Bitmap.createBitmap(
                            spritesheet,
                            16 * i,
                            16 * j,
                            16,
                            16
                        )
                    )
            }
        }
    }

    fun getSpriteSheet(): Bitmap {
        return spritesheet
    }

    fun getSprite(yPos: Int, xPos: Int): Bitmap? {
        return spritesArray[yPos][xPos]
    }

    fun getScaledBitmap(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(
            bitmap,
            bitmap.width * 10,
            bitmap.height * 10,
            false
        )
    }
}