/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryderbelserion.map.markers.banners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;

public record Position(int x, int y, int z) {
    public @NotNull Point toPoint() {
        return Point.of(x(), z());
    }

    public static @NotNull Position load(@NotNull DataInputStream in) throws IOException {
        return new Position(in.readInt(), in.readInt(), in.readInt());
    }

    public void save(@NotNull DataOutputStream out) throws IOException {
        out.writeInt(x());
        out.writeInt(y());
        out.writeInt(z());
    }
}
