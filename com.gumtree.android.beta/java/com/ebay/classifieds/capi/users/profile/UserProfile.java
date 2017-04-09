package com.ebay.classifieds.capi.users.profile;

import java.beans.ConstructorProperties;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "user-profile", strict = false)
@Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
public class UserProfile {
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-display-name", required = false)
    private String displayName;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "hashed-user-email-hex", required = false)
    private String hashedUserEmailHex;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-id", required = false)
    private String id;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-nickname", required = false)
    private String nickName;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-phone-number", required = false)
    private String phoneNumber;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "primary-contact-email", required = false)
    private String primaryContactEmail;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "send-marketing-email", required = false)
    private boolean sendMarketingEmail;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-active-ad-count", required = false)
    private String userActiveAdCount;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-address", required = false)
    private UserAddress userAddress;
    @Namespace(prefix = "user", reference = "http://www.ebayclassifiedsgroup.com/schema/user/v1")
    @Element(name = "user-email", required = false)
    private String userEmail;

    protected boolean canEqual(Object obj) {
        return obj instanceof UserProfile;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserProfile)) {
            return false;
        }
        UserProfile userProfile = (UserProfile) obj;
        if (!userProfile.canEqual(this)) {
            return false;
        }
        String id = getId();
        String id2 = userProfile.getId();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getDisplayName();
        id2 = userProfile.getDisplayName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getNickName();
        id2 = userProfile.getNickName();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPhoneNumber();
        id2 = userProfile.getPhoneNumber();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getUserEmail();
        id2 = userProfile.getUserEmail();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getPrimaryContactEmail();
        id2 = userProfile.getPrimaryContactEmail();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        id = getHashedUserEmailHex();
        id2 = userProfile.getHashedUserEmailHex();
        if (id != null ? !id.equals(id2) : id2 != null) {
            return false;
        }
        UserAddress userAddress = getUserAddress();
        UserAddress userAddress2 = userProfile.getUserAddress();
        if (userAddress != null ? !userAddress.equals(userAddress2) : userAddress2 != null) {
            return false;
        }
        if (isSendMarketingEmail() != userProfile.isSendMarketingEmail()) {
            return false;
        }
        id = getUserActiveAdCount();
        id2 = userProfile.getUserActiveAdCount();
        if (id == null) {
            if (id2 == null) {
                return true;
            }
        } else if (id.equals(id2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 43;
        String id = getId();
        int hashCode = (id == null ? 43 : id.hashCode()) + 59;
        String displayName = getDisplayName();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        displayName = getNickName();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        displayName = getPhoneNumber();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        displayName = getUserEmail();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        displayName = getPrimaryContactEmail();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        displayName = getHashedUserEmailHex();
        hashCode = (displayName == null ? 43 : displayName.hashCode()) + (hashCode * 59);
        UserAddress userAddress = getUserAddress();
        hashCode = (isSendMarketingEmail() ? 79 : 97) + (((userAddress == null ? 43 : userAddress.hashCode()) + (hashCode * 59)) * 59);
        displayName = getUserActiveAdCount();
        hashCode *= 59;
        if (displayName != null) {
            i = displayName.hashCode();
        }
        return hashCode + i;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public void setHashedUserEmailHex(String str) {
        this.hashedUserEmailHex = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setNickName(String str) {
        this.nickName = str;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public void setPrimaryContactEmail(String str) {
        this.primaryContactEmail = str;
    }

    public void setSendMarketingEmail(boolean z) {
        this.sendMarketingEmail = z;
    }

    public void setUserActiveAdCount(String str) {
        this.userActiveAdCount = str;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserEmail(String str) {
        this.userEmail = str;
    }

    public String toString() {
        return "UserProfile(id=" + getId() + ", displayName=" + getDisplayName() + ", nickName=" + getNickName() + ", phoneNumber=" + getPhoneNumber() + ", userEmail=" + getUserEmail() + ", primaryContactEmail=" + getPrimaryContactEmail() + ", hashedUserEmailHex=" + getHashedUserEmailHex() + ", userAddress=" + getUserAddress() + ", sendMarketingEmail=" + isSendMarketingEmail() + ", userActiveAdCount=" + getUserActiveAdCount() + ")";
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getPrimaryContactEmail() {
        return this.primaryContactEmail;
    }

    public String getHashedUserEmailHex() {
        return this.hashedUserEmailHex;
    }

    public UserAddress getUserAddress() {
        return this.userAddress;
    }

    public boolean isSendMarketingEmail() {
        return this.sendMarketingEmail;
    }

    public String getUserActiveAdCount() {
        return this.userActiveAdCount;
    }

    @ConstructorProperties({"user-display-name", "user-nickname", "send-marketing-email"})
    public UserProfile(@Element(name = "user-display-name") String str, @Element(name = "user-nickname") String str2, @Element(name = "send-marketing-email") boolean z) {
        this.displayName = str;
        this.nickName = str2;
        this.sendMarketingEmail = z;
    }
}
