package io.propwave.tree.external.client.google;

import io.propwave.tree.external.client.dto.google.GoogleUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleService {

    private final GoogleApiClient googleApiClient;

    public GoogleUserInfo getUserInfo(String code) {
        return googleApiClient.getUserInfo("Bearer " + code);
    }
}
