package com.gumtree.android.common.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.SearchRecentSuggestions;
import com.gumtree.android.common.providers.GumtreeSuggestionsProvider;

public class SearchSuggestionsInsertTask extends AsyncTask<Void, Void, Exception> {
    private String query;
    private SearchRecentSuggestions suggestions;

    public SearchSuggestionsInsertTask(Context context, String str) {
        this.query = str;
        this.suggestions = new SearchRecentSuggestions(context.getApplicationContext(), GumtreeSuggestionsProvider.AUTHORITY, 1);
    }

    protected Exception doInBackground(Void... voidArr) {
        Exception exception = null;
        try {
            this.suggestions.saveRecentQuery(this.query, null);
        } catch (Exception e) {
            exception = e;
        }
        return exception;
    }

    protected void onPostExecute(Exception exception) {
        super.onPostExecute(exception);
    }
}
