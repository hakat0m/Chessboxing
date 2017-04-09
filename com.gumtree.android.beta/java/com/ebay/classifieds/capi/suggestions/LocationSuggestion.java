package com.ebay.classifieds.capi.suggestions;

import com.ebay.classifieds.capi.locations.Location;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "location-suggestion", strict = false)
@Namespace(prefix = "sug", reference = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1")
public class LocationSuggestion {
    @Namespace(prefix = "sug", reference = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1")
    @Element(name = "keyword", required = false)
    private String keyword;
    @Namespace(prefix = "sug", reference = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1")
    @Element(name = "location", required = false)
    private Location location;
    @Namespace(prefix = "sug", reference = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1")
    @Element(name = "suggestion-type", required = false)
    private String suggestionType;

    public String getKeyword() {
        return this.keyword;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getSuggestionType() {
        return this.suggestionType;
    }
}
