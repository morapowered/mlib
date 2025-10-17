package io.github.morapowered.mlib.configuration.internal

import io.github.morapowered.configuration.serializer.GlobalSerializers
import org.spongepowered.configurate.kotlin.dataClassFieldDiscoverer
import org.spongepowered.configurate.objectmapping.ObjectMapper

object ConfigurationKotlinSerializers {

    fun register() {
        GlobalSerializers.consume { builder ->
            builder.registerAnnotatedObjects(
                ObjectMapper.factoryBuilder().addDiscoverer(dataClassFieldDiscoverer()).build()
            )
        }
    }

}