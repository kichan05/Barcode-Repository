package com.heechan.barcoderepository

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.heechan.barcoderepository.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val barcodeDB: BarcodeDatabase by lazy { BarcodeDatabase.getInstance(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnMainGotoScan.setOnClickListener(scanClick)
    }

    override fun onStart() {
        super.onStart()
        settingBarcode()
    }

    private fun settingBarcode() {
        CoroutineScope(Dispatchers.IO).launch {
            val barcodeList = getBarcodeData()
            val barcodeFragmentList = barcodeList.map {
                BarcodeFragment(it)
            }

            withContext(Dispatchers.Main) {
                binding.vpMain.adapter = ViewPagerAdapter(this@MainActivity, barcodeFragmentList)
            }
        }
    }

    private fun getBarcodeData() : List<Barcode> {
        return barcodeDB.barcodeDao().getAll()
    }

    private val scanClick = { view : View ->
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
    }
}