package com.incito.finshine.download.provider;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * the model of the downloads
 * 
 * @author qiujiaheng
 * 
 */
public class FileModel implements Parcelable, Serializable {
    private static final long serialVersionUID = 3319267082093416699L;
    public final String mId;
    public final String mUri;
    public final String mName;

    public FileModel(String mId, String mUri, String mName) {
        super();
        this.mId = mId;
        this.mUri = mUri;
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUri);
        dest.writeString(mName);
    }

}
