package com.davinchicoder.spring.webflux.domain;

public record RuleResult(boolean fraud, String rule, int score) {

}
