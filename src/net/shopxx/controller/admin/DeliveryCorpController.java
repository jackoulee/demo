/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package net.shopxx.controller.admin;

import javax.annotation.Resource;

import net.shopxx.Message;
import net.shopxx.Pageable;
import net.shopxx.entity.DeliveryCorp;
import net.shopxx.service.DeliveryCorpService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller("adminDeliveryCorpController")
@RequestMapping("/admin/delivery_corp")
public class DeliveryCorpController extends BaseController {

	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/admin/delivery_corp/add";
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(DeliveryCorp deliveryCorp, RedirectAttributes redirectAttributes) {
		if (!isValid(deliveryCorp)) {
			return ERROR_VIEW;
		}
		deliveryCorp.setShippingMethods(null);
		deliveryCorpService.save(deliveryCorp);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("deliveryCorp", deliveryCorpService.find(id));
		return "/admin/delivery_corp/edit";
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(DeliveryCorp deliveryCorp, RedirectAttributes redirectAttributes) {
		if (!isValid(deliveryCorp)) {
			return ERROR_VIEW;
		}
		deliveryCorpService.update(deliveryCorp, "shippingMethods");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", deliveryCorpService.findPage(pageable));
		return "/admin/delivery_corp/list";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		deliveryCorpService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}