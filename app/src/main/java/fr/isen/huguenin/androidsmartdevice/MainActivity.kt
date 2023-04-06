package fr.isen.huguenin.androidsmartdevice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val bluetoothAdapter : BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE) {

        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "AndroidSmartDevice"

        ClickButton()

    }

    private fun ClickButton() {

        binding.button.setOnClickListener {
            if (bluetoothAdapter?.isEnabled == true) {
                Toast.makeText(this, "Bluetooth OK", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ScanActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Bluetooth KO", Toast.LENGTH_LONG).show()
            }
        }
    }
}

