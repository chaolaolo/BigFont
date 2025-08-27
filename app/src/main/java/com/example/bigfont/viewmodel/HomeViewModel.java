package com.example.bigfont.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bigfont.R;
import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.database.AppDatabase;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.data.entity.Tip;
import com.example.bigfont.data.repository.FontSizeRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {
    private final FontSizeRepository repository;
    private final FontSizeDao fontSizeDao;

    private final MutableLiveData<List<FontSize>> _fontSizes = new MutableLiveData<>();
    public LiveData<List<FontSize>> fontSizes = _fontSizes;

    private final MutableLiveData<Boolean> _isDataReady = new MutableLiveData<>();
    public LiveData<Boolean> isDataReady = _isDataReady;

    private static final ExecutorService INIT_EXECUTOR = Executors.newSingleThreadExecutor();

    private final MutableLiveData<String> _snackbarMessage = new MutableLiveData<>();
    public LiveData<String> snackbarMessage = _snackbarMessage;

    public final MutableLiveData<Boolean> _uiUpdateNeeded = new MutableLiveData<>();
    public LiveData<Boolean> uiUpdateNeeded = _uiUpdateNeeded;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        fontSizeDao = AppDatabase.getDatabase(application).fontSizeDao();
        repository = new FontSizeRepository(fontSizeDao);

        _isDataReady.setValue(false);
        INIT_EXECUTOR.execute(() -> {
            // Bước 1: Kiểm tra và chèn dữ liệu ban đầu nếu database rỗng
            List<FontSize> currentSizes = fontSizeDao.getAllFontSizes();
            if (currentSizes == null || currentSizes.isEmpty()) {
                initializeData();
            }
            // Bước 2: Sau khi đảm bảo dữ liệu đã tồn tại, tiến hành tải danh sách
            loadFontSizes();
        });
    }

    private void initializeData() {
        fontSizeDao.insert(new FontSize(100, true));
        fontSizeDao.insert(new FontSize(110, false));
        fontSizeDao.insert(new FontSize(120, false));
        fontSizeDao.insert(new FontSize(130, false));
        fontSizeDao.insert(new FontSize(140, false));
        fontSizeDao.insert(new FontSize(150, false));
        fontSizeDao.insert(new FontSize(160, false));
        fontSizeDao.insert(new FontSize(170, false));
        fontSizeDao.insert(new FontSize(180, false));
        fontSizeDao.insert(new FontSize(190, false));
        fontSizeDao.insert(new FontSize(200, false));
        fontSizeDao.insert(new FontSize(210, false));
        fontSizeDao.insert(new FontSize(220, false));
        fontSizeDao.insert(new FontSize(230, false));
        fontSizeDao.insert(new FontSize(240, false));
        fontSizeDao.insert(new FontSize(250, false));
        fontSizeDao.insert(new FontSize(260, false));
        fontSizeDao.insert(new FontSize(270, false));
        fontSizeDao.insert(new FontSize(280, false));
        fontSizeDao.insert(new FontSize(290, false));
        fontSizeDao.insert(new FontSize(300, false));
        fontSizeDao.insert(new FontSize(310, false));
        fontSizeDao.insert(new FontSize(320, false));
        fontSizeDao.insert(new FontSize(330, false));
        fontSizeDao.insert(new FontSize(340, false));
        fontSizeDao.insert(new FontSize(350, false));
        fontSizeDao.insert(new FontSize(90, false));
        fontSizeDao.insert(new FontSize(80, false));
        fontSizeDao.insert(new FontSize(70, false));
        fontSizeDao.insert(new FontSize(60, false));
        fontSizeDao.insert(new FontSize(50, false));


        AppDatabase.getDatabase(getApplication()).tipDao().deleteAll();
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Cao Tuổi hoặc Suy Giảm Thị Lực", "Tăng kích thước phông chữ lên khoảng 125% đến 150% để dễ đọc hơn. Phông chữ lớn hơn giúp giảm căng thẳng mắt và làm cho văn bản dễ đọc hơn cho những người bị suy giảm thị lực."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Đọc dưới Ánh Sáng Mặt Trời", "Đặt kích thước phông chữ vào khoảng 110% đến 130% để cải thiện khả năng đọc dưới ánh sáng mặt trời. Điều này giảm độ chói và cải thiện độ tương phản."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Các Phiên Đọc Kéo Dài", "Tăng nhẹ kích thước phông chữ lên 110% đến 120% để giảm căng thẳng mắt trong quá trình đọc dài. Điều này giúp duy trì sự tập trung và thoải mái."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Trẻ với Thị Lực Tốt", "Duy trì kích thước phông chữ ở mức 100% đến 110% để hiển thị nhiều nội dung hơn trên màn hình. Điều này phù hợp cho những người dùng có thị lực tốt và muốn hiển thị nhiều nội dung hơn."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Đọc Vào Ban Đêm", "Điều chỉnh kích thước phông chữ lên khoảng 110% để đọc ban đêm thoải mái hơn. Điều này giúp giảm độ chói và căng thẳng mắt trong điều kiện ánh sáng yếu."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Suy Giảm Thị Lực Tạm Thời", "Tăng tạm thời kích thước phông chữ lên 150% hoặc hơn để dễ nhìn hơn sau phẫu thuật. Điều này giúp trong quá trình đọc trong thời gian phục hồi"));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Môi Trường Đa Nhiệm", "Đặt kích thước phông chữ ở mức 130% để dễ dàng nhìn thoáng qua trong môi trường bận rộn. Văn bản lớn hơn dễ đọc hơn khi nhìn nhanh."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho trẻ em hoặc Mục Đích Giáo Dục", "Đặt kích thước phông chữ ở mức 120% để hỗ trợ trẻ em đọc và học. Điều này giúp cải thiện sự tập trung và hiểu biết của trẻ."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Duyệt Nhanh", "Duy trì kích thước phông chữ ở mức 105% để duyệt nhanh và hiệu quả mà không mất nội dung. Điều này cân bằng giữa khả năng đọc và mật độ nội dung."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Trẻ Em đang Phát Triển Thị Lực", "Đặt kích thước phông chữ ở mức 115% để giúp trẻ em phát triển thị lực đọc dễ dàng hơn. Điều này hỗ trợ sự phát triển thị lực của họ và giảm căng thẳng mắt."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Thanh Thiếu Niên Sử Dụng Màn Hình Thường Xuyên", "Điều chỉnh kích thước phông chữ lên 110% cho thanh thiếu niên để giảm căng thẳng mắt do sử dụng màn hình thường xuyên. Điều này giúp giảm thiểu tác động của thời gian sử dụng màn hình kéo dài."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Lớn Với Thị Lực Bình Thường", "Duy trì kích thước phông chữ ở mức 100% cho người lớn có thị lực bình thường để tối đa hóa không gian màn hình. Điều này lý tưởng cho những người dùng muốn có nhiều thông tin hơn trên màn hình."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Già với Suy Giảm Thị Lực Nghiêm Trọng", "Tăng kích thước phông chữ lên 160% hoặc hơn cho người già bị suy giảm thị lực nghiêm trọng. Điều này làm cho văn bản dễ đọc hơn nhiều."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Các Nhiệm Vụ Đọc Chuyên Nghiệp", "Đặt kích thước phông chữ ở mức 110% để cân bằng khả năng đọc và không gian màn hình cho các nhiệm vụ chuyên nghiệp. Điều này giúp duy trì hiệu quả và thoải mái."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Dùng Loạn Thị", "Tăng kích thước phông chữ lên 130% cho người dùng bị loạn thị để giảm mờ. Văn bản lớn hơn ít có khả năng bị biến dạng hơn."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Dùng Trong Điều Kiện Ánh Sáng Yếu", "Đặt kích thước phông chữ ở mức 120% để cải thiện khả năng đọc trong điều kiện ánh sáng yếu. Điều này giúp giảm căng thẳng mắt trong môi trường tối."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Dùng Đeo Kính", "Điều chỉnh kích thước phông chữ lên 110% để giúp người dùng đeo kính đọc dễ dàng hơn. Điều này đảm bảo rằng văn bản dễ nhìn thấy."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Dùng Đeo Kính Áp Tròng", "Duy trì kích thước phông chữ ở mức 100% cho người dùng đeo kính áp tròng để tránh căng thẳng mắt. Điều này ngăn ngừa sự khó chịu do sử dụng kéo dài."));
        AppDatabase.getDatabase(getApplication()).tipDao().insert(new Tip("Dành cho Người Dùng với Chứng Khó Đọc", "Tăng kích thước phông chữ lên 140% cho người dùng bị chứng khó đọc. Văn bản lớn hơn và rõ ràng hơn có thể giúp cải thiện tốc độ và sự hiểu biết khi đọc."));

    }

    private void loadFontSizes() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            List<FontSize> fontSizes = repository.getAllFontSizes();
            _fontSizes.postValue(fontSizes);
            _isDataReady.postValue(true);
        });
    }

    public void insert(FontSize fontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            FontSize existingFontSize = fontSizeDao.getFontSizeByValue(fontSize.getSizeInPercent());
            if (existingFontSize == null) {
                repository.insert(fontSize);
            }
            loadFontSizes();
        });
    }

    public void insertAndSave(FontSize fontSize, int newFontScale) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Thực hiện chèn dữ liệu
            FontSize existingFontSize = fontSizeDao.getFontSizeByValue(fontSize.getSizeInPercent());
            if (existingFontSize == null) {
                repository.insert(fontSize);
            }

            // Tải lại danh sách sau khi chèn
            loadFontSizes();

            // Gửi thông điệp thông báo cho Fragment, bao gồm cả giá trị mới
            _snackbarMessage.postValue("save_font_size:" + newFontScale);
            _uiUpdateNeeded.postValue(true);
        });
    }


    public void deleteFontSize(FontSize fontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            repository.delete(fontSize);
            loadFontSizes();
            _uiUpdateNeeded.postValue(true);
        });
    }


    public void applyFontSize(FontSize newFontSize) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Cập nhật cỡ chữ hiện tại thành không phải mặc định
            FontSize currentDefault = fontSizeDao.getDefaultFontSize();
            if (currentDefault != null) {
                currentDefault.setDefault(false);
                repository.update(currentDefault);
            }
            // Cập nhật cỡ chữ mới thành mặc định
            newFontSize.setDefault(true);
            repository.update(newFontSize);
            loadFontSizes(); // Tải lại danh sách để cập nhật UI
            _snackbarMessage.postValue("applied_font_size:" + newFontSize.getSizeInPercent());
            _uiUpdateNeeded.postValue(true);
        });
    }

    public void resetToDefault() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // Đặt lại tất cả các mục về trạng thái ban đầu của chúng
                FontSize default100Percent = fontSizeDao.getFontSizeByValue(100);
                if (default100Percent != null) {
                    default100Percent.setDefault(true);
                    repository.update(default100Percent);
                }

                // Cập nhật lại danh sách để phản ánh sự thay đổi
                loadFontSizes();
                _snackbarMessage.postValue("reset_to_default");
                _uiUpdateNeeded.postValue(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void clearSnackbarMessage() {
        _snackbarMessage.postValue(null);
    }

    public void resetUiUpdateNeeded() {
        _uiUpdateNeeded.postValue(false);
    }
}
