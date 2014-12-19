package com.example.Goodreads_at_Goodreads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private EditText mBookTitle;
    private Switch mBatchSwitch;

    private List<String> mScannedISBNs;

    private boolean mWantsToAddBatch = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button mScanButton = (Button) findViewById(R.id.startCapture);
        Button mSearchButton = (Button) findViewById(R.id.search);
        mBookTitle = (EditText) findViewById(R.id.bookTitle);
        mBatchSwitch = (Switch) findViewById(R.id.batchAdder);

        mScanButton.setOnClickListener(scanOnClick);
        mBatchSwitch.setOnClickListener(batchSwitchListener);
        mSearchButton.setOnClickListener(searchButtonListener);

        mScannedISBNs = new ArrayList<String>();
    }

    private View.OnClickListener batchSwitchListener = new
            View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mWantsToAddBatch = mBatchSwitch.isChecked();
                }
            };

    private View.OnClickListener searchButtonListener = new
            View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String bookTitle = mBookTitle.getText().toString();
                    // Fire off Async Task here to search!
                }
            };

    private View.OnClickListener scanOnClick =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(
                            "com.google.zxing.client.android.SCAN");
                    startActivityForResult(intent, 0);
                }
            };

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                Log.d("check", contents);
                Log.d("check", format);
                int numbers = contents.length();
                if ((numbers != 10 && numbers != 13)
                        || !"EAN_13".equals(format)) {
                    Toast t = Toast.makeText(this, "Bad Barcode!",
                            Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    mScannedISBNs.add(contents);
                    if (mScannedISBNs.size() > 9) {
                        addAllToCatalog();
                    }
                    Toast t = Toast.makeText(this, "Collected!",
                            Toast.LENGTH_SHORT);
                    t.show();

                    if (mWantsToAddBatch) {
                        startActivityForResult(intent, 0);
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("check", "CANCELLED");
                Toast t;
                if (!mScannedISBNs.isEmpty()) {
                    addAllToCatalog();
                    t = Toast.makeText(this, "Batch Sent!",
                            Toast.LENGTH_SHORT);

                } else {
                    t = Toast.makeText(this, "No barcodes scanned",
                            Toast.LENGTH_SHORT);
                }
                t.show();
            }

        }
    }

    private void addAllToCatalog() {

    }
}
