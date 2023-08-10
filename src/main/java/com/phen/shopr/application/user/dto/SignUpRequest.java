package com.phen.shopr.application.user.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public record SignUpRequest(String firstName, String lastName, String username, String email, String password) {}
