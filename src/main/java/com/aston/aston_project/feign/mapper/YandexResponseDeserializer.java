package com.aston.aston_project.feign.mapper;

import com.aston.aston_project.feign.dto.YandexResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class YandexResponseDeserializer extends JsonDeserializer<YandexResponse> {
    @Override
    public YandexResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        Double[] cords = jsonParser.getCodec().treeToValue(treeNode.get("features").get(0)
                .get("geometry")
                .get("coordinates"),Double[].class);
        YandexResponse yandexResponse = new YandexResponse();
        yandexResponse.setCoordinates(cords);
        return yandexResponse;
    }
}
