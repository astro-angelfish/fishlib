/*
 * FishLib, a Bukkit development library
 * Copyright (C) Lucky_fish0w0
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

public class MappedMapRenderContextImpl implements MapRenderContext {
	private final MapRenderContextImpl mappedContext;
	private final Vector2i mappedFrom;

	private final Shape shape;

	public MappedMapRenderContextImpl(MapRenderContextImpl mappedContext, Vector2i mappedFrom, Vector2i mappedTo) {
		this.mappedContext = mappedContext;
		this.mappedFrom = mappedFrom;
		shape = new Rectangle2D.Double(mappedFrom.getX(), mappedFrom.getY(), mappedTo.getX(), mappedTo.getY());
	}


	@Override
	public void drawText(Vector2i location, String fontName, String text, Color color, int size) {
		Validate.notNull(location, "Cannot draw text to null.");
		Validate.notNull(fontName, "Cannot use a null font");
		Validate.notNull(text, "Cannot write null.");
		Validate.notNull(color, "The color cannot be null.");
		Validate.isTrue(size > 0, "The size of the text should be greater than zero.");

		Graphics2D graphics = mappedContext.getImage().createGraphics();
		Font font = new Font(fontName, Font.PLAIN, size);

		FontRenderContext fontRenderContext = graphics.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, fontRenderContext);
		double baseY = -bounds.getY();

		graphics.clip(shape);

		Vector2i mapped = getMappedLocation(location);
		graphics.drawString(text, mapped.getX(), (int) baseY + mapped.getY());
		graphics.dispose();
	}

	@Override
	public void drawImage(Vector2i location, Image image) {
		Validate.notNull(location, "Cannot draw image at nothing.");
		Validate.notNull(image, "Cannot draw nothing.");

		Graphics2D graphics = mappedContext.getImage().createGraphics();
		graphics.clip(shape);

		Vector2i mapped = getMappedLocation(location);
		graphics.drawImage(image, mapped.getX(), mapped.getY(), null);
		graphics.dispose();
	}

	@Override
	public void setColor(Vector2i location, Color color) {
		Validate.notNull(location, "Cannot set color in null position.");
		Validate.notNull(color, "Cannot display a null color.");

		Graphics2D graphics = mappedContext.getImage().createGraphics();
		graphics.clip(shape);

		graphics.setColor(color);

		Vector2i mapped = getMappedLocation(location);
		graphics.drawLine(mapped.getX(), mapped.getY(), mapped.getX(), mapped.getY());
		graphics.dispose();
	}

	@Override
	public void setColor(Vector2i from, Vector2i to, Color color) {
		Validate.notNull(from, "From position cannot be null.");
		Validate.notNull(to, "To position cannot be null.");
		Validate.notNull(color, "Cannot display a null color.");

		Graphics2D graphics = mappedContext.getImage().createGraphics();
		graphics.clip(shape);
		graphics.setColor(color);

		Vector2i mappedFrom = getMappedLocation(from);
		Vector2i mappedTo = getMappedLocation(to);
		graphics.setColor(color);
		graphics.fillRect(mappedFrom.getX(), mappedFrom.getY(), mappedTo.getX(), mappedTo.getY());
		graphics.dispose();
	}

	private Vector2i getMappedLocation(Vector2i vector) {
		return vector.clone().add(mappedFrom.getX(), mappedFrom.getY());
	}
}
