package io.propwave.tree.send.ui;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import io.propwave.tree.send.application.SendService;
import io.propwave.tree.send.application.dto.response.SendInformationResponseService;
import io.propwave.tree.send.application.dto.response.SendTransactionResponseService;
import io.propwave.tree.send.ui.dto.request.SendTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SendController {

    private final SendService sendService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<SendTransactionResponseService>> sendTransactionSave(@AuthenticationPrincipal User user, @RequestBody final SendTransactionRequest request) {
        return new ResponseEntity<>(
                ApiResponse.success(Success.CREATE_RECEIVE_LINK_SUCCESS, sendService.saveSendTransaction(user.getId(), request)),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/public/send")
    public ResponseEntity<ApiResponse<SendInformationResponseService>> getSendInformation(@RequestParam("link_key") String linkKey) {
        return new ResponseEntity<>(
                ApiResponse.success(Success.GET_SEND_INFORMATION_SUCCESS, sendService.getSendInformation(linkKey)),
                HttpStatus.OK
        );
    }
}
