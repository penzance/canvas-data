package edu.harvard.data.client.verification_utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

public class VerificationUtils {

  public static void textualCompareDumps(final List<File> d1, final List<File> d2,
      final BufferedWriter errors) throws IOException, VerificationException {
    final Set<String> d1Tables = getTables(d1);
    final Set<String> d2Tables = getTables(d2);
    final Set<String> tables = new HashSet<String>(d1Tables);
    tables.retainAll(d2Tables);

    boolean passed = true;
    for (final String table : tables) {
      passed &= compareTable(table, d1, d2, errors);
      System.out.println("Compared " + table);
    }
    if (!passed) {
      throw new VerificationException(
          "Textual comparison of dumps " + d1 + " and " + d2 + " failed.");
    }
  }

  private static boolean compareTable(final String table, final List<File> d1, final List<File> d2,
      final BufferedWriter errors) throws IOException {
    final Map<String, Integer> d1LineMap = new HashMap<String, Integer>();
    for (final File dir : d1) {
      final File tableDir = new File(dir, table);
      if (!(tableDir.exists() && tableDir.isDirectory())) {
        errors.write("Dump directory " + tableDir + " doesn't exist");
        return false;
      }
      buildLineMap(d1LineMap, tableDir);
    }

    boolean passed = true;
    for (final File dir : d2) {
      final File tableDir = new File(dir, table);
      if (!(tableDir.exists() && tableDir.isDirectory())) {
        errors.write("Dump directory " + tableDir + " doesn't exist");
        return false;
      }
      passed &= removeMatchingLines(d1LineMap, tableDir, table, errors);
    }

    for (final String line : d1LineMap.keySet()) {
      for (int i = 0; i < d1LineMap.get(line); i++) {
        passed = false;
        errors.write("< " + table + ":" + line + "\n");
      }
    }
    return passed;
  }

  private static boolean removeMatchingLines(final Map<String, Integer> d1LineMap,
      final File tableDir, final String table, final BufferedWriter errors) throws IOException {
    boolean passed = true;
    for (final String fileName : tableDir.list()) {
      final File file = new File(tableDir, fileName);
      BufferedReader in = null;
      try {
        in = getReaderForFile(file);
        String line = in.readLine();
        while (line != null) {
          if (!d1LineMap.containsKey(line)) {
            passed = false;
            errors.write("> " + table + ": " + line + "\n");
          } else {
            if (d1LineMap.get(line) == 1) {
              d1LineMap.remove(line);
            } else {
              d1LineMap.put(line, d1LineMap.get(line) - 1);
            }
          }
          line = in.readLine();
        }
      } finally {
        if (in != null) {
          in.close();
        }
      }
    }
    return passed;
  }

  private static void buildLineMap(final Map<String, Integer> d1LineMap, final File tableDir)
      throws IOException {
    for (final String fileName : tableDir.list()) {
      final File file = new File(tableDir, fileName);
      BufferedReader in = null;
      try {
        in = getReaderForFile(file);
        String line = in.readLine();
        while (line != null) {
          if (d1LineMap.containsKey(line)) {
            d1LineMap.put(line, d1LineMap.get(line) + 1);
          } else {
            d1LineMap.put(line, 1);
          }
          line = in.readLine();
        }
      } finally {
        if (in != null) {
          in.close();
        }
      }
    }
  }

  private static Set<String> getTables(final List<File> d1) throws IOException {
    final Set<String> tables = new HashSet<String>();
    for (final File dir : d1) {
      for (final String fileName : dir.list()) {
        final File table = new File(dir, fileName);
        if (table.isDirectory()) {
          tables.add(fileName);
        }
      }
    }
    return tables;
  }

  private static BufferedReader getReaderForFile(final File file)
      throws FileNotFoundException, IOException {
    final String filename = file.getName().toLowerCase();
    if (filename.endsWith(".gz")) {
      return new BufferedReader(
          new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
    }
    return new BufferedReader(new FileReader(file));
  }

}
