package com.lisko.microbs.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.ActivityAddKorisnikBinding
import com.lisko.microbs.model.entities.Korisnik
import com.lisko.microbs.viewmodel.KorisnikViewModel
import com.lisko.microbs.viewmodel.KorisnikViewModelFactory
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow

class AddKorisnikActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddKorisnikBinding
    private var listaUsername: List<String> = listOf()


    private val mKorisnikViewModel: KorisnikViewModel by viewModels {
        KorisnikViewModelFactory((this.application
                as MicroApplication).repository)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddKorisnikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.btnAddKorisnik.setOnClickListener{
            if(validateKorisnik()){
                val ime= binding.etIme.text.toString()
                val username= binding.etUsername.text.toString()
                val password= binding.etPassword.text.toString()

                val cryptPassword= String(Bcrypt.hash(password, 10))

                val korisnik= Korisnik(ime,username,cryptPassword)

                mKorisnikViewModel.insert(korisnik)

                showToast(getString(R.string.success_add_korisnik))
                finish()
            }

        }

        binding.btnCancelKorisnik.setOnClickListener{
            showToast(getString(R.string.canceled_add_korisnik))
            finish()
        }

        mKorisnikViewModel.allUsername.observe(this){
            listaUsername=it
        }


    }

    private fun validateKorisnik(): Boolean {
        val ime= binding.etIme.text.toString()
        val username= binding.etUsername.text.toString()
        val password= binding.etPassword.text.toString()

        when {
            ime.isEmpty() -> {
                showToast(getString(R.string.error_ime))
                return false
            }
            username.isEmpty() -> {
                showToast(getString(R.string.error_username))
                return false
            }
            password.isEmpty() -> {
                showToast(getString(R.string.error_password))
                return false
            }
            password.length < 5 ->{
                showToast(getString(R.string.error_password_length))
                return false
            }
            listaUsername.contains(username) ->{
                showToast(getString(R.string.exists_username))
                return false
            }

            else -> return true
        }
    }



    private fun showToast(message: String){
        Toast.makeText(this,
        message, Toast.LENGTH_SHORT).show()
    }


}