package com.example.bigfont.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigfont.R;
import com.example.bigfont.databinding.FragmentSettingsBinding;
import com.example.bigfont.ui.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LANGUAGE_KEY = "language_key";
    private static final String THEME_KEY = "theme_key";
    private static final String SHOW_SNACKBAR_KEY = "show_snackbar";
    private static final String LAST_LANGUAGE_KEY = "last_language";
//    private static final String LANGUAGE_NAME_KEY = "language_name_key";


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
        checkAndShowSnackbar();

        binding.llLanguage.setOnClickListener(v -> showLanguageDialog());

        //theme
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDarkTheme = prefs.getBoolean(THEME_KEY, false);
        binding.swTheme.setChecked(isDarkTheme);
        binding.swTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(THEME_KEY, isChecked).apply();

            requireActivity().recreate();
        });
    }

    private void checkAndShowSnackbar() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean showSnackbar = prefs.getBoolean(SHOW_SNACKBAR_KEY, false);

        if (showSnackbar) {
            String selectedLangCode = prefs.getString(LAST_LANGUAGE_KEY, "vi");
            String selectedLangName = getLanguageNameFromCode(selectedLangCode);
            showSnackbar(getString(R.string.language_applied) + selectedLangName, true);

            // Reset trạng thái sau khi hiển thị
            prefs.edit().putBoolean(SHOW_SNACKBAR_KEY, false).apply();
            prefs.edit().remove(LAST_LANGUAGE_KEY).apply();
        }
    }

    private String getLanguageNameFromCode(String langCode) {
        switch (langCode) {
            case "en":
                return getString(R.string.english);
            case "fr":
                return getString(R.string.french);
            case "de":
                return getString(R.string.german);
            case "zh":
                return getString(R.string.chinese);
            case "ja":
                return getString(R.string.japanese);
            case "es":
                return getString(R.string.spanish);
            case "ru":
                return getString(R.string.russian);
            case "pt":
                return getString(R.string.portuguese);
            case "ko":
                return getString(R.string.korean);
            case "it":
                return getString(R.string.italian);
            case "th":
                return getString(R.string.thai);
            case "lo":
                return getString(R.string.laos);
            case "ar":
                return getString(R.string.arabic);
            case "vi":
            default:
                return getString(R.string.vietnamese);
        }
    }

    private void updateLanguageText() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedLangCode = prefs.getString(LANGUAGE_KEY, "vi");

        String languageName = getLanguageNameFromCode(savedLangCode);
        binding.tvLanguage.setText(languageName);
    }

    private void showLanguageDialog() {
        final String[] languages = {getString(R.string.vietnamese), getString(R.string.english), getString(R.string.french), getString(R.string.german), getString(R.string.chinese), getString(R.string.japanese), getString(R.string.spanish), getString(R.string.russian), getString(R.string.portuguese), getString(R.string.korean), getString(R.string.italian), getString(R.string.arabic), getString(R.string.thai), getString(R.string.laos)};
        final String[] languageCodes = {"vi", "en", "fr", "de", "zh", "ja", "es", "ru", "pt", "ko", "it", "ar", "th", "lo"};

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

            // Áp dụng ngôn ngữ mới và khởi động lại ứng dụng
            setLocale(selectedLangCode);
            dialog.dismiss();
        });

        builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setLocale(String lang) {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(LANGUAGE_KEY, lang);
        editor.putString(LAST_LANGUAGE_KEY, lang);
        editor.putBoolean(SHOW_SNACKBAR_KEY, true);
        editor.apply();


        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireContext().getResources().updateConfiguration(config, requireContext().getResources().getDisplayMetrics());

        requireActivity().recreate();
    }

    private void showSnackbar(String message, boolean showDismissButton) {
        Snackbar snackbar = Snackbar.make(
                requireView(),
                message,
                Snackbar.LENGTH_SHORT);

        if (showDismissButton) {
            snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
        }
        TypedValue typedValue = new TypedValue();
        requireContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnErrorContainer, typedValue, true);
        int colorOnErrorContainer = typedValue.data;
        requireContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnSurfaceVariant, typedValue, true);
        int colorOnSurfaceVariant = typedValue.data;
        requireContext().getTheme().resolveAttribute(com.google.android.material.R.attr.colorSurface, typedValue, true);
        int colorSurface = typedValue.data;
        snackbar.setActionTextColor(colorOnErrorContainer);
        snackbar.setBackgroundTint(colorOnSurfaceVariant);
        snackbar.setTextColor(colorSurface);
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}