package com.lisko.microbs.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.ActivityLoginBinding
import com.lisko.microbs.model.entities.Korisnik
import com.lisko.microbs.viewmodel.KorisnikViewModel
import com.lisko.microbs.viewmodel.KorisnikViewModelFactory
import com.toxicbakery.bcrypt.Bcrypt

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var lista: List<Korisnik> = listOf()

    private val mKorisnikViewModel: KorisnikViewModel by viewModels {
        KorisnikViewModelFactory((this.application
                as MicroApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding.btnLogin.setOnClickListener{
            if(validateEntry())
                checkLogin()
        }

        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, AddKorisnikActivity::class.java))
        }

        mKorisnikViewModel.allKorisnik.observe(this){
            lista= it
        }

    }

    private fun validateEntry(): Boolean {
        val username= binding.username.text.toString()
        val password= binding.password.text.toString()

        when{
            username.isEmpty() -> {
                showToast(getString(R.string.error_username))
                return false
            }
            password.isEmpty() -> {
                showToast(getString(R.string.error_password))
                return false
            }
            else -> return true
        }
    }

    private fun checkLogin(){
        val username= binding.username.text.toString()
        val password= binding.password.text.toString()
        var success= false
        for (item in lista){
            if (item.username == username)
                if(Bcrypt.verify(password, item.password.toByteArray()))
                {
                    startActivity(Intent(this, MainActivity::class.java))
                    showToast(getString(R.string.success_login))
                    finish()
                    success=true
                }
        }
        if(!success) showToast(getString(R.string.error_login))

    }

    private fun showToast(message: String){
        Toast.makeText(this,
            message, Toast.LENGTH_SHORT).show()
    }

}