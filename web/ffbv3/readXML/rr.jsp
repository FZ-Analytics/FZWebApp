<!--%@page import="javax.servlet.jsp.JspWriter"%>
<!--%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<!--%@page import="javax.xml.parsers.DocumentBuilder"%>
<!--%@page import="org.w3c.dom.Document"%>
<!--%@page import="org.w3c.dom.NodeList"%>
<!--%@page import="org.w3c.dom.Node"%>
<!--%@page import="org.w3c.dom.Element"%>
<!--%@page import="java.io.File"%-->

<%!
  public void baca(String nmFile) {

    try {
        String s = "";
//        out.println("<p/>nmFile : "+nmFile);
/*
	File fXmlFile = new File(nmFile);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	out.println("Root element :" + doc.getDocumentElement().getNodeName());

	NodeList nList = doc.getElementsByTagName("staff");

	out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);

		out.println("\nCurrent Element :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			out.println("Staff id : " + eElement.getAttribute("id"));
			out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
			out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
			out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
			out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

		}
	}
*/
    } catch (Exception e) {
	e.printStackTrace();
    }
  }

}
%>