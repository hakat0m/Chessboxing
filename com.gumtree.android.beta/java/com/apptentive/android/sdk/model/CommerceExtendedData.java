package com.apptentive.android.sdk.model;

import com.apptentive.android.sdk.model.ExtendedData.Type;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommerceExtendedData extends ExtendedData {
    private static final String KEY_AFFILIATION = "affiliation";
    private static final String KEY_CURRENCY = "currency";
    private static final String KEY_ID = "id";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_REVENUE = "revenue";
    private static final String KEY_SHIPPING = "shipping";
    private static final String KEY_TAX = "tax";
    private static final int VERSION = 1;

    public class Item extends JSONObject {
        private static final String KEY_CATEGORY = "category";
        private static final String KEY_CURRENCY = "currency";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_PRICE = "price";
        private static final String KEY_QUANTITY = "quantity";

        public Item(String str) throws JSONException {
            super(str);
        }

        public Item(Object obj, Object obj2, String str, Number number, Number number2, String str2) throws JSONException {
            if (obj != null) {
                setId(obj);
            }
            if (obj2 != null) {
                setName(obj2);
            }
            if (str != null) {
                setCategory(str);
            }
            if (number != null) {
                setPrice(number);
            }
            if (number2 != null) {
                setQuantity(number2);
            }
            if (str2 != null) {
                setCurrency(str2);
            }
        }

        public Item setId(Object obj) throws JSONException {
            put(KEY_ID, obj);
            return this;
        }

        public Item setName(Object obj) throws JSONException {
            put(KEY_NAME, obj);
            return this;
        }

        public Item setCategory(String str) throws JSONException {
            put(KEY_CATEGORY, str);
            return this;
        }

        public Item setPrice(Number number) throws JSONException {
            put(KEY_PRICE, number);
            return this;
        }

        public Item setQuantity(Number number) throws JSONException {
            put(KEY_QUANTITY, number);
            return this;
        }

        public Item setCurrency(String str) throws JSONException {
            put(KEY_CURRENCY, str);
            return this;
        }
    }

    protected void init() {
        setType(Type.commerce);
        setVersion(VERSION);
    }

    public CommerceExtendedData(String str) throws JSONException {
        super(str);
    }

    public CommerceExtendedData(Object obj, Object obj2, Number number, Number number2, Number number3, String str) throws JSONException {
        setId(obj);
        setAffiliation(obj2);
        setRevenue(number);
        setShipping(number2);
        setTax(number3);
        setCurrency(str);
    }

    public CommerceExtendedData setId(Object obj) throws JSONException {
        put(KEY_ID, obj);
        return this;
    }

    public CommerceExtendedData setAffiliation(Object obj) throws JSONException {
        put(KEY_AFFILIATION, obj);
        return this;
    }

    public CommerceExtendedData setRevenue(Number number) throws JSONException {
        put(KEY_REVENUE, number);
        return this;
    }

    public CommerceExtendedData setShipping(Number number) throws JSONException {
        put(KEY_SHIPPING, number);
        return this;
    }

    public CommerceExtendedData setTax(Number number) throws JSONException {
        put(KEY_TAX, number);
        return this;
    }

    public CommerceExtendedData setCurrency(String str) throws JSONException {
        put(KEY_CURRENCY, str);
        return this;
    }

    public CommerceExtendedData addItem(Item item) throws JSONException {
        JSONArray jSONArray;
        if (isNull(KEY_ITEMS)) {
            jSONArray = new JSONArray();
            put(KEY_ITEMS, jSONArray);
        } else {
            jSONArray = getJSONArray(KEY_ITEMS);
        }
        jSONArray.put(item);
        return this;
    }
}
