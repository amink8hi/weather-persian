package com.hanamin.weather.ui.base

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.*


open class BaseActivity : AppCompatActivity() {

    val LOG_TAG = javaClass.simpleName

    fun disableScreenShot() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun enableScreenShot() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        startActivity(intent)
    }

    protected fun showSnackbar(
        view: View?,
        @StringRes message: Int,
        rtl: Boolean
    ) {
        showSnackbar(view, getString(message), rtl)
    }

    protected fun showSnackbar(
        view: View?,
        message: String?,
        rtl: Boolean
    ) {
        Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG).show()
    }


    protected fun showSnackbar(
        view: View?,
        message: String?
    ) {
        Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG).show()
    }

    protected fun showSnackbar(
        view: View?,
        @StringRes message: Int,
        rtl: Boolean,
        length: Int
    ) {
        Snackbar.make(view!!, message, length).show()
    }

    private fun updateLanguagePreference(
        selectedLanguage: String?,
        context: AppCompatActivity
    ) {
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLayoutDirection(Locale(selectedLanguage))
        conf.locale = Locale(selectedLanguage)
        res.updateConfiguration(conf, dm)
    }


    fun closeKeyboard(view: View?) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    fun isEmailValid(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun visibleKeyboard(view: View): Boolean {
        var check = false
        view.viewTreeObserver.addOnGlobalLayoutListener {

            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - r.bottom

            check = keypadHeight > screenHeight * 0.15

        }
        return check
    }

}