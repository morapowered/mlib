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

package io.github.morapowered.platform.fabric.dependencies;

import io.github.morapowered.depencymanager.Dependency;
import io.github.morapowered.platform.Dependencies;

import java.util.Set;

public interface FabricDependencies {

    Set<Dependency> DEPENDENCIES = Set.of(
            Dependencies.REACTOR_CORE,
            Dependencies.REACTOR_STREAMS,
            Dependencies.CONFIGURATE_CORE,
            Dependencies.GEANTYREF,
            Dependencies.CONFIGURATE_HOCON,
            Dependencies.HOCON_CONFIG,
            Dependencies.CONFIGURATE_YAML,
            Dependencies.SNAKE_YAML,
            Dependencies.CONFIGURATE_GSON,
            Dependencies.HIKARICP,
            Dependencies.MYSQL_CONNECTOR_J,
            Dependencies.MARIADB_JAVA_CLIENT,
            Dependencies.POSTGRESQL,
            Dependencies.SQLITE,
            Dependencies.H2,
            Dependencies.LETTUCE_CORE,
            Dependencies.MONGODB_DRIVER_SYNC,
            Dependencies.MONGODB_DRIVER_REACTIVESTREAMS,
            Dependencies.MONGODB_BSON,
            Dependencies.MONGODB_DRIVER_CORE
    );

}
