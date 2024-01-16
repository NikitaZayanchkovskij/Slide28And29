package com.example.slide28and29

import android.content.SharedPreferences


const val PREF_LABEL_TEXT_VALUE = "PREF_LABEL_TEXT_VALUE"


/** С помощью этого класса сохраняем значение TextLabel как при выходе из приложения, так и при
 * работе в фоновом режиме, а также при переключениями просто между двух экранов, когда нажимаем
 * CANCEL или UPDATE.
 */
object LabelPreferenceManager {
    var pref: SharedPreferences? = null


    fun savePref(key: String, value: String){
        pref?.edit()?.putString(key, value)?.apply()
    }


    fun getPref(): String {
        return pref?.getString(PREF_LABEL_TEXT_VALUE, "0") ?: "0"
    }
}