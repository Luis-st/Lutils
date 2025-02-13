/*
 * LUtils
 * Copyright (C) 2025 Luis Staudt
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package net.luis.utils.io.data.yaml;

import net.luis.utils.io.data.yaml.exception.YamlTypeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 *
 * @author Luis-St
 *
 */

public class YamlStructure extends AbstractYamlNode implements YamlNode {
	
	private final String key;
	private final YamlNode node;
	private final boolean isAnchorOnly;
	
	public YamlStructure(@NotNull String key, @NotNull String anchor) {
		this.key = Objects.requireNonNull(key, "Key must not be null");
		this.node = new YamlScalar(Objects.requireNonNull(anchor, "Anchor must not be null"));
		this.isAnchorOnly = true;
	}
	
	public YamlStructure(@NotNull String key, @NotNull YamlNode node) {
		this.key = Objects.requireNonNull(key, "Key must not be null");
		this.node = Objects.requireNonNull(node, "Node must not be null");
		if (this.node instanceof YamlStructure) {
			throw new YamlTypeException("Node must not be a yaml structure");
		}
		this.isAnchorOnly = false;
	}
	
	@Override
	public boolean hasAnchor() {
		return this.node.hasAnchor();
	}
	
	@Override
	public @Nullable String getAnchor() {
		return this.node.getAnchor();
	}
	
	@Override
	public void setAnchor(@Nullable String anchor) {
		this.node.setAnchor(anchor);
	}
	
	public @NotNull String getKey() {
		return this.key;
	}
	
	public @NotNull YamlNode getNode() {
		if (this.isAnchorOnly) {
			throw new YamlTypeException("Structure does only contain an anchor");
		}
		return this.node;
	}
	
	//region Object overrides
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof YamlStructure yamlStructure)) return false;
		
		if (!this.key.equals(yamlStructure.key)) return false;
		return this.node.equals(yamlStructure.node);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.node);
	}
	
	@Override
	public String toString() {
		return this.toString(YamlConfig.DEFAULT);
	}
	
	@Override
	public @NotNull String toString(@NotNull YamlConfig config) {
		Objects.requireNonNull(config, "Config must not be null");
		String base = this.key + ":";
		if ((this.node instanceof YamlSequence || this.node instanceof YamlMapping) && !this.node.hasAnchor()) {
			base += System.lineSeparator() + config.indent();
		} else {
			base += " ";
		}
		String node = this.node.toString(config);
		if (this.isAnchorOnly) {
			YamlScalar scalar = this.node.getAsYamlScalar();
			if (scalar.hasAnchor()) {
				base += "&" + scalar.getAnchor() + " ";
			}
			return base + "*" + scalar.getAsString();
		}
		return base + this.node.toString(config).replace(System.lineSeparator(), System.lineSeparator() + config.indent()).stripTrailing();
	}
	//endregion
}
