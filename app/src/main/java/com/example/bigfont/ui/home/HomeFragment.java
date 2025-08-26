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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bigfont.R;
import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.databinding.FragmentHomeBinding;
import com.example.bigfont.viewmodel.HomeViewModel;

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

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        fontSizeAdapter = new FontSizeAdapter(this);
        binding.rvListCustomSize.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListCustomSize.setAdapter(fontSizeAdapter);

        homeViewModel.fontSizes.observe(getViewLifecycleOwner(), fontSizes -> {
            fontSizeAdapter.setFontSizes(fontSizes);
        });

        binding.btnSave.setOnClickListener(v -> {
            if (hasWriteSettingsPermission()) {
                // Thực hiện lưu cỡ chữ tùy chỉnh
                // homeViewModel.saveCustomFontSize(binding.sbCustomSize.getProgress());
                Toast.makeText(getContext(), "Lưu cỡ chữ tùy chỉnh", Toast.LENGTH_SHORT).show();
            } else {
                showPermissionRequiredDialog();
            }
        });

        binding.btnResetToDefault.setOnClickListener(v -> {
            if (hasWriteSettingsPermission()) {
                // Thực hiện đặt lại cỡ chữ mặc định
                // homeViewModel.resetToDefault();
                Toast.makeText(getContext(), "Đặt lại cỡ chữ mặc định", Toast.LENGTH_SHORT).show();
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
                .setTitle("Yêu cầu quyền")
                .setMessage("Bạn cần cấp quyền để thay đổi cỡ chữ hệ thống. Vui lòng cấp quyền trong cài đặt.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Chuyển người dùng đến màn hình cài đặt
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Không có quyền, không thể thực hiện thao tác.", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hasWriteSettingsPermission()) {
            showPermissionRequiredDialog();
        }
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
//            homeViewModel.applyFontSize(fontSize);
            Toast.makeText(getContext(), "Đã áp dụng cỡ chữ " + fontSize.getSizeInPercent() + "%", Toast.LENGTH_SHORT).show();
        } else {
            showPermissionRequiredDialog();
        }
    }

    @Override
    public void onDeleteClick(FontSize fontSize) {
        if (hasWriteSettingsPermission()) {
            // Xóa cỡ chữ khỏi database
//            homeViewModel.deleteFontSize(fontSize);
            Toast.makeText(getContext(), "Đã xóa cỡ chữ " + fontSize.getSizeInPercent() + "%", Toast.LENGTH_SHORT).show();
        } else {
            showPermissionRequiredDialog();
        }
    }
}