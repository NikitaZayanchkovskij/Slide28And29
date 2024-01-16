package com.example.slide28and29

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.slide28and29.databinding.ActivityUpdateCounterBinding


class UpdateCounter : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateCounterBinding
    private lateinit var labelTextValue: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveLabelData()
        buttons()
    }


    /** При открытии этого экрана получаем значение label из SharedPreference, отображаем на экране
     * и закругляем углы CardView на нужное значение.
     */
    private fun receiveLabelData() {
        labelTextValue = LabelPreferenceManager.getPref()
        binding.tvLabel.text = labelTextValue
        binding.cardViewMain.radius = labelTextValue.toFloat()
    }


    /** Что происходит при нажатии на кнопки Отмена и Обновить.
     */
    private fun buttons() {

        /* Кнопка Отмена. */
        binding.btCancel.setOnClickListener {
            finish()
        }

        /* Кнопка Обновить.
         * При нажатии на неё увеличиваем label на 10 и сохраняем значение в SharedPreference.
         */
        binding.btUpdate.setOnClickListener {
            val increasedLabel = labelTextValue.toInt() + 10
            LabelPreferenceManager.savePref(PREF_LABEL_TEXT_VALUE, increasedLabel.toString())
            finish()
        }
    }


}