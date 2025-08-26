package com.example.bigfont.ui.tip;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigfont.R;
import com.example.bigfont.databinding.FragmentDetailTipBinding;
import com.example.bigfont.databinding.FragmentTipsBinding;

public class DetailTipFragment extends Fragment {

    private FragmentDetailTipBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailTipBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("tipTitle");
            String content = bundle.getString("tipContent");

            // Hiển thị dữ liệu lên UI
            binding.txtTitle.setText(title);
            binding.txtContent.setText(content);
        }

        updateApplyButtonVisibility();
    }

    // Kiểm tra quyền và ẩn/hiện nút
    private void updateApplyButtonVisibility() {
        if (!hasWriteSettingsPermission()) {
            binding.btnApply.setVisibility(View.GONE);
        } else {
            binding.btnApply.setVisibility(View.VISIBLE);
        }
    }

    // Kiểm tra quyền
    private boolean hasWriteSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(requireContext());
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}