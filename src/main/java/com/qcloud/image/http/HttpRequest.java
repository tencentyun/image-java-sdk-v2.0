package com.qcloud.image.http;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequest {
	private String url = "";
        private String imageKey = "image";
	private HttpMethod method = HttpMethod.POST;
	private HttpContentType contentType = HttpContentType.MULTIPART_FORM_DATA;
	private Map<String, String> headers = new LinkedHashMap<>();
	private Map<String, Object> params = new LinkedHashMap<>();
              
        private boolean isUrl;
        private File image ;
        private HashMap<String, String> keyList = new HashMap<String, String>(); 
        private ArrayList<String> urlList = new ArrayList<String>();
        private HashMap<String, File> imageList = new HashMap<String, File>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
        
        public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public HttpContentType getContentType() {
		return contentType;
	}

	public void setContentType(HttpContentType contentType) {
		this.contentType = contentType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public void addParam(String key, Object value) {
		this.params.put(key, value);
	}
        
        public ArrayList<String> getUrlList() {
            return urlList;
        }
        
        public File getImage() {
            return image;
        }      

        public void setUrlList(ArrayList<String> urlList) {
            isUrl = true;
            for(String url : urlList){
                this.urlList.add(url);
            }
        }

        public HashMap<String, File> getImageList() {
            return imageList;
        }
        
        public void setImageList(HashMap<String, File> imageList) {
            isUrl = false;
            this.imageList.putAll(imageList);
        }   
        
        public void setKeyList(HashMap<String, String> keyList) {
            this.keyList.putAll(keyList);
        }
        
        public HashMap<String, String> getKeyList() {
            return keyList;
        }
        
        public void setImage(File image) {
            isUrl = false;
            this.image = image;
        } 

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("url:").append(url).append(", method:").append(method).append(", ConentType:")
				.append(contentType.toString()).append("\n");

		sb.append("Headers:\n");
		for (Entry<String, String> entry : headers.entrySet()) {
			sb.append("key:").append(entry.getKey());
			sb.append(", value:").append(entry.getValue());
			sb.append("\n");
		}

		sb.append("params:\n");
		for (Entry<String, Object> entry : params.entrySet()) {
			sb.append("key:").append(entry.getKey());
			sb.append(", value:").append(entry.getValue());
			sb.append("\n");
		}
                
                if(isUrl){
                    sb.append(", [");
                    for(String url : urlList){
                        sb.append(url).append(", ");
                    }
                    sb.append("]");
                }else{
                    sb.append(", [");
                    for(String name : imageList.keySet()){
                        sb.append(name).append(", ");
                    }
                    sb.append("]");
                }

		return sb.toString();
	}
}
