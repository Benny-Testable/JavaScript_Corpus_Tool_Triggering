package com.pramora.testable.report;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Writes a priced summary to a file, after an authorization check. */
public final class SummaryExporter {

    static final String EXPORT_ACTION = "summary:export";

    private SummaryExporter() {
    }

    /** Writes {@code summary} to {@code target} when {@code policy} allows the export. */
    public static Path export(Path target, String summary, AccessPolicy policy) throws IOException {
        policy.checkPermission(EXPORT_ACTION);
        return Files.write(target, summary.getBytes(StandardCharsets.UTF_8));
    }
}
