package io.propwave.tree.external.client.ethereum;

import io.propwave.tree.exception.model.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Web3jService {

    private static final List<String> SUCCESS_STATUSES = Arrays.asList("0x1", "0x5", "0xaa36a7", "0x89", "0xa86a", "0xa4b1");

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

    public String getTransactionStatus(String transactionHash) {
        try {
            Optional<TransactionReceipt> receiptOptional = web3j.ethGetTransactionReceipt(transactionHash)
                    .send()
                    .getTransactionReceipt();

            if (receiptOptional.isPresent()) {
                String status = receiptOptional.get().getStatus();
                return SUCCESS_STATUSES.contains(status) ? "SUCCESS" : "FAILED";
            } else {
                return "PENDING";
            }
        } catch (IOException error) {
            throw new BadRequestException("확인할 수 없는 트랜잭션 해시값입니다.");
        }
    }
}
