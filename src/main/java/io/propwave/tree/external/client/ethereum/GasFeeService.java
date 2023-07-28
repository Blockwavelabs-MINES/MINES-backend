package io.propwave.tree.external.client.ethereum;

import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import io.propwave.tree.exception.model.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class GasFeeService {

    @Value("${block-chain.gas.end-point}")
    private String goerliGasFeeEndPoint;

    public BigInteger estimateGasFeeInWei() {
        String response = callGasFeeApi();
        return parseGasFee(response);
    }

    private String callGasFeeApi() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(goerliGasFeeEndPoint, HttpMethod.GET, requestEntity, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new BadRequestException("가스비 측정에 실패했습니다.");
        }

        return responseEntity.getBody();
    }

    private BigInteger parseGasFee(String response) {
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(response);
        JsonArray jsonArray = jsonObject.getAsJsonArray("speeds");

        double baseFee = jsonArray.get(2).getAsJsonObject().get("baseFee").getAsDouble();
        return BigDecimal.valueOf(baseFee * Math.pow(10, 9)).toBigInteger();
    }
}
