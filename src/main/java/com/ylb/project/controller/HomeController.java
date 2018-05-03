package com.ylb.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ylb.project.dao.BigTypeRepository;
import com.ylb.project.model.BigType;
import com.ylb.project.model.Commodity;
import com.ylb.project.model.SmallType;
import com.ylb.project.service.HomeServiceImpl;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private HomeServiceImpl homeServiceImpl;
	@RequestMapping()
	public ModelAndView getMenu(ModelAndView model){
		//把大类和对应的小类放到hashMap中
		Iterable<BigType> bigTypes=homeServiceImpl.findBTList();
		Map<BigType, List> hashMap1=new HashMap<>();
		Map<BigType, List> hashMap2=new HashMap<>();
		
		for (BigType bigType : bigTypes) {
			hashMap1.put(bigType, homeServiceImpl.findSTList(bigType));
			//从商品list随机取出6个放到另外一个list
			List<Commodity> commodities=homeServiceImpl.findBybigType(bigType);
			List<Commodity> RespCommodities=new ArrayList<Commodity>(5);
			System.out.println("长度为"+RespCommodities.size());
			for(int i=0;i<commodities.size();i++){
				if(i<=5){
					RespCommodities.add(commodities.get(i));
					System.out.println("商品名称："+RespCommodities.get(i).getProductName());
				}
			}

			hashMap2.put(bigType, RespCommodities);
		}
		 for(Map.Entry<BigType, List> entry : hashMap2.entrySet()){

		       System.out.println("key= "+entry.getKey()+" and value= "+entry.getValue().size());

		   }
		model.addObject("hashMap1", hashMap1);
		model.addObject("hashMap2",hashMap2);
		model.setViewName("home");
		return model;
	}
	@RequestMapping("/introduction")
	public ModelAndView introduction(ModelAndView model,HttpServletRequest request){
		int productId=Integer.parseInt(request.getParameter("param"));
		model.addObject("commodity", homeServiceImpl.findByproductId(productId));
		model.setViewName("introduction");
		return model;
	}
	/**
	 * 精确查询，根据小类型来查询商品
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/find")
	public ModelAndView findCommodity(ModelAndView model,HttpServletRequest request){
		String name=request.getParameter("name");
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		if(pageNo!=0){
			pageNo=pageNo-1;
		}
		int pagesize=2;
		SmallType smallType=homeServiceImpl.findByTypeName(name);
		@SuppressWarnings("deprecation")
		Page<Commodity> commodities=homeServiceImpl.findBySmallType(smallType,new PageRequest(pageNo, pagesize));
		model.addObject("name", name);
		model.addObject("totalPage", commodities.getTotalPages());
		model.addObject("pageNo", pageNo);
		model.addObject("commodities", commodities);
		model.setViewName("search");
		return model;
	}
	/**
	 * 模糊查询
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/do_search")
	public ModelAndView searchCommodity(ModelAndView model,HttpServletRequest request){
		String name=request.getParameter("name");
		int pageNo=Integer.parseInt(request.getParameter("pageNo"));
		int pageSize=8;
		if(pageNo!=0){
			pageNo=pageNo-1;
		}
		Page<Commodity> commodities;
		//对搜索框的值分别进行大类型、小类型以及商品名查询
		BigType bigType=homeServiceImpl.findByType(name);
		SmallType smallType=homeServiceImpl.findByTypeName(name);
		if(bigType==null&&smallType!=null){
			commodities=homeServiceImpl.findBySmallType(smallType, new PageRequest(pageNo, pageSize));
			model.addObject("commodities",commodities);
			model.addObject("totalPage", commodities.getTotalPages());
			model.addObject("pageNo", pageNo);
		}else if(smallType==null&&bigType!=null){
			commodities=homeServiceImpl.findByBigType(bigType, new PageRequest(pageNo, pageSize));
			model.addObject("commodities", commodities);
			model.addObject("totalPage", commodities.getTotalPages());
			model.addObject("pageNo", pageNo);
		}else if(bigType==null&&smallType==null){
			commodities=homeServiceImpl.findByproductNameContaining(name, new PageRequest(pageNo, pageSize));
			model.addObject("commodities",commodities);
			model.addObject("totalPage", commodities.getTotalPages());
			model.addObject("pageNo", pageNo);
		}
		model.setViewName("realsearch");
		model.addObject("name", name);
		
		return model;
	}
}
