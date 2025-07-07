package com.insurancebanking.platform.policy.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.insurancebanking.platform.account.model.Account;
import com.insurancebanking.platform.account.service.AccountService;
import com.insurancebanking.platform.auth.model.User;
import com.insurancebanking.platform.auth.repository.UserRepository;
import com.insurancebanking.platform.core.service.BaseEntityService;
import com.insurancebanking.platform.policy.dto.PolicyRequest;
import com.insurancebanking.platform.policy.exception.PolicyCreationException;
import com.insurancebanking.platform.policy.exception.PolicyNotFoundException;
import com.insurancebanking.platform.policy.model.*;

import com.insurancebanking.platform.policy.repository.PolicyRepository;

public class PolicyServiceTest {

    @Mock
    private BaseEntityService baseEntityService;

    @Mock
    private AccountService accountService;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PolicyService policyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserPolicies_shouldReturnUserPolicies() {
        UUID userId = UUID.randomUUID();
        List<Policy> policies = List.of(mock(Policy.class));

        when(policyRepository.findByUser_IdOrderByPolicyNumberAsc(userId)).thenReturn(policies);

        List<Policy> result = policyService.getUserPolicies(userId);

        assertThat(result).isEqualTo(policies);
    }

    @Test
    void getUserPolicyById_shouldReturnPolicyIfUserMatches() {
        UUID userId = UUID.randomUUID();
        UUID policyId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);

        Policy policy = new Policy();
        policy.setId(policyId);
        policy.setUser(user);

        when(policyRepository.findById(policyId)).thenReturn(Optional.of(policy));

        Policy result = policyService.getUserPolicyById(policyId, userId);

        assertThat(result).isEqualTo(policy);
    }

    @Test
    void getUserPolicyById_shouldThrowIfPolicyNotFound() {
        UUID policyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(policyRepository.findById(policyId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> policyService.getUserPolicyById(policyId, userId))
            .isInstanceOf(PolicyNotFoundException.class);
    }

    @Test
    void getUserPolicyById_shouldThrowIfUserMismatch() {
        UUID policyId = UUID.randomUUID();
        UUID correctUserId = UUID.randomUUID();
        UUID wrongUserId = UUID.randomUUID();

        User user = new User();
        user.setId(correctUserId);

        Policy policy = new Policy();
        policy.setId(policyId);
        policy.setUser(user);

        when(policyRepository.findById(policyId)).thenReturn(Optional.of(policy));

        assertThatThrownBy(() -> policyService.getUserPolicyById(policyId, wrongUserId))
            .isInstanceOf(PolicyNotFoundException.class);
    }

    @Test
    void getPolicyTypes_shouldReturnAllTypes() {
        List<PolicyType> result = policyService.getPolicyTypes();
        assertThat(result).containsExactly(PolicyType.values());
    }

    @Test
    void create_shouldCreateAndReturnPolicy() {
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        PolicyRequest request = new PolicyRequest(accountId, PolicyType.LOSS_PROTECTION, 5000.0);

        User user = new User();
        user.setId(userId);

        Account account = new Account();
        account.setId(accountId);
        account.setCurrencyCode("USD");

        String generatedPolicyNumber = "POL123456";

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountService.getUserAccountById(accountId, userId)).thenReturn(account);
        when(baseEntityService.generateEntityPublicIdentifier("POL")).thenReturn(generatedPolicyNumber);
        when(policyRepository.existsByPolicyNumber(generatedPolicyNumber)).thenReturn(false);

        Policy savedPolicy = Policy.builder()
            .user(user)
            .account(account)
            .policyStatus(PolicyStatus.PENDING)
            .policyNumber(generatedPolicyNumber)
            .policyType(PolicyType.LOSS_PROTECTION)
            .coverageAmount(5000.0)
            .premium(125.0) // 5000 * 0.025
            .currencyCode("USD")
            .startDate(Instant.now())
            .endDate(Instant.now().atZone(ZoneOffset.UTC).plusYears(1).toInstant())
            .build();

        when(policyRepository.save(any(Policy.class))).thenReturn(savedPolicy);

        Policy result = policyService.create(request, userId);

        assertThat(result.getPolicyNumber()).isEqualTo(generatedPolicyNumber);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getAccount()).isEqualTo(account);
        assertThat(result.getCoverageAmount()).isEqualTo(5000.0);
        assertThat(result.getPremium()).isEqualTo(125.0);
        assertThat(result.getPolicyStatus()).isEqualTo(PolicyStatus.PENDING);
    }

    @Test
    void create_shouldThrowIfUserNotFound() {
        UUID userId = UUID.randomUUID();
        PolicyRequest request = new PolicyRequest(UUID.randomUUID(), PolicyType.LOSS_PROTECTION, 10000.0);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> policyService.create(request, userId))
            .isInstanceOf(PolicyCreationException.class)
            .hasMessageContaining("User not found");
    }

    @Test
    void generatePolicyNumber_shouldRetryAndSucceedOnSecondAttempt() {
        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        PolicyRequest request = new PolicyRequest(accountId, PolicyType.LOSS_PROTECTION, 1000.0);
        User user = new User();
        user.setId(userId);

        Account account = new Account();
        account.setId(accountId);
        account.setCurrencyCode("USD");

        String badNumber = "POL123";
        String goodNumber = "POL456";

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountService.getUserAccountById(accountId, userId)).thenReturn(account);
        when(baseEntityService.generateEntityPublicIdentifier("POL"))
            .thenReturn(badNumber, goodNumber);
        when(policyRepository.existsByPolicyNumber(badNumber)).thenReturn(true);
        when(policyRepository.existsByPolicyNumber(goodNumber)).thenReturn(false);
        when(policyRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Policy policy = policyService.create(request, userId);

        assertThat(policy.getPolicyNumber()).isEqualTo(goodNumber);
    }

    @Test
    void generatePolicyNumber_shouldThrowAfterMaxRetries() {
        when(baseEntityService.generateEntityPublicIdentifier("POL"))
            .thenReturn("DUPLICATE");

        when(policyRepository.existsByPolicyNumber("DUPLICATE"))
            .thenReturn(true);

        for (int i = 0; i < 10; i++) {
            when(baseEntityService.generateEntityPublicIdentifier("POL"))
                .thenReturn("DUPLICATE");
        }

        UUID userId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        PolicyRequest request = new PolicyRequest(accountId, PolicyType.LOSS_PROTECTION, 5000.0);

        User user = new User();
        user.setId(userId);

        Account account = new Account();
        account.setCurrencyCode("USD");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountService.getUserAccountById(accountId, userId)).thenReturn(account);

        assertThatThrownBy(() -> policyService.create(request, userId))
            .isInstanceOf(PolicyCreationException.class)
            .hasMessageContaining("Unable to generate unique policy number");
    }

    @Test
    void getAccountPoliciesNumbers_shouldReturnPolicyNumbers() {
        UUID accountId = UUID.randomUUID();

        Policy policy1 = new Policy();
        policy1.setPolicyNumber("POL123");

        Policy policy2 = new Policy();
        policy2.setPolicyNumber("POL456");

        List<Policy> policies = List.of(policy1, policy2);

        when(policyRepository.findByAccount_IdOrderByPolicyNumberAsc(accountId)).thenReturn(policies);

        List<String> result = policyService.getAccountPoliciesNumbers(accountId);

        assertThat(result).containsExactly("POL123", "POL456");
    }

}
