package com.example.bigfont.ui.fontweight;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigfont.R;
import com.example.bigfont.data.entity.FontWeight;
import com.example.bigfont.databinding.FragmentFontWeightBinding;
import com.example.bigfont.viewmodel.FontWeightViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FontWeightFragment extends Fragment implements FontWeightAdapter.OnItemClickListener {

    private FragmentFontWeightBinding binding;
    private FontWeightViewModel fontWeightViewModel;
    private FontWeightAdapter fontWeightAdapter;
    private FontWeight currentFontWeight;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFontWeightBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fontWeightViewModel = new ViewModelProvider(this).get(FontWeightViewModel.class);

        fontWeightAdapter = new FontWeightAdapter(this);
        binding.rvListCustomSize.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListCustomSize.setAdapter(fontWeightAdapter);

        fontWeightViewModel.fontWeights.observe(getViewLifecycleOwner(), weights -> {
            fontWeightAdapter.setFontWeights(weights);
            updateCurrentFontWeightUI();
        });
        fontWeightViewModel.snackbarMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                if (message.startsWith("applied_font_weight:")) {
                    String name = message.substring("applied_font_weight:".length());
                    showSnackbar(getString(R.string.applied_font_weight, name));
                } else if (message.startsWith("reset_to_default:")) {
                    showSnackbar(getString(R.string.reset_to_default_font_weight));
                }
//                fontWeightViewModel.snackbarMessage.setValue(null); // Reset giá trị
            }
        });

        updateCurrentFontWeightUI();

        // Xử lý sự kiện click
        binding.btnSave.setOnClickListener(v -> {
            // Không có chức năng lưu tùy chỉnh như cỡ chữ. Nút này sẽ không có chức năng hoặc bạn có thể thay đổi nó.
            // Có thể dùng để lưu một kiểu chữ đang tùy chỉnh bằng SeekBar.
            // Tuy nhiên, vì font weight có các giá trị rời rạc, không nên dùng SeekBar
            showSnackbar("Chức năng lưu không khả dụng cho độ dày phông chữ.");
        });

        binding.btnResetToDefault.setOnClickListener(v -> {
            fontWeightViewModel.resetToDefault();
        });


    }


    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(
                requireView(),
                message,
                Snackbar.LENGTH_SHORT
        );
        snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
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

    private void updateCurrentFontWeightUI() {
        // Lấy Font Weight hiện tại từ ViewModel
        currentFontWeight = fontWeightViewModel.getDefaultFontWeight();

        // Cập nhật UI
        if (currentFontWeight != null) {
            binding.txtCustomSizeValue.setText(currentFontWeight.getName());
            binding.contentTextView1.setTypeface(null, currentFontWeight.getStyle());
            binding.contentTextView2.setTypeface(null, currentFontWeight.getStyle());
            binding.titleTextView.setTypeface(null, currentFontWeight.getStyle());

            fontWeightAdapter.setCurrentAppliedWeight(currentFontWeight);
        }
    }

    @Override
    public void onApplyClick(FontWeight fontWeight) {
        fontWeightViewModel.applyFontWeight(fontWeight);
        // Cập nhật UI ngay lập tức
        updateCurrentFontWeightUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}