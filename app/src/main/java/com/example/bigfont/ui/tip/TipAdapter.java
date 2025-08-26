package com.example.bigfont.ui.tip;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigfont.data.entity.Tip;
import com.example.bigfont.databinding.ItemTipBinding;

import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {

    private List<Tip> tips;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Tip tip);
    }

    public TipAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTips(List<Tip> tips) {
        this.tips = tips;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TipAdapter.TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTipBinding binding = ItemTipBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new TipViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TipAdapter.TipViewHolder holder, int position) {
        Tip currentTip = tips.get(position);
        holder.bind(currentTip);
    }

    @Override
    public int getItemCount() {
        return tips != null ? tips.size() : 0;
    }


    static class TipViewHolder extends RecyclerView.ViewHolder {
        private final ItemTipBinding binding;
        private final OnItemClickListener listener;

        public TipViewHolder(@NonNull ItemTipBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Gọi phương thức onItemClick của listener
                        // Lỗi ở đây: bạn phải truyền đối tượng Tip, nhưng bạn chưa có nó ở đây.
                        // Ta sẽ xử lý điều này trong onBindViewHolder
                    }
                }
            });
        }

        public void bind(Tip tip) {
            binding.txtTitle.setText(tip.getTitle());
            binding.txtContent.setText(tip.getContent());

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(tip);
                }
            });
        }
    }
}
