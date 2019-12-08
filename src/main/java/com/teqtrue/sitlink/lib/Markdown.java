package com.teqtrue.sitlink.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Markdown {
  public static String escape(String str) {
    return str
      .replaceAll("&", "&amp;")
      .replaceAll("<", "&lt;")
      .replaceAll(">", "&gt;")
      .replaceAll("\\n", "<br>");
  }

  public static String parse(String str) {
    str = str.trim();
    str = escape(str);
    str = pairUp(str, "**", "strong");
    str = pairUp(str, "*", "em");
    str = pairUp(str, "__", "u");
    str = pairUp(str, "~~", "s");
    str = findLinks(str);
    str = escapeCleanup(str);
    return str;
  }

  private static String pairUp(String str, String trigger, String tag) {
    final String startTag = "<" + tag + ">";
    final String endTag = "</" + tag + ">";
    final int offset = trigger.length();
    boolean startEndToggle = false;
    int startPos = 0;

    for (int i = 0; i <= (str.length() - offset); i++) {
      if (str.substring(i, i + offset).equals(trigger)) {
        if (!startEndToggle && (str.length() == i + offset || !str.substring(i + offset, i + offset + 1).equals(trigger.substring(0, 1)))) {
          startEndToggle = true;
          startPos = i;
        } else if (startEndToggle) {
          str = str.substring(0, startPos) + startTag + str.substring(startPos + offset, i) + endTag + str.substring(i + offset, str.length());
          startEndToggle = false;
        }
      }
      if (str.substring(i, i + 1).equals("\\")) {
        i++;
      }
    }

    return str;
  }

  private static String findLinks(String str) {
    final String regex = "(?<=\\b)https?:\\/\\/(www\\.)?[a-z0-9\\-_\\.]{2,256}\\.[a-z]{2,6}(((\\/|\\?)[a-z0-9\\/#?&=\\-_.~]+)|\\/)?";
    Pattern ptr = Pattern.compile(regex);
    Matcher match = ptr.matcher(str);
    while (match.find()) {
      str = str.substring(0, match.start()) +
        "<a href=\"" + match.group() +
        "\">" + match.group() +
        "</a>" + str.substring(match.end(), str.length());
    }
    return str;
  }

  private static String escapeCleanup(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (str.substring(i, i + 1).equals("\\")) {
        str = str.substring(0, i) + str.substring(i + 1, str.length());
      }
    }
    return str;
  }
}
