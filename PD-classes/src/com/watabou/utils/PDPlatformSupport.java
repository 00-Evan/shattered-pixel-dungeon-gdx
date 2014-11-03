package com.watabou.utils;

import com.watabou.input.NoosaInputProcessor;

public class PDPlatformSupport<GameActionType> {
	private final String version;
    private final int versionCode;
	private final String basePath;
	private final NoosaInputProcessor<GameActionType> inputProcessor;

	public PDPlatformSupport(String version, int versionCode, String basePath, NoosaInputProcessor<GameActionType> inputProcessor) {
		this.version = version;
        this.versionCode = versionCode;
		this.basePath = basePath;
		this.inputProcessor = inputProcessor;
	}

	public String getVersion() {
		return version;
	}

    public int getVersionCode() { return versionCode; }

	public String getBasePath() {
		return basePath;
	}

	public NoosaInputProcessor<GameActionType> getInputProcessor() {
		return inputProcessor;
	}

	public boolean isFullscreenEnabled() {
		return false;
	}
}
