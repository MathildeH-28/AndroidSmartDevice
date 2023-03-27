package fr.isen.huguenin.androidsmartdevice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityMainBinding
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private val bluetoothAdapter : BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE) {

        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //StopBluetoothKO()
        ClickOn()



        binding.ble.layoutManager = LinearLayoutManager(this)
        binding.ble.adapter = ScanAdapter(arrayListOf("BLE_98", "BLE_78", "BLE_14"))


    }


    private fun ClickOn() {
        binding.pause.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.ble.visibility = View.GONE


        binding.start.setOnClickListener {
            Log.e("button", "CLick sur play")
            Toast.makeText(this, "Start SCAN", Toast.LENGTH_LONG).show()
            binding.scan.text = "LANCER SCAN BLE"
            StopBluetoothKO()
        }

         binding.pause.setOnClickListener {
                Log.e("button", "CLick sur play")
                Toast.makeText(this, "Start SCAN", Toast.LENGTH_LONG).show()
                binding.scan.text = "SCAN BLE en cours"
                StopBluetoothKO()
            }

    }

    private fun StopBluetoothKO() {
        if (bluetoothAdapter?.isEnabled == false) {
            Toast.makeText(this, "BLE KO", Toast.LENGTH_LONG).show()
            binding.scan.text = "Plus de Bluetooth"
            binding.pause.visibility = View.GONE
            binding.start.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.view.visibility = View.VISIBLE
            binding.ble.visibility = View.VISIBLE

        }
        else {
            binding.pause.visibility = View.VISIBLE
            binding.start.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.view.visibility = View.GONE
            binding.ble.visibility = View.VISIBLE
        }
    }
}