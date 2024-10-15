package com.heosoft.androidsurfaceview.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.heosoft.androidsurfaceview.MainActivity
import com.heosoft.androidsurfaceview.R

enum class GameCharacters() {

    PLAYER(R.drawable.spritesheet_character, 7, 4),
    SKELETON(R.drawable.spritesheet_skeleton, 7, 4),
    MCC(R.drawable.relay, 1, 2);

    private lateinit var spritesheet: Bitmap
    private lateinit var options: BitmapFactory.Options
    private lateinit var spritesArray : Array<Array<Bitmap?>>

    constructor(resId: Int, row: Int, col: Int) : this() {

        options = BitmapFactory.Options()
        options.inScaled = false
        spritesheet = BitmapFactory.decodeResource(MainActivity.getGameContext().resources, resId, options)
        System.out.println("Width: " + spritesheet.width + " Height: " + spritesheet.height)
        spritesArray = Array(row) { Array<Bitmap?>(col) { null } }
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