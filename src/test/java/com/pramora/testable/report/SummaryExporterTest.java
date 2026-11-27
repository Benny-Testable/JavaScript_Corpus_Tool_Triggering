package com.pramora.testable.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SummaryExporterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void writesTheSummaryAfterAskingPermission() throws IOException {
        Path target = folder.getRoot().toPath().resolve("summary.txt");
        List<String> asked = new ArrayList<>();

        Path written = SummaryExporter.export(target, "total : 10", asked::add);

        assertEquals(target, written);
        assertEquals("total : 10", new String(Files.readAllBytes(written), StandardCharsets.UTF_8));
        assertEquals(List.of(SummaryExporter.EXPORT_ACTION), asked);
    }

    @Test
    public void writesNothingWhenThePolicyRefuses() {
        Path target = folder.getRoot().toPath().resolve("refused.txt");
        AccessPolicy refuse = action -> {
            throw new SecurityException(action);
        };

        assertThrows(SecurityException.class, () -> SummaryExporter.export(target, "total : 10", refuse));
        assertEquals(false, Files.exists(target));
    }
}
