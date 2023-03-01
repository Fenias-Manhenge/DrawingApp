package com.example.drawingapp

import android.app.Dialog
import android.content.ClipData.Item
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawingView: DrawClass
    private var ibCurrentPaint: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawingView = binding.drawingView
        drawingView.setSizeForBrush(10F)

        ibCurrentPaint = binding.llColorPallet[0] as ImageButton
        ibCurrentPaint?.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallet_selected)
        )

        binding.btnBrushSize.setOnClickListener {
            brushSizeDialog()
        }

        binding.btnCollorPallet.setOnClickListener {
            colorPalletDialog()
        }
    }

    private fun colorPalletDialog(){
        Dialog(this).apply {
            title = "Color Pallet"
            setContentView(R.layout.diolog_color_pallet)
        }.show()
    }

    private fun brushSizeDialog(){
        val brushDialog = Dialog(this).apply {
            setTitle("Brush Size")
            setContentView(R.layout.diolog_brush_size)
        }
        brushDialog.show()

        brushDialog.findViewById<ImageButton>(R.id.ib_small_brush).setOnClickListener {
            drawingView.setSizeForBrush(10F)
            brushDialog.dismiss()
        }

        brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush).setOnClickListener {
            drawingView.setSizeForBrush(20F)
            brushDialog.dismiss()
        }

        brushDialog.findViewById<ImageButton>(R.id.ib_large_brush).setOnClickListener {
            drawingView.setSizeForBrush(30F)
            brushDialog.dismiss()
        }
    }

    fun paintClicked(view: View){
        if (ibCurrentPaint !== view) {
            val imageButton = view as ImageButton
            drawingView.setColor(imageButton.tag.toString()) // passing color tag in the parameter

            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_selected)
            )

            ibCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_design)
            )

            ibCurrentPaint = view
        }
    }
}