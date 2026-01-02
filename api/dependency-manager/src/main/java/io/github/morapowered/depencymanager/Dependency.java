/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025-2026 Pedro Souza
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

package io.github.morapowered.depencymanager;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
public interface Dependency {

    static Builder builder() {
        return new DependencyImpl.BuilderImpl();
    }

    static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @NotNull String getGroupId();

    @NotNull String getArtifactId();

    @NotNull String getVersion();

    default @NotNull String getCoordinates() {
        return getGroupId() + ":" + getArtifactId() + ":" + getVersion();
    }

    byte[] getChecksum();

    @NotNull String getMavenRepositoryPath();

    interface Builder {

        @Contract("_ -> this")
        @NotNull Builder groupId(final @NotNull String groupId);

        @Contract("_ -> this")
        @NotNull Builder artifactId(final @NotNull String artifactId);

        @Contract("_ -> this")
        @NotNull Builder version(final @NotNull String version);

        @Contract("_ -> this")
        default @NotNull Builder coordinates(final @NotNull String coordinates) {
            final String[] coords = coordinates.split(":");
            final String group = coords[0];
            final String artifact = coords[1];
            final String version = coords[coords.length - 1];
            return groupId(group)
                    .artifactId(artifact)
                    .version(version);
        }

        @Contract("_ -> this")
        @NotNull Builder checksum(String checksum);

        @NotNull Dependency build();

    }

}
