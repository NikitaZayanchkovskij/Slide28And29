package com.example.slide28and29


import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.slide28and29.databinding.ActivityMainBinding


/** Это время час (60 минут) в миллисекундах. Сделал это задание используя CountDownTimer.
 * Если пользователь держал приложение в свёрнутом виде дольше 60 минут - то перезапускаю таймер и
 * пользуюсь переменной timeAfter60Minutes вместо timeBefore60Minutes.
 * Chronometer тоже буду использовать, в задании со слайда №31.
 * Просто решил здесь сделать таким способом.
 */
const val COUNT_DOWN_TIME = 3_600_000L


class MainActivity : AppCompatActivity(), MyCustomView.IButtonPressedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var labelTextValue: String
    private var applicationWasInBackground = false
    private lateinit var timer: CountDownTimer
    private var timeBefore60Minutes: Long = 0
    private var timeAfter60Minutes: Long = 0
    private var timeIsLongerThan60Minutes = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* Инициализируем SharedPreferences. */
        LabelPreferenceManager.pref = getSharedPreferences(PREF_LABEL_TEXT_VALUE, MODE_PRIVATE)

        binding.myCustomView.listener = this
    }


    /** Т.к. пункты задания 4 и 3 взаимозаменяют друг друга т.к. оба должны быть реализованы в
     * функции onStart() - оставляю здесь только пункт номер 4.
     */
    override fun onStart() {
        super.onStart()

        /* Когда приложение разворачивается (выходит из фонового режима работы) получаем время и
         * проверяем, что нужно сделать с label.
         */
        receiveLabelData()

        if (applicationWasInBackground == true) {

            if (timeIsLongerThan60Minutes == true) {
                reduceLabel(timeAfter60Minutes)

            } else {
                reduceLabel(timeBefore60Minutes)
            }
        }
    }


    /** Эта функция уменьшает label на нужное значение и сохраняет новое значение в SharedPreference.
     */
    private fun reduceLabel(time: Long) {

        /* Здесь делим на 1000 чтобы получить время в обычных секундах, а не в милисекнудах. */
        val sumToMinus = 2 * (time / 1000).toInt()

        labelTextValue = (labelTextValue.toInt() - sumToMinus).toString()
        LabelPreferenceManager.savePref(PREF_LABEL_TEXT_VALUE, labelTextValue)

        binding.myCustomView.labelText = labelTextValue
        timer.cancel()
        applicationWasInBackground = false
    }


    /** Т.к. пункты задания 4 и 2 взаимозаменяют друг друга т.к. оба должны быть реализованы в
     * функции onStop() - оставляю здесь только пункт номер 4.
     */
    override fun onStop() {
        super.onStop()

        startTimer()
        applicationWasInBackground = true
    }


    /** Функция запускает таймер и каждую секунду сохраняет сколько прошло времени в фоновом режиме
     * в переменную minutesOutsideTheApp.
     */
    private fun startTimer() {
        timer = object: CountDownTimer(COUNT_DOWN_TIME, 1000) {

            override fun onTick(time: Long) {
                timeBefore60Minutes = COUNT_DOWN_TIME - time
              //Log.d("MyLog", "minutesOutsideTheApp: $minutesOutsideTheApp")
              //Log.d("MyLog", "time: $time")
            }

            override fun onFinish() {
                timeAfter60Minutes = timeAfter60Minutes + timeBefore60Minutes
                timeIsLongerThan60Minutes = true
              //Log.d("MyLog", "timeAfter60Minutes: $timeAfter60Minutes")
                startTimer()
            }
        }.start()
    }


    /** Функция запускается каждый раз при открытии приложения после полного выхода или после работы
     * в фоновом режиме и проверяет какие данные сохранены в SharedPreferences, и отображает
     * соответствующую информацию в TextLabel.
     */
    private fun receiveLabelData() {
        labelTextValue = LabelPreferenceManager.getPref()
        binding.myCustomView.labelText = labelTextValue
    }


    /** Слушатель нажатий кастомного View.
     */
    override fun tapPressed() {
        val intent = Intent(this, UpdateCounter::class.java)
        startActivity(intent)
    }


}