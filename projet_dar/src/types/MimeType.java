package types;

public enum MimeType {

	AUDIO_AAC("audio/aac"),
	APPLICATION_X_ABIWORD("application/x-abiword"),
	APPLICATION_OCTET_STREAM("application/octet-stream"),
	VIDEO_X_MSVIDEO("video/x-msvideo"),
	APPLICATION_VND_AMAZON_EBOOK("application/vnd.amazon.ebook"),
	APPLICATION_X_BZIP("application/x-bzip"),
	APPLICATION_X_BZIP2("application/x-bzip2"),
	APPLICATION_X_CSH("application/x-csh"),
	TEXT_CSS("text/css"),
	TEXT_CSV("text/csv"),
	APPLICATION_MSWORD("application/msword"),
	APPLICATION_EPUB_ZIP("application/epub+zip"),
	IMAGE_GIF("image/gif"),
	TEXT_HTML("text/html"),
	IMAGE_X_ICON("image/x-icon"),
	TEXT_CALENDAR("text/calendar"),
	APPLICATION_JAVA_ARCHIVE("application/java-archive"),
	IMAGE_JPEG("image/jpeg"),
	APPLICATION_JS("application/js"),
	APPLICATION_JSON("application/json"),
	AUDIO_MIDI("audio/midi"),
	VIDEO_MPEG("video/mpeg"),
	APPLICATION_VND_APPLE_INSTALLER_XML("application/vnd.apple.installer+xml"),
	APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"),
	APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
	APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
	AUDIO_OGG("audio/ogg"),
	VIDEO_OGG("video/ogg"),
	APPLICATION_OGG("application/ogg"),
	APPLICATION_PDF("application/pdf"),
	APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
	APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed"),
	APPLICATION_RTF("application/rtf"),
	APPLICATION_X_SH("application/x-sh"),
	IMAGE_SVG_XML("image/svg+xml"),
	APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash"),
	APPLICATION_X_TAR("application/x-tar"),
	IMAGE_TIFF("image/tiff"),
	APPLICATION_X_FONT_TTF("application/x-font-ttf"),
	APPLICATION_VND_VISIO("application/vnd.visio"),
	AUDIO_X_WAV("audio/x-wav"),
	AUDIO_WEBM("audio/webm"),
	VIDEO_WEBM("video/webm"),
	IMAGE_WEBP("image/webp"),
	APPLICATION_X_FONT_WOFF("application/x-font-woff"),
	APPLICATION_XHTML_XML("application/xhtml+xml"),
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
	APPLICATION_XML("application/xml"),
	APPLICATION_VND_MOZILLA_XUL_XML("application/vnd.mozilla.xul=xml"),
	APPLICATION_ZIP("application/zip"),
	VIDEO_3GPP("video/3gpp"),
	VIDEO("video"),
	VIDEO_3GPP2("video/3gpp2"),
	APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");
	
	private String name;

	private MimeType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
