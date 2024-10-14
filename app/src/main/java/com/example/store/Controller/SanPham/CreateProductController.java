package com.example.store.Controller.SanPham;

import static com.example.store.VMCrop.keySanPham;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.store.DatabaseHandler;
import com.example.store.MainActivity;
import com.example.store.R;
import com.google.firebase.database.DatabaseReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateProductController extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;

    private EditText edtTenMatHang, edtMaVach, edtGiaBan, edtSoLuong, edtDonViTinh;
    private CheckBox chkApDungThue;
    private Button btnLuu;
    private DatabaseReference mDatabase;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product_controller);

        // Initialize SQLite Database handler
        db = new DatabaseHandler(this);

        imageView = findViewById(R.id.imageView);
        edtTenMatHang = findViewById(R.id.edtTenMatHang);
        edtMaVach = findViewById(R.id.edtMaVach);
        edtGiaBan = findViewById(R.id.edtGiaBan);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtDonViTinh = findViewById(R.id.edtDonViTinh);
        chkApDungThue = findViewById(R.id.chkApDungThue);
        btnLuu = findViewById(R.id.btnLuu);

        // Open image chooser
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Save product
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    convertImageToBase64AndSave();
                } else {
                    addSanPham(null); // No image
                }
            }
        });

        // Request storage permission if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        // Barcode scanner button
        findViewById(R.id.btnQuetMaVach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarcode();
            }
        });
    }

    // Method to start barcode scanning
    private void scanBarcode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Quét mã vạch");
        integrator.setOrientationLocked(true);  // Khóa chế độ dọc
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCaptureActivity(CustomScannerActivity.class); // Custom scanner activity
        integrator.initiateScan();
    }

    // Method to handle both barcode scanning and image picking results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null && result.getContents() != null) {
                edtMaVach.setText(result.getContents()); // Set scanned barcode
            } else {
                Toast.makeText(this, "Quét mã thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Open image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Convert image to Base64
    private void convertImageToBase64AndSave() {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        addSanPham(base64Image); // Save product with image
    }

    // Add product to SQLite
    public void addSanPham(@Nullable String base64Image) {
        String idProduct = generateRandomId(5);
        String tenMatHang = edtTenMatHang.getText().toString().trim();
        String maVach = edtMaVach.getText().toString().trim();
        String giaBan = edtGiaBan.getText().toString().trim();
        String soLuong = edtSoLuong.getText().toString().trim();
        String donViTinh = edtDonViTinh.getText().toString().trim();
        String ghiChu = "";

        // Validation
        if (tenMatHang.isEmpty() || giaBan.isEmpty() || soLuong.isEmpty() || donViTinh.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert product data into SQLite
        db.executeSQL("insert into " + keySanPham + " (productId, tenMatHang, giaBan, soLuong, donViTinh, imgProduct, ghiChu) values('"
                + idProduct + "','" + tenMatHang + "','" + giaBan + "','" + soLuong + "','" + donViTinh + "','" + base64Image + "','" + ghiChu + "')");
        Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG).show();
        Intent back = new Intent(CreateProductController.this, MainActivity.class);
        startActivity(back);
        finish();
    }

    // Generate random product ID
    private String generateRandomId(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
