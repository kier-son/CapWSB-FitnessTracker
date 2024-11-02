package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record SimpleUserEmailDto(@Nullable Long id, String email) {
}

