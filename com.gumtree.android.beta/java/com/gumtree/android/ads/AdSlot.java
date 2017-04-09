package com.gumtree.android.ads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdSlot implements Serializable {
    private List<AdSlotItem> adSlot = new ArrayList();

    public class AdSlotItem implements Serializable {
        private String adSlotType;
        private VipCustomTab vipCustomTab;

        public class VipCustomTab implements Serializable {
            private String id;
            private String labelIcon;
            private String labelText;
            private String targetUrl;

            public String getId() {
                return this.id;
            }

            public String getLabelText() {
                return this.labelText;
            }

            public String getLabelIcon() {
                return this.labelIcon;
            }

            public String getTargetUrl() {
                return this.targetUrl;
            }

            public void setId(String str) {
                this.id = str;
            }

            public void setLabelText(String str) {
                this.labelText = str;
            }

            public void setLabelIcon(String str) {
                this.labelIcon = str;
            }

            public void setTargetUrl(String str) {
                this.targetUrl = str;
            }
        }

        public String getAdSlotType() {
            return this.adSlotType;
        }

        public VipCustomTab getVipCustomTab() {
            return this.vipCustomTab;
        }

        public void setAdSlotType(String str) {
            this.adSlotType = str;
        }

        public void setVipCustomTab(VipCustomTab vipCustomTab) {
            this.vipCustomTab = vipCustomTab;
        }
    }

    public List<AdSlotItem> getAdSlotItemList() {
        return this.adSlot;
    }

    public void setAdSlotItemList(List<AdSlotItem> list) {
        this.adSlot = list;
    }
}
