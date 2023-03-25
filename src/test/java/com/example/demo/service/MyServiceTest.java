package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    @InjectMocks
    private MyService undertest;
    @Mock
    private UserRepository userRepository;

    private final static String SUCCESS = "success";
    private final static String REASON = "reason";
    private final static String LOGIN_SUCCESS = "Log in successful";
    private final static String USERNAME_EXISTS = "Username already exists";
    private final static String CREATE_SUCCESSFUL = "Create user successful";
    private final static String INVALID_USERNAME = "Wrong user name";
    private final static String INVALID_USERNAME_LENGTH = "Username length should be between 3 and 32 characters";
    private final static String INVALID_PASSWORD_LENGTH = "Password length should be between 8 and 32 characters";
    private final static String INVALID_PASSWORD_FORMAT = "Password must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number";

    @Test
    public void testCreateExistedUserName() throws NoSuchAlgorithmException {
        when(userRepository.existsByName(any())).thenReturn(true);

        Map<String, Object> actual = undertest.accountCreateJPA("existedUserName", any());

        assertFalse((Boolean) actual.get(SUCCESS));
        assertEquals(USERNAME_EXISTS, actual.get(REASON));
        verify(userRepository).existsByName("existedUserName");
    }

    @Test
    public void testCreateUserSuccess() throws NoSuchAlgorithmException {
        when(userRepository.existsByName("validUsername0")).thenReturn(false);

        Map<String, Object> actual = undertest.accountCreateJPA("validUsername0", "Password0");

        assertTrue((Boolean) actual.get(SUCCESS));
        assertEquals(CREATE_SUCCESSFUL, actual.get(REASON));
        verify(userRepository).save(new UserInfo("validUsername0", "Password0"));
    }

    private static Stream<Arguments> invalidCredentialList() {
        return Stream.of(
                Arguments.of("u", "password", INVALID_USERNAME_LENGTH),
                Arguments.of("validUsername0", "passwor", INVALID_PASSWORD_LENGTH),
                Arguments.of("validUsername0", "password", INVALID_PASSWORD_FORMAT)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCredentialList")
    void testInvalidCredential(String userName, String password, String reason) throws NoSuchAlgorithmException {
        when(userRepository.existsByName(any())).thenReturn(false);

        Map<String, Object> actual = undertest.accountCreateJPA(userName, password);

        assertFalse((Boolean) actual.get(SUCCESS));
        assertEquals(reason, actual.get(REASON));
        verify(userRepository).existsByName(userName);
    }

    @Test
    void testAccountVerifyFail() throws NoSuchAlgorithmException {
        when(userRepository.existsByName(any())).thenReturn(false);

        Map<String, Object> actual = undertest.accountVerifyJPA("username", "password");
        assertFalse((Boolean) actual.get(SUCCESS));
        assertEquals(INVALID_USERNAME, actual.get(REASON));
    }

    @Test
    void testAccountVerifySuccess() throws NoSuchAlgorithmException {
        UserInfo user = new UserInfo("userName", "password");
        when(userRepository.existsByName(any())).thenReturn(true);
        when(userRepository.findByName("username")).thenReturn(Arrays.asList(user));
        Map<String, Object> actual = undertest.accountVerifyJPA("username", "password");
        assertTrue((Boolean) actual.get(SUCCESS));
        assertEquals(LOGIN_SUCCESS, actual.get(REASON));
    }
}
