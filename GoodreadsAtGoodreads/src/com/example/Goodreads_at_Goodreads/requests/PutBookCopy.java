package com.example.Goodreads_at_Goodreads.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vinitnayak on 12/18/14.
 */
public class PutBookCopy {

    Map<String, String> isbnBookCount = new HashMap<String, String>();

    public boolean sendAll(List<String> isbnList) {

        return false;
    }

    public boolean sendSingleBook(String isbn) {

        return false;
    }

    /** Build the request to be sent to get book metadata
     * @param isbn to be uploaded to catalog
     */
    private void buildRequest(String isbn) {

    }
}
