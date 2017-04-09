package com.ebay.classifieds.capi.users.cv;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "stored-cv", strict = false)
@Namespace(prefix = "cv", reference = "http://www.ebayclassifiedsgroup.com/schema/cv/v1")
public class StoredCV {
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/cv/v1")
    @Element(name = "content-type", required = false)
    private String contentType;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/cv/v1")
    @Element(name = "file-id", required = false)
    private String fileId;
    @Namespace(reference = "http://www.ebayclassifiedsgroup.com/schema/cv/v1")
    @Element(name = "file-name", required = false)
    private String fileName;
    @Element(name = "file-size", required = false)
    private long fileSize;

    public String getFileId() {
        return this.fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getContentType() {
        return this.contentType;
    }

    public long getFileSize() {
        return this.fileSize;
    }
}
