package com.lustyflix.streamverse.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.lustyflix.streamverse.APIHolder
import com.lustyflix.streamverse.APIHolder.getApiProviderLangSettings
import com.lustyflix.streamverse.AllLanguagesName
import com.lustyflix.streamverse.R
import com.lustyflix.streamverse.databinding.FragmentSetupProviderLanguagesBinding
import com.lustyflix.streamverse.mvvm.normalSafeApiCall
import com.lustyflix.streamverse.utils.SubtitleHelper
import com.lustyflix.streamverse.utils.UIHelper.fixPaddingStatusbar

class SetupFragmentProviderLanguage : Fragment() {
    var binding: FragmentSetupProviderLanguagesBinding? = null

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val localBinding = FragmentSetupProviderLanguagesBinding.inflate(inflater, container, false)
        binding = localBinding
        return localBinding.root
        //return inflater.inflate(R.layout.fragment_setup_provider_languages, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fixPaddingStatusbar(binding?.setupRoot)

        normalSafeApiCall {
            val ctx = context ?: return@normalSafeApiCall

            val settingsManager = PreferenceManager.getDefaultSharedPreferences(ctx)

            val arrayAdapter =
                ArrayAdapter<String>(ctx, R.layout.sort_bottom_single_choice)

            val current = ctx.getApiProviderLangSettings()
            val langs = synchronized(APIHolder.apis) { APIHolder.apis.map { it.lang }.toSet()
                .sortedBy { SubtitleHelper.fromTwoLettersToLanguage(it) } + AllLanguagesName}

            val currentList =
                current.map { langs.indexOf(it) }.filter { it != -1 } // TODO LOOK INTO

            val languageNames = langs.map {
                if (it == AllLanguagesName) {
                    getString(R.string.all_languages_preference)
                } else {
                    val emoji = SubtitleHelper.getFlagFromIso(it)
                    val name = SubtitleHelper.fromTwoLettersToLanguage(it)
                    "$emoji $name"
                }
            }

            arrayAdapter.addAll(languageNames)
            binding?.apply {
            listview1.adapter = arrayAdapter
            listview1.choiceMode = AbsListView.CHOICE_MODE_MULTIPLE
            currentList.forEach {
                listview1.setItemChecked(it, true)
            }

            listview1.setOnItemClickListener { _, _, _, _ ->
                val currentLanguages = mutableListOf<String>()
                listview1.checkedItemPositions?.forEach { key, value ->
                    if (value) currentLanguages.add(langs[key])
                }
                settingsManager.edit().putStringSet(
                    ctx.getString(R.string.provider_lang_key),
                    currentLanguages.toSet()
                ).apply()
            }

            nextBtt.setOnClickListener {
                findNavController().navigate(R.id.navigation_setup_provider_languages_to_navigation_setup_media)
            }

            prevBtt.setOnClickListener {
                findNavController().popBackStack()
            } }
        }
    }


}