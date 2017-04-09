package com.facebook.stetho.inspector.protocol.module;

import com.facebook.stetho.common.ListUtil;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.elements.Document;
import com.facebook.stetho.inspector.elements.Origin;
import com.facebook.stetho.inspector.helper.ChromePeerManager;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcResult;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsMethod;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.json.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class CSS implements ChromeDevtoolsDomain {
    private final Document mDocument;
    private final ObjectMapper mObjectMapper = new ObjectMapper();
    private final ChromePeerManager mPeerManager = new ChromePeerManager();

    class CSSComputedStyleProperty {
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String value;

        private CSSComputedStyleProperty() {
        }
    }

    class CSSProperty {
        @JsonProperty
        public Boolean disabled;
        @JsonProperty
        public Boolean implicit;
        @JsonProperty
        public Boolean important;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty
        public Boolean parsedOk;
        @JsonProperty
        public SourceRange range;
        @JsonProperty
        public String text;
        @JsonProperty(required = true)
        public String value;

        private CSSProperty() {
        }
    }

    class CSSRule {
        @JsonProperty
        public Origin origin;
        @JsonProperty(required = true)
        public SelectorList selectorList;
        @JsonProperty
        public CSSStyle style;
        @JsonProperty
        public String styleSheetId;

        private CSSRule() {
        }
    }

    class CSSStyle {
        @JsonProperty(required = true)
        public List<CSSProperty> cssProperties;
        @JsonProperty
        public String cssText;
        @JsonProperty
        public SourceRange range;
        @JsonProperty
        public List<ShorthandEntry> shorthandEntries;
        @JsonProperty
        public String styleSheetId;

        private CSSStyle() {
        }
    }

    class GetComputedStyleForNodeRequest {
        @JsonProperty(required = true)
        public int nodeId;

        private GetComputedStyleForNodeRequest() {
        }
    }

    class GetComputedStyleForNodeResult implements JsonRpcResult {
        @JsonProperty(required = true)
        public List<CSSComputedStyleProperty> computedStyle;

        private GetComputedStyleForNodeResult() {
        }
    }

    class GetMatchedStylesForNodeRequest implements JsonRpcResult {
        @JsonProperty
        public Boolean excludeInherited;
        @JsonProperty
        public Boolean excludePseudo;
        @JsonProperty(required = true)
        public int nodeId;

        private GetMatchedStylesForNodeRequest() {
        }
    }

    class GetMatchedStylesForNodeResult implements JsonRpcResult {
        @JsonProperty
        public List<InheritedStyleEntry> inherited;
        @JsonProperty
        public List<RuleMatch> matchedCSSRules;
        @JsonProperty
        public List<PseudoIdMatches> pseudoElements;

        private GetMatchedStylesForNodeResult() {
        }
    }

    class InheritedStyleEntry {
        @JsonProperty(required = true)
        public CSSStyle inlineStyle;
        @JsonProperty(required = true)
        public List<RuleMatch> matchedCSSRules;

        private InheritedStyleEntry() {
        }
    }

    class PseudoIdMatches {
        @JsonProperty(required = true)
        public List<RuleMatch> matches = new ArrayList();
        @JsonProperty(required = true)
        public int pseudoId;
    }

    class RuleMatch {
        @JsonProperty
        public List<Integer> matchingSelectors;
        @JsonProperty
        public CSSRule rule;

        private RuleMatch() {
        }
    }

    class Selector {
        @JsonProperty
        public SourceRange range;
        @JsonProperty(required = true)
        public String value;

        private Selector() {
        }
    }

    class SelectorList {
        @JsonProperty
        public List<Selector> selectors;
        @JsonProperty
        public String text;

        private SelectorList() {
        }
    }

    class ShorthandEntry {
        @JsonProperty
        public Boolean imporant;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String value;

        private ShorthandEntry() {
        }
    }

    class SourceRange {
        @JsonProperty(required = true)
        public int endColumn;
        @JsonProperty(required = true)
        public int endLine;
        @JsonProperty(required = true)
        public int startColumn;
        @JsonProperty(required = true)
        public int startLine;

        private SourceRange() {
        }
    }

    public CSS(Document document) {
        this.mDocument = (Document) Util.throwIfNull(document);
        this.mPeerManager.setListener(new PeerManagerListener(this, null));
    }

    @ChromeDevtoolsMethod
    public void enable(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
    }

    @ChromeDevtoolsMethod
    public void disable(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult getComputedStyleForNode(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
        GetComputedStyleForNodeRequest getComputedStyleForNodeRequest = (GetComputedStyleForNodeRequest) this.mObjectMapper.convertValue(jSONObject, GetComputedStyleForNodeRequest.class);
        GetComputedStyleForNodeResult getComputedStyleForNodeResult = new GetComputedStyleForNodeResult();
        getComputedStyleForNodeResult.computedStyle = new ArrayList();
        this.mDocument.postAndWait(new 1(this, getComputedStyleForNodeRequest, getComputedStyleForNodeResult));
        return getComputedStyleForNodeResult;
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult getMatchedStylesForNode(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
        GetMatchedStylesForNodeRequest getMatchedStylesForNodeRequest = (GetMatchedStylesForNodeRequest) this.mObjectMapper.convertValue(jSONObject, GetMatchedStylesForNodeRequest.class);
        GetMatchedStylesForNodeResult getMatchedStylesForNodeResult = new GetMatchedStylesForNodeResult();
        RuleMatch ruleMatch = new RuleMatch();
        RuleMatch accessibilityRuleMatch = getAccessibilityRuleMatch();
        getMatchedStylesForNodeResult.matchedCSSRules = ListUtil.newImmutableList(ruleMatch, accessibilityRuleMatch);
        ruleMatch.matchingSelectors = ListUtil.newImmutableList(Integer.valueOf(0));
        Selector selector = new Selector();
        selector.value = "<this_element>";
        CSSRule cSSRule = new CSSRule();
        cSSRule.origin = Origin.REGULAR;
        cSSRule.selectorList = new SelectorList();
        cSSRule.selectorList.selectors = ListUtil.newImmutableList(selector);
        cSSRule.style = new CSSStyle();
        cSSRule.style.cssProperties = new ArrayList();
        ruleMatch.rule = cSSRule;
        cSSRule.style.shorthandEntries = Collections.emptyList();
        this.mDocument.postAndWait(new 2(this, getMatchedStylesForNodeRequest, ruleMatch, accessibilityRuleMatch));
        getMatchedStylesForNodeResult.inherited = Collections.emptyList();
        getMatchedStylesForNodeResult.pseudoElements = Collections.emptyList();
        return getMatchedStylesForNodeResult;
    }

    private RuleMatch getAccessibilityRuleMatch() {
        Selector selector = new Selector();
        selector.value = "Accessibility Properties";
        CSSRule cSSRule = new CSSRule();
        cSSRule.origin = Origin.REGULAR;
        cSSRule.selectorList = new SelectorList();
        cSSRule.selectorList.selectors = ListUtil.newImmutableList(selector);
        cSSRule.style = new CSSStyle();
        cSSRule.style.cssProperties = new ArrayList();
        cSSRule.style.shorthandEntries = Collections.emptyList();
        RuleMatch ruleMatch = new RuleMatch();
        ruleMatch.matchingSelectors = ListUtil.newImmutableList(Integer.valueOf(0));
        ruleMatch.rule = cSSRule;
        return ruleMatch;
    }
}
