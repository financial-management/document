package org.apache.jsp.pages;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class documentList_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<title>在此处插入标题</title>\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap/css/bootstrap.min.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap-table/src/bootstrap-table.css\">\r\n");
      out.write("<link rel=\"stylesheet\" href=\"tree/themes/default/style.min.css\" />  \r\n");
      out.write("<script src=\"../vendor/jquery/jquery.min.js\"></script>\r\n");
      out.write("<script src=\"tree/jstree.js\"></script>  \r\n");
      out.write("<link rel=\"stylesheet\" href=\"../vendor/bootstrap/css/bootstrap.min.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<form>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"tabbable\" id=\"tabs-146746\">\r\n");
      out.write("\t\t\t\t<div class=\"tab-content\">\r\n");
      out.write("\t\t\t\t\t<div class=\"tab-pane active\" id=\"panel-847015\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-md-2\">\r\n");
      out.write("\t\t\t\t\t\t\t<ul id=\"myTab1\" class=\"nav nav-pills nav-stacked\">\r\n");
      out.write("\t\t\t\t\t          <li >\r\n");
      out.write("\t\t\t\t\t          \t  <div id=\"jstree_div\"></div>  \r\n");
      out.write("\t\t\t\t\t          </li>\r\n");
      out.write("\t\t       \t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-md-10\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tab-content\">\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"tab-pane fade in active\" id='message'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<iframe id=\"menucontent\" src=\"\" scrolling=\"yes\" frameborder=\"0\"  width=\"99%\" height=\"80%\"></iframe>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t  \r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("         $(function() { \r\n");
      out.write("            $('#jstree_div').jstree({  \r\n");
      out.write("                'core' : {  \r\n");
      out.write("                    'check_callback': true,  \r\n");
      out.write("                    \"data\" : function (obj, callback){  \r\n");
      out.write("                            $.ajax({  \r\n");
      out.write("                                url : \"/document/documentTree/authDocumentTree\",  \r\n");
      out.write("                                dataType : \"json\",  \r\n");
      out.write("                                type : \"GET\",  \r\n");
      out.write("                                success : function(data) {  \r\n");
      out.write("                                    //console.info(data); \r\n");
      out.write("                                    if(data) {  \r\n");
      out.write("                                        callback.call(this, data);  \r\n");
      out.write("                                    }else{  \r\n");
      out.write("                                        $(\"#jstree_div\").html(\"暂无数据！\");  \r\n");
      out.write("                                    }  \r\n");
      out.write("                                }  \r\n");
      out.write("                            });  \r\n");
      out.write("                    }  \r\n");
      out.write("                },  \r\n");
      out.write("             //   \"plugins\" : [\"sort\"]  \r\n");
      out.write("            }).bind(\"select_node.jstree\", function(event, data) {  \r\n");
      out.write("                var inst = data.instance;  \r\n");
      out.write("                \r\n");
      out.write("                var selectedNode = inst.get_node(data.selected);\r\n");
      out.write("                toDetail(selectedNode.data);  \r\n");
      out.write("                //$(\"#message\").html(selectedNode.data);\r\n");
      out.write("               \r\n");
      out.write("                //console.info(selectedNode.aria-level);  \r\n");
      out.write("                //var level = $(\"#\"+selectedNode.id).attr(\"aria-level\");  \r\n");
      out.write("                //loadConfig(inst, selectedNode);\r\n");
      out.write("            });  \r\n");
      out.write("        });  \r\n");
      out.write("      \r\n");
      out.write("        function toDetail(data){\r\n");
      out.write("        \t//alert(data.id);\r\n");
      out.write("            x = document.getElementById(\"menucontent\");\r\n");
      out.write("        \tvar opts=data.opreators;\r\n");
      out.write("        \t\r\n");
      out.write("        \t//公共文件夹\r\n");
      out.write("        \t//非个人文档需要控制\r\n");
      out.write("        \t\r\n");
      out.write("        \tif(data.group_type!==\"2\"){\r\n");
      out.write("        \t\t//按钮权限控制    \t\t\t\r\n");
      out.write("        \t\tx.src = \"demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=SUPER&ParentPKField=DIRECTORY_ID&ParentPKValue=\"+data.id+\"&opts=\"+opts;\r\n");
      out.write("        \t}else\r\n");
      out.write("        \t{\r\n");
      out.write("        \t\t//无权限控制\r\n");
      out.write("        \t\tx.src = \"demoSuper.jsp?pageCode=DOC_DOCUMENT_SELECT&menuCode=PERSION&ParentPKField=DIRECTORY_ID&ParentPKValue=\"+data.id;\r\n");
      out.write("        \t}\r\n");
      out.write("        }\r\n");
      out.write("        \r\n");
      out.write("        function loadConfig(inst, selectedNode){  \r\n");
      out.write("            var temp = selectedNode.id;  \r\n");
      out.write("            //inst.open_node(selectedNode);  \r\n");
      out.write("            //alert(temp);  \r\n");
      out.write("            $.ajax({  \r\n");
      out.write("                url : \"/system/copyOfParty/ajax?PARENT_PARTY_CODE='\"+temp+\"' \",  \r\n");
      out.write("                dataType : \"json\",  \r\n");
      out.write("                type : \"GET\",  \r\n");
      out.write("                success : function(data) {  \r\n");
      out.write("                    if(data) { \r\n");
      out.write("                       selectedNode.children = [];  \r\n");
      out.write("                       $.each(data, function (i, item) {  \r\n");
      out.write("                                var obj = {text:item};  \r\n");
      out.write("                                //$('#jstree_div').jstree('create_node', selectedNode, obj, 'last');  \r\n");
      out.write("                                inst.create_node(selectedNode,item,\"last\");  \r\n");
      out.write("                       });  \r\n");
      out.write("                       inst.open_node(selectedNode);  \r\n");
      out.write("                    }else{  \r\n");
      out.write("                        $(\"#jstree_div\").html(\"暂无数据！\");  \r\n");
      out.write("                    }  \r\n");
      out.write("                }  \r\n");
      out.write("            });  \r\n");
      out.write("        }  \r\n");
      out.write("    </script>  \r\n");
      out.write("    \r\n");
      out.write("</body>\r\n");
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
