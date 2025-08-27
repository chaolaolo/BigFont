package com.example.bigfont.ui.tip;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bigfont.R;
import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.databinding.FragmentDetailTipBinding;
import com.example.bigfont.databinding.FragmentTipsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        binding.btnApply.setOnClickListener(v -> {
            if (hasWriteSettingsPermission()) {
                String content = binding.txtContent.getText().toString();
                int suggestedFontSize = extractAndCalculateFontSize(content);

                if (suggestedFontSize > 0) {
                    applyNewFontSize(suggestedFontSize);
                    Toast.makeText(requireContext(), getString(R.string.applied_font_size) + suggestedFontSize + "%", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                } else {
                    showSnackbar("Không tìm thấy cỡ chữ gợi ý");
                }
            }
        });
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

    private int extractAndCalculateFontSize(String text) {
        List<Integer> fontSizes = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+)%");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            try {
                int size = Integer.parseInt(matcher.group(1));
                fontSizes.add(size);
            } catch (NumberFormatException e) {
                Log.e("DetailTipFragment", "Invalid number format: " + e.getMessage());
            }
        }

        if (fontSizes.isEmpty()) {
            return -1; // Trả về -1 nếu không tìm thấy cỡ chữ nào
        }

        int sum = 0;
        for (int size : fontSizes) {
            sum += size;
        }
        return Math.round((float) sum / fontSizes.size());
    }


    private void applyNewFontSize(int newFontSizeInPercent) {
        // Áp dụng cỡ chữ lên hệ thống
        Settings.System.putFloat(
                requireContext().getContentResolver(),
                Settings.System.FONT_SCALE,
                newFontSizeInPercent / 100f
        );

        AppDatabase.databaseWriteExecutor.execute(() -> {
            FontSizeDao fontSizeDao = AppDatabase.getDatabase(requireContext()).fontSizeDao();
            FontSize newFontSize = new FontSize(newFontSizeInPercent, true);

            // Cập nhật tất cả các cỡ chữ khác thành không phải mặc định
            List<FontSize> allFontSizes = fontSizeDao.getAllFontSizes();
            for (FontSize fs : allFontSizes) {
                fs.setDefault(false);
                fontSizeDao.update(fs);
            }

            // Chèn cỡ chữ mới nếu chưa tồn tại
            FontSize existingFontSize = fontSizeDao.getFontSizeByValue(newFontSizeInPercent);
            if (existingFontSize == null) {
                fontSizeDao.insert(newFontSize);
            } else {
                // Nếu đã tồn tại, chỉ cần cập nhật trạng thái
                existingFontSize.setDefault(true);
                fontSizeDao.update(existingFontSize);
            }

        });

    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}