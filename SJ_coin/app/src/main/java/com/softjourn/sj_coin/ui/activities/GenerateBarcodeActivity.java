package com.softjourn.sj_coin.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by omartynets on 08.12.2016.
 */

public class GenerateBarcodeActivity extends BaseActivity implements Const {

    @BindView(R.id.input_amount)
    EditText mAmount;

    @BindView(R.id.btn_generate)
    Button mButtonGenerate;

    @BindView(R.id.qr_code_image)
    ImageView mQRImage;

    @BindView(R.id.current_balance)
    TextView mBalance;

    private Bitmap mBitmap;
    private final static int PERMISSION_ALL = 1;

    @OnClick(R.id.qr_code_image)
    public void onQRCodeCLick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            } else {
                shareCode();
            }
        } else {
            shareCode();
        }
    }

    @OnClick(R.id.btn_generate)
    public void onGenerateClick() {
        if (validateInputAmount()) {
            try {
                mBitmap = generateCode(mAmount.getText().toString());
                mQRImage.setImageBitmap(mBitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_money_barcode_layout);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBalance.setText(String.format(getString(R.string.your_balance_is), Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.select_machine).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareCode();
                } else {
                    Utils.showSnackBar(findViewById(R.id.root_layout_profile), getString(R.string.permissions_camera_message));
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Utils.showSnackBar(findViewById(R.id.root_layout_profile), getString(R.string.permissions_camera_message));
            } else {
                shareCode();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void shareCode() {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "MoneyCode", null);
        Uri uri = Uri.parse(path);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    private boolean validateInputAmount() {
        if (TextUtils.isEmpty(mAmount.getText().toString())) {
            mAmount.setError(getString(R.string.empty_field_error));
            mAmount.requestFocus();
            return false;
        }
        if (!mAmount.getText().toString().matches("\\d+")) {
            mAmount.setError(getString(R.string.float_number_error));
            mAmount.requestFocus();
            return false;
        }
        if (Long.parseLong(mAmount.getText().toString()) > Integer.parseInt(Preferences.retrieveStringObject(USER_BALANCE_PREFERENCES_KEY))) {
            mAmount.setError(getString(R.string.exceed_balance_error));
            mAmount.requestFocus();
            return false;
        }
        return true;
    }

    private Bitmap generateCode(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, mQRImage.getWidth(), mQRImage.getWidth(), null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, mQRImage.getWidth(), 0, 0, w, h);
        return bitmap;
    }


    @Override
    public void showSnackBar(String message) {

    }
}
