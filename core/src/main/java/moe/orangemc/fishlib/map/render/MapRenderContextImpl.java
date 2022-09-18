/*
 * FishLib, a Bukkit development library
 * Copyright (C) Astro angelfish
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package moe.orangemc.fishlib.map.render;

import moe.orangemc.fishlib.map.MapRenderContext;
import moe.orangemc.fishlib.utils.Vector2i;
import org.apache.commons.lang3.Validate;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

public record MapRenderContextImpl(BufferedImage image) implements MapRenderContext {

	@Override
	public void drawText(Vector2i location, String fontName, String text, Color color, int size) {
		Validate.notNull(location, "Cannot draw text to null.");
		Validate.notNull(fontName, "Cannot use a null font.");
		Validate.notNull(text, "Cannot write null.");
		Validate.notNull(color, "The color cannot be null.");
		Validate.isTrue(size > 0, "The size of the text should be greater than zero.");

		Graphics2D gO = image.createGraphics();
		gO.setColor(color);
		Font font = new Font(fontName, Font.PLAIN, size);

		FontRenderContext fontRenderContext = gO.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, fontRenderContext);
		double baseY = -bounds.getY();

		gO.drawString(text, location.getX(), (int) baseY + location.getY());
		gO.dispose();
	}

	@Override
	public void drawImage(Vector2i location, Image image) {
		Validate.notNull(location, "Cannot draw image at nothing.");
		Validate.notNull(image, "Cannot draw nothing.");

		Graphics graphics = this.image.getGraphics();
		graphics.drawImage(image, location.getX(), location.getY(), null);
		graphics.dispose();
	}

	@Override
	public void setColor(Vector2i location, Color color) {
		Validate.notNull(location, "Cannot set color in null position.");
		Validate.notNull(color, "Cannot display a null color.");

		image.setRGB(location.getX(), location.getY(), color.getRGB());
	}

	@Override
	public void setColor(Vector2i from, Vector2i to, Color color) {
		Validate.notNull(from, "From position cannot be null.");
		Validate.notNull(to, "To position cannot be null.");
		Validate.notNull(color, "Cannot display a null color.");

		int width = to.getX() - from.getX();
		int height = to.getY() - from.getY();
		int[] rgbColors = new int[width * height];
		Arrays.fill(rgbColors, color.getRGB());
		image.setRGB(from.getX(), from.getY(), width, height, rgbColors, 0, 0);
	}

	public MappedMapRenderContextImpl createMappingContext(Vector2i from, Vector2i to) {
		return new MappedMapRenderContextImpl(this, from, to);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (MapRenderContextImpl) obj;
		return Objects.equals(this.image, that.image);
	}

	@Override
	public String toString() {
		return "MapRenderContextImpl[" + "image=" + image + ']';
	}
}
