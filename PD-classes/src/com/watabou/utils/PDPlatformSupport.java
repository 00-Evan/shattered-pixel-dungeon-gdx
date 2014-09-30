package com.watabou.utils;

import com.badlogic.gdx.InputProcessor;

public class PDPlatformSupport {
	private final String version;
	private final String basePath;
	private final InputProcessor inputProcessor;

	public PDPlatformSupport(String version, String basePath, InputProcessor inputProcessor) {
		this.version = version;
		this.basePath = basePath;
		this.inputProcessor = inputProcessor;
	}

	public String getVersion() {
		return version;
	}

	public String getBasePath() {
		return basePath;
	}

	public InputProcessor getInputProcessor() {
		return inputProcessor;
	}
}
