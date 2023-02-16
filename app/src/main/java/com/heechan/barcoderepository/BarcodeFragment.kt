package com.heechan.barcoderepository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.zxing.BarcodeFormat
import com.heechan.barcoderepository.databinding.FragmentBarcodeBinding
import com.journeyapps.barcodescanner.BarcodeEncoder

class BarcodeFragment(private val barcodeData : Barcode) : Fragment() {
    lateinit var binding : FragmentBarcodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_barcode, container, false)
        binding.barcodeData = barcodeData

        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(barcodeData.id, BarcodeFormat.CODE_128, 300, 100)
        binding.imgBarcodeBarcode.setImageBitmap(bitmap)

        return binding.root
    }
}