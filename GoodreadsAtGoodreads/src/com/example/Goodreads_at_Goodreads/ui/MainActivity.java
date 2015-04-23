package com.example.Goodreads_at_Goodreads.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import com.example.Goodreads_at_Goodreads.R;
import com.example.Goodreads_at_Goodreads.models.BookDetails;
import com.example.Goodreads_at_Goodreads.models.BookMetadata;
import com.example.Goodreads_at_Goodreads.requests.GetBookMetadata;
import com.example.Goodreads_at_Goodreads.requests.PutBookCopy;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String URL = "http://xisbn.worldcat.org/webservices/xid";

    // TODO-XX: When searching is up and running
//    private EditText mBookTitle;
    private Switch mBatchSwitch;

    private List<String> mScannedISBNs;

    private boolean mWantsToBatchAdd = false;

    private PutBookCopy httpBookUploader = new PutBookCopy();

    private View.OnClickListener batchSwitchListener = new
            View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mWantsToBatchAdd = mBatchSwitch.isChecked();
                }
            };
    private View.OnClickListener searchButtonListener = new
            View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // TODO-XX: When searching is up and running
//                    String bookTitle = mBookTitle.getText().toString();
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button mScanButton = (Button) findViewById(R.id.startCapture);
        Button mSearchButton = (Button) findViewById(R.id.search);
        // TODO-XX: When searching is up and running
//        mBookTitle = (EditText) findViewById(R.id.bookTitle);
        mBatchSwitch = (Switch) findViewById(R.id.batchAdder);

        mScanButton.setOnClickListener(scanOnClick);
        mBatchSwitch.setOnClickListener(batchSwitchListener);
        mSearchButton.setOnClickListener(searchButtonListener);

        mScannedISBNs = new ArrayList<String>();
        requestSingleMetaData("9780439554930");
    }

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

                    if (mWantsToBatchAdd) {
                        startActivityForResult(intent, 0);
                    } else {
                        requestSingleMetaData(contents);
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

    /**
     * Upload all the books currently in {@link #mScannedISBNs}
     */
    private void addAllToCatalog() {
        // View state add spinner
        httpBookUploader.sendAll(mScannedISBNs);
        mScannedISBNs.clear();
    }

    private void requestSingleMetaData(String isbn) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)  //call your base url
                .build();

        GetBookMetadata request = restAdapter.create(GetBookMetadata.class);
        request.getMetaData(isbn, new Callback<BookMetadata>() {
            @Override
            public void success(BookMetadata s, Response response) {
                Log.d("metatdata response", s.getBook().getAuthor() + " " + s.getBook().getTitle());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("metatdata response", "FAILURE: " + retrofitError.getMessage());
            }
        });
    }
}
