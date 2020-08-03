package com.github.git24j.example;

import com.github.git24j.core.Oid;

public class Main {
    public static void main(String[] args) {
        Oid oid = Oid.of("0123456789012345678901234567890123456789");
        System.out.println(oid);
    }
}
