package com.example.demo.service;

import com.example.demo.entity.Message;
import com.example.demo.entity.Response;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserResponse;
import com.example.demo.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.example.demo.utils.PasswordService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService undertest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordService passwordService;
    @Test
    public void testCreateExistedUserName() throws NoSuchAlgorithmException {
        when(userRepository.existsByName(any())).thenReturn(true);

        UserResponse actual = undertest.accountCreate("existedUserName", any());

        assertFalse(actual.getStatusCode() == 200);
        assertEquals(Message.USERNAME_EXISTS, actual.getMessage());
        verify(userRepository).existsByName("existedUserName");
    }

    @Test
    public void testCreateUserSuccess() throws NoSuchAlgorithmException {
        when(userRepository.existsByName("validUsername0")).thenReturn(false);

        UserResponse actual = undertest.accountCreate("validUsername0", "Password0");

        assertTrue(actual.getStatusCode() == 200);
        assertEquals(Message.CREATE_SUCCESSFUL, actual.getMessage());
        verify(userRepository).save(new UserInfo("validUsername0", passwordService.hashPassword("Password0")));
    }

    @ParameterizedTest
    @MethodSource("invalidCredentialList")
    void testInvalidCredential(String userName, String password, String reason) throws NoSuchAlgorithmException {
        when(userRepository.existsByName(any())).thenReturn(false);

        UserResponse actual = undertest.accountCreate(userName, password);

        assertFalse(actual.getStatusCode() == 200);
        assertEquals(reason, actual.getMessage());
        verify(userRepository).existsByName(userName);
    }
    private static Stream<Arguments> invalidCredentialList() {
        return Stream.of(
                Arguments.of("u", "password", Message.INVALID_USERNAME_LENGTH),
                Arguments.of("validUsername0", "passwor", Message.INVALID_PASSWORD_LENGTH),
                Arguments.of("validUsername0", "password", Message.INVALID_PASSWORD_FORMAT)
        );
    }

    @Test
    void testAccountVerifyFailWithInvalidUserName() {
        when(userRepository.findByName("username")).thenReturn(Optional.empty());

        UserResponse actual = undertest.accountVerify("username", "password");

        assertFalse(actual.getStatusCode() == 200);
        assertEquals(Message.INVALID_USERNAME, actual.getMessage());
    }

    @Test
    void testAccountVerifyFailWithInvalidPassword() {
        UserInfo user = new UserInfo("userName", "correctPassword", 2, null);
        when(userRepository.findByName("username")).thenReturn(Optional.of(user));
        when(passwordService.verifyPassword(any(), any())).thenReturn(false);

        UserResponse actual = undertest.accountVerify("username", "password");

        assertFalse(actual.getStatusCode() == 200);
        assertEquals(Message.INVALID_PASSWORD, actual.getMessage());
    }

    @Test
    void testAccountVerifySuccess() {
        UserInfo userInfoFromDB = new UserInfo("userName", "hashedPassword", 4, null);
        when(userRepository.findByName("username")).thenReturn(Optional.of(userInfoFromDB));
        when(passwordService.verifyPassword(any(), any())).thenReturn(true);

        UserResponse actual = undertest.accountVerify("username", "password");

        assertTrue(actual.getStatusCode()==200);
        assertEquals(Message.LOGIN_SUCCESS, actual.getMessage());
    }

    @Test
    void testFailAttemptsAt5th() {
        UserInfo user = new UserInfo("userName", "correctPassword", 4, null);
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));

        UserResponse actual = undertest.accountVerify("username", "password");

        assertFalse(actual.getStatusCode()==200);
        assertEquals(Message.INVALID_PASSWORD, actual.getMessage());
    }

    //Fail over five times, and try 6th attempts in less 60 second.
    @Test
    void testFailAttemptsMoreThan5() {
        UserInfo user = new UserInfo("userName", "correctPassword", 5, new Timestamp(System.currentTimeMillis() - 30 * 1000));
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));

        UserResponse actual = undertest.accountVerify("username", "password");

        assertFalse(actual.getStatusCode()==200);
        assertEquals(Message.FAILED_ATTEMPTS, actual.getMessage());
        verifyNoMoreInteractions(userRepository);
    }

    //Fail over five times, and try 6th attempts after  60 second.
    @Test
    void testFailAttemptsMoreThan5After60s() {
        UserInfo user = new UserInfo("userName", "correctPassword", 5, new Timestamp(System.currentTimeMillis() - 70 * 1000));
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));

        UserResponse actual = undertest.accountVerify("username", "password");

        assertFalse(actual.getStatusCode()==200);
        assertEquals(Message.INVALID_PASSWORD, actual.getMessage());
    }

    //Fail over five times, and try 6th attempts after  60 second with correct password.
    @Test
    void testSuccessAfterFailAttempts5times() {
        UserInfo user = new UserInfo("userName", "hashedPassword", 5, new Timestamp(System.currentTimeMillis() - 70 * 1000));
        when(userRepository.findByName(any())).thenReturn(Optional.of(user));
        when(passwordService.verifyPassword(any(), any())).thenReturn(true);

        UserResponse actual = undertest.accountVerify("username", "password");

        assertTrue(actual.getStatusCode()==200);
        assertEquals(Message.LOGIN_SUCCESS, actual.getMessage());
    }
}
