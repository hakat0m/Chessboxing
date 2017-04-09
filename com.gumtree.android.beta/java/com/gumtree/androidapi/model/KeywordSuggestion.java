package com.gumtree.androidapi.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KeywordSuggestion {
    @SerializedName("suggestionTrackingId")
    private String suggestionTrackingId = null;
    @SerializedName("suggestions")
    private List<Suggestion> suggestions = new ArrayList();

    public KeywordSuggestion suggestions(List<Suggestion> list) {
        this.suggestions = list;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public List<Suggestion> getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(List<Suggestion> list) {
        this.suggestions = list;
    }

    public KeywordSuggestion suggestionTrackingId(String str) {
        this.suggestionTrackingId = str;
        return this;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getSuggestionTrackingId() {
        return this.suggestionTrackingId;
    }

    public void setSuggestionTrackingId(String str) {
        this.suggestionTrackingId = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KeywordSuggestion keywordSuggestion = (KeywordSuggestion) obj;
        if (Objects.equals(this.suggestions, keywordSuggestion.suggestions) && Objects.equals(this.suggestionTrackingId, keywordSuggestion.suggestionTrackingId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.suggestions, this.suggestionTrackingId});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("class KeywordSuggestion {\n");
        stringBuilder.append("    suggestions: ").append(toIndentedString(this.suggestions)).append("\n");
        stringBuilder.append("    suggestionTrackingId: ").append(toIndentedString(this.suggestionTrackingId)).append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toIndentedString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.toString().replace("\n", "\n    ");
    }
}
