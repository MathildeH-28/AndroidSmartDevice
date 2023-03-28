package fr.isen.huguenin.androidsmartdevice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityDevice2Binding
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityScanBinding

class DeviceActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityDevice2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device2)

        binding = ActivityDevice2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        clickOnLed()
    }

    private fun clickOnLed() {
        binding.led1.setOnClickListener {
            if( binding.led1.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led1.imageTintList = getColorStateList(R.color.black)
            }
            else {
                binding.led1.imageTintList = getColorStateList(R.color.purple_200)
            }
        }

        binding.led2.setOnClickListener {
            if( binding.led2.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led2.imageTintList = getColorStateList(R.color.black)
            }
            else {
                binding.led2.imageTintList = getColorStateList(R.color.purple_200)
            }
        }


        binding.led3.setOnClickListener {
            if( binding.led3.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led3.imageTintList = getColorStateList(R.color.black)
            }
            else {
                binding.led3.imageTintList = getColorStateList(R.color.purple_200)
            }
        }

    }
}