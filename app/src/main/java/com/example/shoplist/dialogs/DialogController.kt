package com.example.shoplist.dialogs

import android.content.Context

class DialogController constructor(serviceInjection: DialogInterface) : DialogInterface {
    private val service: DialogInterface = serviceInjection

    override fun createDialogProduct(context: Context) {
        service.createDialogProduct(context)
    }
}