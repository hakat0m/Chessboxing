package com.ebay.classifieds.capi.suggestions;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "sug:suggestions", strict = false)
@Namespace(prefix = "sug", reference = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1")
public class LocationSuggestions {
    @ElementList(inline = true, required = false)
    private List<LocationSuggestion> suggestions;

    public List<LocationSuggestion> getSuggestions() {
        return this.suggestions;
    }
}
