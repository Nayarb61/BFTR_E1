package com.example.bftr_e1

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.bftr_e1.databinding.ActivityFormularioBinding
import java.text.SimpleDateFormat
import java.util.*

class Formulario : AppCompatActivity() {
    //  private lateinit var datePicker: DatePicker
    private lateinit var binding: ActivityFormularioBinding
    var FechaSeleccionada = ""
    var imagenCarrera = 0
    var ing_carrera = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagenes = arrayOf(
            R.drawable.fi,
            R.drawable.ing1aeroespacial,
            R.drawable.ing2civil,
            R.drawable.ing3geomatica,
            R.drawable.ing4ambiental,
            R.drawable.ing5geofisica,
            R.drawable.ing6geologica,
            R.drawable.ing7petrolera,
            R.drawable.ing8minas,
            R.drawable.ing9computo,
            R.drawable.ing10electrica,
            R.drawable.ing11telecom,
            R.drawable.ing12mecanica,
            R.drawable.ing13industrial,
            R.drawable.ing14mecatronica,
            R.drawable.ing15biomed
        )

        ArrayAdapter.createFromResource(
            this,
            R.array.opciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ing_carrera = parent?.getItemAtPosition(position).toString()
                imagenCarrera = imagenes[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun isValidEmail(mail: CharSequence) =
        (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches())

    fun clickCalendar(view: View) {
        val fechaActual = Calendar.getInstance()

        // Configura la fecha mínima permitida
        val fechaMinima = Calendar.getInstance()
        fechaMinima.set(Calendar.YEAR, 1960)

        // Crea una instancia de DatePickerDialog y configura las fechas mínima y máxima permitidas
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // La fecha seleccionada se devuelve en los parámetros year, month y dayOfMonth
                FechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
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

        //VALIDA QUE NINNGUN CAMPO QUEDE SIN SER LLENADO
        if (binding.etNombre.text.isEmpty() || binding.etApellidos.text.isEmpty() ||
            binding.etEmail.text.isEmpty() || binding.etNoCuenta.text.isEmpty() || binding.etFNacimiento.text.isEmpty()
        ) {
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
        } else {

            //VALIDA QUE LA ENTRADA DE CORREO TENGA EL FORMATO DE CORREO
            if (isValidEmail(binding.etEmail.text)) {

                //VALIDA QUE EL NUMERO DE CUENTA SI SEA DE LA LONGITUD NECESARIA
                if (binding.etNoCuenta.text.length != 9) {
                    binding.etNoCuenta.error = getString(R.string.NcuentaError)
                } else {

                    //VALIDA LA ELECCIÓN DE LA CARRERA
                    if (binding.spinner.selectedItemPosition == 0) {
                        Toast.makeText(this, getString(R.string.faltaCarrera), Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, getString(R.string.RegCompleto), Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(this, PDatos::class.java)
                        val bundle = Bundle()

                        val us_edad = calculoEdad(FechaSeleccionada)
                        val us_signoZ = SignoZodiacal(FechaSeleccionada)
                        val us_HChino = HoroscopoChino(FechaSeleccionada)

                        val us_Imagen = imagenCarrera

                        val us_nombre = binding.etNombre.text.toString()
                        val us_apellido = binding.etApellidos.text.toString()
                        val us_cuenta = binding.etNoCuenta.text.toString()
                        val us_correo = binding.etEmail.text.toString()
                        val us_carrera = ing_carrera

                        val usuario = Usuario(
                            us_nombre,
                            us_apellido,
                            us_cuenta,
                            us_edad,
                            us_signoZ,
                            us_HChino,
                            us_correo,
                            us_carrera,
                            us_Imagen
                        )
                        bundle.putParcelable("usuario", usuario)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }
            } else {
                binding.etEmail.error = getString(R.string.EmailError)
            }
        }
    }

    fun calculoEdad(fechaNacimiento: String): Int {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNac = formatoFecha.parse(fechaNacimiento)
        val cFechaNac = Calendar.getInstance()
        val cFechaActual = Calendar.getInstance()
        cFechaNac.time = fechaNac

        var edad = cFechaActual.get(Calendar.YEAR) - cFechaNac.get(Calendar.YEAR)
        if (cFechaActual.get(Calendar.DAY_OF_YEAR) < cFechaNac.get(Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }

    //FUNCION QUE AVERIGUA EL SIGNO ZODIACAL
    fun SignoZodiacal(fechaNacimiento: String): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNac = formatoFecha.parse(fechaNacimiento)
        val cFechaNac = Calendar.getInstance()
        cFechaNac.time = fechaNac

        val dia = cFechaNac.get(Calendar.DAY_OF_MONTH)
        val mes = cFechaNac.get(Calendar.MONTH)

        when {
            (mes == 0 && dia >= 20) || (mes == 1 && dia <= 18) -> return "Acuario"
            (mes == 1 && dia >= 19) || (mes == 2 && dia <= 20) -> return "Piscis"
            (mes == 2 && dia >= 21) || (mes == 3 && dia <= 19) -> return "Aries"
            (mes == 3 && dia >= 20) || (mes == 4 && dia <= 20) -> return "Tauro"
            (mes == 4 && dia >= 21) || (mes == 5 && dia <= 20) -> return "Géminis"
            (mes == 5 && dia >= 21) || (mes == 6 && dia <= 22) -> return "Cáncer"
            (mes == 6 && dia >= 23) || (mes == 7 && dia <= 22) -> return "Leo"
            (mes == 7 && dia >= 23) || (mes == 8 && dia <= 22) -> return "Virgo"
            (mes == 8 && dia >= 23) || (mes == 9 && dia <= 22) -> return "Libra"
            (mes == 9 && dia >= 23) || (mes == 10 && dia <= 21) -> return "Escorpio"
            (mes == 10 && dia >= 22) || (mes == 11 && dia <= 21) -> return "Sagitario"
            else -> return "Capricornio"
        }
    }

    //FUNCION QUE AVERIGUA EL HOROSCOPO CHINO
    fun HoroscopoChino(fechaNacimiento: String): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNac = formatoFecha.parse(fechaNacimiento)
        val cFechaNac = Calendar.getInstance()
        cFechaNac.time = fechaNac

        val year = cFechaNac.get(Calendar.YEAR)
        val animalSignos = arrayOf(
            "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
            "Caballo", "Cabra", "Mono", "Gallo", "Perro", "Cerdo"
        )
        return animalSignos[(year - 4) % 12]
    }


}




