package com.moreai.hebrew.ui.screens

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import com.moreai.hebrew.data.DataRepository
import com.moreai.hebrew.data.model.AppSettings
import com.moreai.hebrew.data.model.Category
import com.moreai.hebrew.data.model.HebrewEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataRepository(application)

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _currentEntry = MutableStateFlow<HebrewEntry?>(null)
    val currentEntry: StateFlow<HebrewEntry?> = _currentEntry.asStateFlow()

    private val _bottomSheetVisible = MutableStateFlow(false)
    val bottomSheetVisible: StateFlow<Boolean> = _bottomSheetVisible.asStateFlow()

    private val _ttsReady = MutableStateFlow(false)
    val ttsReady: StateFlow<Boolean> = _ttsReady.asStateFlow()

    private val tts = TextToSpeech(application) { status ->
        if (status == TextToSpeech.SUCCESS) {
            val hebrewLocale = Locale("he", "IL")
            val result = tts.setLanguage(hebrewLocale)
            _ttsReady.value = result != TextToSpeech.LANG_MISSING_DATA
                    && result != TextToSpeech.LANG_NOT_SUPPORTED
            tts.setSpeechRate(0.8f)
        }
    }

    init {
        pickRandom()
    }

    fun pickRandom() {
        val s = _settings.value
        _currentEntry.value = repository.getRandomEntry(s.selectedCategory, s.onlyWords)
    }

    fun speakCurrent() {
        val entry = _currentEntry.value ?: return
        if (!_ttsReady.value) return
        tts.speak(entry.hebrew, TextToSpeech.QUEUE_FLUSH, null, entry.id)
    }

    fun toggleNikkud() {
        _settings.update { it.copy(showNikkud = !it.showNikkud) }
    }

    fun toggleOnlyWords() {
        _settings.update { it.copy(onlyWords = !it.onlyWords) }
        pickRandom()
    }

    fun selectCategory(category: Category) {
        _settings.update { it.copy(selectedCategory = category) }
        pickRandom()
    }

    fun toggleBottomSheet() {
        _bottomSheetVisible.update { !it }
    }

    fun hideBottomSheet() {
        _bottomSheetVisible.value = false
    }

    override fun onCleared() {
        tts.stop()
        tts.shutdown()
        super.onCleared()
    }
}
