package com.ginkgooai.core.common.utils;

import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class UrlUtils {

	public static String appendQueryParam(String url, String paramName, String paramValue) {
		try {
			URI uri = new URI(url);
			String newQuery = uri.getQuery() == null ?
				paramName + "=" + URLEncoder.encode(paramValue, StandardCharsets.UTF_8.toString()) :
				uri.getQuery() + "&" + paramName + "=" + URLEncoder.encode(paramValue, StandardCharsets.UTF_8.toString());

			return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(),
				newQuery, uri.getFragment()).toString();
		} catch (Exception e) {
			return url + (url.contains("?") ? "&" : "?") + paramName + "=" + paramValue;
		}
	}
}
