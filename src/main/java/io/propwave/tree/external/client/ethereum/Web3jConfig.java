package io.propwave.tree.external.client.ethereum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Value("${block-chain.endpoint}")
    private String goerliEndPoint;

    @Value("${block-chain.metamask.private-key.office}")
    private String privateKey;

    public Web3j getWeb3j() {
        return Web3j.build(new HttpService(goerliEndPoint));
    }

    public Credentials getCredentials() {
        return Credentials.create(privateKey);
    }
}
