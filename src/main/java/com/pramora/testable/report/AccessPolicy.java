package com.pramora.testable.report;

/** Decides whether the caller may perform an action. */
@FunctionalInterface
public interface AccessPolicy {

    /** Throws {@link SecurityException} when {@code action} is not allowed. */
    void checkPermission(String action);
}
