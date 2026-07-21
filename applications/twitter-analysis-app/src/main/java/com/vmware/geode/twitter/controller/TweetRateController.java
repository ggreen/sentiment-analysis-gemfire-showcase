package com.vmware.geode.twitter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gregory Green
 *
 */
//@RestController
public class TweetRateController
{
	private final TweetRateServiceController service;

	public TweetRateController(TweetRateServiceController service)
	{
		this.service = service;
	}

	@ResponseBody
	@RequestMapping("/live_tweets")
	@CrossOrigin
	public void live_tweets(HttpServletResponse response)
	throws IOException
	{
		service.live_tweets(response);
	}
}
