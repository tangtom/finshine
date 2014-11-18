/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.incito.finshine.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.FileEntity;

/**
 * Created with IntelliJ IDEA. User: wyouflf Date: 13-6-24 Time: 下午4:45
 */
public class UploadFileEntity extends FileEntity implements UploadEntity{

	public UploadFileEntity(File file, String contentType) {
		super(file, contentType);
		fileSize = file.length();
		uploadedSize = 0;
	}

	private long fileSize;
	private long uploadedSize;
	private RequestCallBackHandler callback = null;
	 
	@Override
	public void setCallBack(RequestCallBackHandler callBack) {
		this.callback = callBack;
	}

	public interface UploadEntity {
	    void setCallBack(RequestCallBackHandler callBack);
	}
	
	@Override
	public void writeTo(OutputStream outStream) throws IOException {
		if (outStream == null) {
			throw new IllegalArgumentException("Output stream may not be null");
		}
		InputStream inStream = new FileInputStream(this.file);
		try {
			byte[] tmp = new byte[4*1024];
			int len;
			while ((len = inStream.read(tmp)) != -1) {
				outStream.write(tmp, 0, len);
				uploadedSize += len;
				if (callback != null) {
					if (!callback.updateProgress(fileSize, uploadedSize, false)) {
						throw new IOException("stop");
					}
				}
			}
			outStream.flush();
			if (callback != null) {
				callback.updateProgress(fileSize, uploadedSize, true);
			}
		} finally {
			inStream.close();
		}
	}
}