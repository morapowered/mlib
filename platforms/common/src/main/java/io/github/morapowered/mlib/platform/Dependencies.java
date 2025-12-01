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

package io.github.morapowered.mlib.platform;

import io.github.morapowered.depencymanager.Dependency;

public interface Dependencies {

    Dependency REACTOR_CORE = Dependency.builder()
            .coordinates("io.projectreactor:reactor-core:3.6.6")
            .build();

    Dependency CONFIGURATE_CORE = Dependency.builder()
            .coordinates("org.spongepowered:configurate-core:4.1.2")
            .build();

    Dependency GEANTYREF = Dependency.builder()
            .coordinates("io.leangen.geantyref:geantyref:1.3.1")
            .build();

    Dependency CONFIGURATE_YAML = Dependency.builder()
            .coordinates("org.spongepowered:configurate-yaml:4.1.2")
            .build();

    Dependency SNAKE_YAML = Dependency.builder()
            .coordinates("org.yaml:snakeyaml:1.28")
            .build();


    Dependency CONFIGURATE_HOCON = Dependency.builder()
            .coordinates("org.spongepowered:configurate-hocon:4.1.2")
            .build();

    Dependency HOCON_CONFIG = Dependency.builder()
            .coordinates("com.typesafe:config:1.4.1")
            .build();

    Dependency CONFIGURATE_GSON = Dependency.builder()
            .coordinates("org.spongepowered:configurate-gson:4.1.2")
            .build();

    Dependency HIKARICP = Dependency.builder()
            .coordinates("com.zaxxer:HikariCP:7.0.2")
            .build();

    Dependency MYSQL_CONNECTOR_J = Dependency.builder()
            .coordinates("com.mysql:mysql-connector-j:9.5.0")
            .build();

    Dependency MARIADB_JAVA_CLIENT = Dependency.builder()
            .coordinates("org.mariadb.jdbc:mariadb-java-client:3.5.5")
            .build();

    Dependency POSTGRESQL = Dependency.builder()
            .coordinates("org.postgresql:postgresql:42.7.7")
            .build();

    Dependency SQLITE = Dependency.builder()
            .coordinates("org.xerial:sqlite-jdbc:3.50.3.0")
            .build();

    Dependency H2 = Dependency.builder()
            .coordinates("com.h2database:h2:2.4.240")
            .build();

    Dependency LETTUCE_CORE = Dependency.builder()
            .coordinates("io.lettuce:lettuce-core:6.6.0.RELEASE")
            .build();

    Dependency MONGODB_DRIVER_SYNC = Dependency.builder()
            .coordinates("org.mongodb:mongodb-driver-sync:5.6.1")
            .build();

    Dependency MONGODB_DRIVER_REACTIVESTREAMS = Dependency.builder()
            .coordinates("org.mongodb:mongodb-driver-reactivestreams:5.6.1")
            .build();

    Dependency MONGODB_BSON = Dependency.builder()
            .coordinates("org.mongodb:bson:5.6.1")
            .build();

    Dependency MONGODB_DRIVER_CORE = Dependency.builder()
            .coordinates("org.mongodb:mongodb-driver-core:5.6.1")
            .build();


}
