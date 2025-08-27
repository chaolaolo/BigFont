package com.example.bigfont.ui.fontweight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfont.R;
import com.example.bigfont.data.entity.FontWeight;
import com.example.bigfont.databinding.ItemCustomSizeBinding;

import java.util.List;

public class FontWeightAdapter extends RecyclerView.Adapter<FontWeightAdapter.FontWeightViewHolder> {

    public interface OnItemClickListener {
        void onApplyClick(FontWeight fontWeight);
    }

    private List<FontWeight> fontWeights;
    private final OnItemClickListener listener;
    private FontWeight currentAppliedWeight;

    public FontWeightAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setFontWeights(List<FontWeight> fontWeights) {
        this.fontWeights = fontWeights;
        notifyDataSetChanged();
    }

    public void setCurrentAppliedWeight(FontWeight currentAppliedWeight) {
        this.currentAppliedWeight = currentAppliedWeight;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FontWeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomSizeBinding binding = ItemCustomSizeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FontWeightViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FontWeightViewHolder holder, int position) {
        FontWeight fontWeight = fontWeights.get(position);
        holder.bind(fontWeight);
    }

    @Override
    public int getItemCount() {
        return fontWeights != null ? fontWeights.size() : 0;
    }

    class FontWeightViewHolder extends RecyclerView.ViewHolder {
        private ItemCustomSizeBinding binding;

        FontWeightViewHolder(ItemCustomSizeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(FontWeight fontWeight) {
            binding.txtCustomSizeValue.setText(fontWeight.getName());
            binding.txtSampleText.setText("ABC abc 123");
            binding.txtSampleText.setTypeface(null, fontWeight.getStyle());

            if (currentAppliedWeight != null && currentAppliedWeight.getName().equals(fontWeight.getName())) {
                binding.btnApply.setText(R.string.current);
                binding.btnApply.setEnabled(false);
                binding.btnApply.setBackgroundResource(R.drawable.warning_outline);
                binding.btnApply.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.colorWarning500));
            } else {
                binding.btnApply.setText(R.string.apply);
                binding.btnApply.setEnabled(true);
                binding.btnApply.setBackgroundResource(R.drawable.primary_outline);
                binding.btnApply.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.colorPrimaryDark));
            }
            binding.btnApply.setOnClickListener(v -> listener.onApplyClick(fontWeight));
            binding.btnDelete.setVisibility(View.GONE);

        }
    }
}
