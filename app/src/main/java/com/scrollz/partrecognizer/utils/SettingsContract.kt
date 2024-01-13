package com.scrollz.partrecognizer.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract

class SettingsContract : ActivityResultContract<Unit?, Unit>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", context.packageName, null)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?) { }
}
