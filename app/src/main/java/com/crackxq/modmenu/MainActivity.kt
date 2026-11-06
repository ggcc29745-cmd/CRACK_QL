package com.crackxq.modmenu

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.FrameLayout
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable

class MainActivity : Activity() {
    private lateinit var arena: TrainingArena
    private lateinit var aimButton: Button
    private lateinit var espButton: Button
    private var aimEnabled = false
    private var espEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(12, 12, 16)
        window.navigationBarColor = Color.rgb(12, 12, 16)

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(22), dp(18), dp(16))
            setBackgroundColor(Color.rgb(12, 12, 16))
        }

        val title = TextView(this).apply {
            text = "CRACK QL"
            textSize = 28f
            setTextColor(Color.WHITE)
            typeface = Typeface.create("sans-serif-black", Typeface.BOLD)
            gravity = Gravity.CENTER
        }
        page.addView(title, LinearLayout.LayoutParams(-1, dp(48)))

        val subtitle = TextView(this).apply {
            text = "TRAINING LAB  •  OFFLINE DEMO"
            textSize = 11f
            letterSpacing = 0.16f
            setTextColor(Color.rgb(255, 75, 85))
            gravity = Gravity.CENTER
        }
        page.addView(subtitle, LinearLayout.LayoutParams(-1, dp(28)))

        val status = TextView(this).apply {
            text = "●  SIMULATION ACTIVE  |  NO GAME CONNECTION"
            textSize = 11f
            setTextColor(Color.rgb(160, 160, 170))
            gravity = Gravity.CENTER
        }
        page.addView(status, LinearLayout.LayoutParams(-1, dp(34)))

        arena = TrainingArena(this)
        val arenaParams = LinearLayout.LayoutParams(-1, 0, 1f).apply {
            topMargin = dp(12); bottomMargin = dp(14)
        }
        page.addView(arena, arenaParams)

        aimButton = makeButton("🎯  HEAD LOCK DEMO: OFF")
        aimButton.setOnClickListener {
            aimEnabled = !aimEnabled
            aimButton.text = if (aimEnabled) "🎯  HEAD LOCK DEMO: ON" else "🎯  HEAD LOCK DEMO: OFF"
            arena.aimDemo = aimEnabled
            arena.invalidate()
        }
        page.addView(aimButton, buttonParams())

        espButton = makeButton("👁  ESP VISUALIZATION: ON")
        espButton.setOnClickListener {
            espEnabled = !espEnabled
            espButton.text = if (espEnabled) "👁  ESP VISUALIZATION: ON" else "👁  ESP VISUALIZATION: OFF"
            arena.showMarkers = espEnabled
            arena.invalidate()
        }
        page.addView(espButton, buttonParams())

        val note = TextView(this).apply {
            text = "Demo targets are generated locally. This app does not read, modify, or attach to CrossFire."
            textSize = 10f
            setTextColor(Color.rgb(135, 135, 145))
            gravity = Gravity.CENTER
            setPadding(0, dp(10), 0, 0)
        }
        page.addView(note, LinearLayout.LayoutParams(-1, dp(42)))

        setContentView(page)
    }

    private fun makeButton(label: String) = Button(this).apply {
        text = label
        textSize = 12f
        isAllCaps = false
        setTextColor(Color.WHITE)
        background = GradientDrawable().apply {
            cornerRadius = dp(12).toFloat()
            setColor(Color.rgb(125, 20, 32))
            setStroke(dp(1), Color.rgb(235, 55, 70))
        }
    }

    private fun buttonParams() = LinearLayout.LayoutParams(-1, dp(52)).apply {
        topMargin = dp(5); bottomMargin = dp(5)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}

private class TrainingArena(context: android.content.Context) : View(context) {
    var showMarkers = true
    var aimDemo = false
    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(42, 43, 52); strokeWidth = 1f
    }
    private val boxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(255, 65, 80); style = Paint.Style.STROKE; strokeWidth = 3f
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE; textSize = 28f; typeface = Typeface.DEFAULT_BOLD
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(28, 29, 38); style = Paint.Style.FILL
    }
    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(70, 230, 150); strokeWidth = 3f; style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(0f, 0f, width.toFloat(), height.toFloat(), 24f, 24f, fillPaint)
        for (x in 0..width step 48) canvas.drawLine(x.toFloat(), 0f, x.toFloat(), height.toFloat(), gridPaint)
        for (y in 0..height step 48) canvas.drawLine(0f, y.toFloat(), width.toFloat(), y.toFloat(), gridPaint)

        textPaint.textSize = 24f
        textPaint.color = Color.rgb(175, 175, 190)
        canvas.drawText("LOCAL TRAINING ARENA", 24f, 42f, textPaint)

        val targets = listOf(
            Target(width * .28f, height * .38f, "BOT-01"),
            Target(width * .68f, height * .34f, "BOT-02"),
            Target(width * .52f, height * .70f, "BOT-03")
        )
        targets.forEachIndexed { index, target ->
            val left = target.x - 34f
            val top = target.y - 62f
            val right = target.x + 34f
            val bottom = target.y + 62f
            if (showMarkers) {
                canvas.drawRect(left, top, right, bottom, boxPaint)
                canvas.drawCircle(target.x, top - 10f, 8f, boxPaint)
                textPaint.textSize = 22f
                textPaint.color = Color.rgb(255, 100, 110)
                canvas.drawText(target.name, left - 5f, top - 24f, textPaint)
                canvas.drawLine(width / 2f, height - 20f, target.x, bottom, boxPaint)
            }
            if (aimDemo && index == 0) {
                canvas.drawCircle(target.x, top - 10f, 25f, greenPaint)
                canvas.drawLine(target.x - 38f, top - 10f, target.x + 38f, top - 10f, greenPaint)
                canvas.drawLine(target.x, top - 48f, target.x, top + 28f, greenPaint)
            }
        }
        if (aimDemo) {
            textPaint.textSize = 22f
            textPaint.color = Color.rgb(90, 240, 160)
            canvas.drawText("DEMO LOCK: BOT-01", 24f, height - 28f, textPaint)
        }
    }

    private data class Target(val x: Float, val y: Float, val name: String)
}
