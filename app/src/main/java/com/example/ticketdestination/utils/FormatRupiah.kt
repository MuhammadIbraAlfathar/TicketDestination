package com.example.ticketdestination.utils


import java.util.*


fun convertRupiah(price: Long): String {
    val rupiahFormat = java.text.NumberFormat.getCurrencyInstance(Locale("id","ID"))
    return rupiahFormat.format(price)
}