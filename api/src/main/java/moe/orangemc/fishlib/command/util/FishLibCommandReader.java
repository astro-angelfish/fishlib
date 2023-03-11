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

package moe.orangemc.fishlib.command.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class FishLibCommandReader extends StringReader {
	private final Plugin owner;
	private final CommandSender sender;

	public FishLibCommandReader(StringReader other, Plugin owner, CommandSender sender) {
		super(other);
		this.owner = owner;
		this.sender = sender;
	}

	public FishLibCommandReader(String string, Plugin owner, CommandSender sender) {
		super(string);
		this.owner = owner;
		this.sender = sender;
	}

	@Override
	public void setCursor(int cursor) {
		if (cursor >= this.getTotalLength()) {
			throw new StringIndexOutOfBoundsException(cursor);
		}
		super.setCursor(cursor);
	}

	@Override
	public int getRemainingLength() {
		return super.getRemainingLength();
	}

	@Override
	public int getTotalLength() {
		return super.getTotalLength();
	}

	@Override
	public int getCursor() {
		return super.getCursor();
	}

	@Override
	public String getRead() {
		return super.getRead();
	}

	@Override
	public String getRemaining() {
		return super.getRemaining();
	}

	@Override
	public boolean canRead(int length) {
		return super.canRead(length);
	}

	@Override
	public boolean canRead() {
		return super.canRead();
	}

	@Override
	public char peek() {
		return super.peek();
	}

	@Override
	public char peek(int offset) {
		return super.peek(offset);
	}

	@Override
	public char read() {
		return super.read();
	}

	@Override
	public void skip() {
		super.skip();
	}

	@Override
	public void skipWhitespace() {
		super.skipWhitespace();
	}

	@Override
	public int readInt() throws CommandSyntaxException {
		try {
			return super.readInt();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.int.expected", "Integer expected", this);
			return 0;
		}
	}

	@Override
	public long readLong() throws CommandSyntaxException {
		try {
			return super.readLong();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.long.expected", "Long expected", this);
			return 0;
		}
	}

	@Override
	public double readDouble() throws CommandSyntaxException {
		try {
			return super.readDouble();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.double.expected", "Double expected", this);
			return 0;
		}
	}

	@Override
	public float readFloat() throws CommandSyntaxException {
		try {
			return super.readFloat();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.float.expected", "Float expected", this);
			return 0;
		}
	}

	@Override
	public String readUnquotedString() {
		return super.readUnquotedString();
	}

	@Override
	public String readQuotedString() throws CommandSyntaxException {
		try {
			return super.readQuotedString();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.string_quote.expected", "Quote expected at the beginning", this);
			return "=w=";
		}
	}

	@Override
	public String readStringUntil(char terminator) throws CommandSyntaxException {
		try {
			return super.readStringUntil(terminator);
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.string_escape.invalid", "Special escape characters are not supported", this);
			return "OwO";
		}
	}

	@Override
	public String readString() throws CommandSyntaxException {
		try {
			return super.readString();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.string_escape.invalid", "Special escape characters are not supported", this);
			return "OwO";
		}
	}

	@Override
	public boolean readBoolean() throws CommandSyntaxException {
		try {
			return super.readBoolean();
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.float.expected", "Boolean expected", this);
			return false;
		}
	}

	@Override
	public void expect(char c) throws CommandSyntaxException {
		try {
			super.expect(c);
		} catch (CommandSyntaxException e) {
			CommandSyntaxExceptionBuilder.raise(this.owner, this.sender, "argument.char.expected", "'\" + c + \"' expected", this, c);
		}
	}

	public Plugin getOwner() {
		return owner;
	}

	public CommandSender getSender() {
		return sender;
	}
}
