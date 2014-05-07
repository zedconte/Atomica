package com.industrika.sie.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.industrika.commons.businesslogic.ApplicationContextProvider;
import com.industrika.commons.businesslogic.MenuOptions;
import com.industrika.commons.dto.Module;
import com.industrika.commons.dto.User;
import com.industrika.commons.dao.UserDao;
import com.industrika.sie.delegate.BusinessDelegate;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("signedUser");
		String formName = request.getParameter("formName");
		if (formName == null || formName.equalsIgnoreCase("index")){
			request.getRequestDispatcher("index.jsp").include(request, response);
		} else if (formName.equalsIgnoreCase("login")){
			try{
				List<Module> modules = (List<Module>) request.getSession().getAttribute("menuModules");
				if (modules == null || user == null){
					loadModules(request);
				}
				user=((UserDao)ApplicationContextProvider.getCtx().getBean("userDaoHibernate")).authenticateUser(request.getParameter("sieuserid"), request.getParameter("sieuserpass"));
				if (user != null){
					request.getSession().setAttribute("signedUser", user);
				} else {
					request.getSession().setAttribute("errorLogin", "Las credenciales son inv&aacute;lidas");
				}
				request.getRequestDispatcher("index.jsp").include(request, response);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		} else if (formName.equalsIgnoreCase("logout")){
			request.getSession().removeAttribute("signedUser");
			request.getSession().removeAttribute("menuModules");
			request.getRequestDispatcher("index.jsp").include(request, response);			
		} else {
			if (user != null){
				String bdResponse = BusinessDelegate.delegate(formName, request);
				request.getSession().setAttribute("formcall", formName);
				request.getRequestDispatcher(bdResponse).include(request, response);
				request.getRequestDispatcher("security.jsp").include(request, response);
			} else {
				request.getSession().removeAttribute("signedUser");
				request.getSession().removeAttribute("menuModules");
				request.getSession().setAttribute("errorsesion", "Su sesi&oacute;n ha caducado, favor de volver a entrar al sistema");
				request.getRequestDispatcher("sessionclose.jsp").include(request, response);
			}
		}
	}

	private void loadModules(HttpServletRequest request){
		Properties delegates= new Properties();
		try{
			delegates.load(BusinessDelegate.class.getResourceAsStream("delegates.properties"));
			List<Module> modules = new ArrayList<Module>();
			Set<Object> keys = delegates.keySet();
			if (keys != null && keys.size() > 0){
				List<String> list = new ArrayList<String>();
				for (Object key : keys){
					list.add(key.toString());
				}
				Collections.sort(list);
				for (String key:list){
					try{
						MenuOptions mo = (MenuOptions) ApplicationContextProvider.getCtx().getBean(delegates.getProperty(key));
						modules.add(mo.getModule());
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
			Collections.sort(modules);
			request.getSession().setAttribute("menuModules", modules);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
