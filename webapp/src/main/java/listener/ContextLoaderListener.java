package listener;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import controller.UserInfoListController;
import controller.UserInfoLoginController;
import servlet.UserInfoDaoImpl;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		// 서버가 켜지면 자동 로딩
		try {
			ServletContext sc = event.getServletContext();
			InitialContext initialContext = new InitialContext();
			DataSource datasource = (DataSource)initialContext.lookup("java:comp/env/jdbc/oracle");
			UserInfoDaoImpl userinfoDao = new UserInfoDaoImpl();
			userinfoDao.setDataSource(datasource);
			sc.setAttribute("/userinfo/login.do", new UserInfoLoginController().setUserInfoDao(userinfoDao));
			sc.setAttribute("/userinfo/list.do",
					new UserInfoListController().serUserInfoDao(userinfoDao));
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
}