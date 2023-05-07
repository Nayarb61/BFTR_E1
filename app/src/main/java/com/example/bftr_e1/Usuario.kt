package com.example.bftr_e1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Usuario(
    var nombre: String?,
    var apellidos: String?,
    var ncuenta: String?,
    var edad: Int?,
    var SignoZ: String?,
    var HChino: String?,
    var correo: String?,
    var carrera: String?,
    var imagen:Int?
) :
    Parcelable