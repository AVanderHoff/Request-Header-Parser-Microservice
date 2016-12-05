package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Main {

	@RequestMapping("/")
	public String home(@RequestHeader(value = "Accept-Language") String acceptLanguage,
			@RequestHeader(value = "User-Agent") String userAgent, HttpServletResponse response, Model model) {

		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (ip != null) {
			model.addAttribute("ip", ip.getHostAddress());
		} else {
			model.addAttribute("ip", "Cannot get IP Address");
		}

		if (acceptLanguage != null) {
			model.addAttribute("lang", acceptLanguage);
		} else {
			model.addAttribute("lang", "Cannot get Language");
		}

		int firstParenthesis = StringUtils.indexOf(userAgent, "(");
		int secondColon = StringUtils.ordinalIndexOf(userAgent, ";", 2);

		if (firstParenthesis != -1 && secondColon != -1) {
			String parsed = StringUtils.substring(userAgent, firstParenthesis + 1, secondColon);
			model.addAttribute("os", parsed);
		} else {
			model.addAttribute("os", "Cannot get Operating System");
		}

		return "home";
	}

}
