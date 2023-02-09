package com.heechan.barcoderepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.heechan.barcoderepository.databinding.ActivityScanBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ScanActivity : AppCompatActivity() {
    lateinit var binding : ActivityScanBinding
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "바코드 스캔에 실패했습니다.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Scanned : ${result.contents}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan)

        scanBarcode()

        with(binding) {
            btnScanSave.setOnClickListener(saveBarcode)
        }
    }

    private fun scanBarcode() {
        val scanOption = ScanOptions()
        barcodeLauncher.launch(scanOption)
    }

    private fun checkInput() : Boolean {
        val name = binding.edtScanBarcodeName.text.toString()
        val description = binding.edtScanBarcodeDescription.text.toString()

        if(name.isBlank()){
            binding.edtScanBarcodeName.error = "이름을 입력해주세요."
            return false
        }

        if (description.isBlank()){
            binding.edtScanBarcodeDescription.error = "설명을 입력해주세요."
            return false
        }

        return true
    }

    private val saveBarcode = saveBarcode@{ view : View ->
        if(!checkInput()) {
            return@saveBarcode
        }

        Toast.makeText(this, "저장", Toast.LENGTH_LONG).show()
    }
}