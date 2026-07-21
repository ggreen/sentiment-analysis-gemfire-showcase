package com.vmware.geode.twitter.streaming.source.data;

import com.vmware.geode.twitter.domain.Tweet;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TwitterStreamData
 *
 * @author Gregory Green
 */
@Data
@NoArgsConstructor
public class TwitterStreamRecord
{
/*
{"data":{"id":"1497310240282398722","text":"RT @PLAISport: ‼️ COMPETITION TIME ‼️\n\n👟 WIR...\n\n1️⃣ 👍 LIKE OUR PAGE…"},"matching_rules":[{"id":"1477262727555031048","tag":""}]}

 */
    private Tweet data;
}
