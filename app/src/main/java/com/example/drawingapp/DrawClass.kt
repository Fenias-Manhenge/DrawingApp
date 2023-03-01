package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawClass(context: Context, attrs: AttributeSet): View(context, attrs) {

    private lateinit var drawPath: CustomPath
    private lateinit var canvasBitmap: Bitmap
    private lateinit var drawPaint: Paint
    private lateinit var canvasPaint: Paint
    private var brushSize = 0F
    private var brushColor = Color.BLUE
    // The backGround on witch will be drown
    private lateinit var canvas: Canvas
    private val arrayPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        drawPaint = Paint()
        drawPaint.apply {
            color = brushColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.SQUARE
        }
        canvasPaint = Paint(Paint.DITHER_FLAG)
        //brushSize = 20F
        drawPath = CustomPath(brushColor, brushSize)
    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float): Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(canvasBitmap, 0f, 0f, canvasPaint)

        for (path in arrayPaths){
            drawPaint.strokeWidth = path.brushThickness
            drawPaint.color = path.color
            canvas?.drawPath(path, drawPaint)
        }

        if (!drawPath.isEmpty) {
            drawPaint.strokeWidth = drawPath.brushThickness
            drawPaint.color = drawPath.color
            canvas?.drawPath(drawPath, drawPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                drawPath.color = brushColor
                drawPath.brushThickness = brushSize

                drawPath.reset()
                if (touchX!= null && touchY!= null)
                    drawPath.moveTo(touchX, touchY)
            }

            MotionEvent.ACTION_MOVE ->{
                if (touchX!= null && touchY!= null)
                    drawPath.lineTo(touchX, touchY)
            }

            MotionEvent.ACTION_UP ->{
                arrayPaths.add(drawPath)
                drawPath = CustomPath(brushColor, brushSize)
            }
            else -> return false
        }
        invalidate()

        return true
    }

    fun setSizeForBrush(newSize: Float){
        brushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        drawPaint.strokeWidth = brushSize
    }

    fun setColor(newColor: String){
        brushColor = Color.parseColor(newColor)
        drawPaint.color = brushColor
    }
}