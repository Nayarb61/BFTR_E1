package com.example.bftr_e1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.bftr_e1.databinding.ActivityFormularioBinding

class Formulario : AppCompatActivity() {
  //  private lateinit var datePicker: DatePicker
    private lateinit var binding: ActivityFormularioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        binding= ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.opciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("LOGTAG","La carrera seleccionada es $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun isValidEmail(mail:CharSequence) =
        (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches())

    fun isValidNoCuenta(Ncuenta: String): Boolean {
        return Ncuenta.length == 9
    }

    fun click(view: View) {
        if(binding.etNombre.text.isEmpty() || binding.etApellidos.text.isEmpty() ||
            binding.etEmail.text.isEmpty() || binding.etNoCuenta.text.isEmpty()){
            Toast.makeText(this,getString(R.string.error),Toast.LENGTH_LONG).show()
        }
        else{
            if(isValidEmail(binding.etEmail.text)){
            }else{
                binding.etEmail.error = getString(R.string.EmailError)
            }
            if(binding.etNoCuenta.text.length != 9){
                binding.etNoCuenta.error = getString(R.string.NcuentaError)
            }else{
                if(binding.spinner.selectedItemPosition == 0){
                    Toast.makeText(this, getString(R.string.faltaCarrera), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,getString(R.string.RegCompleto),Toast.LENGTH_LONG).show()
                    val intent = Intent (this,PDatos::class.java)
                    val bundle =  Bundle()

                    val us_nombre = binding.etNombre.text.toString()
                    val us_apellido = binding.etApellidos.text.toString()
                    val us_cuenta = binding.etNoCuenta.text.toString()

                    val usuario = Usuario(us_nombre,us_apellido,us_cuenta)
                    bundle.putParcelable("usuario",usuario)

                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }


        }
    }


}




