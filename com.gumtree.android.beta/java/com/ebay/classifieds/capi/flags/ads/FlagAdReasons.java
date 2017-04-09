package com.ebay.classifieds.capi.flags.ads;

import java.io.Serializable;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "getFlagAdReasons", strict = false)
@Namespace(prefix = "flag", reference = "http://www.ebayclassifiedsgroup.com/schema/flag/v1")
public class FlagAdReasons implements Serializable {
    @ElementList(entry = "reason", inline = true, required = false)
    private List<Reason> reasons;

    @Root(name = "reason", strict = false)
    public class Reason implements Serializable {
        @Element(name = "id-name", required = false)
        private String idName;
        @Element(name = "localized-name", required = false)
        private String localizedName;

        public String getIdName() {
            return this.idName;
        }

        public String getLocalizedName() {
            return this.localizedName;
        }

        public boolean equals(Object obj) {
            if (obj instanceof Reason) {
                return ((Reason) obj).idName.equals(this.idName);
            }
            return super.equals(obj);
        }

        public int hashCode() {
            return this.idName.hashCode();
        }
    }

    public List<Reason> getReasons() {
        return this.reasons;
    }
}
