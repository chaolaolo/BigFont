package com.example.bigfont.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bigfont.R;
import com.example.bigfont.databinding.ActivityMainBinding;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LANGUAGE_KEY = "language_key";

    @Override
    protected void attachBaseContext(Context newBase) {
        // Lấy ngôn ngữ đã lưu từ SharedPreferences
        SharedPreferences prefs = newBase.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lang = prefs.getString(LANGUAGE_KEY, "vi"); // "vi" là ngôn ngữ mặc định

        // Tạo Locale mới và cập nhật Configuration
        Locale locale = new Locale(lang);
        Configuration config = newBase.getResources().getConfiguration();
        config.setLocale(locale);

        // Tạo Context mới với Configuration đã cập nhật
        Context context = newBase.createConfigurationContext(config);
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Di chuyển setSupportActionBar lên trên
        setSupportActionBar(binding.toolbar);

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.nav_home);
        topLevelDestinations.add(R.id.nav_font_weight);
        topLevelDestinations.add(R.id.nav_tips);
        topLevelDestinations.add(R.id.nav_settings);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            // setup first title in toolbar
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(destination.getLabel());
                }
            });
        }
    }
}