package com.gumtree.android.postad;

import android.support.annotation.NonNull;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.List;

public final class DraftFeature implements Serializable {
    private final String description;
    private final String identifier;
    @NonNull
    private final List<DraftOption> options;
    private final String title;
    private final Type type;

    public enum Type {
        UNSUPPORTED,
        IN_SPOTLIGHT,
        URGENT,
        FEATURED,
        BUMPED_UP,
        INSERTION
    }

    public String toString() {
        return "DraftFeature(title=" + getTitle() + ", description=" + getDescription() + ", type=" + getType() + ", identifier=" + getIdentifier() + ", options=" + getOptions() + ")";
    }

    public static DraftFeatureBuilder builder() {
        return new DraftFeatureBuilder();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DraftFeature)) {
            return false;
        }
        DraftFeature draftFeature = (DraftFeature) obj;
        String title = getTitle();
        String title2 = draftFeature.getTitle();
        if (title != null ? !title.equals(title2) : title2 != null) {
            return false;
        }
        title = getDescription();
        title2 = draftFeature.getDescription();
        if (title != null ? !title.equals(title2) : title2 != null) {
            return false;
        }
        Type type = getType();
        Type type2 = draftFeature.getType();
        if (type != null ? !type.equals(type2) : type2 != null) {
            return false;
        }
        title = getIdentifier();
        title2 = draftFeature.getIdentifier();
        if (title == null) {
            if (title2 == null) {
                return true;
            }
        } else if (title.equals(title2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String title = getTitle();
        int hashCode = (title == null ? 43 : title.hashCode()) + 59;
        String description = getDescription();
        hashCode = (description == null ? 43 : description.hashCode()) + (hashCode * 59);
        Type type = getType();
        hashCode = (type == null ? 43 : type.hashCode()) + (hashCode * 59);
        description = getIdentifier();
        hashCode *= 59;
        if (description != null) {
            i = description.hashCode();
        }
        return hashCode + i;
    }

    @ConstructorProperties({"title", "description", "type", "identifier", "options"})
    public DraftFeature(String str, String str2, Type type, String str3, @NonNull List<DraftOption> list) {
        if (list == null) {
            throw new NullPointerException("options");
        }
        this.title = str;
        this.description = str2;
        this.type = type;
        this.identifier = str3;
        this.options = list;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Type getType() {
        return this.type;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @NonNull
    public List<DraftOption> getOptions() {
        return this.options;
    }
}
