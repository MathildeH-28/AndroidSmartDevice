package fr.isen.huguenin.androidsmartdevice

import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.*
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityDevice2Binding
import fr.isen.huguenin.androidsmartdevice.databinding.ActivityScanBinding
import java.util.*

@SuppressLint("MissingPermission")
class DeviceActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityDevice2Binding
    private var nbr = 0
    //private val LED1_CHARACTERISTIC_UUID = UUID.fromString("SERVICE 3 UUID HERE")
    private val characteristicLedUUID = UUID.fromString("0000abcd-8e22-4541-9d4c-21edae82ed19")
    private val serviceUUID = UUID.fromString("0000feed-cc7a-482a-984a-7f2ed5b3e58f")

    var bluetoothGatt: BluetoothGatt? = null

   /* private val bluetoothAdapter: BluetoothAdapter? by
    lazy(LazyThreadSafetyMode.NONE) {

        val bluetoothManager =
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDevice2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.nameBLE.text = intent.getStringExtra("device")
        val device = intent.getParcelableExtra<BluetoothDevice>("device")
        binding.nameBLE.text = device?.name

        bluetoothGatt = device?.connectGatt(this, false, bluetoothGattCallback)
       // bluetoothGatt?.connect()

        clickOnLed()
        binding.nb.text = "${nbr}"


    }

    override fun onStop() {
        super.onStop()
        bluetoothGatt?.close()
    }

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED)
            runOnUiThread {
                bluetoothGatt?.discoverServices()
                displayContantConnected()
                Log.e("connect", "Succès")
                binding.progressBar2.visibility = View.GONE
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e("connect", "Echec")

            }
        }
    }

    private fun displayContantConnected() {
        binding.textLed.text = "Allumez les LED"
        binding.textLed.isVisible = true
        binding.led1.isVisible = true
        binding.led2.isVisible = true
        binding.led3.isVisible = true
        binding.textView3.isVisible = false
        binding.nb.isVisible = true
    }

    private fun clickOnLed() {


        binding.led1.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            if( binding.led1.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led1.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
            else {
                binding.led1.imageTintList = getColorStateList(R.color.purple_200)
                characteristic?.value = byteArrayOf(0x01)
                bluetoothGatt?.writeCharacteristic(characteristic)
                binding.led2.imageTintList = getColorStateList(R.color.black)
                binding.led3.imageTintList = getColorStateList(R.color.black)
                binding.nb.text = "${nbr++}"
            }
        }

        binding.led2.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            if( binding.led2.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led2.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
            else {
                binding.led2.imageTintList = getColorStateList(R.color.purple_200)
                characteristic?.value = byteArrayOf(0x02)
                bluetoothGatt?.writeCharacteristic(characteristic)
                binding.led1.imageTintList = getColorStateList(R.color.black)
                binding.led3.imageTintList = getColorStateList(R.color.black)
                binding.nb.text = "${nbr++}"
            }
        }


        binding.led3.setOnClickListener {
            val characteristic = bluetoothGatt?.getService(serviceUUID)?.getCharacteristic(characteristicLedUUID)
            if( binding.led3.imageTintList == getColorStateList(R.color.purple_200)) {
                binding.led3.imageTintList = getColorStateList(R.color.black)
                characteristic?.value = byteArrayOf(0x00)
                bluetoothGatt?.writeCharacteristic(characteristic)
            }
            else {
                binding.led3.imageTintList = getColorStateList(R.color.purple_200)
                characteristic?.value = byteArrayOf(0x03)
                bluetoothGatt?.writeCharacteristic(characteristic)
                binding.led1.imageTintList = getColorStateList(R.color.black)
                binding.led2.imageTintList = getColorStateList(R.color.black)
                binding.nb.text = "${nbr++}"
            }
        }

    }


}