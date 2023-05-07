package com.example.bftr_e1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bftr_e1.databinding.ActivityFormularioBinding
import com.example.bftr_e1.databinding.ActivityPdatosBinding

class PDatos : AppCompatActivity() {

    private lateinit var binding: ActivityPdatosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdatos)
        binding = ActivityPdatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if (bundle != null) {

            var usuario: Usuario? = null

            usuario = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable<Usuario>("usuario", Usuario::class.java)
            } else {
                bundle.getParcelable<Usuario>("usuario")
            }

            if (usuario != null) {
                binding.tvNombre.text = usuario.nombre
                binding.tvApellido.text = usuario.apellidos
                binding.tvCuenta.text = usuario.ncuenta
                binding.tvEdad.text = usuario.edad.toString()
                binding.tvSignoZodiacal.text = usuario.SignoZ
                binding.tvHChino.text = usuario.HChino
                binding.tvCorreo.text = usuario.correo
                usuario.imagen?.let { binding.ivImagenCarrera.setImageResource(it) }
            }
        }



    }

    fun click(view: View) {
        val intent = Intent(this, Formulario::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}