package com.heechan.barcoderepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.heechan.barcoderepository.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "바코드 스캔에 실패했습니다.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Scanned : ${result.contents}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnMainGotoScan.setOnClickListener(scanClick)
    }

    private val scanClick = { view : View ->
        val scanOption = ScanOptions()
        barcodeLauncher.launch(scanOption)
    }
}