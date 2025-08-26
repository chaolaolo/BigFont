package com.example.bigfont.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bigfont.data.dao.FontSizeDao;
import com.example.bigfont.data.dao.TipDao;
import com.example.bigfont.data.entity.FontSize;
import com.example.bigfont.data.entity.Tip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FontSize.class, Tip.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FontSizeDao fontSizeDao();

    public abstract TipDao tipDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "font_size_database")
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                FontSizeDao fontSizeDao = INSTANCE.fontSizeDao();
                TipDao tipDao = INSTANCE.tipDao(); // Thêm dữ liệu tips ban đầu


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


                tipDao.insert(new Tip("Dành cho Người Cao Tuổi hoặc Suy Giảm Thị Lực", "Tăng kích thước phông chữ lên khoảng 125% đến 150% để dễ đọc hơn. Phông chữ lớn hơn giúp giảm căng thẳng mắt và làm cho văn bản dễ đọc hơn cho những người bị suy giảm thị lực."));
                tipDao.insert(new Tip("Dành cho Đọc dưới Ánh Sáng Mặt Trời", "Đặt kích thước phông chữ vào khoảng 110% đến 130% để cải thiện khả năng đọc dưới ánh sáng mặt trời. Điều này giảm độ chói và cải thiện độ tương phản."));
                tipDao.insert(new Tip("Dành cho Các Phiên Đọc Kéo Dài", "Tăng nhẹ kích thước phông chữ lên 110% đến 120% để giảm căng thẳng mắt trong quá trình đọc dài. Điều này giúp duy trì sự tập trung và thoải mái."));
                tipDao.insert(new Tip("Dành cho Người Trẻ với Thị Lực Tốt", "Duy trì kích thước phông chữ ở mức 100% đến 110% để hiển thị nhiều nội dung hơn trên màn hình. Điều này phù hợp cho những người dùng có thị lực tốt và muốn hiển thị nhiều nội dung hơn."));
                tipDao.insert(new Tip("Dành cho Đọc Vào Ban Đêm", "Điều chỉnh kích thước phông chữ lên khoảng 110% để đọc ban đêm thoải mái hơn. Điều này giúp giảm độ chói và căng thẳng mắt trong điều kiện ánh sáng yếu."));
                tipDao.insert(new Tip("Dành cho Người Suy Giảm Thị Lực Tạm Thời", "Tăng tạm thời kích thước phông chữ lên 150% hoặc hơn để dễ nhìn hơn sau phẫu thuật. Điều này giúp trong quá trình đọc trong thời gian phục hồi"));
                tipDao.insert(new Tip("Dành cho Môi Trường Đa Nhiệm", "Đặt kích thước phông chữ ở mức 130% để dễ dàng nhìn thoáng qua trong môi trường bận rộn. Văn bản lớn hơn dễ đọc hơn khi nhìn nhanh."));
                tipDao.insert(new Tip("Dành cho trẻ em hoặc Mục Đích Giáo Dục", "Đặt kích thước phông chữ ở mức 120% để hỗ trợ trẻ em đọc và học. Điều này giúp cải thiện sự tập trung và hiểu biết của trẻ."));
                tipDao.insert(new Tip("Dành cho Duyệt Nhanh", "Duy trì kích thước phông chữ ở mức 105% để duyệt nhanh và hiệu quả mà không mất nội dung. Điều này cân bằng giữa khả năng đọc và mật độ nội dung."));
                tipDao.insert(new Tip("Dành cho Trẻ Em đang Phát Triển Thị Lực", "Đặt kích thước phông chữ ở mức 115% để giúp trẻ em phát triển thị lực đọc dễ dàng hơn. Điều này hỗ trợ sự phát triển thị lực của họ và giảm căng thẳng mắt."));
                tipDao.insert(new Tip("Dành cho Thanh Thiếu Niên Sử Dụng Màn Hình Thường Xuyên", "Điều chỉnh kích thước phông chữ lên 110% cho thanh thiếu niên để giảm căng thẳng mắt do sử dụng màn hình thường xuyên. Điều này giúp giảm thiểu tác động của thời gian sử dụng màn hình kéo dài."));
                tipDao.insert(new Tip("Dành cho Người Lớn Với Thị Lực Bình Thường", "Duy trì kích thước phông chữ ở mức 100% cho người lớn có thị lực bình thường để tối đa hóa không gian màn hình. Điều này lý tưởng cho những người dùng muốn có nhiều thông tin hơn trên màn hình."));
                tipDao.insert(new Tip("Dành cho Người Già với Suy Giảm Thị Lực Nghiêm Trọng", "Tăng kích thước phông chữ lên 160% hoặc hơn cho người già bị suy giảm thị lực nghiêm trọng. Điều này làm cho văn bản dễ đọc hơn nhiều."));
                tipDao.insert(new Tip("Dành cho Các Nhiệm Vụ Đọc Chuyên Nghiệp", "Đặt kích thước phông chữ ở mức 110% để cân bằng khả năng đọc và không gian màn hình cho các nhiệm vụ chuyên nghiệp. Điều này giúp duy trì hiệu quả và thoải mái."));
                tipDao.insert(new Tip("Dành cho Người Dùng Loạn Thị", "Tăng kích thước phông chữ lên 130% cho người dùng bị loạn thị để giảm mờ. Văn bản lớn hơn ít có khả năng bị biến dạng hơn."));
                tipDao.insert(new Tip("Dành cho Người Dùng Trong Điều Kiện Ánh Sáng Yếu", "Đặt kích thước phông chữ ở mức 120% để cải thiện khả năng đọc trong điều kiện ánh sáng yếu. Điều này giúp giảm căng thẳng mắt trong môi trường tối."));
                tipDao.insert(new Tip("Dành cho Người Dùng Đeo Kính", "Điều chỉnh kích thước phông chữ lên 110% để giúp người dùng đeo kính đọc dễ dàng hơn. Điều này đảm bảo rằng văn bản dễ nhìn thấy."));
                tipDao.insert(new Tip("Dành cho Người Dùng Đeo Kính Áp Tròng", "Duy trì kích thước phông chữ ở mức 100% cho người dùng đeo kính áp tròng để tránh căng thẳng mắt. Điều này ngăn ngừa sự khó chịu do sử dụng kéo dài."));
                tipDao.insert(new Tip("Dành cho Người Dùng với Chứng Khó Đọc", "Tăng kích thước phông chữ lên 140% cho người dùng bị chứng khó đọc. Văn bản lớn hơn và rõ ràng hơn có thể giúp cải thiện tốc độ và sự hiểu biết khi đọc."));

            });
        }
    };

