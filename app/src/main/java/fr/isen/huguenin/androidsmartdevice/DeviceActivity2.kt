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
    }
}