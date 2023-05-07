package com.example.bftr_e1

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.bftr_e1.databinding.ActivityFormularioBinding
import java.util.*

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

    fun clickCalendar(view: View){
        val fechaActual = Calendar.getInstance()

        // Configura la fecha mínima permitida para 1900
        val fechaMinima = Calendar.getInstance()
        fechaMinima.set(Calendar.YEAR, 1960)

        // Crea una instancia de DatePickerDialog y configura las fechas mínima y máxima permitidas
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // La fecha seleccionada se devuelve en los parámetros year, month y dayOfMonth
                val FechaSeleccionada = "$dayOfMonth/${month+1}/$year"
                binding.etFNacimiento.setText(FechaSeleccionada)
            },
            fechaActual.get(Calendar.YEAR),
            fechaActual.get(Calendar.MONTH),
            fechaActual.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.minDate = fechaMinima.timeInMillis
        datePickerDialog.datePicker.maxDate = fechaActual.timeInMillis

        val btnFecha = findViewById<ImageButton>(R.id.imButton)
        btnFecha.setOnClickListener {
            datePickerDialog.show()
        }

    }
    fun click(view: View) {
        if(binding.etNombre.text.isEmpty() || binding.etApellidos.text.isEmpty() ||
            binding.etEmail.text.isEmpty() || binding.etNoCuenta.text.isEmpty()|| binding.etFNacimiento.text.isEmpty()){
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




