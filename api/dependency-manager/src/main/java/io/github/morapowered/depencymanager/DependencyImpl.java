/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025 Pedro Souza
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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Objects;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */

@Getter
@EqualsAndHashCode
@ToString
public class DependencyImpl implements Dependency {

    public static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final byte[] checksum;
    private final String mavenRepositoryPath;

    public DependencyImpl(String groupId, String artifactId, String version, String checksum) {
        this.groupId = Objects.requireNonNull(groupId, "group");
        this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
        this.version = Objects.requireNonNull(version, "version");
        this.checksum = checksum != null && !checksum.isEmpty() ? Base64.getDecoder().decode(checksum) : new byte[]{};
        this.mavenRepositoryPath = String.format(MAVEN_FORMAT,
                groupId.replace(".", "/"),
                artifactId,
                version,
                artifactId,
                version);
    }


    public static final class BuilderImpl implements Dependency.Builder {

        private String group;
        private String artifact;
        private String version;
        private String checksum;

        BuilderImpl() {
        }

        @Override
        public @NotNull Builder groupId(@NotNull String groupId) {
            this.group = Objects.requireNonNull(groupId, "group");
            return this;
        }

        @Override
        public @NotNull Builder artifactId(@NotNull String artifactId) {
            this.artifact = Objects.requireNonNull(artifactId, "artifactId");
            return this;
        }

        @Override
        public @NotNull Builder version(@NotNull String version) {
            this.version = Objects.requireNonNull(version, "version");
            return this;
        }

        @Override
        public @NotNull Builder checksum(String checksum) {
            this.checksum = Objects.requireNonNull(checksum, "checksum");
            return this;
        }

        @Override
        public @NotNull Dependency build() {
            return new DependencyImpl(group, artifact, version, checksum);
        }
    }
}
