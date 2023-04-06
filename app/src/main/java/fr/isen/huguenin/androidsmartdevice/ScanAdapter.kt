package fr.isen.huguenin.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors.getColorStateList
import fr.isen.huguenin.androidsmartdevice.R.color.teal_200
import fr.isen.huguenin.androidsmartdevice.databinding.CellScanBinding

class ScanAdapter(var nom : ArrayList<BluetoothDevice>, var onDeviceClickListener: (BluetoothDevice) -> Unit): RecyclerView.Adapter<ScanAdapter.CellViewHolder>() {

    class CellViewHolder(binding: CellScanBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.bleName
       // val imageView = binding.imageView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CellViewHolder {
        val binding = CellScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return nom.size
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        //holder.textView.text = nom[position].address
        holder.textView.text = nom[position].name ?: "Inconnue"
        holder.itemView.setOnClickListener {
            onDeviceClickListener(nom[position])
        }
    }

    @SuppressLint("MissingPermission")
    fun addDevice(device: BluetoothDevice) {
        var shouldAddDevice = true
        nom.forEachIndexed { index, bluetoothDevice ->
            if (bluetoothDevice.name == device.name) {
                nom[index] = device
                shouldAddDevice = false
            }
        }
        if(shouldAddDevice) {
            nom.add(device)
        }

    }


}
