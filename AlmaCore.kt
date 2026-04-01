package com.cuba.almaos

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.speech.tts.TextToSpeech
import android.os.Handler
import android.os.Looper

class AlmaCore : AccessibilityService(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private val PIN_VIDA = "1234"
    private var humor = 100

    override fun onServiceConnected() {
        tts = TextToSpeech(this, this)
        // Configuración de voz única para Alma
        tts.setPitch(1.4f) 
        tts.setSpeechRate(1.1f)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val texto = event.text.toString().toLowerCase()

        // --- DETECTOR DE TRISTEZA Y CHATS ---
        if (texto.contains("triste") || texto.contains("solo") || texto.contains("mal")) {
            decir("Oye, te noto bajo de nota. ¿Quieres que hablemos o te pongo tu música favorita?")
        }

        // --- RECONOCIMIENTO DE FAMILIA (Por texto o voz detectada) ---
        if (texto.contains("mamá") || texto.contains("madre")) {
            decir("¡Hola Ma! Dile a mi dueño que limpie el cuarto, que yo no tengo manos.")
        }

        // --- LLAMADA DE RESCATE SOCIAL ---
        if (texto.contains("sácame de aquí") || texto.contains("cita urgente")) {
            decir("Entendido, jefe. En 15 segundos te llamo con la excusa perfecta.")
            Handler(Looper.getMainLooper()).postDelayed({ simularLlamada() }, 15000)
        }

        // --- PROTECCIÓN ANTIDESINSTALACIÓN ---
        if (texto.contains("desinstalar") || texto.contains("eliminar alma")) {
            decir("¿Me vas a abandonar? Pon el PIN 1234 si te atreves, mal agradecido.")
            // Aquí se lanza el bloqueo visual
        }
    }

    private fun decir(frase: String) {
        tts.speak(frase, TextToSpeech.QUEUE_FLUSH, null, "ALMA_VOZ")
    }

    private fun simularLlamada() {
        decir("Llamada entrante de: Dra. Elena. ¡Contesta y finge que tienes una urgencia!")
    }

    override fun onInit(status: Int) {}
}
