package com.gumtree.android.savedsearches;

import android.content.Context;
import android.content.Intent;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.alerts.Alert;
import com.gumtree.android.alerts.AlertSerializer;
import com.gumtree.android.alerts.parser.AlertResponseParser;
import com.gumtree.android.alerts.parser.AlertsParser;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.SyncAdapter;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.savedsearches.SavedSearchesDao.Search;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class SavedSearchSync {
    private static final String ALERTS = "alerts/";
    @Inject
    BaseAccountManager accountManager;
    private ICapiClient capi = this.capiClientManager.getCapiClient();
    @Inject
    CapiClientManager capiClientManager;
    private SavedSearchesDao dao;
    private boolean isLimitReached;

    public SavedSearchSync(Context context) {
        GumtreeApplication.component().inject(this);
        this.capi.authorize(this.accountManager.getAuthentication());
        this.dao = new SavedSearchesDao(context);
    }

    public static void executeSavedSearchesSync(Context context) {
        try {
            new SavedSearchSync(GumtreeApplication.getContext()).execute();
            context.sendBroadcast(new Intent(SyncAdapter.ACTION_FINISHED_SYNC));
        } catch (Exception e) {
            if (e instanceof ResponseException) {
                if (((ResponseException) e).getError().isLimitReached()) {
                    context.sendBroadcast(new Intent(SyncAdapter.ACTION_LIMIT_REACHED_SYNC));
                    return;
                } else if (((ResponseException) e).getError().isNetwork()) {
                    context.sendBroadcast(new Intent(SyncAdapter.ACTION_NETWORK_ERROR_SYNC));
                    return;
                }
            }
            context.sendBroadcast(new Intent(SyncAdapter.ACTION_FAILURE_SYNC));
        }
    }

    private void execute() {
        if (this.accountManager.isUserLoggedIn()) {
            List<Search> localSavedSearchesSynced = getLocalSavedSearchesSynced();
            List<Alert> savedSearchFromServer = getSavedSearchFromServer();
            if (savedSearchFromServer != null) {
                Collections.reverse(savedSearchFromServer);
                for (Alert alert : savedSearchFromServer) {
                    Search localSearch = getLocalSearch(alert, localSavedSearchesSynced);
                    if (localSearch == null) {
                        persistInTable(alert);
                    } else {
                        updateIfNecessary(alert, localSearch);
                    }
                }
            }
            for (Search search : localSavedSearchesSynced) {
                if (isNotIn(search, savedSearchFromServer)) {
                    deleteFromTable(search);
                }
            }
            for (Search search2 : getLocalSavedSearchesToSync()) {
                sync(search2);
            }
            for (Search search22 : getLocalSavedSearchesToDelete()) {
                deleteOnServerAndFromTable(search22);
            }
            if (this.isLimitReached) {
                ResultError limitReached = ResultError.limitReached();
                limitReached.setMessage(GumtreeApplication.getContext().getString(2131165774));
                throw new ResponseException(limitReached);
            }
        }
    }

    public void cleanup() {
        this.dao.cleanTable();
    }

    private void updateIfNecessary(Alert alert, Search search) {
        try {
            if (thereAreChanges(alert, search)) {
                if (search.isToUpdate()) {
                    alert.setLink(search.getUrl());
                    alert.setEmailAlertEnabled(search.isEmailAlertEnabled());
                    this.capi.put(ALERTS + alert.getId()).withContent(new AlertSerializer().serialize(alert)).execute();
                    this.dao.update(String.valueOf(search.getId()), alert);
                    return;
                }
                this.dao.update(String.valueOf(search.getId()), alert);
            } else if (search.isToUpdate()) {
                this.dao.update(String.valueOf(search.getId()), alert);
            }
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private boolean thereAreChanges(Alert alert, Search search) {
        if (alert.isEmailAlertEnabled() != search.isEmailAlertEnabled()) {
            return true;
        }
        if (alert.getTitle() == null && search.getTitle() == null) {
            return false;
        }
        if (alert.getTitle() == null || !alert.getTitle().equals(search.getTitle())) {
            return true;
        }
        return false;
    }

    private void deleteFromTable(Search search) {
        try {
            this.dao.delete(search);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private Search getLocalSearch(Alert alert, List<Search> list) {
        for (Search search : list) {
            if (alert.getId() != null && alert.getId().equals(search.getUid())) {
                return search;
            }
        }
        return null;
    }

    private boolean isNotIn(Search search, List<Alert> list) {
        for (Alert alert : list) {
            if (alert.getId() != null && alert.getId().equals(search.getUid())) {
                return false;
            }
        }
        return true;
    }

    private void deleteOnServerAndFromTable(Search search) {
        try {
            this.capi.delete(ALERTS + search.getUid()).execute();
            this.dao.delete(search);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void persistInTable(Alert alert) {
        try {
            this.dao.persist(alert);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void sync(Search search) {
        try {
            Alert alert = new Alert();
            alert.setLink(search.getUrl());
            alert.setEmailAlertEnabled(search.isEmailAlertEnabled());
            Result execute = this.capi.post("alerts").withContent(new AlertSerializer().serialize(alert)).execute(new AlertResponseParser());
            if (execute.hasError()) {
                throw new ResponseException(execute.getError().getMessage());
            }
            this.dao.update(String.valueOf(search.getId()), (Alert) execute.getData());
        } catch (Exception e) {
            if ((e instanceof ResponseException) && ((ResponseException) e).getError().isLimitReached()) {
                this.dao.delete(search);
                this.isLimitReached = true;
            }
        }
    }

    private List<Search> getLocalSavedSearchesSynced() {
        return this.dao.synced();
    }

    private List<Search> getLocalSavedSearchesToSync() {
        return this.dao.toSync();
    }

    private List<Search> getLocalSavedSearchesToDelete() {
        return this.dao.toDelete();
    }

    private List<Alert> getSavedSearchFromServer() {
        Result execute = this.capi.get("alerts.json").execute(new AlertsParser());
        if (!execute.hasError()) {
            return (List) execute.getData();
        }
        throw new ResponseException(execute.getError().getMessage());
    }

    private void log(String str) {
        Log.e(str + BuildConfig.FLAVOR);
    }
}
