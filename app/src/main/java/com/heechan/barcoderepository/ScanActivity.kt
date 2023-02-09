package com.heechan.barcoderepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.zxing.BarcodeFormat
import com.heechan.barcoderepository.databinding.ActivityScanBinding
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanActivity : AppCompatActivity() {
    lateinit var binding: ActivityScanBinding
    private val barcodeDB: BarcodeDatabase by lazy { BarcodeDatabase.getInstance(applicationContext) }
    var barcodeId: String? = null

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        barcodeId = result.contents
        if (barcodeId == null) {
            Toast.makeText(this, "바코드 스캔에 실패했습니다.", Toast.LENGTH_LONG).show()
        } else {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(barcodeId, BarcodeFormat.CODE_128, 400, 200)

            binding.txtScanBarcodeID.text = barcodeId
            binding.imgScanBarcode.setImageBitmap(bitmap)
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

    private fun checkInput(): Boolean {
        val name = binding.edtScanBarcodeName.text.toString()
        val description = binding.edtScanBarcodeDescription.text.toString()

        if (barcodeId == null) {
            Toast.makeText(this, "바코드를 스캔해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (name.isBlank()) {
            binding.edtScanBarcodeName.error = "이름을 입력해주세요."
            return false
        }

        if (description.isBlank()) {
            binding.edtScanBarcodeDescription.error = "설명을 입력해주세요."
            return false
        }

        return true
    }

    private val saveBarcode = saveBarcode@{ view: View ->
        if (!checkInput()) {
            return@saveBarcode
        }

        val name = binding.edtScanBarcodeName.text.toString()
        val description = binding.edtScanBarcodeDescription.text.toString()

        val saveData = Barcode(id = barcodeId!!, name = name, description = description)

        CoroutineScope(Dispatchers.IO).launch {
            barcodeDB.barcodeDao().insertAll(saveData)

            withContext(Dispatchers.Main) {
                Toast.makeText(this@ScanActivity, "저장 성공", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}