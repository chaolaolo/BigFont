package com.example.bigfont.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.bigfont.R;
import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.databinding.FragmentHomeBinding;
import com.example.bigfont.viewmodel.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment implements FontSizeAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    private FontSizeAdapter fontSizeAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        fontSizeAdapter = new FontSizeAdapter(this);
        binding.rvListCustomSize.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListCustomSize.setAdapter(fontSizeAdapter);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.contentScrollView.setVisibility(View.GONE);
        homeViewModel.isDataReady.observe(getViewLifecycleOwner(), isReady -> {
            if (isReady) {
                // Khi dữ liệu đã sẵn sàng, ẩn ProgressBar và hiển thị nội dung chính
                binding.progressBar.setVisibility(View.GONE);
                binding.contentScrollView.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.fontSizes.observe(getViewLifecycleOwner(), fontSizes -> {
            fontSizeAdapter.setFontSizes(fontSizes);
            updateCurrentFontScaleUI();
        });

        homeViewModel.snackbarMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                if (message.startsWith("applied_font_size:")) {
                    String[] parts = message.split(":");
                    if (parts.length > 1) {
                        int size = Integer.parseInt(parts[1]);
                        showSnackbar(getString(R.string.applied_font_size) + size + "%", true);
                    }
                } else if (message.equals("reset_to_default")) {
                    showSnackbar(getString(R.string.reset_to_default) + "100%", true);
                } else if (message.startsWith("save_font_size")) {
                    String[] parts = message.split(":");
                    if(parts.length>1){
                        int size = Integer.parseInt(parts[1]);
                        showSnackbar(getString(R.string.save_font_size) + size + "%", true);
                    }
                }
//                homeViewModel.clearSnackbarMessage();
            }
        });

        binding.sbCustomSize.setMax(350 - 50);
        updateCurrentFontScaleUI();

        binding.sbCustomSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int fontSizeInPercent = 50 + progress;

                binding.txtCustomSizeValue.setText(fontSizeInPercent + "%");
                float newSizeSp = 14f * (fontSizeInPercent / 100f);
                binding.contentTextView1.setTextSize(newSizeSp);
                binding.contentTextView2.setTextSize(newSizeSp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.btnSave.setOnClickListener(v -> {
            if (hasWriteSettingsPermission()) {
                // Thực hiện lưu cỡ chữ tùy chỉnh
                int fontSizeInPercent = 50 + binding.sbCustomSize.getProgress();
                homeViewModel.insertAndSave(new FontSize(fontSizeInPercent, false), fontSizeInPercent);
                Settings.System.putFloat(
                        requireContext().getContentResolver(),
                        Settings.System.FONT_SCALE,
                        fontSizeInPercent / 100f
                );
                updateCurrentFontScaleUI();
            } else {
                showPermissionRequiredDialog();
            }
        });

        binding.btnResetToDefault.setOnClickListener(v -> {
            if (hasWriteSettingsPermission()) {
                homeViewModel.resetToDefault();
                Settings.System.putFloat(requireContext().getContentResolver(), Settings.System.FONT_SCALE, 1.0f);
                updateCurrentFontScaleUI();
            } else {
                showPermissionRequiredDialog();
            }
        });

    }

    private boolean hasWriteSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(requireContext());
        }
        return true;
    }

    private void showPermissionRequiredDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.require_permission)
                .setMessage(R.string.u_need_to_accept_permission)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Chuyển người dùng đến màn hình cài đặt
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                    showSnackbar(getString(R.string.dont_have_permission), true);
                })
                .show();
    }

    private void updateCurrentFontScaleUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            float fontScale = Settings.System.getFloat(
                    requireContext().getContentResolver(),
                    Settings.System.FONT_SCALE,
                    1.0f
            );
            int fontScaleInPercent = (int) (fontScale * 100);
            // Cập nhật SeekBar và TextView
            binding.sbCustomSize.setProgress(fontScaleInPercent - 50);
            binding.txtCustomSizeValue.setText(fontScaleInPercent + "%");

            // Cập nhật cỡ chữ mẫu
            float newSizeSp = 14f * (fontScaleInPercent / 100f);
            binding.contentTextView1.setTextSize(newSizeSp);
            binding.contentTextView2.setTextSize(newSizeSp);

            // Cập nhật Adapter để hiển thị trạng thái "HIỆN TẠI"
            fontSizeAdapter.setCurrentFontScale(fontScaleInPercent);
        }
    }

    private void showSnackbar(String message, boolean showDismissButton) {
        Snackbar snackbar = Snackbar.make(
                requireView(),
                message,
                Snackbar.LENGTH_SHORT);

        if (showDismissButton) {
            snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
        }
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasWriteSettingsPermission()) {
            showPermissionRequiredDialog();
        }
        updateCurrentFontScaleUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onApplyClick(FontSize fontSize) {
        if (hasWriteSettingsPermission()) {
            // Cập nhật cỡ chữ hiện tại trong database và hệ thống
            homeViewModel.applyFontSize(fontSize);
            Settings.System.putFloat(
                    requireContext().getContentResolver(),
                    Settings.System.FONT_SCALE,
                    fontSize.getSizeInPercent() / 100f
            );
//            showSnackbar(getString(R.string.applied_font_size) + fontSize.getSizeInPercent() + "%", true);
            updateCurrentFontScaleUI();
        } else {
            showPermissionRequiredDialog();
        }
    }

    @Override
    public void onDeleteClick(FontSize fontSize) {
        if (hasWriteSettingsPermission()) {
            // Xóa cỡ chữ khỏi database
            homeViewModel.deleteFontSize(fontSize);
            showSnackbar(getString(R.string.deleted_font_size) + fontSize.getSizeInPercent() + "%", true);
        } else {
            showPermissionRequiredDialog();
        }
    }
}