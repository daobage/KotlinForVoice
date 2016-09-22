package com.example.uworks.kotlinforvoice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * Created by UWorks on 2016/9/22.
 */
class Voice : View {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    var Swidth = 500
    var Sheight = 500
    var outCirclPaint:Paint ?= null
    var voicePaint:Paint ?= null
    var cirpaht: Path ?= null
    var rectF:RectF ?= null
    var  voiceNumber = 0
    var  downy = 0
    var movey:Float ?= null

    fun init(){
        outCirclPaint = Paint()
        outCirclPaint?.color = Color.YELLOW
        outCirclPaint?.isAntiAlias = true
        outCirclPaint?.style = Paint.Style.STROKE
        outCirclPaint?.strokeWidth = 20.toFloat()

        voicePaint = Paint()
        voicePaint?.color = Color.GREEN
        voicePaint?.isAntiAlias = true
        voicePaint?.style = Paint.Style.STROKE
        voicePaint?.strokeWidth = 20.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate((Swidth/2).toFloat(), (Sheight/2).toFloat())
        val voice:Bitmap = BitmapFactory.decodeResource(resources,R.drawable.voice)
        val bwidth = voice.width
        val bheight = voice.height
        canvas?.drawBitmap(voice,(-bwidth/2).toFloat(),(-bheight/2).toFloat(),outCirclPaint)

        if (cirpaht == null){
            cirpaht = Path();
        }
        if (rectF == null ){
            val r = bwidth/2*1.2f
            rectF = RectF(-r,-r,r,r)
        }
        cirpaht?.addArc(rectF, 0.toFloat(), 360.toFloat())
        canvas?.drawPath(cirpaht,outCirclPaint)
        canvas?.drawArc(rectF,0.toFloat(),voiceNumber.toFloat(),false,voicePaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
         when (action) {
            MotionEvent.ACTION_DOWN -> {
                downy = event?.y!!.toInt()
            }
            MotionEvent.ACTION_MOVE ->{
                movey = event?.y?.minus(downy)
                if (voiceNumber > 360){
                    voiceNumber = 360
                }
                 else if (voiceNumber < 0){
                    voiceNumber = 0
                }else{
                    voiceNumber = (movey?.div(100)?.plus(voiceNumber))!!.toInt()
                    invalidate()
                }
            }
        }

        return true
    }
}
