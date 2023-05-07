package com.example.bftr_e1

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
                binding.tvNombre.text = getString(R.string.MsgNombre, usuario.nombre)
                binding.tvApellido.text = getString(R.string.MsgApellidos, usuario.apellidos)
                binding.tvCuenta.text = getString(R.string.MsgNoCuenta, usuario.ncuenta.toString())
                binding.tvEdad.text = getString(R.string.MsgEdad,usuario.edad.toString())
                binding.tvSignoZodiacal.text = getString(R.string.MsgSignoZodiaco, usuario.SignoZ.toString())
                binding.tvHChino.text = getString(R.string.MsgHchino, usuario.HChino.toString())
                binding.tvCorreo.text = getString(R.string.MsgCorreo,usuario.correo.toString())
                binding.tvIngenieria.text = usuario.carrera
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