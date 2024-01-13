package com.scrollz.partrecognizer.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.scrollz.partrecognizer.presentation.scanner.ScannerActivity

class ScannerContract : ActivityResultContract<Unit?, Unit>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, ScannerActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?) { }
}
