package com.example.bigfont.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfont.R;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.databinding.ItemCustomSizeBinding;
import com.example.bigfont.databinding.ItemCustomSizeBinding;

import java.util.List;

public class FontSizeAdapter extends RecyclerView.Adapter<FontSizeAdapter.FontSizeViewHolder> {

    private List<FontSize> fontSizes;
    private OnItemClickListener listener;

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onApplyClick(FontSize fontSize);
        void onDeleteClick(FontSize fontSize);
    }

    public FontSizeAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setFontSizes(List<FontSize> fontSizes) {
        this.fontSizes = fontSizes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FontSizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomSizeBinding binding = ItemCustomSizeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new FontSizeViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FontSizeViewHolder holder, int position) {
        FontSize currentFontSize = fontSizes.get(position);
        holder.bind(currentFontSize);
    }

    @Override
    public int getItemCount() {
        return fontSizes != null ? fontSizes.size() : 0;
    }

    static class FontSizeViewHolder extends RecyclerView.ViewHolder {
        private final ItemCustomSizeBinding binding;
        private final OnItemClickListener listener;

        public FontSizeViewHolder(@NonNull ItemCustomSizeBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(FontSize fontSize) {
            binding.txtCustomSizeValue.setText(String.valueOf(fontSize.getSizeInPercent()) + "%");

            // Thiết lập sự kiện click cho nút "Áp dụng"
            binding.btnApply.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onApplyClick(fontSize);
                }
            });

            // Thiết lập sự kiện click cho nút "Xóa"
            binding.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(fontSize);
                }
            });

            // Cập nhật text của nút "Áp dụng" nếu đây là cỡ chữ mặc định
            if (fontSize.isDefault()) {
                binding.btnApply.setText("HIỆN TẠI");
                binding.btnApply.setEnabled(false); // Vô hiệu hóa nút "Áp dụng" cho cỡ chữ hiện tại
                binding.btnDelete.setVisibility(View.GONE); // Ẩn nút "Xóa"
            } else {
                binding.btnApply.setText("ÁP DỤNG");
                binding.btnApply.setEnabled(true);
                binding.btnDelete.setVisibility(View.VISIBLE);
            }
        }

    }

}
