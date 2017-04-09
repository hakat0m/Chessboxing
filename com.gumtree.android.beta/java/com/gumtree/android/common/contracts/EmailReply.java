package com.gumtree.android.common.contracts;

import com.gumtree.android.reply.ReplyMetadataField;
import java.util.Collection;

public interface EmailReply {
    void sendReply(Collection<ReplyMetadataField> collection);
}
