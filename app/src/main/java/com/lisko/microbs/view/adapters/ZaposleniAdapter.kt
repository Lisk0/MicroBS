package com.lisko.microbs.view.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.R
import com.lisko.microbs.databinding.DialogZaposleniDetailsBinding
import com.lisko.microbs.databinding.ZaposleniListBinding
import com.lisko.microbs.model.entities.Zaposleni
import com.lisko.microbs.view.fragments.AddKupacFragment
import com.lisko.microbs.view.fragments.ZaposleniFragment

class ZaposleniAdapter(private val fragment: Fragment) : RecyclerView.Adapter<ZaposleniAdapter.ViewHolder>(){

    class ViewHolder (view: ZaposleniListBinding): RecyclerView.ViewHolder(view.root){
        val tvImePrezime= view.tvZaposleniImePrezime
        val tvGrad= view.tvZaposleniGrad
        val tvSifra= view.tvZaposleniSifra
    }

    private var mZaposleniList: List<Zaposleni> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZaposleniAdapter.ViewHolder {
        val binding: ZaposleniListBinding = ZaposleniListBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZaposleniAdapter.ViewHolder, position: Int) {
        val zaposleni= mZaposleniList[position]
        val imeIprezime= zaposleni.ime + " " + zaposleni.prezime
        holder.tvGrad.text= zaposleni.grad
        holder.tvImePrezime.text= imeIprezime
        holder.tvSifra.text= zaposleni.sifra
        if(fragment is ZaposleniFragment){
        holder.itemView.setOnClickListener {
            zaposleniDialog(zaposleni)
        }}

        else if(fragment is AddKupacFragment){
            holder.itemView.setOnClickListener {
                fragment.setSelection(zaposleni.sifra.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return mZaposleniList.size
    }

    fun setZaposleniList(list: List<Zaposleni>){
        mZaposleniList= list
        notifyDataSetChanged()
    }

    fun zaposleniDialog(zaposleni: Zaposleni){
        val dialogBind = DialogZaposleniDetailsBinding.inflate(fragment.layoutInflater)
        val dialog= Dialog(fragment.requireContext())
        var magacini= zaposleni.magacin
        //magacini= "cao, ja sam, ivana"
        magacini=magacini.replace(", ", "\n")
        dialog.setContentView(dialogBind.root)
        dialog.setTitle(fragment.getString(R.string.lb_zaposleni))
        dialogBind.tvZaposleniImeDialog.text= zaposleni.ime
        dialogBind.tvZaposleniGradDialog.text= zaposleni.grad
        dialogBind.tvZaposleniSifraDialog.text= zaposleni.sifra
        dialogBind.tvZaposleniMagacinDialog.text= magacini
        dialogBind.tvZaposleniPrezimeDialog.text= zaposleni.prezime
        dialog.show()
    }

}