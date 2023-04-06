package fr.isen.huguenin.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityMainBinding
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private val bluetoothAdapter: BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE) {

        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (!it.containsValue(false)) {
                Log.i("Permission: ", "Granted")
                scanBLEDevices()

            } else {
                Log.i("Permission: ", "Denied")
            }
        }


    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())


    private lateinit var adapter: ScanAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showData()


        // ClickOn()


        if (bluetoothAdapter?.isEnabled == true) {
            //Toast.makeText(this, "BLE KO", Toast.LENGTH_LONG).show()
            scanDeviceWithPermisions()

        } else {
            Toast.makeText(this, "BLE KO", Toast.LENGTH_LONG).show()
        }

        binding.pause.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.ble.visibility = View.GONE
        binding.scan.text = "LANCER SCAN BLE"
        clickPlayPause()


    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()
        if (bluetoothAdapter?.isEnabled == true && allPermissionsGranted()) {
            scanning = false
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }

    }

    private fun scanDeviceWithPermisions() {
        if (allPermissionsGranted()) {
            clickPlayPause()
        } else {
            requestPermissionLauncher.launch(
                gtAllPermissions()
            )

        }
    }

    @SuppressLint("MissingPermission")
    private fun scanBLEDevices() {
        Toast.makeText(this, "TOUT OK", Toast.LENGTH_LONG).show()
        // Stops scanning after 10 seconds.
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false
                bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
                modePause()
            }, SCAN_PERIOD)
            scanning = true
            modePlay()
            bluetoothAdapter?.bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            scanning = false
            modePause()
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }

    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.e("Scan", "$result")
            adapter.addDevice(result.device)
            adapter.notifyDataSetChanged()
        }
    }

    private fun allPermissionsGranted(): Boolean {
        val allPermissions = gtAllPermissions()
        return allPermissions.all {
            // verification des permissions
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED

        }
    }

    private fun gtAllPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_CONNECT

            )
        } else {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    private fun showData() {
        binding.ble.layoutManager = LinearLayoutManager(this)
        adapter = ScanAdapter(arrayListOf()) {
            val intent = Intent(this, DeviceActivity2::class.java)
            intent.putExtra("device", it)
            startActivity(intent)
        }
        binding.ble.adapter = adapter
    }

    private fun modePlay() {
        binding.pause.visibility = View.VISIBLE
        binding.start.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.view.visibility = View.GONE
        binding.ble.visibility = View.VISIBLE
        binding.scan.text = "SCAN BLE en cours"
    }

    private fun modePause() {
        binding.scan.text = "LANCER SCAN BLE"
        binding.pause.visibility = View.GONE
        binding.start.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.view.visibility = View.VISIBLE
        binding.ble.visibility = View.VISIBLE
    }

    private fun clickPlayPause() {

        binding.start.setOnClickListener {
            Log.e("button", "CLick sur play")
            Toast.makeText(this, "Start SCAN", Toast.LENGTH_LONG).show()
            scanBLEDevices()
        }

        binding.pause.setOnClickListener {
            Log.e("button", "CLick sur pause")
            Toast.makeText(this, "SCAN stop", Toast.LENGTH_LONG).show()
            scanBLEDevices()
        }

    }

    companion object {
        // Stops scanning after 10 seconds.
        private val SCAN_PERIOD: Long = 10000
    }

}