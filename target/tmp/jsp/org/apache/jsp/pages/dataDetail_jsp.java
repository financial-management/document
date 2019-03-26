package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dataDetail_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=UTF-8");
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
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"zh-cn\">\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta charset=\"utf-8\">\r\n");
      out.write("\t\t<title>单表模板</title>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<form class=\"form-horizontal\">\r\n");
      out.write("\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" id=\"bulidTable\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-heading\" id ='pageName'>\r\n");
      out.write("\t\t\t\t\t\t\t菜单管理\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"panel panel-default\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- \r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"save(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-saved\" aria-hidden=\"true\"></span>保存\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"back(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-ban-circle\" aria-hidden=\"true\"></span>返回\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel-heading\" >\r\n");
      out.write("\t\t\t\t\t\t\t\t\t信息\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- 维护页面-->\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"panel-body\" id=\"superInsertPage\">\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div id=\"toolbar\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- 按钮页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t\t<!-- <button type=\"button\" class=\"btn btn-info\" onclick=\"window.history.back(-1);\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-ban-circle\" aria-hidden=\"true\"></span>返回主表\r\n");
      out.write("\t\t\t\t\t\t\t\t</button> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id='button_div' buttonType='super'></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id='button_child_div' buttonType='child'></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id='button_child_div_001' >\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- 列表页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"table\"></table>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"panel-body\" id=\"bulidPage\" style=\"display: none\">\r\n");
      out.write("\t\t\t\t\t<div class=\"panel panel-primary\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t\t\t\t新增\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"save(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-saved\" aria-hidden=\"true\"></span>保存\r\n");
      out.write("\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" onclick=\"back(this)\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-ban-circle\" aria-hidden=\"true\"></span>返回\r\n");
      out.write("\t\t\t\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<!-- 维护页面 -->\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"panel-body\" id=\"insPage\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<!--主表主键  用于 新增、修改-->\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type='hidden' name=\"subCode\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input type='hidden' name=\"ParentPK\">\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\t\t\r\n");
      out.write("\t</form>\r\n");
      out.write("\t\t<!-- 缓存参数 -->\r\n");
      out.write("\t\t<!-- 是否为修改页面 -->\r\n");
      out.write("\t\t</body>\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "../include/public.jsp", out, false);
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t  $(function() {\r\n");
      out.write("\t\t\t    var mainPageParam =pageParamFormat(ParentId +\" =\"+ParentPKValue);\r\n");
      out.write("\t\t\t\tvar Request = new Object(); \r\n");
      out.write("\t\t\t\tRequest = GetRequest();\r\n");
      out.write("\t\t\t\tvar isReadonly = Request['isReadonly'];\r\n");
      out.write("\t\t\t    /*if(isReadonly=='1'){\r\n");
      out.write("\t\t\t    \tmainPageParam=mainPageParam+\"&showType=text\";\r\n");
      out.write("\t\t\t    }*/\r\n");
      out.write("\t\t\t\tbulidMaintainPage($(\"#superInsertPage\"),dataSourceCode,mainPageParam);\r\n");
      out.write("\t\t\t\tbulidMaintainPage($(\"#insPage\"),_dataSourceCode,mainPageParam);\r\n");
      out.write("\t\t\t\tbulidButton($(\"#button_div\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidButton($(\"#button_child_div\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidButton($(\"#button_child_div_001\"),_dataSourceCode,'');\r\n");
      out.write("\t\t\t\t//bulidListPage($(\"#table\"),_dataSourceCode,_query_param);\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t//alert(isReadonly);\r\n");
      out.write("\t\t\t\tif(isReadonly=='1'){\r\n");
      out.write("\t\t\t\t\t//setReadonlyByDiv($(\"#superInsertPage input[type='text']\"));\r\n");
      out.write("\t\t\t\t\t$(\"#superInsertPage\").find(\"[id]\").each(function(){\r\n");
      out.write("\t\t\t\t\t\t$(this).attr(\"disabled\",true);\r\n");
      out.write("\t\t\t\t\t});\r\n");
      out.write("\t\t\t\t}else if(isReadonly=='0'){\r\n");
      out.write("\t\t\t\t\t$(\"#superInsertPage\").find(\"[id='TOTAL_CODE']\").attr(\"disabled\",true);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t  });\r\n");
      out.write("\t\t\r\n");
      out.write("\t//重写back方法\r\n");
      out.write("\tfunction back(t){\r\n");
      out.write("\t\ttog(t);\r\n");
      out.write("\t\t//排除主表主键\r\n");
      out.write("\t\t$inspage.find('[id]').not('#'+ParentPKField).val(\"\");\r\n");
      out.write("\t\t$(\"#ins_or_up_buttontoken\").val(\"\");\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("</html>");
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
