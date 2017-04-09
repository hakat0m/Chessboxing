package com.gumtree.android.favourites;

import android.content.Context;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.Log;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.AdsDao;
import com.gumtree.android.ads.SRPAds;
import com.gumtree.android.ads.parser.AdsListParser;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.favourites.SavedFavouritesDao.SavedAd;
import com.gumtree.android.message_box.ConversationsIntentService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class SavedFavouritesSync {
    public static final int BATCH_NUM_ITEMS = 50;
    private static final String IDS = "ids";
    private static final String USERS_S_WATCHLIST_JSON = "users/%s/watchlist.json";
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    BaseAccountManager mAccountManager;
    private AdsDao mAdsDao;
    private SavedFavouritesDao mSavedFavouritesDao;

    public SavedFavouritesSync(Context context) {
        ComponentsManager.get().getAppComponent().inject(this);
        this.mAdsDao = new AdsDao(context);
        this.mSavedFavouritesDao = new SavedFavouritesDao(context);
    }

    public static List<String> populateIdsBatch(LinkedList<String> linkedList) {
        List<String> arrayList = new ArrayList();
        for (int i = 0; i < BATCH_NUM_ITEMS; i++) {
            String str = (String) linkedList.poll();
            if (str == null) {
                break;
            }
            arrayList.add(str);
        }
        return arrayList;
    }

    public static String createCommaSeparatedString(Collection<String> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator it = collection.iterator();
        while (true) {
            stringBuilder.append(it.next());
            if (!it.hasNext()) {
                return stringBuilder.toString();
            }
            stringBuilder.append(",");
        }
    }

    public static boolean isAdInSavedAds(Ad ad, List<SavedAd> list) {
        String id = ad.getId();
        if (id != null) {
            for (SavedAd id2 : list) {
                if (id.equals(id2.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSavedAdInAds(SavedAd savedAd, List<Ad> list) {
        String id = savedAd.getId();
        if (id != null) {
            for (Ad id2 : list) {
                if (id.equals(id2.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void execute() {
        syncFavoriteAds(getLocalSavedAds(), loadServerSavedAds());
    }

    private void syncFavoriteAds(List<SavedAd> list, List<Ad> list2) {
        if (list2 != null) {
            Collection arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            List arrayList3 = new ArrayList();
            for (SavedAd savedAd : list) {
                int syncStatus = savedAd.getSyncStatus();
                switch (syncStatus) {
                    case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                        if (!isSavedAdInAds(savedAd, list2)) {
                            arrayList3.add(savedAd.getId());
                            break;
                        } else {
                            updateSavedAdIdInTable(savedAd.getId());
                            break;
                        }
                    case HighlightView.GROW_NONE /*1*/:
                        arrayList.add(savedAd.getId());
                        break;
                    case HighlightView.GROW_LEFT_EDGE /*2*/:
                        if (!isSavedAdInAds(savedAd, list2)) {
                            deleteSavedAdFromTable(savedAd.getId());
                            break;
                        } else {
                            arrayList2.add(savedAd.getId());
                            break;
                        }
                    default:
                        Log.e(getClass().getSimpleName(), "case not supported " + syncStatus);
                        break;
                }
            }
            deleteSavedAdsIdsFromTable(arrayList);
            for (int size = list2.size() - 1; size >= 0; size--) {
                Ad ad = (Ad) list2.get(size);
                if (!arrayList2.contains(ad.getId())) {
                    if (arrayList3.contains(ad.getId())) {
                        this.mAdsDao.updateAdInTable(ad);
                    } else {
                        this.mAdsDao.insertAdInTable(ad);
                    }
                    persistSavedAdInTable(ad);
                }
            }
            deleteCapiFavouriteAds(arrayList2);
            uploadCapiFavouriteAds(arrayList3);
        }
    }

    public List<SavedAd> getLocalSavedAds() {
        return this.mSavedFavouritesDao.all();
    }

    private List<SavedAd> getLocalSavedSyncedAds() {
        return this.mSavedFavouritesDao.synced();
    }

    private void deleteSavedAdFromTable(String str) {
        try {
            this.mSavedFavouritesDao.delete(str);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void deleteSavedAdsIdsFromTable(Collection<String> collection) {
        try {
            this.mSavedFavouritesDao.delete((Collection) collection);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void deleteSavedAdsFromTable(Collection<SavedAd> collection) {
        try {
            this.mSavedFavouritesDao.deleteSavedAds(collection);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void persistSavedAdInTable(Ad ad) {
        try {
            this.mSavedFavouritesDao.persist(ad.getId(), System.nanoTime());
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void updateSavedAdIdInTable(String str) {
        try {
            this.mSavedFavouritesDao.update(str, null);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private void updateOrCreateAdInAdsTable(Ad ad) {
        try {
            this.mAdsDao.updateAdsTable(ad);
        } catch (Exception e) {
            log(e.getMessage());
        }
    }

    private List<Ad> loadServerSavedAds() {
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        capiClient.authorize(this.mAccountManager.getAuthentication());
        String format = String.format(USERS_S_WATCHLIST_JSON, new Object[]{this.mAccountManager.getUsername()});
        Result execute = capiClient.get(format).execute(new AdsListParser(format, String.valueOf(System.currentTimeMillis())));
        if (execute.hasError()) {
            throw new ResponseException(execute.getError().getMessage());
        }
        SRPAds sRPAds = (SRPAds) execute.getData();
        if (sRPAds != null) {
            return sRPAds.getAds();
        }
        return null;
    }

    private void deleteCapiFavouriteAds(List<String> list) {
        if (list.size() > 0) {
            List arrayList;
            LinkedList linkedList = new LinkedList(list);
            List list2 = null;
            while (true) {
                ICapiClient capiClient = this.capiClientManager.getCapiClient();
                capiClient.authorize(this.mAccountManager.getAuthentication());
                Collection populateIdsBatch = populateIdsBatch(linkedList);
                capiClient.withParam(IDS, createCommaSeparatedString(populateIdsBatch));
                String format = String.format(USERS_S_WATCHLIST_JSON, new Object[]{this.mAccountManager.getUsername()});
                Result execute = capiClient.delete(format).execute(new AdsListParser(format, String.valueOf(System.currentTimeMillis())));
                if (execute.hasError()) {
                    throw new ResponseException(execute.getError().getMessage());
                }
                deleteSavedAdsIdsFromTable(populateIdsBatch);
                SRPAds sRPAds = (SRPAds) execute.getData();
                if (sRPAds != null) {
                    arrayList = new ArrayList(sRPAds.getAds());
                } else {
                    arrayList = list2;
                }
                if (linkedList.size() <= 0) {
                    break;
                }
                list2 = arrayList;
            }
            if (arrayList != null) {
                updateSyncedAds(arrayList);
            }
        }
    }

    private void uploadCapiFavouriteAds(List<String> list) {
        if (list.size() > 0) {
            List arrayList;
            Collections.reverse(list);
            LinkedList linkedList = new LinkedList(list);
            List list2 = null;
            while (true) {
                ICapiClient capiClient = this.capiClientManager.getCapiClient();
                capiClient.authorize(this.mAccountManager.getAuthentication());
                Collection populateIdsBatch = populateIdsBatch(linkedList);
                capiClient.withParam(IDS, createCommaSeparatedString(populateIdsBatch));
                String format = String.format(USERS_S_WATCHLIST_JSON, new Object[]{this.mAccountManager.getUsername()});
                Result execute = capiClient.post(format).execute(new AdsListParser(format, String.valueOf(System.currentTimeMillis())));
                if (execute.hasError()) {
                    throw new ResponseException(execute.getError().getMessage());
                }
                deleteSavedAdsIdsFromTable(populateIdsBatch);
                SRPAds sRPAds = (SRPAds) execute.getData();
                if (sRPAds != null) {
                    arrayList = new ArrayList(sRPAds.getAds());
                } else {
                    arrayList = list2;
                }
                if (linkedList.size() <= 0) {
                    break;
                }
                list2 = arrayList;
            }
            if (arrayList != null) {
                updateSyncedAds(arrayList);
            }
        }
    }

    private void updateSyncedAds(List<Ad> list) {
        deleteSavedAdsFromTable(getLocalSavedSyncedAds());
        saveServerAds3(list);
    }

    public void saveServerAds1(List<Ad> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            Ad ad = (Ad) list.get(size);
            updateOrCreateAdInAdsTable(ad);
            persistSavedAdInTable(ad);
        }
    }

    public void saveServerAds2(List<Ad> list) {
        List localSavedAds = getLocalSavedAds();
        for (int size = list.size() - 1; size >= 0; size--) {
            Ad ad = (Ad) list.get(size);
            if (isAdInSavedAds(ad, localSavedAds)) {
                this.mAdsDao.updateAdInTable(ad);
            } else {
                this.mAdsDao.insertAdInTable(ad);
            }
            persistSavedAdInTable(ad);
        }
    }

    public void saveServerAds3(List<Ad> list) {
        List<SavedAd> localSavedAds = getLocalSavedAds();
        int size = list.size();
        Set hashSet = new HashSet(localSavedAds.size());
        for (SavedAd id : localSavedAds) {
            hashSet.add(id.getId());
        }
        for (size--; size >= 0; size--) {
            Ad ad = (Ad) list.get(size);
            if (hashSet.contains(ad.getId())) {
                this.mAdsDao.updateAdInTable(ad);
            } else {
                this.mAdsDao.insertAdInTable(ad);
            }
            persistSavedAdInTable(ad);
        }
    }

    public void cleanup() {
        this.mSavedFavouritesDao.cleanTable();
    }

    private void log(String str) {
        try {
            com.gumtree.android.common.utils.Log.e(str);
        } catch (Exception e) {
            System.out.println(BuildConfig.FLAVOR + str);
        }
    }
}
