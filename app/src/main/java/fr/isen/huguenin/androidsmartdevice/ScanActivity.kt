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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
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
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
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

    private fun modePlay() {
        binding.pause.visibility = View.VISIBLE
        binding.start.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.view.visibility = View.GONE
        binding.ble.visibility = View.VISIBLE
    }
    private fun modePause() {
        binding.pause.visibility = View.GONE
        binding.start.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.view.visibility = View.VISIBLE
        binding.ble.visibility = View.VISIBLE
    }
    private fun ClickOn() {
        binding.pause.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.ble.visibility = View.GONE
        binding.scan.text = "LANCER SCAN BLE"


        binding.start.setOnClickListener {
            Log.e("button", "CLick sur play")
            requestPermission()
            Toast.makeText(this, "Start SCAN", Toast.LENGTH_LONG).show()
            binding.scan.text = "SCAN BLE en cours"
            BluetoothActive()
        }

         binding.pause.setOnClickListener {
                Log.e("button", "CLick sur pause")
                Toast.makeText(this, "SCAN stop", Toast.LENGTH_LONG).show()
                binding.scan.text = "LANCER SCAN BLE"
                modePause()
               // StopBluetoothKO()
            }

    }


    private fun BluetoothActive() {
        if (bluetoothAdapter?.isEnabled == false) {
            Toast.makeText(this, "BLE KO", Toast.LENGTH_LONG).show()
            binding.scan.text = "Plus de Bluetooth"
            modePause()
        }
        else {
            modePlay()
            scanDeviceWithPermisions()
        }
    }
    
    private fun scanDeviceWithPermisions() {
        if(allPermissionsGranted()) {
            scanBLEDevices()
        }
        else {
            //request permission
        }
    }

    private fun scanBLEDevices() {
        TODO("Not yet implemented")
    }


    private fun allPermissionsGranted(): Boolean {
        val allPermissions = gtAllPermissions()
        return allPermissions.all {
           // varification des permissions
            requestPermission()

        }
    }

    private fun gtAllPermissions(): Array<String> {
        return arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT, android.Manifest.permission.BLUETOOTH_SCAN)
    }


    private fun requestPermission() : Boolean {
        val allPermissions = gtAllPermissions()
        when{
            ContextCompat.checkSelfPermission(
                this,
                allPermissions.toString()
            ) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "BLE CONNECT", Toast.LENGTH_LONG).show()
                return true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                allPermissions.toString()
            ) -> {
                //additional rationale should be displayed
                Toast.makeText(this, "BLE CONNECT add", Toast.LENGTH_LONG).show()
                return false
            }
            else -> {
                //permission has not been asked yet
                Toast.makeText(this, "NOT BLE CONNECT", Toast.LENGTH_LONG).show()
                return false
            }
        }

    }

}