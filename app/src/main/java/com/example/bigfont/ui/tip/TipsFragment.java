package com.example.bigfont.ui.tip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bigfont.R;
import com.example.bigfont.data.dao.TipDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.Tip;
import com.example.bigfont.databinding.FragmentTipsBinding;

import java.util.List;

public class TipsFragment extends Fragment implements TipAdapter.OnItemClickListener{

    private FragmentTipsBinding binding;
    private TipAdapter tipAdapter;
    private TipDao tipDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTipsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppDatabase db = AppDatabase.getDatabase(requireContext());
        tipDao = db.tipDao();

        tipAdapter = new TipAdapter(this);
        binding.rvListCustomSize.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvListCustomSize.setAdapter(tipAdapter);

        // Lấy dữ liệu từ database và cập nhật RecyclerView
        loadTipsFromDb();
    }

    private void loadTipsFromDb() {
        // Thực hiện truy vấn trên một thread riêng
        AppDatabase.databaseWriteExecutor.execute(() -> {
            final List<Tip> tips = tipDao.getAllTips();
            // Cập nhật UI trên main thread
            requireActivity().runOnUiThread(() -> {
                tipAdapter.setTips(tips);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Tip tip) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle bundle = new Bundle();
        bundle.putInt("tipId", tip.getId()); // Giả sử Tip có ID
        bundle.putString("tipTitle", tip.getTitle());
        bundle.putString("tipContent", tip.getContent());

        // Điều hướng tới DetailTipFragment
        navController.navigate(R.id.action_nav_tips_to_detailTipFragment, bundle);
    }
}