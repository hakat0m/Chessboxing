package com.gumtree.android.features;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Feature implements Serializable {
    private String categoryLocation;
    private String description;
    private List<FeatureDurationOption> durationOptions;
    private String id;
    private boolean isPurchased;
    private boolean isSelected;
    private String longDescription;
    private String name;

    public enum FeatureType {
        HIGHLIGHT("AD_GP_HP_GALLERY"),
        URGENT("AD_URGENT"),
        TOP_AD("AD_GP_TOP_AD"),
        BUMP_UP("AD_BUMP_UP"),
        INSERTION("AD_INSERTION");
        
        private String value;

        private FeatureType(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }

    public Feature() {
        this.durationOptions = new ArrayList();
    }

    public Feature(String str) {
        this();
        setName(str);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<FeatureDurationOption> getDurationOptions() {
        return this.durationOptions;
    }

    public void setDurationOptions(List<FeatureDurationOption> list) {
        this.durationOptions = list;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean isPurchased() {
        return this.isPurchased;
    }

    public void setPurchased(boolean z) {
        this.isPurchased = z;
    }

    public String toString() {
        return "Feature{, name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", isPurchased=" + this.isPurchased + '}';
    }

    public FeatureDurationOption hasFeatureDurationOptionChecked() {
        for (FeatureDurationOption featureDurationOption : getDurationOptions()) {
            if (featureDurationOption.isChecked()) {
                return featureDurationOption;
            }
        }
        return null;
    }

    public void setChecked(int i, boolean z) {
        if (i > 0) {
            for (FeatureDurationOption featureDurationOption : getDurationOptions()) {
                if (featureDurationOption.getDurationDays() == i) {
                    featureDurationOption.setCheckStatus(z);
                } else {
                    featureDurationOption.setCheckStatus(!z);
                }
            }
        } else if (z) {
            findNoDefaultDuration().setCheckStatus(true);
        }
    }

    private FeatureDurationOption findNoDefaultDuration() {
        FeatureDurationOption firstOption = getFirstOption();
        if (getDurationOptions().size() > 0) {
            List arrayList = new ArrayList();
            for (FeatureDurationOption featureDurationOption : getDurationOptions()) {
                if (featureDurationOption.getParentName().equalsIgnoreCase(FeatureType.TOP_AD.getValue()) && featureDurationOption.isChecked()) {
                    arrayList.add(featureDurationOption);
                }
            }
            if (arrayList.size() >= 1) {
                return (FeatureDurationOption) arrayList.get(arrayList.size() - 1);
            }
        }
        return firstOption;
    }

    private FeatureDurationOption getFirstOption() {
        return (FeatureDurationOption) getDurationOptions().get(0);
    }

    public int getFeatureIndexFeatureSelected() {
        int indexOf = getDurationOptions().indexOf(findNoDefaultDuration());
        return indexOf >= 0 ? indexOf : 0;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public void setLongDescription(String str) {
        this.longDescription = str;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getCategoryLocation() {
        return this.categoryLocation;
    }

    public void setCategoryLocation(String str) {
        this.categoryLocation = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this.name.equals(((Feature) obj).name)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
