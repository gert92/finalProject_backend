package com.seg.slugify;
import com.seg.model.Slugify;

import java.text.Normalizer;
import java.util.List;

public class TagSlugifier {
    private  final int MAX_SLUG_LENGTH = 256;

    public String slugify(final String s) {

        final String intermediateResult = Normalizer
                .normalize(s, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^-_a-zA-Z0-9]", "-").replaceAll("\\s+", "-")
                .replaceAll("[-]+", "-").replaceAll("^-", "")
                .replaceAll("-$", "").toLowerCase();
        return intermediateResult.substring(0,
                Math.min(MAX_SLUG_LENGTH, intermediateResult.length()));
    }

    public <T extends Slugify> StringBuilder slugify (List<T> list, StringBuilder slug){

            if(list.size()>1){
                list.sort((var1,var2)-> String.CASE_INSENSITIVE_ORDER.compare(var2.getName(),var1.getName()));
                String lastSlug = list.get(list.size()-1).getTag();
                Integer increment = Integer.parseInt(lastSlug.substring(lastSlug.length()-1))+1;
                slug.append("-").append(increment.toString());
            }else{
                slug.append("-1");
            }

        return slug;
    }

}
