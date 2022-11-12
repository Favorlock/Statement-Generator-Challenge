package com.favorlock.challenge.models;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public abstract class Action {
    private Date date;
}
