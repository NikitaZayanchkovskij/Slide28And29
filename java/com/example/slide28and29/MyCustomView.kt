package com.example.slide28and29


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.atan2


class MyCustomView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val paintRect = Paint()
    private val paintCircle = Paint()
    private val paintText = Paint()
    private val rect = Rect()
    var listener: IButtonPressedListener? = null
    var labelText = "0"


    /* Здесь настраиваю кисточку под себя: нужный мне цвет и т.д. */
    init {
        paintRect.style = Paint.Style.FILL
        paintRect.color = ContextCompat.getColor(context, R.color.yellow)

        paintCircle.style = Paint.Style.FILL
        paintCircle.color = ContextCompat.getColor(context, R.color.blue)

        paintText.style = Paint.Style.FILL
        paintText.color = Color.BLACK
        paintText.textSize = 60f
    }


    /** Это метод рисует элементы View.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /* Указываю, что хочу, чтобы мой квадрат (жёлтый фон) заполнил весь холст (canvas).
         */
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintRect)
      //Log.d("MyLog", "Width: ${width.toFloat()}, Height: ${height.toFloat()}")

        /* Рисую круг внутри жёлтого квадрата.
         */
        drawCircleButton(canvas)

        /* Пишу текст/лейбл вверху экрана.
         */
        paintText.color = Color.BLACK
        paintText.getTextBounds(labelText, 0, labelText.length, rect)
        canvas.drawText(labelText, (width / 2) - rect.exactCenterX(), 60f, paintText)

        /* Пишу надпись Tap внутри круга. Чтобы вычислить центр текста нам нужен Rect.
         * Т.к. если просто указать координату для текста, то это будет не его центр, а нижний левый
         * угол текста, откуда он начнётся.
         */
        paintText.color = Color.WHITE
        paintText.getTextBounds("Tap", 0, 3, rect)

        canvas.drawText(
            "Tap",
            (width / 2) - rect.exactCenterX(),
            (height - 180) - rect.exactCenterY(),
            paintText)
    }


    /** Функция рисует синюю круглую кнопку Tap.
     */
    private fun drawCircleButton(canvas: Canvas) {
        val circleCenterX = (width / 2).toFloat()
        val circleCenterY = (height - 180).toFloat()
        val circleRadius = ((width / 3) / 2).toFloat()

        canvas.drawArc(
            circleCenterX - circleRadius,
            circleCenterY - circleRadius,
            circleCenterX + circleRadius,
            circleCenterY + circleRadius,
            -180f,
            360f,
            true,
            paintCircle
        )
    }


    /** Слушатель нажатий на элементы View.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                paintCircle.color = Color.GRAY
                listener?.tapPressed()
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                paintCircle.color = ContextCompat.getColor(context, R.color.blue)
                invalidate()
            }
        }

        return true
    }


    interface IButtonPressedListener {
        fun tapPressed()
    }

}