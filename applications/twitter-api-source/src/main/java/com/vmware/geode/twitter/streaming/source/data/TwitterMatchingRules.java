package com.vmware.geode.twitter.streaming.source.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TwitterMatchingRules
 *
 * @author Gregory Green
 */
@Data
@NoArgsConstructor
public class TwitterMatchingRules
{
    /*
    "matching_rules":[{"id":"1477262727555031048","tag":""}
     */
    private String id;
    private String tag;
}
