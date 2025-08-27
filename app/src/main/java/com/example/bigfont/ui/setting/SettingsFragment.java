package com.example.bigfont.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigfont.R;
import com.example.bigfont.databinding.FragmentSettingsBinding;
import com.example.bigfont.ui.MainActivity;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LANGUAGE_KEY = "language_key";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateLanguageText();

        binding.llLanguage.setOnClickListener(v -> showLanguageDialog());
    }

    private void updateLanguageText() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedLangCode = prefs.getString(LANGUAGE_KEY, "vi");

        String languageName;
        switch (savedLangCode) {
            case "en":
                languageName = "English";
                break;
            case "fr":
                languageName = getString(R.string.english);
                break;
            case "de":
                languageName = getString(R.string.german);
                break;
            case "zh":
                languageName = getString(R.string.chinese);
                break;
            case "ja":
                languageName = getString(R.string.japanese);
                break;
            case "es":
                languageName = getString(R.string.spanish);
                break;
            case "ru":
                languageName = getString(R.string.russian);
                break;
            case "pt":
                languageName = getString(R.string.portuguese);
                break;
            case "ko":
                languageName = getString(R.string.korean);
                break;
            case "it":
                languageName = getString(R.string.italian);
                break;
            case "th":
                languageName = getString(R.string.thai);
                break;
            case "lo":
                languageName = getString(R.string.laos);
                break;
            case "ar":
                languageName = getString(R.string.arabic);
                break;
            case "vi":
            default:
                languageName = getString(R.string.vietnamese);
                break;
        }
        binding.tvLanguage.setText(languageName);
    }

    private void showLanguageDialog() {
        final String[] languages = {getString(R.string.vietnamese), getString(R.string.english), getString(R.string.french), getString(R.string.german), getString(R.string.chinese), getString(R.string.japanese), getString(R.string.spanish), getString(R.string.russian), getString(R.string.portuguese), getString(R.string.korean), getString(R.string.italian), getString(R.string.arabic), getString(R.string.thai), getString(R.string.laos)};
        final String[] languageCodes = {"vi", "en", "fr", "de", "zh", "ja", "es", "ru", "pt", "ko", "it", "ar","th","lo"};

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String currentLangCode = prefs.getString(LANGUAGE_KEY, "vi");
        int checkedItem = -1;

        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(currentLangCode)) {
                checkedItem = i;
                break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.choose_a_language);
        builder.setSingleChoiceItems(languages, checkedItem, (dialog, which) -> {
            String selectedLangCode = languageCodes[which];
            // Lưu ngôn ngữ đã chọn
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LANGUAGE_KEY, selectedLangCode);
            editor.apply();

            // Áp dụng ngôn ngữ mới và khởi động lại ứng dụng
            setLocale(selectedLangCode);
            dialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());

        // Khởi động lại Activity
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}