package io.propwave.tree.external.client.ethereum;

import io.propwave.tree.exception.model.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.tsp.TSPUtil;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class Web3jService {

    private final GasFeeService gasFeeService;
    private final Web3j web3j;
    private final Credentials credentials;

    public Web3jService(GasFeeService gasFeeService, Web3jConfig web3jConfig) {
        this.gasFeeService = gasFeeService;
        this.web3j = web3jConfig.getWeb3j();
        this.credentials = web3jConfig.getCredentials();
    }

    public String sendGoerliEthToReceiver(String senderAddress, double amount) throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        BigInteger gasPrice = gasFeeService.estimateGasFeeInWei();
        BigInteger gasLimit = BigInteger.valueOf(21_000);

        BigInteger value = Convert.toWei(BigDecimal.valueOf(amount), Convert.Unit.ETHER).toBigInteger();
        RawTransaction transaction = RawTransaction.createEtherTransaction(
                nonce,
                gasPrice,
                gasLimit,
                senderAddress,
                value
        );

        byte[] signedMessage = TransactionEncoder.signMessage(transaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
        return ethSendTransaction.getTransactionHash();
    }
}
