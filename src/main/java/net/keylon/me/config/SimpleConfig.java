package net.keylon.me.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.bukkit.configuration.file.YamlConfiguration;

import net.keylon.me.Core;

public class SimpleConfig extends YamlConfiguration {

	private final File file;

	public SimpleConfig(String fileName) {
		this.file = extract(fileName);

		loadConfig();
	}

	public void reloadConfig() {
		saveConfig();
		loadConfig();
	}

	public void saveConfig() {
		try {
			super.save(file);
		} catch (final IOException ex) {
			System.out.println("Failed to save configuration from " + file);

			ex.printStackTrace();
		}
	}

	public void loadConfig() {
		try {
			super.load(file);
		} catch (final Throwable t) {
			System.out.println("Failed to load configuration from " + file);

			t.printStackTrace();
		}
	}
	public void write(String path, Object value) {
		set(path, value);

		reloadConfig();
	}

	private File extract(String path) {
		final File file = new File(Core.getInstance().getDataFolder(), path);

		if (file.exists())
			return file;

		final InputStream is = Core.getInstance().getResource(path);
		Objects.requireNonNull(is, "Inbuilt file not found: " + file);

		try {
			createFile(path);

			Files.copy(is, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);

		} catch (final IOException ex) {
			ex.printStackTrace();
		}

		return file;

	}

	private File createFile(String path) {
		final File datafolder = Core.getInstance().getDataFolder();
		final int lastIndex = path.lastIndexOf('/');
		final File directory = new File(datafolder, path.substring(0, lastIndex >= 0 ? lastIndex : 0));

		directory.mkdirs();

		final File destination = new File(datafolder, path);

		try {
			destination.createNewFile();
		} catch (final IOException ex) {
			System.out.println("Failed to create file " + path);

			ex.printStackTrace();
		}
		return destination;
	}


}
