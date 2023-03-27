package fr.isen.huguenin.androidsmartdevice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.isen.huguenin.androidsmartdevice.databinding.CellScanBinding

class ScanAdapter(val nom : ArrayList<String>): RecyclerView.Adapter<ScanAdapter.CellViewHolder>() {

    class CellViewHolder(binding: CellScanBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.bleName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CellViewHolder {
        val binding = CellScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nom.size
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.textView.text = nom[position]
        }
}
