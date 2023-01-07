package com.seg.slugify;
import java.text.Normalizer;
public class TagSlugifier {
    private  final int MAX_SLUG_LENGTH = 256;

    public String slugify(final String s) {
        //algorithm used in https://github.com/slugify/slugify/blob/master/core/src/main/java/com/github/slugify/Slugify.java
        final String intermediateResult = Normalizer
                .normalize(s, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^-_a-zA-Z0-9]", "-").replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-").replaceAll("^-", "")
                .replaceAll("-$", "").toLowerCase();
        return intermediateResult.substring(0,
                Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
    }

}
