package com.heosoft.androidsurfaceview

class GameLoop(
    private val gamePanel: GamePanel
) : Runnable {
    private val gameThread = Thread(this)


    override fun run() {
        var lastFPSCheck = System.currentTimeMillis()
        var fps = 0
        var lastDelta: Double = System.nanoTime().toDouble()
        val nanoSec = 1_000_000_000
        while (true) {

            val nowDelta: Double = System.nanoTime().toDouble()
            val timeSinceLastDelta: Double = nowDelta - lastDelta
            val delta: Double = timeSinceLastDelta / nanoSec

            this.gamePanel.update(delta)
            this.gamePanel.render()
            lastDelta = nowDelta
            fps++
            val now = System.currentTimeMillis()
            if (now - lastFPSCheck >= 1000) {
                System.out.println("FPS: " + fps + " " + lastFPSCheck)
                fps = 0
                lastFPSCheck += 1000
            }
        }
    }

    fun startGameLoop() {
        gameThread.start()
    }
}