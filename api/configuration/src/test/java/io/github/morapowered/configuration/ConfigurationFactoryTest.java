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

package io.github.morapowered.configuration;

import io.github.morapowered.configuration.value.NodeConfiguration;
import io.github.morapowered.configuration.value.ObjectMappingConfiguration;
import io.github.morapowered.util.io.Duplex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ConfigurationFactoryTest {

    @Test
    void test() throws IOException {
        ConfigurationFactory<YamlConfigurationLoader, CommentedConfigurationNode, YamlConfigurationLoader.Builder> loader = ConfigurationFactory.builder(YamlConfigurationLoader.Builder.class)
                .configure(builder -> builder.indent(2)
                        .nodeStyle(NodeStyle.BLOCK))
                .build();

        NodeConfiguration<YamlConfigurationLoader, CommentedConfigurationNode> nodeConfiguration = loader.load(Duplex.resource("test_config.yml"));
        CommentedConfigurationNode node = nodeConfiguration.getNode();
        Assertions.assertEquals("Hello world!", node.node("message").require(String.class));
    }

    @Test
    void testMappedConfiguration() throws IOException {
        ConfigurationFactory<YamlConfigurationLoader, CommentedConfigurationNode, YamlConfigurationLoader.Builder> loader = ConfigurationFactory.builder(YamlConfigurationLoader.Builder.class)
                .configure(builder -> builder.indent(2)
                        .nodeStyle(NodeStyle.BLOCK))
                .build();

        Path path = Paths.get("test_mapped_configuration.yml");
        Files.deleteIfExists(path);
        Duplex duplex = Duplex.path(path);

        // First to create!
        ObjectMappingConfiguration<MappedConfiguration, YamlConfigurationLoader, CommentedConfigurationNode> mappingConfiguration = loader.loadObjectOrDefaultAndSave(duplex, new MappedConfiguration());
        Assertions.assertNotNull(mappingConfiguration.getValue());
        Assertions.assertEquals("Hello world!", mappingConfiguration.getValue().message);

        // Now load and compare
        mappingConfiguration = loader.loadObjectOrDefaultAndSave(duplex, new MappedConfiguration());
        Assertions.assertNotNull(mappingConfiguration.getValue());
        Assertions.assertEquals("Hello world!", mappingConfiguration.getValue().message);

        // Clean!
        Files.deleteIfExists(path);
    }

    @ConfigSerializable
    static class MappedConfiguration {
        public String message = "Hello world!";
    }


}