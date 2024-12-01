package org.planx.common.models.pddl4j

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.planx.common.models.pddl4j.PDDL4jConverter.Companion.createBitVectorData
import java.io.IOException
import java.util.*

/**
 * Custom serializer for [BitVectorData]
 */
class BitSetSerializer : JsonSerializer<BitVectorData>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: BitVectorData, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartArray()
        for (l in value.toLongArray()) {
            gen.writeNumber(l)
        }
        gen.writeEndArray()
    }

    override fun handledType(): Class<BitVectorData> {
        return BitVectorData::class.java
    }
}

/**
 * Custom deserializer for [BitVectorData]
 */
class BitSetDeserializer : JsonDeserializer<BitVectorData?>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext?): BitVectorData {
        val l = ArrayList<Long>()
        var token: JsonToken
        while (JsonToken.END_ARRAY != jsonParser.nextValue().also { token = it }) {
            if (token.isNumeric) {
                l.add(jsonParser.longValue)
            }
        }
        return createBitVectorData(l.toLongArray())
    }
}
