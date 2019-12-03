package edu.sysu.sdcs.web.util;

import edu.sysu.sdcs.web.entity.User;
import edu.sysu.sdcs.web.exception.SessionTimeoutException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * get user for shiro
 * @author anan
 * @date: Created in 2019/11/26 11:31
 */
public class SubjectUtils {

	public static User getProfile() {
		return getProfile(SecurityUtils.getSubject());
	}

	public static User getProfile(Subject subject) {
    User user = null;
		try {
      /*-- anan -------------------------------------------------------------------------
      |                         C O N S T A N T S                                        |
      |   CustomRealm.doGetAuthorizationInfo()                                           |
      |   SecurityUtils.getSubject().getSession().setAttribute("user", byAccount);       |
      |                                                                                  |
      =====================================================================================*/
      user = (User) subject.getSession().getAttribute("user");
		} catch (Exception e) {
			throw new SessionTimeoutException();
		}
		if (user == null)
			throw new SessionTimeoutException();
		return user;
	}

	public static String getUsername() { return getAccount(SecurityUtils.getSubject()); }

	public static String getAccount(Subject subject) { return getProfile(subject).getAccount(); }

}
