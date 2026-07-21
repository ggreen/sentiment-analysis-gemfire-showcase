package com.vmware.geode.twitter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "tweet": "#3RMXi v0.1.2 had just been released!\nThe Language component State is decoupled from the UI now!\nhttps://t.co/y3D4tIurAf\n#react #javascript", "polarity": "0.67"
 * @author Gregory Green
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tweet
{
	private String id;
	private String text;
}
