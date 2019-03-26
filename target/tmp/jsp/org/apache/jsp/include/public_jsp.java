package org.apache.jsp.include;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.yonyou.util.theme.ThemePath;

public final class public_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!-- bootstrapIE8Ã¨Â¡Â¥Ã¤Â¸Â -->\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/vendor/bootstrap/js/html5.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/vendor/bootstrap/js/respond.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->\r\n");
      out.write("<!--[if lt IE 9]>\r\n");
      out.write("  <script src=\"http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js\"></script>\r\n");
      out.write("  <script src=\"http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js\"></script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap/css/bootstrap.min.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap-table/src/bootstrap-table.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap/css/bootstrap-datetimepicker.min.css\" media=\"screen\">\r\n");
      out.write("<!-- <link rel=\"stylesheet\" href=\"../vendor/bootstrap-fileinput/css/fileinput.min.css\"  media=\"all\"> -->\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(ThemePath.findPath(request, ThemePath.SUB_SYSTEM_CSS));
      out.write("\">\r\n");
      out.write("<script src=\"../vendor/jquery/jquery.min.js\"></script>\r\n");
      out.write("<script src=\"../vendor/bootstrap/js/bootstrap.min.js\"></script>\r\n");
      out.write("<script src=\"../vendor/bootstrap-table/src/bootstrap-table.js\"></script>\r\n");
      out.write("<!-- <script src=\"../vendor/bootstrap-fileinput/js/fileinput.js\"></script> -->\r\n");
      out.write("<!-- <script src=\"../vendor/bootstrap-fileinput/js/locales/zh.js\"></script> -->\r\n");
      out.write("<script src=\"../vendor/bootstrap/js/bootstrap-datetimepicker.js\" charset=\"UTF-8\"></script>\r\n");
      out.write("<script src=\"../vendor/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js\" charset=\"UTF-8\"></script>\r\n");
      out.write("<script src=\"../pages/js/reference.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("var isBuildButtonByFile = true ; //Ã¦ÂÂ¯Ã¥ÂÂ¦Ã¥Â¼ÂÃ¥ÂÂ¯jsÃ¦ÂÂÃ¤Â»Â¶Ã¨Â°ÂÃ¨Â¯ÂÃ¦Â¨Â¡Ã¥Â¼Â,true :jsÃ¦ÂÂÃ¤Â»Â¶Ã¨ÂÂ·Ã¥ÂÂÃ¦ÂÂÃ©ÂÂ®  Ã¯Â¼Â false:Ã¦ÂÂ°Ã¦ÂÂ®Ã¥ÂºÂÃ¨ÂÂ·Ã¥ÂÂÃ¥Â·Â²Ã¦ÂÂÃ¦ÂÂÃ¦ÂÂÃ©ÂÂ®Ã¯Â¼Â\r\n");
      out.write("var context = '");
      out.print(request.getContextPath());
      out.write("';\r\n");
      out.write("var dataSourceCode ='");
      out.print(request.getParameter("pageCode"));
      out.write("';\r\n");
      out.write("var pageName ='");
      out.print(request.getParameter("pageName"));
      out.write("';\r\n");
      out.write("var menuCode ='");
      out.print(request.getParameter("menuCode")!=null?request.getParameter("menuCode"):"");
      out.write("';\r\n");
      out.write("var rolecode='';\r\n");
      out.write("var corpcode='';\r\n");
      out.write("var token='");
      out.print(request.getParameter("token")!=null?request.getParameter("token"):"");
      out.write("';\r\n");
      out.write("\r\n");
      out.write("var isNewModel = false;\r\n");
      out.write("\r\n");
      out.write("var _dataSourceCode='");
      out.print( request.getParameter("_dataSourceCode"));
      out.write("';\r\n");
      out.write("var ParentId ='ID';\r\n");
      out.write("var ParentPKField='");
      out.print(request.getParameter("ParentPKField")!=null?request.getParameter("ParentPKField"):"");
      out.write("';\r\n");
      out.write("var ParentPKValue='");
      out.print(request.getParameter("ParentPKValue")!=null?request.getParameter("ParentPKValue"):"");
      out.write("';\r\n");
      out.write("var _query_param='';\r\n");
      out.write("if(ParentPKField!=null&&ParentPKField.length>0){\r\n");
      out.write("\t_query_param =pageParamFormat(ParentPKField+\" = \"+ParentPKValue);\r\n");
      out.write("}\t\r\n");
      out.write("var query_buttonToken ='';\r\n");
      out.write("$('input[name=\"ParentPK\"]').attr('id',ParentPKField).val(ParentPKValue);\r\n");
      out.write("function pageParamFormat(p){\r\n");
      out.write("\tif(p!=null&p.length>0){\r\n");
      out.write("\t\treturn \"&pageParam=\"+\"'\"+p+\"'\";\r\n");
      out.write("\t}else{\r\n");
      out.write("\t\treturn \"\";\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("var cacheTableJsonArray =null;\r\n");
      out.write("\r\n");
      out.write("var initTableParam='");
      out.print(request.getParameter("initTableParam")!=null?request.getParameter("initTableParam").replaceAll(",", "&"):"");
      out.write("';\r\n");
      out.write("if(initTableParam!=null&&initTableParam.length>0){\r\n");
      out.write("\tinitTableParam=\"&\"+initTableParam;\r\n");
      out.write("}else{\r\n");
      out.write("\tinitTableParam =\"\";\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("<script src=\"../js/public.js\"></script>\r\n");
      out.write("<script src=\"../js/properties.js\"></script>\r\n");
      out.write("<script src=\"../js/crud.js\"></script>\r\n");
      out.write("<script src=\"../js/bootTable.js\"></script>\r\n");
      out.write("<script src=\"../js/common.js\"></script>\r\n");
      out.write("<script src=\"../js/initPage.js\"></script>\r\n");
      out.write("<script src=\"../js/buttonJs.js\"></script>\r\n");
      out.write("<script src=\"../js/validation.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<input type=\"hidden\" id=\"ins_or_up_buttontoken\"/>\r\n");
      out.write("<input type=\"hidden\" id=\"query_buttontoken\" value=\"query\"/>\r\n");
      out.write("<input type=\"hidden\" id=\"cache_dataSourceCode\"/>\r\n");
      out.write("<input type=\"hidden\" id=\"directoryid\" value=\"");
      out.print(request.getParameter("ParentPKValue")!=null?request.getParameter("ParentPKValue"):"");
      out.write("\"/>\r\n");
      out.write("\r\n");
      out.write("<link href=\"../vendor/bootstrap/css/bootstrap.css\" rel=\"stylesheet\" />\r\n");
      out.write("<link href=\"../vendor/Bootstrap-icon-picker/css/icon-picker.css\" rel=\"stylesheet\" /> \r\n");
      out.write("<script src=\"../vendor/Bootstrap-icon-picker/js/iconPicker.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"../pages/js/multiselectDemo.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../pages/vailidator/css/bootstrapValidator.css\"/>\r\n");
      out.write("<script type=\"text/javascript\" src=\"../pages/vailidator/js/bootstrapValidator.js\"></script>");
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
