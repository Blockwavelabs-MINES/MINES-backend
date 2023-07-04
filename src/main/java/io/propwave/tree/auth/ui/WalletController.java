package io.propwave.tree.auth.ui;

import io.propwave.tree.auth.application.WalletService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.ui.dto.request.WalletRequest;
import io.propwave.tree.common.dto.ApiResponse;
import io.propwave.tree.exception.Success;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> register(@AuthenticationPrincipal User user, @RequestBody @Valid final WalletRequest request) {
        walletService.registerWallet(user.getId(), request);
        return new ResponseEntity<>(
                ApiResponse.success(Success.REGISTER_WALLET_SUCCESS),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<ApiResponse> delete(@AuthenticationPrincipal User user, @PathVariable final String walletId) {
        walletService.deleteWallet(user.getId(), Long.parseLong(walletId));
        return new ResponseEntity<>(
                ApiResponse.success(Success.DELETE_WALLET_SUCCESS),
                HttpStatus.OK
        );
    }
}
