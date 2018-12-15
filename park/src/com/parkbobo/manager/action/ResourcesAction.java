package com.parkbobo.manager.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.parkbobo.manager.model.Menu;
import com.parkbobo.manager.model.Resources;
import com.parkbobo.manager.service.MenuService;
import com.parkbobo.manager.service.ResourcesService;
import com.parkbobo.utils.PageBean;
import com.sun.org.apache.bcel.internal.generic.NEW;
@Controller("resourcesAction")
@Scope("prototype")
public class ResourcesAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2793709868335916229L;
	private Resources resources;
	private PageBean<Resources> resourcesPage;
	private Long id;
	private List<Menu> menuList;
	
	@Resource(name="resourcesService")
	private ResourcesService resourcesService;
	@Resource(name="menuService")
	private MenuService menuService;
	/**
	 * 添加资源权限
	 * @return
	 */
	public String add()
	{
		if(getMethod().equals("add"))
		{
			resources.setIsopen((short)1);
			resources.setIsleaf((short)1);
			resources.setResourcetype("0");
			resources.setCreateTime(resources.getCreateTimeToDate().getTime());
			resourcesService.add(resources);
			return forward("/resources_list");
		}
		else
		{
			menuList = menuService.getAll();
			return "add";
		}
	}
	/**
	 * 编辑资源权限
	 * @return
	 */
	public String edit()
	{
		if(getMethod().equals("edit"))
		{
			resources.setIsopen((short)1);
			resources.setIsleaf((short)1);
			resources.setResourcetype("0");
			resources.setCreateTime(resources.getCreateTimeToDate().getTime());
			resourcesService.add(resources);
			return forward("/resources_list");
		}
		else
		{
			menuList = menuService.getAll();
			resources = resourcesService.getById(id);
			return "edit";
		}
	}
	/**
	 * 资源权限列表
	 * @return
	 */
	public String list()
	{
		menuList = menuService.getAll();
		String hql = "from Resources as r where 1=1 ";
		if(resources != null)
		{
			if(resources.getName() != null && !resources.getName().equals(""))
			{
				hql += " and r.name like '%" + resources.getName().trim() + "%'";
			}
			if(resources.getEnable() != null)
			{
				hql += " and r.enable = " + resources.getEnable();
			}
			if(resources.getMenu() != null && resources.getMenu().getMenuId() != null)
			{
				hql += " and r.menu.menuId = " + resources.getMenu().getMenuId();
			}
		}
		hql += " order by r.createTime desc";
		resourcesPage = resourcesService.loadPage(hql, getPageSize(), getPage());
		return "list";
	}
	/**
	 * 资源权限删除
	 * @return
	 */
	public String delete()
	{
		resourcesService.bulkDelete(getIds());
		return forward("/resources_list");
	}
	public String addBulkResources() {
		List resourcesList = new ArrayList<Resources>();
		//应用管理
		Menu appManageMenu = menuService.getById(8l);
		//停车场
		Menu carparkManageMenu = menuService.getById(30l);
		
//		resourcesList.add(new Resources(appManageMenu,"订单管理列表","berthOrder_list","/berthOrder_list"));
//		resourcesList.add(new Resources(appManageMenu,"订单管理删除","berthOrder_delete","/berthOrder_delete"));
		
//		resourcesList.add(new Resources(appManageMenu,"预约停车列表","orderTask_list","/berthOrder_list"));
//		resourcesList.add(new Resources(appManageMenu,"预约停车删除","orderTask_delete","/orderTask_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"充值记录列表","userBalance_list","/userBalance_list"));
//		resourcesList.add(new Resources(appManageMenu,"充值记录删除","userBalance_delete","/userBalance_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"提现列表","withdraw_list","/withdraw_list"));
//		resourcesList.add(new Resources(appManageMenu,"提现删除","withdraw_delete","/withdraw_delete"));
//		resourcesList.add(new Resources(appManageMenu,"提现审核","withdraw_check","/withdraw_check"));
//		
//		resourcesList.add(new Resources(appManageMenu,"车主认证列表","driverAuth_list","/driverAuth_list"));
//		resourcesList.add(new Resources(appManageMenu,"车主认证删除","driverAuth_delete","/driverAuth_delete"));
//		resourcesList.add(new Resources(appManageMenu,"车主认证审核","driverAuth_check","/driverAuth_check"));
//		
//		resourcesList.add(new Resources(appManageMenu,"车位认证列表","carparkAuthentication_list","/carparkAuthentication_list"));
//		resourcesList.add(new Resources(appManageMenu,"车位认证删除","carparkAuthentication_delete","/carparkAuthentication_delete"));
//		resourcesList.add(new Resources(appManageMenu,"车位认证审核","carparkAuthentication_check","/carparkAuthentication_check"));
//		
//		resourcesList.add(new Resources(appManageMenu,"车位分享列表","berthShare_list","/berthShare_list"));
//		resourcesList.add(new Resources(appManageMenu,"车位分享删除","berthShare_delete","/berthShare_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"积分明细列表","userPoint_list","/userPoint_list"));
//		resourcesList.add(new Resources(appManageMenu,"积分明细删除","userPoint_delete","/userPoint_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"信誉明细列表","userCredit_list","/userCredit_list"));
//		resourcesList.add(new Resources(appManageMenu,"信誉明细删除","userCredit_delete","/userCredit_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"邀请码列表","invitecode_list","/invitecode_list"));
//		resourcesList.add(new Resources(appManageMenu,"邀请码删除","invitecode_delete","/invitecode_delete"));
//		
//		resourcesList.add(new Resources(appManageMenu,"APP活动列表","appActivity_list","/appActivity_list"));
//		resourcesList.add(new Resources(appManageMenu,"APP活动删除","appActivity_delete","/appActivity_delete"));
//		resourcesList.add(new Resources(appManageMenu,"APP活动添加","appActivity_add","/appActivity_add"));
//		resourcesList.add(new Resources(appManageMenu,"APP活动修改","appActivity_update","/appActivity_update"));
//		
//		resourcesList.add(new Resources(appManageMenu,"系统通知列表","notify_list","/notify_list"));
//		resourcesList.add(new Resources(appManageMenu,"系统通知删除","notify_delete","/notify_delete"));
//		resourcesList.add(new Resources(appManageMenu,"系统通知发送","notify_add","/notify_add"));
//		
//		resourcesList.add(new Resources(appManageMenu,"违约处理列表","defaultApply_list","/defaultApply_list"));
//		resourcesList.add(new Resources(appManageMenu,"违约处理删除","defaultApply_delete","/defaultApply_delete"));
//		resourcesList.add(new Resources(appManageMenu,"违约处理审核","defaultApply_check","/defaultApply_check"));
//		
//		resourcesList.add(new Resources(appManageMenu,"操作日志列表","optLogs_list","/optLogs_list"));
//		resourcesList.add(new Resources(appManageMenu,"操作日志删除","optLogs_delete","/optLogs_delete"));
//		//////////////////////////
//		resourcesList.add(new Resources(carparkManageMenu,"停车场车位列表","carparkBerthPolygon_list","/carparkBerthPolygon_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场车位删除","carparkBerthPolygon_delete","/carparkBerthPolygon_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场车位添加","carparkBerthPolygon_add","/carparkBerthPolygon_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场车位修改","carparkBerthPolygon_update","/carparkBerthPolygon_update"));
//		
	

//		
//		resourcesList.add(new Resources(carparkManageMenu,"标注分类列表","carparkMarkerCategory_list","/carparkMarkerCategory_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"标注分类删除","carparkMarkerCategory_delete","/carparkMarkerCategory_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"标注分类添加","carparkMarkerCategory_add","/carparkMarkerCategory_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"标注分类修改","carparkMarkerCategory_update","/carparkMarkerCategory_update"));
//		
//		resourcesList.add(new Resources(carparkManageMenu,"停车场标注列表","carparkMarkerPoint_list","/carparkMarkerPoint_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场标注删除","carparkMarkerPoint_delete","/carparkMarkerPoint_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场标注添加","carparkMarkerPoint_add","/carparkMarkerPoint_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场标注修改","carparkMarkerPoint_update","/carparkMarkerPoint_update"));
//		
//		resourcesList.add(new Resources(carparkManageMenu,"停车场辅助图层列表","carparkFuzhuPolygon_list","/carparkFuzhuPolygon_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场辅助图层删除","carparkFuzhuPolygon_delete","/carparkFuzhuPolygon_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场辅助图层添加","carparkFuzhuPolygon_add","/carparkFuzhuPolygon_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场辅助图层修改","carparkFuzhuPolygon_update","/carparkFuzhuPolygon_update"));
//		
//		resourcesList.add(new Resources(carparkManageMenu,"停车场外部道路列表","carparkCityPolyline_list","/carparkCityPolyline_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场外部道路删除","carparkCityPolyline_delete","/carparkCityPolyline_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场外部道路添加","carparkCityPolyline_add","/carparkCityPolyline_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场外部道路修改","carparkCityPolyline_update","/carparkCityPolyline_update"));
//		
//		resourcesList.add(new Resources(carparkManageMenu,"停车场背景底图列表","carparkBackgroundPolygon_list","/carparkBackgroundPolygon_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场背景底图删除","carparkBackgroundPolygon_delete","/carparkBackgroundPolygon_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场背景底图添加","carparkBackgroundPolygon_add","/carparkBackgroundPolygon_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场背景底图修改","carparkBackgroundPolygon_update","/carparkBackgroundPolygon_update"));
//		
//		resourcesList.add(new Resources(carparkManageMenu,"停车场商场房间列表","carparkShopPolygon_list","/carparkShopPolygon_list"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场商场房间删除","carparkShopPolygon_delete","/carparkShopPolygon_delete"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场商场房间添加","carparkShopPolygon_add","/carparkShopPolygon_add"));
//		resourcesList.add(new Resources(carparkManageMenu,"停车场商场房间修改","carparkShopPolygon_update","/carparkShopPolygon_update"));
		resourcesService.bulkAdd(resourcesList);
		return null;
	}
	public String addBulkMenu() {
		List menus = new ArrayList<Menu>();
	
//		Menu menu = new Menu(null, "停车场管理", (short)0);
//		menuService.add(menu);
//		menus.add(new Menu(menu, "停车场车位", (short)1));
//		menus.add(new Menu(menu, "停车场出入口", (short)1));
//		menus.add(new Menu(menu, "标注分类", (short)1));
//		menus.add(new Menu(menu, "停车场标注", (short)1));
//		menus.add(new Menu(menu, "停车场辅助图层", (short)1));
//		menus.add(new Menu(menu, "停车场外部道路", (short)1));
//		menus.add(new Menu(menu, "停车场背景底图", (short)1));
//		menus.add(new Menu(menu, "停车场商场房间", (short)1));
		
//		Menu appManageMenu = menuService.getById(8l);
//		
//		menus.add(new Menu(appManageMenu, "订单管理", (short)1));
//		menus.add(new Menu(appManageMenu, "预约停车", (short)1));
//		menus.add(new Menu(appManageMenu, "充值记录", (short)1));
//		menus.add(new Menu(appManageMenu, "提现", (short)1));
//		menus.add(new Menu(appManageMenu, "车主认证", (short)1));
//		menus.add(new Menu(appManageMenu, "车位认证", (short)1));
//		menus.add(new Menu(appManageMenu, "车位分享", (short)1));
//		menus.add(new Menu(appManageMenu, "积分明细", (short)1));
//		menus.add(new Menu(appManageMenu, "信誉明细", (short)1));
//		menus.add(new Menu(appManageMenu, "邀请码", (short)1));
//		menus.add(new Menu(appManageMenu, "App活动", (short)1));
//		menus.add(new Menu(appManageMenu, "系统通知", (short)1));
//		menus.add(new Menu(appManageMenu, "违约处理申请", (short)1));
//		menus.add(new Menu(appManageMenu, "意见反馈", (short)1));
//		menus.add(new Menu(appManageMenu, "操作日志", (short)1));
//		
		Menu appManageMenu = menuService.getById(30l);
//			Menu menu = new Menu(appManageMenu, "停车场内部道路", (short)1);
//			menu.getResourceses().add(new Resources(menu,"停车场内部道路列表","carparkRoadPolyline_list","/carparkRoadPolyline_list"));
//			menu.getResourceses().add(new Resources(menu,"停车场内部道路添加","carparkRoadPolyline_toAdd","/carparkRoadPolyline_toAdd"));
//			menu.getResourceses().add(new Resources(menu,"停车场内部道路修改","carparkRoadPolyline_toUpdate","/carparkRoadPolyline_toUpdate"));
//			menu.getResourceses().add(new Resources(menu,"停车场内部道路删除","carparkRoadPolyline_delete","/carparkRoadPolyline_delete"));
//			menus.add(menu);
//		menus.add(new Menu(appManageMenu, "停车场导航路线", (short)1));
//			menu1.getResourceses().add(new Resources(menu1,"停车场导航路线列表","carparkNavigationPolyline_list","/carparkNavigationPolyline_list"));
//			menu1.getResourceses().add(new Resources(menu1,"停车场导航路线添加","carparkNavigationPolyline_toAdd","/carparkNavigationPolyline_toAdd"));
//			menu1.getResourceses().add(new Resources(menu1,"停车场导航路线修改","carparkNavigationPolyline_toUpdate","/carparkNavigationPolyline_toUpdate"));
//			menu1.getResourceses().add(new Resources(menu1,"停车场导航路线删除","carparkNavigationPolyline_delete","/carparkNavigationPolyline_delete"));
//			menus.add(menu1);
//		Menu menu2 = new Menu(appManageMenu, "停车场导航点", (short)1);
//			menu2.getResourceses().add(new Resources(menu2,"停车场导航点列表","carparkNavigationPoint_list","/carparkNavigationPoint_list"));
//			menu2.getResourceses().add(new Resources(menu2,"停车场导航点添加","carparkNavigationPoint_toAdd","/carparkNavigationPoint_toAdd"));
//			menu2.getResourceses().add(new Resources(menu2,"停车场导航点修改","carparkNavigationPoint_toUpdate","/carparkNavigationPoint_toUpdate"));
//			menu2.getResourceses().add(new Resources(menu2,"停车场导航点删除","carparkNavigationPoint_delete","/carparkNavigationPoint_delete"));
//			menus.add(menu2);
//		Menu menu3 = new Menu(appManageMenu, "停车场高度模型图层", (short)1);
//			menu3.getResourceses().add(new Resources(menu3,"停车场高度模型图层列表","carparkModelPolygon_list","/carparkModelPolygon_list"));
//			menu3.getResourceses().add(new Resources(menu3,"停车场高度模型图层添加","carparkModelPolygon_toAdd","/carparkModelPolygon_toAdd"));
//			menu3.getResourceses().add(new Resources(menu3,"停车场高度模型图层修改","carparkModelPolygon_toUpdate","/carparkModelPolygon_toUpdate"));
//			menu3.getResourceses().add(new Resources(menu3,"停车场高度模型图层删除","carparkModelPolygon_delete","/carparkModelPolygon_delete"));
//			menus.add(menu3);
		Menu menu3 = new Menu(appManageMenu, "停车场分类", (short)1);
		menu3.getResourceses().add(new Resources(menu3,"停车场分类列表","carparkCategory_list","/carparkCategory_list"));
		menu3.getResourceses().add(new Resources(menu3,"停车场分类添加","carparkCategory_toAdd","/carparkCategory_toSave"));
		menu3.getResourceses().add(new Resources(menu3,"停车场分类修改","carparkCategory_toUpdate","/carparkCategory_toSave"));
		menu3.getResourceses().add(new Resources(menu3,"停车场分类删除","carparkCategory_delete","/carparkCategory_delete"));
		menus.add(menu3);
		Menu menu4 = new Menu(appManageMenu, "停车场楼层", (short)1);
		menu4.getResourceses().add(new Resources(menu3,"停车场楼层列表","carparkFloor_list","/carparkFloor_list"));
		menu4.getResourceses().add(new Resources(menu3,"停车场楼层添加","carparkFloor_toAdd","/carparkFloor_toSave"));
		menu4.getResourceses().add(new Resources(menu3,"停车场楼层修改","carparkFloor_toUpdate","/carparkFloor_toSave"));
		menu4.getResourceses().add(new Resources(menu3,"停车场楼层删除","carparkFloor_delete","/carparkFloor_delete"));
		menus.add(menu4);
		menuService.bulkAdd(menus);
		return null;
	}
	
	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public PageBean<Resources> getResourcesPage() {
		return resourcesPage;
	}

	public void setResourcesPage(PageBean<Resources> resourcesPage) {
		this.resourcesPage = resourcesPage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	@Override
	public String logModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
