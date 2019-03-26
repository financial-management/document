package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.yonyou.web.CreateButton;
import com.yonyou.business.entity.TokenUtil;
import com.yonyou.business.entity.TokenEntity;
import java.io.Serializable;

public final class buttonjs_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");

String userId 	= request.getParameter("userId") ; 
//String companyId= TokenUtil.initTokenEntity(request).COMPANY.getCompany_id();
String companyId=  request.getParameter("companyId") ; 
String muneCode = request.getParameter("totalCode") ; 
String pageCode = request.getParameter("pageCode") ; 
if("".equals(companyId)|| null ==companyId){
	companyId = TokenUtil.initTokenEntity(request).COMPANY.getCompany_id();
}
if("".equals(userId)|| null ==userId){
	userId = TokenUtil.initTokenEntity(request).USER.getId();
}
//<%=new CreateButton().getButtonInfo(userId,companyId,muneCode,pageCode)

      out.write("\r\n");
      out.write("</script> \t\t");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
