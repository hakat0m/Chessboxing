package com.facebook.stetho.inspector.network;

import java.util.ArrayList;
import javax.annotation.Nullable;

public class MimeMatcher<T> {
    private final ArrayList<MimeMatcherRule> mRuleMap = new ArrayList();

    public void addRule(String str, T t) {
        this.mRuleMap.add(new MimeMatcherRule(this, str, t));
    }

    public void clear() {
        this.mRuleMap.clear();
    }

    @Nullable
    public T match(String str) {
        int size = this.mRuleMap.size();
        for (int i = 0; i < size; i++) {
            MimeMatcherRule mimeMatcherRule = (MimeMatcherRule) this.mRuleMap.get(i);
            if (mimeMatcherRule.match(str)) {
                return mimeMatcherRule.getResultIfMatched();
            }
        }
        return null;
    }
}
