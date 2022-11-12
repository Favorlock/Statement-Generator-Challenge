package com.favorlock.challenge.models.transaction;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public abstract class Transaction {
    private Date date;

    public abstract String getStock();
}
