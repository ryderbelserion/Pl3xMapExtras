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
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public record Banner(Position pos, Icon icon, String name) {

    public boolean isBanner(@NotNull World world) {
        return world.getBlockAt(pos().x(), pos().y(), pos().z()).getState() instanceof org.bukkit.block.Banner;
    }

    public static @NotNull Banner load(@NotNull DataInputStream in) throws IOException {
        return new Banner(Position.load(in), Icon.valueOf(in.readUTF()), in.readUTF());
    }

    public void save(@NotNull DataOutputStream out) throws IOException {
        pos().save(out);

        out.writeUTF(icon().name());
        out.writeUTF(name());
    }
}