//    public static void initializeData() {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            FontSizeDao fontSizeDao = INSTANCE.fontSizeDao();
//            fontSizeDao.insert(new FontSize(100, true));
//            fontSizeDao.insert(new FontSize(110, false));
//            fontSizeDao.insert(new FontSize(120, false));
//            fontSizeDao.insert(new FontSize(130, false));
//            fontSizeDao.insert(new FontSize(140, false));
//            fontSizeDao.insert(new FontSize(150, false));
//            fontSizeDao.insert(new FontSize(160, false));
//            fontSizeDao.insert(new FontSize(170, false));
//            fontSizeDao.insert(new FontSize(180, false));
//            fontSizeDao.insert(new FontSize(190, false));
//            fontSizeDao.insert(new FontSize(200, false));
//            fontSizeDao.insert(new FontSize(210, false));
//            fontSizeDao.insert(new FontSize(220, false));
//            fontSizeDao.insert(new FontSize(230, false));
//            fontSizeDao.insert(new FontSize(240, false));
//            fontSizeDao.insert(new FontSize(250, false));
//            fontSizeDao.insert(new FontSize(260, false));
//            fontSizeDao.insert(new FontSize(270, false));
//            fontSizeDao.insert(new FontSize(280, false));
//            fontSizeDao.insert(new FontSize(290, false));
//            fontSizeDao.insert(new FontSize(300, false));
//            fontSizeDao.insert(new FontSize(310, false));
//            fontSizeDao.insert(new FontSize(320, false));
//            fontSizeDao.insert(new FontSize(330, false));
//            fontSizeDao.insert(new FontSize(340, false));
//            fontSizeDao.insert(new FontSize(350, false));
//            fontSizeDao.insert(new FontSize(90, false));
//            fontSizeDao.insert(new FontSize(80, false));
//            fontSizeDao.insert(new FontSize(70, false));
//            fontSizeDao.insert(new FontSize(60, false));
//            fontSizeDao.insert(new FontSize(50, false));
//
//
//            TipDao tipDao = INSTANCE.tipDao(); // Thêm dữ liệu tips ban đầu
//            tipDao.insert(new Tip("Dành cho Người Cao Tuổi hoặc Suy Giảm Thị Lực", "Tăng kích thước phông chữ lên khoảng 125% đến 150% để dễ đọc hơn. Phông chữ lớn hơn giúp giảm căng thẳng mắt và làm cho văn bản dễ đọc hơn cho những người bị suy giảm thị lực."));
//            tipDao.insert(new Tip("Dành cho Đọc dưới Ánh Sáng Mặt Trời", "Đặt kích thước phông chữ vào khoảng 110% đến 130% để cải thiện khả năng đọc dưới ánh sáng mặt trời. Điều này giảm độ chói và cải thiện độ tương phản."));
//            tipDao.insert(new Tip("Dành cho Các Phiên Đọc Kéo Dài", "Tăng nhẹ kích thước phông chữ lên 110% đến 120% để giảm căng thẳng mắt trong quá trình đọc dài. Điều này giúp duy trì sự tập trung và thoải mái."));
//            tipDao.insert(new Tip("Dành cho Người Trẻ với Thị Lực Tốt", "Duy trì kích thước phông chữ ở mức 100% đến 110% để hiển thị nhiều nội dung hơn trên màn hình. Điều này phù hợp cho những người dùng có thị lực tốt và muốn hiển thị nhiều nội dung hơn."));
//            tipDao.insert(new Tip("Dành cho Đọc Vào Ban Đêm", "Điều chỉnh kích thước phông chữ lên khoảng 110% để đọc ban đêm thoải mái hơn. Điều này giúp giảm độ chói và căng thẳng mắt trong điều kiện ánh sáng yếu."));
//            tipDao.insert(new Tip("Dành cho Người Suy Giảm Thị Lực Tạm Thời", "Tăng tạm thời kích thước phông chữ lên 150% hoặc hơn để dễ nhìn hơn sau phẫu thuật. Điều này giúp trong quá trình đọc trong thời gian phục hồi"));
//            tipDao.insert(new Tip("Dành cho Môi Trường Đa Nhiệm", "Đặt kích thước phông chữ ở mức 130% để dễ dàng nhìn thoáng qua trong môi trường bận rộn. Văn bản lớn hơn dễ đọc hơn khi nhìn nhanh."));
//            tipDao.insert(new Tip("Dành cho trẻ em hoặc Mục Đích Giáo Dục", "Đặt kích thước phông chữ ở mức 120% để hỗ trợ trẻ em đọc và học. Điều này giúp cải thiện sự tập trung và hiểu biết của trẻ."));
//            tipDao.insert(new Tip("Dành cho Duyệt Nhanh", "Duy trì kích thước phông chữ ở mức 105% để duyệt nhanh và hiệu quả mà không mất nội dung. Điều này cân bằng giữa khả năng đọc và mật độ nội dung."));
//            tipDao.insert(new Tip("Dành cho Trẻ Em đang Phát Triển Thị Lực", "Đặt kích thước phông chữ ở mức 115% để giúp trẻ em phát triển thị lực đọc dễ dàng hơn. Điều này hỗ trợ sự phát triển thị lực của họ và giảm căng thẳng mắt."));
//            tipDao.insert(new Tip("Dành cho Thanh Thiếu Niên Sử Dụng Màn Hình Thường Xuyên", "Điều chỉnh kích thước phông chữ lên 110% cho thanh thiếu niên để giảm căng thẳng mắt do sử dụng màn hình thường xuyên. Điều này giúp giảm thiểu tác động của thời gian sử dụng màn hình kéo dài."));
//            tipDao.insert(new Tip("Dành cho Người Lớn Với Thị Lực Bình Thường", "Duy trì kích thước phông chữ ở mức 100% cho người lớn có thị lực bình thường để tối đa hóa không gian màn hình. Điều này lý tưởng cho những người dùng muốn có nhiều thông tin hơn trên màn hình."));
//            tipDao.insert(new Tip("Dành cho Người Già với Suy Giảm Thị Lực Nghiêm Trọng", "Tăng kích thước phông chữ lên 160% hoặc hơn cho người già bị suy giảm thị lực nghiêm trọng. Điều này làm cho văn bản dễ đọc hơn nhiều."));
//            tipDao.insert(new Tip("Dành cho Các Nhiệm Vụ Đọc Chuyên Nghiệp", "Đặt kích thước phông chữ ở mức 110% để cân bằng khả năng đọc và không gian màn hình cho các nhiệm vụ chuyên nghiệp. Điều này giúp duy trì hiệu quả và thoải mái."));
//            tipDao.insert(new Tip("Dành cho Người Dùng Loạn Thị", "Tăng kích thước phông chữ lên 130% cho người dùng bị loạn thị để giảm mờ. Văn bản lớn hơn ít có khả năng bị biến dạng hơn."));
//            tipDao.insert(new Tip("Dành cho Người Dùng Trong Điều Kiện Ánh Sáng Yếu", "Đặt kích thước phông chữ ở mức 120% để cải thiện khả năng đọc trong điều kiện ánh sáng yếu. Điều này giúp giảm căng thẳng mắt trong môi trường tối."));
//            tipDao.insert(new Tip("Dành cho Người Dùng Đeo Kính", "Điều chỉnh kích thước phông chữ lên 110% để giúp người dùng đeo kính đọc dễ dàng hơn. Điều này đảm bảo rằng văn bản dễ nhìn thấy."));
//            tipDao.insert(new Tip("Dành cho Người Dùng Đeo Kính Áp Tròng", "Duy trì kích thước phông chữ ở mức 100% cho người dùng đeo kính áp tròng để tránh căng thẳng mắt. Điều này ngăn ngừa sự khó chịu do sử dụng kéo dài."));
//            tipDao.insert(new Tip("Dành cho Người Dùng với Chứng Khó Đọc", "Tăng kích thước phông chữ lên 140% cho người dùng bị chứng khó đọc. Văn bản lớn hơn và rõ ràng hơn có thể giúp cải thiện tốc độ và sự hiểu biết khi đọc."));
//
//        });
//    }

}
